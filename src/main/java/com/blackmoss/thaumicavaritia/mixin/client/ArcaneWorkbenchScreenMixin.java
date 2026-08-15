// 涌流充能器：奥术工作台界面中，当可用灵气为无限（Integer.MAX_VALUE）时显示为"∞"。
// @Redirect 拦截 vis_available 的 Component.translatable 调用（target 为 Minecraft 的
// Component 类，IDE 可解析，不受 flatDir 依赖限制）；其它翻译调用原样透传。
package com.blackmoss.thaumicavaritia.mixin.client;

import com.leclowndu93150.thaumaturge.client.screen.workbench.ArcaneWorkbenchScreen;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ArcaneWorkbenchScreen.class)
public abstract class ArcaneWorkbenchScreenMixin {

    private static final int INFINITE_VIS = Integer.MAX_VALUE;

    @Redirect(
            method = "extractBackgroundOverlay",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/network/chat/Component;translatable(Ljava/lang/String;[Ljava/lang/Object;)Lnet/minecraft/network/chat/Component;"))
    private Component thaumicavaritia$infinityVisText(String key, Object[] args) {
        if ("gui.thaumaturge.arcane_workbench.vis_available".equals(key)
                && ((ArcaneWorkbenchScreen) (Object) this).getMenu().getCachedVis() >= INFINITE_VIS) {
            return Component.translatable(key, "\u221E");
        }
        return Component.translatable(key, args);
    }
}
