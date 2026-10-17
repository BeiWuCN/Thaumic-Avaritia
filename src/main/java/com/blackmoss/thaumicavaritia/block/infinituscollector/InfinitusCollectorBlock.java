package com.blackmoss.thaumicavaritia.block.infinituscollector;

import com.blackmoss.thaumicavaritia.block.InfinitusCollectorBlockEntity;
import com.blackmoss.thaumicavaritia.registry.TABlockEntities;
import committee.nova.mods.avaritia.api.common.block.BaseTileEntityBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jspecify.annotations.NonNull;

public class InfinitusCollectorBlock extends BaseTileEntityBlock {
    public static final EnumProperty<Direction> FACING = HorizontalDirectionalBlock.FACING;
    public static final BooleanProperty LIT = BlockStateProperties.LIT;

    public InfinitusCollectorBlock(Properties properties) {
        super(properties);
    }

    public @Nullable BlockEntity newBlockEntity(@NotNull BlockPos pos, @NotNull BlockState state) {
        return new InfinitusCollectorBlockEntity(pos, state);
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, LIT);
    }

    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection());
    }

    public @NotNull InteractionResult useWithoutItem(
            @NotNull BlockState state,
            Level level,
            @NotNull BlockPos pos,
            @NotNull Player player,
            @NotNull BlockHitResult trace) {
        if (!level.isClientSide()) {
            BlockEntity tile = level.getBlockEntity(pos);
            if (tile instanceof InfinitusCollectorBlockEntity compressor) {
                player.openMenu(compressor, pos);
            }
        }

        return InteractionResult.SUCCESS;
    }

    public @NonNull BlockState rotate(BlockState state, Rotation rot) {
        return state.setValue(FACING, rot.rotate(state.getValue(FACING)));
    }

    public @NonNull BlockState mirror(BlockState state, Mirror mirror) {
        return state.rotate(mirror.getRotation(state.getValue(FACING)));
    }

    protected <T extends BlockEntity> BlockEntityTicker<T> getServerTicker(Level level, BlockState state, BlockEntityType<T> type) {
        return createTicker(type, TABlockEntities.INFINITUS_COLLECTOR.get(), InfinitusCollectorBlockEntity::serverTick);
    }
}

//    // 成形：由 BE 维护（成形才工作，触发模型/视觉切换）。
//    public static final BooleanProperty FORM = BooleanProperty.create("form");
//    // 水平朝向：放置时跟玩家 facing 相反（仿熔炉/冶炼厂），用于 blockstate 模型旋转。
//    public static final EnumProperty<Direction> FACING = BlockStateProperties.HORIZONTAL_FACING;
//
//    private static final MapCodec<InfinitusCollectorBlock> CODEC = simpleCodec(InfinitusCollectorBlock::new);
//
//    // 行为配置：收集概率/灵气消耗/污染/永动等（可自定义，便于扩展）。
//    private final InfinitusCollectorSettings settings;
//
//    public InfinitusCollectorBlock(Properties properties) {
//        this(properties, InfinitusCollectorSettings.builder().build());
//    }
//
//    public InfinitusCollectorBlock(Properties properties, InfinitusCollectorSettings settings) {
//        super(properties);
//        this.settings = settings;
//        this.registerDefaultState(this.getStateDefinition().any()
//                .setValue(FORM, false)
//                .setValue(FACING, Direction.NORTH));
//    }
//
//    // 本方块的行为配置。
//    public InfinitusCollectorSettings getSettings() {
//        return settings;
//    }
//
//    @Override
//    protected @NonNull MapCodec<? extends BaseEntityBlock> codec() {
//        return CODEC;
//    }
//
//    @Override
//    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
//        builder.add(FORM, FACING);
//    }
//
//    // 放置朝向：跟玩家水平 facing 相反（仿熔炉/冶炼厂，方块正面朝向玩家）。
//    @Override
//    public BlockState getStateForPlacement(BlockPlaceContext context) {
//        return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
//    }
//
//    // 红石锭旋转/镜像：facing 跟着变换（保证结构被复制/旋转时方向正确）。
//    @Override
//    protected @NonNull BlockState rotate(BlockState state, Rotation rotation) {
//        return state.setValue(FACING, rotation.rotate(state.getValue(FACING)));
//    }
//
//    @Override
//    protected @NonNull BlockState mirror(BlockState state, Mirror mirror) {
//        return state.setValue(FACING, mirror.mirror(state.getValue(FACING)));
//    }
//
//    @Override
//    protected @NonNull RenderShape getRenderShape(@NonNull BlockState state) {
//        return RenderShape.MODEL;
//    }
//
//    @Override
//    public @Nullable BlockEntity newBlockEntity(@NonNull BlockPos blockPos, @NonNull BlockState blockState) {
//        return new InfinitusCollectorBlockEntity(blockPos, blockState);
//    }
//
//    // 右键（空手）交互：打开 GUI（结晶输出槽在 GUI 内）。Shift+右键敲打解除噎住。
//    @Override
//    protected @NonNull InteractionResult useWithoutItem(
//            @NonNull BlockState state,
//            @NonNull Level level,
//            @NonNull BlockPos pos,
//            @NonNull Player player,
//            @NonNull BlockHitResult hitResult) {
//        if (level.isClientSide()) {
//            return InteractionResult.SUCCESS;
//        }
//        if (level.getBlockEntity(pos) instanceof InfinitusCollectorBlockEntity be) {
//            if (player.isSecondaryUseActive() && be.isJammed()) {
//                // Shift+右键：解除噎住（GUI 内未提供敲打按钮，保留快捷入口）。
//                be.knock();
//                return InteractionResult.SUCCESS;
//            }
//            // 普通右键：打开 GUI（结晶槽/进度条/玩家物品栏）。
//            player.openMenu(be, buf -> buf.writeBlockPos(pos));
//            return InteractionResult.CONSUME;
//        }
//        return InteractionResult.PASS;
//    }
//
//    @Override
//    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(
//            @NonNull Level level,
//            @NonNull BlockState blockState,
//            @NonNull BlockEntityType<T> type) {
//        if (level.isClientSide() || type != TABlockEntities.INFINITUS_COLLECTOR.get()) {
//            return null;
//        }
//        return (tickLevel, pos, tickState, blockEntity) ->
//                ((InfinitusCollectorBlockEntity) blockEntity).serverTick(tickLevel, pos);
//    }
