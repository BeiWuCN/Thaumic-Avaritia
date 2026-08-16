// 服务端事件：限制中子态素杖柄的持有——未解锁研究时合成产物会从背包弹出并提示。
package com.blackmoss.thaumicavaritia.content.event;

import com.blackmoss.thaumicavaritia.ThaumicAvaritia;
import com.blackmoss.thaumicavaritia.registry.TAWandParts;
import com.leclowndu93150.thaumaturge.api.capability.KnowledgeAccess;
import com.leclowndu93150.thaumaturge.api.capability.ResearchFlag;
import com.leclowndu93150.thaumaturge.api.wands.WandRod;
import com.leclowndu93150.thaumaturge.content.wands.ItemWandRod;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;

@EventBusSubscriber(modid = ThaumicAvaritia.MODID, value = Dist.DEDICATED_SERVER)
public final class TAServerEvents {

    private static final Identifier ROD_RESEARCH =
            Identifier.fromNamespaceAndPath(ThaumicAvaritia.MODID, "wand_rod_studded_cosmic_neutronium");

    private static final Component BLOCK_MESSAGE = Component.translatable("message.thaumicavaritia.wand_rod_locked");

    private TAServerEvents() {
    }

    @SubscribeEvent
    public static void onItemCrafted(PlayerEvent.ItemCraftedEvent event) {
        ItemStack crafted = event.getCrafting();
        if (crafted.isEmpty()) {
            return;
        }
        // 仅拦截本 mod 的中子态素杖柄（ItemWandRod 且 rod 能力指向 STUDDED_COSMIC_NEUTRONIUM）。
        if (!(crafted.getItem() instanceof ItemWandRod rodItem)) {
            return;
        }
        WandRod rod = rodItem.rod();
        if (rod != TAWandParts.STUDDED_COSMIC_NEUTRONIUM.get()) {
            return;
        }
        Player player = event.getEntity();
        // 已解锁研究则放行。
        if (isResearchUnlocked(player)) {
            return;
        }
        // 未解锁：把刚合成的杖柄从背包移除并掉落到世界，同时提示玩家。
        removeCraftedFromInventory(player, crafted);
        player.drop(crafted.copy(), false);
        player.sendSystemMessage(BLOCK_MESSAGE);
    }

    private static boolean isResearchUnlocked(Player player) {
        try {
            return KnowledgeAccess.of(player).hasResearchFlag(ROD_RESEARCH, ResearchFlag.RESEARCH);
        } catch (IllegalStateException ignored) {
            // KnowledgeAccess 尚未绑定（极端时序），保守放行以免误吞合成物。
            return true;
        }
    }

    // 遍历背包找到与 target 同物品的槽位，移除 target.getCount() 个。
    // 用 removeItem(slot, amount)（原版 Inventory 无 removeItem(ItemStack) 便捷重载）。
    private static void removeCraftedFromInventory(Player player, ItemStack target) {
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
