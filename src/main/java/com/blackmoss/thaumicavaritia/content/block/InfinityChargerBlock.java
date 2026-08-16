package com.blackmoss.thaumicavaritia.content.block;

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

    // 涌流充能器可放置的位置：
    // - 下方是奥术工作台（原版用途，父类行为）；
    // - 下方是节点锚或灵气节点（本 mod 用途：节点 → 节点锚 → 充能器 布局，充能器在节点上方）。
    // 父类 BlockArcaneWorkbenchCharger 的 canSurvive 只认奥术工作台，必须覆写否则放到节点/节点锚上方会立刻消失。
    @Override
    protected boolean canSurvive(@NonNull BlockState state, @NonNull LevelReader level, @NonNull BlockPos pos) {
        BlockState below = level.getBlockState(pos.below());
        return super.canSurvive(state, level, pos)
                || below.getBlock() instanceof NodeAnchorBlock
                || below.getBlock() instanceof BlockNode;
    }
}
