package com.blackmoss.thaumicavaritia.block;

import com.blackmoss.thaumicavaritia.menu.CosmicNeutroniumEssentiaSmelteryMenu;
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

    @Override
    public float getEfficiency() {
        return 1.0F;
    }

    public void cosmicTick() {
        if (level == null || level.isClientSide()) {
            return;
        }
        boolean hasInput = getInventory().getAmountAsInt(0) > 0;
        if (getBlockState().getValue(BlockSmelter.LIT) != hasInput) {
            level.setBlock(getBlockPos(), getBlockState().setValue(BlockSmelter.LIT, hasInput), 3);
        }
        if (hasInput) {
            smeltInstantly();
            pushToAlembics();
        }
    }

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
                    && alembic.fill(aspect, 1) > 0) {
                return true;
            }
            deep++;
        }
        deep = 1;
        while (true) {
            BlockEntity be = null;
            if (level != null) {
                be = level.getBlockEntity(outputPos.above(deep));
            }
            if (!(be instanceof BlockEntityAlembic alembic)) {
                return false;
            }
            if (alembic.accepts(aspect) && alembic.fill(aspect, 1) > 0) {
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
