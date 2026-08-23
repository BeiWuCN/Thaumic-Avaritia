// 无限收集器物品（方块物品）：手持右键灵气节点时，把节点数据(NODE_DATA)存入物品，
// 节点方块被替换成收集器方块（节点消失，收集器出现在节点位置）。
// 收集器数据存于 NODE_DATA 组件，放置时由 BE 读取，挖下时由 BE 写回，可随物品携带。
package com.blackmoss.thaumicavaritia.content.item;

import com.blackmoss.thaumicavaritia.content.blockentity.InfinitusCollectorBlockEntity;
import com.leclowndu93150.thaumaturge.content.aura.node.BlockEntityNode;
import com.leclowndu93150.thaumaturge.content.aura.node.NodeData;
import com.leclowndu93150.thaumaturge.registry.TCDataComponents;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jspecify.annotations.NonNull;

import java.util.Optional;

public class InfinitusCollectorItem extends BlockItem {

    public InfinitusCollectorItem(Block block, Properties properties) {
        super(block, properties);
    }

    // 右键节点：读取节点数据存入物品 NODE_DATA，并把节点位置替换成收集器方块（节点消失）。
    @Override
    public @NonNull InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        BlockPos pos = context.getClickedPos();
        BlockEntity be = level.getBlockEntity(pos);
        if (be instanceof BlockEntityNode node) {
            // 读取节点数据，构造 NodeData 存入手持物品。
            NodeData data = new NodeData(
                    node.getNodeType(),
                    Optional.ofNullable(node.getNodeModifier()),
                    node.getAspects(),
                    node.getAspectsBase());
            ItemStack stack = context.getItemInHand();
            stack.set(TCDataComponents.NODE_DATA.get(), data);
            // 节点方块消失，收集器出现在节点位置（使用本物品对应的收集器方块）。
            BlockState collectorState = getBlock().defaultBlockState();
            if (!level.isClientSide()) {
                level.setBlock(pos, collectorState, 3);
                // 手动设置 BE 的节点数据（因为 setBlock 绕过了正常放置流程）。
                BlockEntity newBe = level.getBlockEntity(pos);
                if (newBe instanceof InfinitusCollectorBlockEntity collectorBe) {
                    collectorBe.setNodeDataFromItem(data);
                }
            }
            return InteractionResult.SUCCESS;
        }
        return super.useOn(context);
    }
}