package com.blackmoss.thaumicavaritia.mixin.client;

import com.leclowndu93150.thaumaturge.client.hud.CasterHudOverlay;
import committee.nova.mods.avaritia.api.utils.lang.TextUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.client.resources.language.I18n;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(CasterHudOverlay.class)
public abstract class CasterHudOverlayMixin {
    @Unique
    private static final String INFINITY_KEY = "tooltip.infinity";

    @Unique
    private static final double INFINITY_THRESHOLD = 1_000_000.0;

    @Unique
    private static final ChatFormatting[] thaumicavaritia$FABULOUS = {
            ChatFormatting.RED,
            ChatFormatting.GOLD,
            ChatFormatting.YELLOW,
            ChatFormatting.GREEN,
            ChatFormatting.AQUA,
            ChatFormatting.BLUE,
            ChatFormatting.LIGHT_PURPLE
    };

    @Redirect(
            method = "drawBar",
            at = @At(value = "INVOKE", target = "Ljava/text/NumberFormat;format(D)Ljava/lang/String;"))
    private String thaumicavaritia$infiniteHudVis(java.text.NumberFormat instance, double number) {
        if (number < 0 || number >= INFINITY_THRESHOLD) {
            return TextUtils.ludicrousFormatting(I18n.get(INFINITY_KEY), thaumicavaritia$FABULOUS, 80.0, 1, 1);
        }
        return instance.format(number);
    }
}
