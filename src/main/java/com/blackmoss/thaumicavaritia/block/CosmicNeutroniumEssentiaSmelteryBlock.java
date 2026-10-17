// 宇宙中子态素源质冶炼厂方块：0 燃料 / 0 污染 / 瞬间产出。
// 继承原版 BlockSmelter 复用外观/放置/粒子逻辑，但改用自定义 BE 类型与菜单。
package com.blackmoss.thaumicavaritia.block;

import com.blackmoss.thaumicavaritia.registry.TABlockEntities;
import com.leclowndu93150.thaumaturge.content.essentia.smeltery.BlockSmelter;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

public class CosmicNeutroniumEssentiaSmelteryBlock extends BlockSmelter {
    private static final MapCodec<CosmicNeutroniumEssentiaSmelteryBlock> CODEC =
            simpleCodec(CosmicNeutroniumEssentiaSmelteryBlock::new);

    public CosmicNeutroniumEssentiaSmelteryBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected @NonNull MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new CosmicNeutroniumEssentiaSmelteryBlockEntity(blockPos, blockState);
    }

    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(
            Level level, BlockState blockState, BlockEntityType<T> type) {
        return level.isClientSide() ? null : createTickerHelper(
                type, TABlockEntities.COSMIC_NEUTRONIUM_ESSENTIA_SMELTERY.get(),
                CosmicNeutroniumEssentiaSmelteryBlockEntity::staticTick);
    }

    @Override
    protected @NonNull InteractionResult useWithoutItem(
            BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hit) {
        if (level.isClientSide()) {
            return InteractionResult.SUCCESS;
        }
        if (level.getBlockEntity(pos) instanceof CosmicNeutroniumEssentiaSmelteryBlockEntity be) {
            player.openMenu(be, buf -> buf.writeBlockPos(pos));
        }
        return InteractionResult.CONSUME;
    }
}
