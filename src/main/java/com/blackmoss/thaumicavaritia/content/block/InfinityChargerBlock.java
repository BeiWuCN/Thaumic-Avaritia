package com.blackmoss.thaumicavaritia.content.block;

import com.leclowndu93150.thaumaturge.content.workbench.BlockArcaneWorkbenchCharger;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import org.jspecify.annotations.NonNull;

public class InfinityChargerBlock extends BlockArcaneWorkbenchCharger {
    public InfinityChargerBlock(Properties properties) {
        super(properties);
    }
}
