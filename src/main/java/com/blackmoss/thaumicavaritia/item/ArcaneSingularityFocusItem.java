package com.blackmoss.thaumicavaritia.item;

import com.leclowndu93150.thaumaturge.content.spell.item.FocusItem;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;

import java.util.function.Consumer;

public class ArcaneSingularityFocusItem extends FocusItem {
    public ArcaneSingularityFocusItem(Item.Properties properties) {
        super(properties);
    }

    @Override
    public void appendHoverText(
            ItemStack stack,
            Item.TooltipContext context,
            TooltipDisplay display,
            Consumer<Component> tooltip,
            TooltipFlag flag) {
        super.appendHoverText(stack, context, display, tooltip, flag);
        tooltip.accept(Component.translatable("tooltip.thaumicavaritia.arcane_singularity_focus")
                .withStyle(ChatFormatting.GRAY, ChatFormatting.ITALIC));
    }
}
