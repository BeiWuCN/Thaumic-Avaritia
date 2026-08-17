// 客户端 tooltip 事件：为本 mod 的方块/物品统一追加 lore 描述（item.<id>.desc）。
// 方块物品（registerSimpleBlockItem 的普通 BlockItem）不覆写 appendHoverText，故统一在此处理。
package com.blackmoss.thaumicavaritia.client;

import com.blackmoss.thaumicavaritia.ThaumicAvaritia;
import com.blackmoss.thaumicavaritia.registry.TAItems;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;

@EventBusSubscriber(modid = ThaumicAvaritia.MODID, value = Dist.CLIENT)
public final class TATooltipHandler {

    private TATooltipHandler() {
    }

    @SubscribeEvent
    public static void onTooltip(ItemTooltipEvent event) {
        ItemStack stack = event.getItemStack();
        String key = null;
        if (stack.is(TAItems.WAND_CAP_CRYSTAL_MATRIX.get())) {
            key = "item.thaumicavaritia.wand_cap_crystal_matrix.desc";
        } else if (stack.is(TAItems.WAND_ROD_STUDDED_COSMIC_NEUTRONIUM.get())) {
            key = "item.thaumicavaritia.wand_rod_studded_cosmic_neutronium.desc";
        } else if (stack.is(TAItems.INFINITY_CHARGER.get())) {
            key = "item.thaumicavaritia.infinity_charger.desc";
        } else if (stack.is(TAItems.COSMIC_NEUTRONIUM_ESSENTIA_SMELTERY.get())) {
            key = "item.thaumicavaritia.cosmic_neutronium_essentia_smeltery.desc";
        } else if (stack.is(TAItems.NODE_ANCHOR.get())) {
            key = "item.thaumicavaritia.node_anchor.desc";
        } else if (stack.is(TAItems.ORIGIN_JAR.get())) {
            key = "item.thaumicavaritia.origin_jar.desc";
        }
        if (key != null) {
            event.getToolTip().add(Component.translatable(key).withStyle(ChatFormatting.GRAY, ChatFormatting.ITALIC));
        }
    }
}
