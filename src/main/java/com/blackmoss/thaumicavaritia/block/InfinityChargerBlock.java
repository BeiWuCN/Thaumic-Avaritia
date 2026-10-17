package com.blackmoss.thaumicavaritia.block;

import com.leclowndu93150.thaumaturge.content.aura.node.BlockNode;
import com.leclowndu93150.thaumaturge.content.workbench.BlockArcaneWorkbenchCharger;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockState;
import org.jspecify.annotations.NonNull;

public class InfinityChargerBlock extends BlockArcaneWorkbenchCharger {
    public InfinityChargerBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected boolean canSurvive(@NonNull BlockState state, @NonNull LevelReader level, @NonNull BlockPos pos) {
        BlockState below = level.getBlockState(pos.below());
        return super.canSurvive(state, level, pos)
                || below.getBlock() instanceof NodeAnchorBlock
                || below.getBlock() instanceof BlockNode;
    }
}
