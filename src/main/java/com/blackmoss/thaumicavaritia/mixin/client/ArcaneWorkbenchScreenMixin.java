package com.blackmoss.thaumicavaritia.mixin.client;

import com.leclowndu93150.thaumaturge.client.screen.workbench.ArcaneWorkbenchScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(ArcaneWorkbenchScreen.class)
public abstract class ArcaneWorkbenchScreenMixin {

    @Unique
    private static final int INFINITE_VIS = Integer.MAX_VALUE;

    @ModifyArg(method = "extractBackgroundOverlay",
            at = @At(
                    value = "INVOKE",
                    ordinal = 0,
                    target = "Lnet/minecraft/network/chat/Component;translatable(Ljava/lang/String;[Ljava/lang/Object;)Lnet/minecraft/network/chat/MutableComponent;"),
            index = 1)
    private Object[] thaumicavaritia$infinityVis(Object[] args) {
        if (((ArcaneWorkbenchScreen) (Object) this).getMenu().getCachedVis() >= INFINITE_VIS) {
            return new Object[]{"∞"};
        }
        return args;
    }
}
