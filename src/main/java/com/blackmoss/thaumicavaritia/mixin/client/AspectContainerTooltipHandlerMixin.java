package com.blackmoss.thaumicavaritia.mixin.client;

import com.leclowndu93150.thaumaturge.client.tooltip.AspectContainerTooltipHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(AspectContainerTooltipHandler.class)
public abstract class AspectContainerTooltipHandlerMixin {
    @ModifyArg(
            method = "renderAspectList",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/network/chat/Component;literal(Ljava/lang/String;)Lnet/minecraft/network/chat/MutableComponent;"),
            index = 0)
    private static String thaumicavaritia$infiniteTooltip(String text) {
        if (text.startsWith(" x")) {
            String num = text.substring(2);
            try {
                if (Integer.parseInt(num) == Integer.MAX_VALUE) {
                    return " x∞";
                }
            } catch (NumberFormatException ignored) {
            }
        }
        return text;
    }
}
