// 宇宙中子态素源质冶炼厂 BE：0 燃料 / 0 污染 / 瞬间产出。
// 继承原版 BlockEntitySmelter 复用 aspects/vis/inventory/takeAspect 等字段与方法。
// 注意：不引用 protected 的 BlockEntityAlembic.processAlembics（类须放本 mod 包，
// 避免模块导出冲突），改用其 public API（aspectKey/amount/doesContainerAccept/addToContainer）复刻等价路由。
// 原版 tick() 为 private，由 BlockEntitySmelterMixin 在 tick HEAD 改调 cosmicTick() 并取消原逻辑。
package com.blackmoss.thaumicavaritia.content.blockentity;

import com.blackmoss.thaumicavaritia.content.menu.CosmicNeutroniumEssentiaSmelteryMenu;
import com.blackmoss.thaumicavaritia.registry.TABlockEntities;
import com.leclowndu93150.thaumaturge.api.aspect.AspectIndexAccess;
import com.leclowndu93150.thaumaturge.api.aspect.AspectInstance;
import com.leclowndu93150.thaumaturge.api.aspect.AspectList;
import com.leclowndu93150.thaumaturge.api.aspect.IAspect;
import com.leclowndu93150.thaumaturge.content.essentia.smeltery.BlockEntityAlembic;
import com.leclowndu93150.thaumaturge.content.essentia.smeltery.BlockEntitySmelter;
import com.leclowndu93150.thaumaturge.content.essentia.smeltery.BlockSmelter;
import com.leclowndu93150.thaumaturge.content.essentia.smeltery.BlockSmelterAux;
import java.util.Objects;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.neoforged.neoforge.transfer.item.ItemStacksResourceHandler;
import net.neoforged.neoforge.transfer.transaction.Transaction;
import org.jspecify.annotations.Nullable;

public class CosmicNeutroniumEssentiaSmelteryBlockEntity extends BlockEntitySmelter {

    public static final int MAX_VIS = 256;

    public CosmicNeutroniumEssentiaSmelteryBlockEntity(BlockPos pos, BlockState state) {
        super(TABlockEntities.COSMIC_NEUTRONIUM_ESSENTIA_SMELTERY.get(), pos, state);
    }

    // 0 污染：恒 100% 效率，无丢率。
    @Override
    public float getEfficiency() {
        return 1.0F;
    }

    // 由 mixin 在 tick HEAD 调用并取消原版燃料/耗时逻辑。
    public void cosmicTick() {
        if (level == null || level.isClientSide()) {
            return;
        }
        // 燃烧状态跟随输入：槽内有可熔炼物品才亮（平时熄灭），与瞬间冶炼同步。
        // 菜单物品槽继承原版 SmelterInventory 的 isValid 校验（物品必须有要素），
        // 因此"有物品"即"正在冶炼"。
        boolean hasInput = getInventory().getAmountAsInt(0) > 0;
        if (getBlockState().getValue(BlockSmelter.LIT) != hasInput) {
            level.setBlock(getBlockPos(), getBlockState().setValue(BlockSmelter.LIT, hasInput), 3);
        }
        // 0 燃料：无燃料槽，跳过燃烧逻辑。
        if (hasInput) {
            smeltInstantly();
            pushToAlembics();
        }
    }

    // 瞬间冶炼：输入槽有可熔炼物品时立即写入全部要素（无效率损失），随后消耗 1 个输入。
    private void smeltInstantly() {
        ItemStacksResourceHandler inv = getInventory();
        if (inv.getAmountAsInt(0) <= 0 || vis >= MAX_VIS) {
            return;
        }
        ItemStack input = inv.getResource(0).toStack();
        AspectList itemAspects = AspectIndexAccess.index().of(input);
        if (itemAspects.isEmpty()) {
            return;
        }
        for (AspectInstance instance : itemAspects.entries()) {
            aspects = aspects.add(instance);
        }
        vis = aspects.totalAmount();
        Transaction transaction = Transaction.openRoot();
        inv.extract(0, inv.getResource(0), 1, transaction);
        transaction.commit();
        setChanged();
    }

    // 输出路由：与原版一致——直连上方蒸馏釜 + 四方相邻的辅助蒸馏器。
    private void pushToAlembics() {
        if (aspects.isEmpty()) {
            return;
        }
        tryPush(getBlockPos());
        for (Direction dir : Direction.Plane.HORIZONTAL) {
            if (getBlockState().getValue(BlockSmelter.FACING) != dir) {
                BlockPos pos = getBlockPos().relative(dir);
                BlockState state = null;
                if (level != null) {
                    state = level.getBlockState(pos);
                }
                if (state != null && state.getBlock() instanceof BlockSmelterAux
                        && state.getValue(BlockStateProperties.HORIZONTAL_FACING) == dir.getOpposite()) {
                    tryPush(pos);
                }
            }
        }
    }

    private void tryPush(BlockPos outputPos) {
        for (AspectInstance instance : aspects.entries()) {
            if (instance.amount() > 0 && pushToAlembic(outputPos, instance.aspect())) {
                takeAspect(instance.aspect(), 1);
                break;
            }
        }
    }

    // 复刻原版 BlockEntityAlembic.processAlembics 路由（仅用 public API）：
    // 优先加进已含同要素的蒸馏釜，否则加进空蒸馏釜（含过滤匹配）。
    private boolean pushToAlembic(BlockPos outputPos, Holder<IAspect> aspect) {
        int deep = 1;
        while (true) {
            BlockEntity be = null;
            if (level != null) {
                be = level.getBlockEntity(outputPos.above(deep));
            }
            if (!(be instanceof BlockEntityAlembic alembic)) {
                break;
            }
            if (alembic.amount() > 0
                    && Objects.equals(alembic.aspectKey(), aspect.getKey())
                    && alembic.addToContainer(aspect, 1) == 0) {
                return true;
            }
            deep++;
        }
        // 兜底：找一列蒸馏釜中第一个可接受（过滤匹配/空）的。
        deep = 1;
        while (true) {
            BlockEntity be = null;
            if (level != null) {
                be = level.getBlockEntity(outputPos.above(deep));
            }
            if (!(be instanceof BlockEntityAlembic alembic)) {
                return false;
            }
            if (alembic.doesContainerAccept(aspect) && alembic.addToContainer(aspect, 1) == 0) {
                return true;
            }
            deep++;
        }
    }

    @Override
    public @Nullable AbstractContainerMenu createMenu(int i, Inventory inventory, Player player) {
        return new CosmicNeutroniumEssentiaSmelteryMenu(i, inventory, this);
    }
}
