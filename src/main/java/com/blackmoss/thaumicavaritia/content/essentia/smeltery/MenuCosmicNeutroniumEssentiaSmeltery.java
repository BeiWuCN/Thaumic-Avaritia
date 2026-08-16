// 宇宙中子态素源质冶炼厂菜单：单输入槽（物品），无燃料槽（0 消耗）。
// 布局沿用原版 MenuSmelter（物品槽位置一致），复用原版 SmelterScreen 的 GUI 纹理。
package com.blackmoss.thaumicavaritia.content.essentia.smeltery;

import com.blackmoss.thaumicavaritia.registry.TABlocks;
import com.blackmoss.thaumicavaritia.registry.TAMenus;
import net.minecraft.core.BlockPos;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.transfer.item.ItemStacksResourceHandler;
import net.neoforged.neoforge.transfer.item.ResourceHandlerSlot;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

public final class MenuCosmicNeutroniumEssentiaSmeltery extends AbstractContainerMenu {
    public static final int ITEM_X = 80;
    public static final int ITEM_Y = 7;
    public static final int PLAYER_GRID_X = 8;
    public static final int PLAYER_GRID_Y = 84;
    public static final int SLOT_COUNT = 1;
    public static final int PLAYER_ROW_SLOTS = 9;
    public static final int PLAYER_ROWS = 3;
    public static final int PLAYER_TOTAL_SLOTS = PLAYER_ROW_SLOTS * (PLAYER_ROWS + 1);
    public static final int TOTAL_INVENTORY_SLOTS = SLOT_COUNT + PLAYER_TOTAL_SLOTS;

    private final ItemStacksResourceHandler items;
    private final ContainerLevelAccess access;
    private final BlockPos pos;

    public MenuCosmicNeutroniumEssentiaSmeltery(int containerId, Inventory playerInventory, RegistryFriendlyByteBuf buf) {
        BlockPos pos = buf.readBlockPos();
        this(
                containerId,
                playerInventory,
                new ItemStacksResourceHandler(SLOT_COUNT),
                ContainerLevelAccess.create(playerInventory.player.level(), pos),
                pos);
    }

    public MenuCosmicNeutroniumEssentiaSmeltery(int containerId, Inventory playerInventory, BlockEntityCosmicNeutroniumEssentiaSmeltery blockEntity) {
        // BlockEntity.getLevel() 可能为 null（实体尚未绑定维度时），回退到玩家所在维度确保非空。
        Level level = blockEntity.getLevel();
        if (level == null) {
            level = playerInventory.player.level();
        }
        this(
                containerId,
                playerInventory,
                blockEntity.getInventory(),
                ContainerLevelAccess.create(level, blockEntity.getBlockPos()),
                blockEntity.getBlockPos());
    }

    private MenuCosmicNeutroniumEssentiaSmeltery(
            int containerId,
            Inventory playerInventory,
            ItemStacksResourceHandler items,
            ContainerLevelAccess access,
            BlockPos pos) {
        super(TAMenus.COSMIC_NEUTRONIUM_ESSENTIA_SMELTERY.get(), containerId);
        this.items = items;
        this.access = access;
        this.pos = pos;

        addSlot(new ResourceHandlerSlot(items, items::set, 0, ITEM_X, ITEM_Y));

        for (int row = 0; row < PLAYER_ROWS; row++) {
            for (int col = 0; col < PLAYER_ROW_SLOTS; col++) {
                addSlot(new Slot(
                        playerInventory,
                        col + row * PLAYER_ROW_SLOTS + PLAYER_ROW_SLOTS,
                        PLAYER_GRID_X + col * 18,
                        PLAYER_GRID_Y + row * 18));
            }
        }
        for (int col = 0; col < PLAYER_ROW_SLOTS; col++) {
            addSlot(new Slot(playerInventory, col, PLAYER_GRID_X + col * 18, PLAYER_GRID_Y + 3 * 18 + 4));
        }
    }

    public BlockPos pos() {
        return pos;
    }

    public ItemStacksResourceHandler items() {
        return items;
    }

    // 通过 ContainerLevelAccess 解析当前方块实体；若方块已卸载或类型不符则返回 null，调用方需判空。
    @Nullable
    public BlockEntityCosmicNeutroniumEssentiaSmeltery blockEntity() {
        return (BlockEntityCosmicNeutroniumEssentiaSmeltery) access.evaluate(Level::getBlockEntity)
                .filter(be -> be instanceof BlockEntityCosmicNeutroniumEssentiaSmeltery)
                .orElse(null);
    }

    @Override
    public boolean stillValid(@NonNull Player player) {
        return AbstractContainerMenu.stillValid(access, player, TABlocks.COSMIC_NEUTRONIUM_ESSENTIA_SMELTERY.get());
    }

    @Override
    public @NonNull ItemStack quickMoveStack(@NonNull Player player, int slotIndex) {
        ItemStack returnStack = ItemStack.EMPTY;
        Slot slot = slots.get(slotIndex);
        if (slot.hasItem()) {
            ItemStack stackInSlot = slot.getItem();
            returnStack = stackInSlot.copy();
            if (slotIndex < SLOT_COUNT) {
                if (!moveItemStackTo(stackInSlot, SLOT_COUNT, TOTAL_INVENTORY_SLOTS, true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!moveItemStackTo(stackInSlot, 0, SLOT_COUNT, false)) {
                return ItemStack.EMPTY;
            }
            if (stackInSlot.isEmpty()) {
                slot.setByPlayer(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }
        }
        return returnStack;
    }
}
