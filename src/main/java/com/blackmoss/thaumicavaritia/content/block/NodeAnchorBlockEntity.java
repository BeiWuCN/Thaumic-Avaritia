// 节点锚 BE：每 tick 检测上方/下方节点与涌流充能器，直接通过节点 public API 控制其状态。
// 布局分两种（充能器始终在锚上方）：
//   1) 爪朝上：锚 / 节点(above) / 充能器(above 2) → 节点在上方，充能器在节点上方。
//   2) 爪朝下：充能器(above) / 锚 / 节点(below) → 节点在下方，充能器在锚正上方。
// - 充能器激活（无非红石）：完全原版化——调用 node.setEnergized(true) 进入换能器充能态：
//   节点要素分解为原初、每 tick 从灵气自动补充、可被魔杖抽取 vis、客户端显示蓝色脉冲。
// - 否则（锁定模式）：冻结节点当前状态（放进来什么样就一直是什么样），使节点自身 tick 的
//   喂料/衰减/放电/行为无法改变它；若玩家用魔杖抽走部分 vis（总量下降），则重新以当前状态为快照再锁定。
package com.blackmoss.thaumicavaritia.content.block;

import com.blackmoss.thaumicavaritia.registry.TABlockEntities;
import com.leclowndu93150.thaumaturge.api.aspect.AspectList;
import com.leclowndu93150.thaumaturge.api.nodes.NodeModifier;
import com.leclowndu93150.thaumaturge.api.nodes.NodeType;
import com.leclowndu93150.thaumaturge.content.aura.node.BlockEntityNode;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jspecify.annotations.Nullable;

public class NodeAnchorBlockEntity extends BlockEntity {
    private @Nullable Snapshot snapshot;
    private boolean charged;
    private boolean locked;

    public NodeAnchorBlockEntity(BlockPos pos, BlockState state) {
        super(TABlockEntities.NODE_ANCHOR.get(), pos, state);
    }

    public void serverTick(Level tickLevel, BlockPos pos) {
        Level level = this.level;
        if (level == null) {
            return;
        }
        BlockEntity above = level.getBlockEntity(pos.above());
        BlockEntity below = level.getBlockEntity(pos.below());
        boolean redstone = level.hasNeighborSignal(pos);
        BlockEntityNode node;
        boolean hasCharger;
        if (above instanceof BlockEntityNode n) {
            node = n;
            hasCharger = !redstone && hasChargerAbove(level, pos);
        } else if (below instanceof BlockEntityNode n) {
            node = n;
            hasCharger = !redstone
                    && level.getBlockState(pos.above(1)).getBlock() instanceof InfinityChargerBlock;
        } else {
            snapshot = null;
            charged = false;
            locked = false;
            return;
        }

        // 充能模式翻转 CHARGED 状态（仅变化时写，触发模型切换）。
        if (getBlockState().getValue(NodeAnchorBlock.CHARGED) != hasCharger) {
            level.setBlock(pos, getBlockState().setValue(NodeAnchorBlock.CHARGED, hasCharger), 3);
        }

        if (hasCharger) {
            if (!charged) {
                // 进入充能：保存当前状态供退出时恢复，然后调用原版 setEnergized(true)——
                // 节点分解为原初要素、自动从灵气补充、可被魔杖抽取 vis、客户端蓝色脉冲。
                // setEnergized 内部已 setChanged + sendBlockUpdated 同步；类型/修饰变化再补一次。
                snapshot = new Snapshot(node);
                node.setEnergized(true);
                node.setNodeType(NodeType.NORMAL);
                node.setNodeModifier(null);
                node.nodeChange();
                charged = true;
            }
            locked = false;
        } else {
            // 退出充能：解除 energized（恢复原始 aspectsBase），并用充能前快照还原状态后冻结。
            if (charged) {
                node.setEnergized(false);
                charged = false;
            }
            // 锁定模式：冻结节点当前状态。
            if (snapshot == null) {
                snapshot = new Snapshot(node);
            } else if (node.getAspects().totalAmount() < snapshot.aspects.totalAmount()) {
                // 玩家抽走部分 vis → 以当前状态重新锁定。
                snapshot = new Snapshot(node);
            }
            if (!locked
                    || node.getNodeType() != snapshot.type
                    || node.getNodeModifier() != snapshot.modifier
                    || node.getAspects().totalAmount() != snapshot.aspects.totalAmount()) {
                node.setAspects(snapshot.aspects);
                node.setNodeType(snapshot.type);
                node.setNodeModifier(snapshot.modifier);
                // 通知客户端节点状态已变（setAspects 等只改服务端内存，不主动同步）。
                node.nodeChange();
                locked = true;
            }
        }
    }

    // 上方两格是否为充能来源：直接是涌流充能器，或是一个已处于充能状态的节点锚（级联充能）。
    private static boolean hasChargerAbove(Level level, BlockPos pos) {
        BlockState above2 = level.getBlockState(pos.above(2));
        if (above2.getBlock() instanceof InfinityChargerBlock) {
            return true;
        }
        return above2.getBlock() instanceof NodeAnchorBlock
                && above2.getValue(NodeAnchorBlock.CHARGED);
    }

    // 冻结快照：捕获节点当前的要素 / 类型 / 修饰。
    private static final class Snapshot {
        final AspectList aspects;
        final NodeType type;
        final @Nullable NodeModifier modifier;

        Snapshot(BlockEntityNode node) {
            this.aspects = node.getAspects();
            this.type = node.getNodeType();
            this.modifier = node.getNodeModifier();
        }
    }
}
