// 节点锚 BE：每 tick 检测上方节点与上方两格的涌流充能器，直接通过节点 public API 控制其状态。
// 布局（自顶向下）：涌流充能器 / 节点锚 / 节点 / 节点锚，即锚正上方一格是节点、上方两格是充能器。
// - 充能器在上两格（无非红石）：将节点每种要素固定为 256（6 原初 + infinitus），并置为普通节点。
// - 否则（锁定模式）：冻结上方节点当前状态，使节点自身 tick 的喂料/衰减/放电/行为无法改变它；
//   若玩家用魔杖抽走部分 vis（总量下降），则重新以当前状态为快照再锁定。
package com.blackmoss.thaumicavaritia.content.block;

import com.blackmoss.thaumicavaritia.api.aspect.TAAspects;
import com.blackmoss.thaumicavaritia.registry.TABlockEntities;
import com.leclowndu93150.thaumaturge.api.aspect.AspectInstance;
import com.leclowndu93150.thaumaturge.api.aspect.AspectList;
import com.leclowndu93150.thaumaturge.api.aspect.IAspect;
import com.leclowndu93150.thaumaturge.api.aspect.TCAspects;
import com.leclowndu93150.thaumaturge.api.nodes.NodeModifier;
import com.leclowndu93150.thaumaturge.api.nodes.NodeType;
import com.leclowndu93150.thaumaturge.content.aura.node.BlockEntityNode;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jspecify.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class NodeAnchorBlockEntity extends BlockEntity {
    // 固定要素的单一数值（无论节点原有没有该要素）。
    private static final int FIXED_AMOUNT = 256;

    private @Nullable AspectList full256;
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
        // 节点锚正上方一格必须是灵气节点，否则此锚无效。
        // 上方两格若为涌流充能器（且未接红石），则进入"充能固定"模式。
        // 布局示例（自顶向下）：
        //   涌流充能器 / 节点锚 / 节点 / 节点锚  → 下锚 above=节点、above(2)=充能器 → 固定256；上锚仅锁定
        //   节点锚 / 节点 / 节点锚                → 各锚 above=节点、above(2)=节点锚 → 仅锁定
        BlockEntity above = level.getBlockEntity(pos.above());
        if (!(above instanceof BlockEntityNode node)) {
            snapshot = null;
            charged = false;
            locked = false;
            return;
        }
        boolean redstone = level.hasNeighborSignal(pos);
        boolean hasCharger = !redstone
                && level.getBlockState(pos.above(2)).getBlock() instanceof InfinityChargerBlock;

        // 充能模式翻转 CHARGED 状态（仅变化时写，触发模型切换：稳定器 ↔ 换能器）。
        if (getBlockState().getValue(NodeAnchorBlock.CHARGED) != hasCharger) {
            level.setBlock(pos, getBlockState().setValue(NodeAnchorBlock.CHARGED, hasCharger), 3);
        }

        if (hasCharger) {
            if (full256 == null) {
                full256 = buildFull(level);
            }
            if (!charged
                    || node.getNodeType() != NodeType.NORMAL
                    || node.getNodeModifier() != null) {
                node.setAspects(full256);
                node.setNodeType(NodeType.NORMAL);
                node.setNodeModifier(null);
                charged = true;
            }
            snapshot = null;
            locked = false;
        } else {
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
                locked = true;
            }
            charged = false;
        }
    }

    // 构造 256 的要素列表：6 个原初要素 + 本 mod 的 infinitus（无论节点原有没有）。
    private static AspectList buildFull(Level level) {
        HolderLookup.RegistryLookup<IAspect> lookup =
                level.registryAccess().lookupOrThrow(IAspect.REGISTRY_KEY);
        List<AspectInstance> entries = new ArrayList<>();
        for (ResourceKey<IAspect> key : TCAspects.PRIMALS) {
            entries.add(new AspectInstance(lookup.getOrThrow(key), FIXED_AMOUNT));
        }
        entries.add(new AspectInstance(lookup.getOrThrow(TAAspects.INFINITUS), FIXED_AMOUNT));
        return AspectList.ofEntries(entries);
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
