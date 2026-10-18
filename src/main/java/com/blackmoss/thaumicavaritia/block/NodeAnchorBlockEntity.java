package com.blackmoss.thaumicavaritia.block;

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
    public static final int MAX_COUNT = 37;
    public int count;

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

        if (getBlockState().getValue(NodeAnchorBlock.CHARGED) != hasCharger) {
            level.setBlock(pos, getBlockState().setValue(NodeAnchorBlock.CHARGED, hasCharger), 3);
        }

        if (hasCharger) {
            if (!charged) {
                snapshot = new Snapshot(node);
                node.setEnergized(true);
                node.setNodeType(NodeType.NORMAL);
                node.setNodeModifier(null);
                node.nodeChange();
                charged = true;
            }
            locked = false;
        } else {
            if (charged) {
                node.setEnergized(false);
                charged = false;
            }
            if (snapshot == null) {
                snapshot = new Snapshot(node);
            } else if (node.getAspects().totalAmount() < snapshot.aspects.totalAmount()) {
                snapshot = new Snapshot(node);
            }
            if (!locked
                    || node.getNodeType() != snapshot.type
                    || node.getNodeModifier() != snapshot.modifier
                    || node.getAspects().totalAmount() != snapshot.aspects.totalAmount()) {
                node.setAspects(snapshot.aspects);
                node.setNodeType(snapshot.type);
                node.setNodeModifier(snapshot.modifier);
                node.nodeChange();
                locked = true;
            }
        }
    }

    public void clientTick(Level level, BlockPos pos) {
        if (isEngaged(level, pos)) {
            count = Math.min(MAX_COUNT, count + 1);
        } else if (count > 0) {
            count--;
        }
    }

    private static boolean isEngaged(Level level, BlockPos pos) {
        return level.getBlockEntity(pos.above()) instanceof BlockEntityNode
                || level.getBlockEntity(pos.below()) instanceof BlockEntityNode;
    }

    private static boolean hasChargerAbove(Level level, BlockPos pos) {
        BlockState above2 = level.getBlockState(pos.above(2));
        if (above2.getBlock() instanceof InfinityChargerBlock) {
            return true;
        }
        return above2.getBlock() instanceof NodeAnchorBlock
                && above2.getValue(NodeAnchorBlock.CHARGED);
    }

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
