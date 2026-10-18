package com.blackmoss.thaumicavaritia.menu;

import com.blackmoss.thaumicavaritia.block.InfinitusCollectorBlockEntity;
import com.blackmoss.thaumicavaritia.registry.TAMenus;
import committee.nova.mods.avaritia.api.common.menu.BaseTileMenu;
import committee.nova.mods.avaritia.api.common.slot.OutputSlot;
import committee.nova.mods.avaritia.api.common.wrapper.ItemStackWrapper;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class InfinitusCollectorMenu extends BaseTileMenu<InfinitusCollectorBlockEntity> {
    private final ContainerData data;

    public InfinitusCollectorMenu(int id, Inventory playerInventory, FriendlyByteBuf buffer) {
        this(id, playerInventory, buffer.readBlockPos(), new SimpleContainerData(1));
    }

    public InfinitusCollectorMenu(int id, Inventory playerInventory, BlockPos pos, ContainerData data) {
        super(TAMenus.INFINITUS_COLLECTOR.get(), id, playerInventory, pos);
        this.data = data;
        ItemStackWrapper inventory = this.getTileEntity().getInventory();
        Objects.requireNonNull(inventory);
        this.addSlot(new OutputSlot(inventory, inventory::set, 0, 80, 32));
        this.addDataSlots(data);
        this.createInventorySlots(playerInventory);
    }

    public @NotNull ItemStack quickMoveStack(@NotNull Player player, int slotNumber) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(slotNumber);
        if (slot.hasItem()) {
            ItemStack itemstack1 = slot.getItem();
            itemstack = itemstack1.copy();
            if (slotNumber < 1) {
                if (!this.moveItemStackTo(itemstack1, 1, 37, true)) {
                    return ItemStack.EMPTY;
                }

                slot.onQuickCraft(itemstack1, itemstack);
            } else if (slotNumber < 28) {
                if (!this.moveItemStackTo(itemstack1, 28, 37, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (slotNumber < 37 && !this.moveItemStackTo(itemstack1, 1, 28, false)) {
                return ItemStack.EMPTY;
            }

            if (itemstack1.getCount() == 0) {
                slot.set(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }

            if (itemstack1.getCount() == itemstack.getCount()) {
                return ItemStack.EMPTY;
            }

            slot.onTake(player, itemstack1);
        }

        return itemstack;
    }

    public int getProgress() {
        return this.data.get(0);
    }
}
