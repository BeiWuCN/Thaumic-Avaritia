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
        } else if (stack.is(TAItems.INFINITUS_COLLECTOR.get())
                || stack.is(TAItems.INFINITUS_COLLECTOR_COMPACT.get())
                || stack.is(TAItems.INFINITUS_COLLECTOR_DENSE.get())
                || stack.is(TAItems.INFINITUS_COLLECTOR_ENDLESS.get())) {
            key = "item.thaumicavaritia.infinitus_collector.desc";
        } else if (stack.is(TAItems.LIMIT_FRAME.get())) {
            key = "item.thaumicavaritia.limit_frame.desc";
        } else if (stack.is(TAItems.CRYSTAL_MATRIX_PLATE.get())) {
            key = "item.thaumicavaritia.crystal_matrix_plate.desc";
        } else if (stack.is(TAItems.NEUTRON_PLATE.get())) {
            key = "item.thaumicavaritia.neutron_plate.desc";
        } else if (stack.is(TAItems.INFINITY_PLATE.get())) {
            key = "item.thaumicavaritia.infinity_plate.desc";
        } else if (stack.is(TAItems.LIMIT_ALCHEMICAL_CONSTRUCT.get())) {
            key = "item.thaumicavaritia.limit_alchemical_construct.desc";
        } else if (stack.is(TAItems.LIMIT_FRAME.get())) {
            key = "item.thaumicavaritia.limit_frame.desc";
        }
        if (key != null) {
            event.getToolTip().add(Component.translatable(key).withStyle(ChatFormatting.GRAY, ChatFormatting.ITALIC));
        }
    }
}
