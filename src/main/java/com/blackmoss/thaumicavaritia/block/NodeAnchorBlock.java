package com.blackmoss.thaumicavaritia.block;

import com.blackmoss.thaumicavaritia.registry.TABlockEntities;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

public class NodeAnchorBlock extends BaseEntityBlock {
    public static final EnumProperty<Direction> FACING =
            EnumProperty.create("facing", Direction.class, Direction.UP, Direction.DOWN);
    public static final BooleanProperty CHARGED = BooleanProperty.create("charged");
    private static final MapCodec<NodeAnchorBlock> CODEC = simpleCodec(NodeAnchorBlock::new);
    public static final VoxelShape SHAPE = Shapes.or(
            box(0.0F, 0.0F, 0.0F, 16.0F, 2.0F, 16.0F),
            box(1.0F, 2.0F, 5.0F, 15.0F, 3.0F, 11.0F),
            box(0.0F, 3.0F, 5.0F, 16.0F, 4.0F, 11.0F),
            box(1.0F, 4.0F, 5.0F, 15.0F, 5.0F, 11.0F),
            box(0.0F, 5.0F, 6.0F, 16.0F, 7.0F, 10.0F),
            box(2.0F, 5.0F, 5.0F, 14.0F, 6.0F, 6.0F),
            box(2.0F, 5.0F, 10.0F, 14.0F, 6.0F, 11.0F),
            box(1.0F, 7.0F, 6.0F, 15.0F, 8.0F, 10.0F),
            box(3.0F, 2.0F, 3.0F, 13.0F, 8.0F, 5.0F),
            box(3.0F, 2.0F, 11.0F, 13.0F, 8.0F, 13.0F),
            box(3.0F, 6.0F, 5.0F, 13.0F, 8.0F, 6.0F),
            box(3.0F, 6.0F, 10.0F, 13.0F, 8.0F, 11.0F),
            box(4.0F, 8.0F, 4.0F, 12.0F, 9.0F, 12.0F),
            box(6.0F, 9.0F, 6.0F, 10.0F, 10.0F, 10.0F),
            box(5.0F, 2.0F, 1.0F, 11.0F, 5.0F, 3.0F),
            box(5.0F, 2.0F, 13.0F, 11.0F, 5.0F, 15.0F),
            box(5.0F, 3.0F, 0.0F, 11.0F, 4.0F, 1.0F),
            box(5.0F, 3.0F, 15.0F, 11.0F, 4.0F, 16.0F),
            box(5.0F, 5.0F, 2.0F, 11.0F, 6.0F, 3.0F),
            box(5.0F, 5.0F, 13.0F, 11.0F, 6.0F, 14.0F),
            box(6.0F, 5.0F, 0.0F, 10.0F, 7.0F, 2.0F),
            box(6.0F, 5.0F, 14.0F, 10.0F, 7.0F, 16.0F),
            box(6.0F, 6.0F, 2.0F, 10.0F, 8.0F, 3.0F),
            box(6.0F, 6.0F, 13.0F, 10.0F, 8.0F, 14.0F),
            box(6.0F, 7.0F, 1.0F, 10.0F, 8.0F, 2.0F),
            box(6.0F, 7.0F, 14.0F, 10.0F, 8.0F, 15.0F));

    public static final VoxelShape SHAPE_DOWN = rotateX180();

    private static VoxelShape rotateX180() {
        VoxelShape result = Shapes.empty();
        for (AABB aabb : NodeAnchorBlock.SHAPE.toAabbs()) {
            result = Shapes.or(result, Shapes.box(
                    aabb.minX, 1.0D - aabb.maxY, 1.0D - aabb.maxZ,
                    aabb.maxX, 1.0D - aabb.minY, 1.0D - aabb.minZ));
        }
        return result;
    }

    public NodeAnchorBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected @NonNull MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, CHARGED);
    }

    @Override
    public @Nullable BlockState getStateForPlacement(@NonNull BlockPlaceContext context) {
        Direction facing = context.isSecondaryUseActive() ? Direction.DOWN : Direction.UP;
        return defaultBlockState().setValue(FACING, facing).setValue(CHARGED, false);
    }

    protected @NonNull VoxelShape getShape(
            @NonNull BlockState state,
            @NonNull BlockGetter level,
            @NonNull BlockPos pos,
            @NonNull CollisionContext context) {
        return state.getValue(FACING) == Direction.DOWN ? SHAPE_DOWN : SHAPE;
    }

    protected @NonNull RenderShape getRenderShape(@NonNull BlockState state) {
        return RenderShape.INVISIBLE;
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(@NonNull BlockPos blockPos, @NonNull BlockState blockState) {
        return new NodeAnchorBlockEntity(blockPos,
                blockState);
    }

    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(
            @NonNull Level level, @NonNull BlockState blockState, @NonNull BlockEntityType<T> type) {
        if (level.isClientSide() || type != TABlockEntities.NODE_ANCHOR.get()) {
            return null;
        }
        return (tickLevel, pos, tickState,
                blockEntity) ->
                ((NodeAnchorBlockEntity) blockEntity).serverTick(tickLevel, pos);
    }
}
