package com.blackmoss.thaumicavaritia.client;

import com.blackmoss.thaumicavaritia.ThaumicAvaritia;
import com.leclowndu93150.thaumaturge.content.wands.ItemWand;
import com.leclowndu93150.thaumaturge.content.wands.ItemWandRod;
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

    private static final double INFINITY_NUMBER = 1_000_000.0;

    private static final ChatFormatting[] FABULOUS = {
            ChatFormatting.RED,
            ChatFormatting.GOLD,
            ChatFormatting.YELLOW,
            ChatFormatting.GREEN,
            ChatFormatting.AQUA,
            ChatFormatting.BLUE,
            ChatFormatting.LIGHT_PURPLE
    };

    private WandTooltipHandler() {
    }

    @SubscribeEvent
    public static void onTooltip(ItemTooltipEvent event) {
        ItemStack stack = event.getItemStack();
        List<Component> tooltip = event.getToolTip();

        if (stack.getItem() instanceof ItemWandRod rod) {
            if (rod.rod().capacity() >= INFINITY_NUMBER) {
                replaceCapacityLine(tooltip);
            }
            return;
        }

        if (!(stack.getItem() instanceof ItemWand)) {
            return;
        }

        for (int i = 0; i < tooltip.size(); i++) {
            String text = tooltip.get(i).getString();
            if (isInfiniteVisLine(text)) {
                tooltip.set(i, rebuildVisLine(text));
            } else if (isInfiniteCapacityLine(text)) {
                tooltip.set(i, capacityLine());
            }
        }
    }

    private static boolean isInfiniteVisLine(String text) {
        String[] parts = text.split(" \\| ");
        if (parts.length < 2) {
            return false;
        }
        boolean any = false;
        for (String part : parts) {
            String value = part.trim();
            if (value.isEmpty()) {
                continue;
            }
            any = true;
            try {
                double number = Double.parseDouble(value);
                if (number >= 0 && number < INFINITY_NUMBER) {
                    return false;
                }
            } catch (NumberFormatException e) {
                return false;
            }
        }
        return any;
    }

    private static boolean isInfiniteCapacityLine(String text) {
        String marker = I18n.get(CAPACITY_KEY, "").trim();
        if (marker.isEmpty() || !text.contains(marker)) {
            return false;
        }
        String value = text.substring(text.indexOf(marker) + marker.length()).trim();
        try {
            double number = Double.parseDouble(value);
            return number < 0 || number >= INFINITY_NUMBER;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private static void replaceCapacityLine(List<Component> tooltip) {
        String marker = I18n.get(CAPACITY_KEY, "").trim();
        if (marker.isEmpty()) {
            return;
        }
        for (int i = 0; i < tooltip.size(); i++) {
            if (tooltip.get(i).getString().contains(marker)) {
                tooltip.set(i, capacityLine());
            }
        }
    }

    private static MutableComponent capacityLine() {
        return Component.translatable(CAPACITY_KEY, infinity()).withStyle(ChatFormatting.GOLD);
    }

    private static MutableComponent rebuildVisLine(String text) {
        String[] parts = text.split(" \\| ");
        StringBuilder line = new StringBuilder();
        for (int i = 0; i < parts.length; i++) {
            if (i > 0) {
                line.append(VIS_SEPARATOR);
            }
            line.append(I18n.get(INFINITY_KEY));
        }
        return Component.literal(TextUtils.ludicrousFormatting(line.toString(), FABULOUS, 80.0, 1, 1));
    }

    private static MutableComponent infinity() {
        return Component.literal(TextUtils.ludicrousFormatting(I18n.get(INFINITY_KEY), FABULOUS, 80.0, 1, 1));
    }
}
