// 万物起源之罐：除「无限」外所有源质各 32767 点，只出不进。
// 继承 BlockJar 复用罐子形状/流端口/要素罐接口，但使用自定义多要素 BE 与只出不进逻辑。
package com.blackmoss.thaumicavaritia.content.block;

import com.blackmoss.thaumicavaritia.content.essentia.jar.BlockEntityOriginJar;
import com.leclowndu93150.thaumaturge.content.essentia.jar.BlockJar;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.Identifier;
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

    private static final Identifier SIDE_TEXTURE =
            Identifier.fromNamespaceAndPath("thaumicavaritia", "block/origin_jar_side");
    private static final Identifier TOP_TEXTURE =
            Identifier.fromNamespaceAndPath("thaumicavaritia", "block/origin_jar_top");
    private static final Identifier BOTTOM_TEXTURE =
            Identifier.fromNamespaceAndPath("thaumicavaritia", "block/origin_jar_bottom");

    public static final MapCodec<OriginJarBlock> CODEC = simpleCodec(OriginJarBlock::new);

    public OriginJarBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected @NonNull MapCodec<? extends BlockJar> codec() {
        return CODEC;
    }

    @Override
    public Identifier jarSideTexture() {
        return SIDE_TEXTURE;
    }

    @Override
    public Identifier jarTopTexture() {
        return TOP_TEXTURE;
    }

    @Override
    public Identifier jarBottomTexture() {
        return BOTTOM_TEXTURE;
    }

    @Override
    public @NonNull BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new BlockEntityOriginJar(pos, state);
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
