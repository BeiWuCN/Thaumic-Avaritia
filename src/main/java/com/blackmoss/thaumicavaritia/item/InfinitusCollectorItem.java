package com.blackmoss.thaumicavaritia.item;

import com.blackmoss.thaumicavaritia.block.InfinitusCollectorBlockEntity;
import com.leclowndu93150.thaumaturge.content.aura.node.BlockEntityNode;
import com.leclowndu93150.thaumaturge.content.aura.node.NodeData;
import com.leclowndu93150.thaumaturge.registry.TTDataComponents;
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

    @Override
    public @NonNull InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        BlockPos pos = context.getClickedPos();
        BlockEntity be = level.getBlockEntity(pos);
        if (be instanceof BlockEntityNode node) {
            NodeData data = new NodeData(
                    node.getNodeType(),
                    Optional.ofNullable(node.getNodeModifier()),
                    node.getAspects(),
                    node.getAspectsBase());
            ItemStack stack = context.getItemInHand();
            stack.set(TTDataComponents.NODE_DATA.get(), data);
            BlockState collectorState = getBlock().defaultBlockState();
            if (!level.isClientSide()) {
                level.setBlock(pos, collectorState, 3);
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