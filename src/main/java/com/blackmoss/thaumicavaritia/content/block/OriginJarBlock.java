package com.blackmoss.thaumicavaritia.content.block;

import com.blackmoss.thaumicavaritia.content.blockentity.OriginJarBlockEntity;
import com.leclowndu93150.thaumaturge.content.essentia.jar.BlockJar;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

public final class OriginJarBlock extends BlockJar {
    public static final MapCodec<OriginJarBlock> CODEC = simpleCodec(OriginJarBlock::new);

    public OriginJarBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected @NonNull MapCodec<? extends BlockJar> codec() {
        return CODEC;
    }

    @Override
    public @NonNull BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new OriginJarBlockEntity(pos, state);
    }

    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(
            Level level, BlockState blockState, BlockEntityType<T> type) {
        return null; // 纯展示/抽取罐，无 tick 逻辑。
    }

    // 只出不进：右键不做交互（避免父类对 BlockEntityJar 的强转），由上方管道抽取源质。
    @Override
    protected @NonNull InteractionResult useWithoutItem(
            BlockState state, Level level, BlockPos pos, net.minecraft.world.entity.player.Player player, BlockHitResult hitResult) {
        return InteractionResult.PASS;
    }

    @Override
    protected @NonNull InteractionResult useItemOn(
            ItemStack stack,
            BlockState state,
            Level level,
            BlockPos pos,
            net.minecraft.world.entity.player.Player player,
            net.minecraft.world.InteractionHand hand,
            BlockHitResult hit) {
        return InteractionResult.PASS;
    }
}
