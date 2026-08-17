package com.blackmoss.thaumicavaritia.content.event;

import com.blackmoss.thaumicavaritia.ThaumicAvaritia;
import com.blackmoss.thaumicavaritia.content.NotQualifiedGate;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;

// Dist.BOTH：开发环境 runClient 是集成服务器（物理端 CLIENT），DEDICATED_SERVER 注册会缺失导致不生效；
// 事件处理器内用 instanceof ServerPlayer 确保只走服务端逻辑。
@EventBusSubscriber(modid = ThaumicAvaritia.MODID)
public final class TAServerEvents {

    private TAServerEvents() {
    }

    @SubscribeEvent
    public static void onItemCrafted(PlayerEvent.ItemCraftedEvent event) {
        // 仅服务端逻辑：集成服务器/专用服务器都触发，这里用 ServerPlayer 兜底。
        if (!(event.getEntity() instanceof ServerPlayer player)) {
            return;
        }
        ItemStack crafted = event.getCrafting();
        if (crafted.isEmpty()) {
            return;
        }
        // 非门禁物品放行。
        if (!NotQualifiedGate.isGated(crafted)) {
            return;
        }
        // 已解锁研究则放行。
        if (NotQualifiedGate.isResearchUnlocked(player, crafted)) {
            return;
        }
        // 未解锁：把刚合成的这一份从背包移除（不整包扫描，不处理别人给予的），提示玩家。
        removeOneFromInventory(player, crafted);
        player.sendSystemMessage(NotQualifiedGate.BLOCK_MESSAGE);
    }

    // 从背包移除与 target 同物品的一份（target.getCount() 个），合成事件触发时产物已在背包。
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
