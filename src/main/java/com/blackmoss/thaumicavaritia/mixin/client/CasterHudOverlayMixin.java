package com.blackmoss.thaumicavaritia.mixin.client;

import com.leclowndu93150.thaumaturge.client.hud.CasterHudOverlay;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(CasterHudOverlay.class)
public abstract class CasterHudOverlayMixin {
    @Unique
    private static final String INFINITY_SYMBOL = "∞";

    @Unique
    private static final double INFINITY_THRESHOLD = Integer.MAX_VALUE / 100.0;

    @Redirect(
            method = "drawBar",
            at = @At(value = "INVOKE", target = "Ljava/text/NumberFormat;format(D)Ljava/lang/String;"))
    private String thaumicavaritia$infiniteHudVis(java.text.NumberFormat instance, double value) {
        if (value < 0 || value >= INFINITY_THRESHOLD) {
            return INFINITY_SYMBOL;
        }
        return instance.format(value);
    }
}
