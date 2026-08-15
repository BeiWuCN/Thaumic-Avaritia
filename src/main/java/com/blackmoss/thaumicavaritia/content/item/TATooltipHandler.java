package com.blackmoss.thaumicavaritia.content.item;

import com.blackmoss.thaumicavaritia.ThaumicAvaritia;
import com.blackmoss.thaumicavaritia.registry.TAItems;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;

@EventBusSubscriber(modid = ThaumicAvaritia.MODID)
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
        }
        if (key != null) {
            event.getToolTip().add(Component.translatable(key).withStyle(ChatFormatting.GRAY, ChatFormatting.ITALIC));
        }
    }
}
