// 无限结晶：无限要素的物理形态（人工制造的凭证），可堆叠，作为所有"无限"造物的配方材料。
// 不可合成、只能由无限收集器产出；datamap 带 INFINITUS×8，可被冶炼厂熔回无限源质。
package com.blackmoss.thaumicavaritia.content.item;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import org.jspecify.annotations.NonNull;

import java.util.function.Consumer;

public class InfinitusCrystalItem extends Item {

    public InfinitusCrystalItem(Properties properties) {
        super(properties);
    }

    @SuppressWarnings("deprecation") // 构建 classpath 中 5 参 appendHoverText 为唯一可覆写点。
    @Override
    public void appendHoverText(
            @NonNull ItemStack stack,
            Item.@NonNull TooltipContext context,
            @NonNull TooltipDisplay display,
            @NonNull Consumer<Component> tooltip,
            @NonNull TooltipFlag flag) {
        super.appendHoverText(stack, context, display, tooltip, flag);
        tooltip.accept(Component.translatable("item.thaumicavaritia.infinitus_crystal.desc")
                .withStyle(ChatFormatting.GRAY, ChatFormatting.ITALIC));
    }
}
