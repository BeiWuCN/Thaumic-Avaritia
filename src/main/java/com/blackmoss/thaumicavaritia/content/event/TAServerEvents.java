package com.blackmoss.thaumicavaritia.content.event;

import com.blackmoss.thaumicavaritia.ThaumicAvaritia;
import com.blackmoss.thaumicavaritia.content.NotQualifiedGate;
import com.blackmoss.thaumicavaritia.registry.TAWandParts;
import com.leclowndu93150.thaumaturge.content.wands.ItemWand;
import com.leclowndu93150.thaumaturge.content.wands.WandParts;
import committee.nova.mods.avaritia.common.entity.ImmortalItemEntity;
import committee.nova.mods.avaritia.init.registry.ModEntityTypes;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;

// Dist.BOTH：开发环境 runClient 是集成服务器（物理端 CLIENT），DEDICATED_SERVER 注册会缺失导致不生效；
// 事件处理器内用 instanceof ServerPlayer 确保只走服务端逻辑。
@EventBusSubscriber(modid = ThaumicAvaritia.MODID)
public final class TAServerEvents {

    private TAServerEvents() {
    }

    // 特殊法杖（cap 水晶矩阵 或 rod 宇宙中子态素）掉落为不毁实体（ImmortalItemEntity），
    // 直接引用 avaritia 实现：不消失、跟随玩家、免疫火/爆炸（效果与无尽物品一致）。
    // 注意：level.addFreshEntity(immortal) 会再次触发 EntityJoinLevelEvent，
    // 必须放行已是 ImmortalItemEntity 的实体，否则无限递归 StackOverflow。
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

    // 判断物品是否为含水晶矩阵杖端或宇宙中子态素杖柄的组装法杖。
    private static boolean isSpecialWand(ItemStack stack) {
        if (!(stack.getItem() instanceof ItemWand wand)) {
            return false;
        }
        WandParts parts = wand.getParts(stack);
        if (parts == null) {
            return false;
        }
        return parts.cap() == TAWandParts.CRYSTAL_MATRIX.get()
                || parts.rod() == TAWandParts.STUDDED_COSMIC_NEUTRONIUM.get();
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
