package com.blackmoss.thaumicavaritia.client;

import com.blackmoss.thaumicavaritia.ThaumicAvaritia;
import com.leclowndu93150.thaumaturge.content.wands.ItemWand;
import com.leclowndu93150.thaumaturge.content.wands.WandVisHelper;
import committee.nova.mods.avaritia.api.utils.lang.TextUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;

import java.util.List;

@EventBusSubscriber(modid = ThaumicAvaritia.MODID, value = Dist.CLIENT)
public final class WandTooltipHandler {

    private static final String INFINITY_KEY = "tooltip.infinity";
    private static final String CAPACITY_KEY = "tooltip.thaumaturge.wand.capacity";
    private static final String VIS_SEPARATOR = " | ";

    private static final ChatFormatting[] FABULOUS = {
            ChatFormatting.RED, ChatFormatting.GOLD, ChatFormatting.YELLOW, ChatFormatting.GREEN,
            ChatFormatting.AQUA, ChatFormatting.BLUE, ChatFormatting.LIGHT_PURPLE
    };

    private WandTooltipHandler() {
    }

    @SubscribeEvent
    public static void onTooltip(ItemTooltipEvent event) {
        ItemStack stack = event.getItemStack();
        if (!(stack.getItem() instanceof ItemWand) || !isInfiniteWand(stack)) {
            return;
        }

        List<Component> tooltip = event.getToolTip();
        String capacityMarker = I18n.get(CAPACITY_KEY, "").trim();

        for (int i = 0; i < tooltip.size(); i++) {
            String text = tooltip.get(i).getString();
            if (text.contains(VIS_SEPARATOR)) {
                tooltip.set(i, rebuildVisLine(text));
            } else if (!capacityMarker.isEmpty() && text.contains(capacityMarker)) {
                tooltip.set(i, Component.translatable(CAPACITY_KEY, infinity(0)).withStyle(ChatFormatting.GOLD));
            }
        }
    }

    private static MutableComponent rebuildVisLine(String text) {
        String[] parts = text.split(" \\| ");
        MutableComponent line = Component.empty();
        for (int i = 0; i < parts.length; i++) {
            if (i > 0) {
                line.append(Component.literal(VIS_SEPARATOR).withStyle(ChatFormatting.DARK_GRAY));
            }
            line.append(infinity(i));
        }
        return line;
    }

    private static MutableComponent infinity(int index) {
        return Component.literal(TextUtils.ludicrousFormatting(I18n.get(INFINITY_KEY), FABULOUS, 80.0, 1, index + 1));
    }

    private static boolean isInfiniteWand(ItemStack stack) {
        int capacity = WandVisHelper.capacityOf(stack);
        return capacity < 0 || capacity >= 2_000_000_000;
    }
}
