// 节点锚：放在灵气节点下方（同稳定器位置），结构为 节点 → 节点锚 → 涌流充能器（自顶向下）。
// 普通情况（无充能器激活）直接锁定上方节点的状态（冻结要素/类型/修饰，不被喂料/衰减/放电/行为改变）；
// 当上方两格放置涌流充能器（且未被红石关闭）时，将节点每种要素固定为 256（无论原有没有）。
// 渲染：锁定模式用稳定器（stabilizer）几何，充能模式用换能器（transducer）几何，两者共用同一套纹理。
// 通过 CHARGED 方块状态在 tick 中切换模型（仿冶炼厂 LIT 的做法）。
// 参考 Thaumaturge 的 BlockNodeTransducer：独立 EntityBlock，在 serverTick 中直接操作节点 public API，
// 不修改 Thaumaturge 任何内部实现（BlockNodeStabilizer 为 final 且锁定逻辑在节点私有方法，无法复用，故自实现）。
package com.blackmoss.thaumicavaritia.content.block;

import com.blackmoss.thaumicavaritia.registry.TABlockEntities;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
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
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

public class NodeAnchorBlock extends BaseEntityBlock {
    // 方向：爪朝向（UP=朝上托住上方节点，DOWN=朝下压住下方节点），放置时由点击面决定。
    public static final EnumProperty<Direction> FACING =
            EnumProperty.create("facing", Direction.class, Direction.UP, Direction.DOWN);
    // 充能：true=上方两格为涌流充能器（且未接红石），由 BE tick 维护（对应原版换能器的激活态）。
    public static final BooleanProperty CHARGED = BooleanProperty.create("charged");

    private static final MapCodec<NodeAnchorBlock> CODEC = simpleCodec(NodeAnchorBlock::new);

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

    // 放置朝向：默认朝上（爪向上托住上方节点）；按住 Shift 放置则朝下（爪向下压住下方节点）。
    @Override
    public @Nullable BlockState getStateForPlacement(@NonNull BlockPlaceContext context) {
        Direction facing = context.isSecondaryUseActive() ? Direction.DOWN : Direction.UP;
        return defaultBlockState().setValue(FACING, facing).setValue(CHARGED, false);
    }

    // 世界内渲染：走 blockstate 的 CHARGED 状态分派稳定器/换能器模型。
    // 注意不能照抄原版稳定器的 RenderShape.INVISIBLE——原版靠 NodeStabilizerRenderer(BER) 代码绘制，
    // 本方块没有 BER，INVISIBLE 会导致世界中完全不渲染。
    @Override
    protected @NonNull RenderShape getRenderShape(@NonNull BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(@NonNull BlockPos blockPos, @NonNull BlockState blockState) {
        return new NodeAnchorBlockEntity(blockPos, blockState);
    }

    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(
            @NonNull Level level, @NonNull BlockState blockState, @NonNull BlockEntityType<T> type) {
        if (level.isClientSide() || type != TABlockEntities.NODE_ANCHOR.get()) {
            return null;
        }
        return (tickLevel, pos, tickState, blockEntity) ->
                ((NodeAnchorBlockEntity) blockEntity).serverTick(tickLevel, pos);
    }
}
