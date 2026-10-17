package com.blackmoss.thaumicavaritia.event;

import com.blackmoss.thaumicavaritia.ThaumicAvaritia;
import com.blackmoss.thaumicavaritia.content.NotQualifiedGate;
import com.blackmoss.thaumicavaritia.registry.TAWandParts;
import com.leclowndu93150.thaumaturge.content.wands.ItemWand;
import com.leclowndu93150.thaumaturge.content.wands.WandParts;
import committee.nova.mods.avaritia.common.entity.ImmortalItemEntity;
import committee.nova.mods.avaritia.init.registry.ModEntityTypes;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;

@EventBusSubscriber(modid = ThaumicAvaritia.MODID)
public final class TAServerEvents {

    private TAServerEvents() {
    }

    @SubscribeEvent
    public static void onEntityJoinLevel(EntityJoinLevelEvent event) {
        if (!(event.getEntity() instanceof ItemEntity itemEntity)) {
            return;
        }
        if (itemEntity instanceof ImmortalItemEntity) {
            return;
        }
        if (!isSpecialWand(itemEntity.getItem())) {
            return;
        }
        if (event.getLevel().isClientSide()) {
            return;
        }
        Level level = event.getLevel();
        ImmortalItemEntity immortal = ImmortalItemEntity.create(
                ModEntityTypes.IMMORTAL.get(),
                level,
                itemEntity.getX(),
                itemEntity.getY(),
                itemEntity.getZ(),
                itemEntity.getItem());
        if (immortal != null) {
            immortal.setDeltaMovement(itemEntity.getDeltaMovement());
            immortal.setPickUpDelay(0);
            event.setCanceled(true);
            itemEntity.discard();
            level.addFreshEntity(immortal);
        }
    }

    private static boolean isSpecialWand(ItemStack stack) {
        if (!(stack.getItem() instanceof ItemWand wand)) {
            return false;
        }
        WandParts parts = wand.getParts(stack);
        if (parts == null) {
            return false;
        }
        return parts.cap() == TAWandParts.CRYSTAL_MATRIX.get()
                || parts.rod() == TAWandParts.NEUTRONIUM.get();
    }

    @SubscribeEvent
    public static void onItemCrafted(PlayerEvent.ItemCraftedEvent event) {
        if (!(event.getEntity() instanceof ServerPlayer player)) {
            return;
        }
        ItemStack crafted = event.getCrafting();
        if (crafted.isEmpty()) {
            return;
        }
        if (NotQualifiedGate.isGated(crafted)) {
            return;
        }
        if (NotQualifiedGate.isResearchUnlocked(player, crafted)) {
            return;
        }
        removeOneFromInventory(player, crafted);
        player.sendSystemMessage(NotQualifiedGate.BLOCK_MESSAGE);
    }

    private static void removeOneFromInventory(ServerPlayer player, ItemStack target) {
        int remaining = target.getCount();
        for (int slot = 0; slot < player.getInventory().getContainerSize(); slot++) {
            ItemStack stack = player.getInventory().getItem(slot);
            if (stack.isEmpty() || !stack.is(target.getItem())) {
                continue;
            }
            int toRemove = Math.min(remaining, stack.getCount());
            player.getInventory().removeItem(slot, toRemove);
            remaining -= toRemove;
            if (remaining <= 0) {
                break;
            }
        }
    }
}
