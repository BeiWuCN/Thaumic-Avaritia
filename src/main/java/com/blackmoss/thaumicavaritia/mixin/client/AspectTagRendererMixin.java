// 万物起源之罐的数量显示：amount == 32767（无限标记）时把数字渲染为 ∞。
// 作用于 Thaumaturge AspectTagRenderer.drawAmount（GUI 中要素标签的角标数字）。
package com.blackmoss.thaumicavaritia.mixin.client;

import com.blackmoss.thaumicavaritia.content.essentia.jar.BlockEntityOriginJar;
import com.leclowndu93150.thaumaturge.client.render.aspect.AspectTagRenderer;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AspectTagRenderer.class)
public abstract class AspectTagRendererMixin {

    // 命中私有 drawAmount(GuiGraphicsExtractor, Font, double, double, float)：
    // 当数量为无限标记（32767）时，用 ∞ 文本替代数字并取消原绘制。
    @Inject(
            method = "drawAmount(Lnet/minecraft/client/gui/GuiGraphicsExtractor;Lnet/minecraft/client/gui/Font;DDF)V",
            at = @At("HEAD"),
            cancellable = true)
    private static void thaumicavaritia$infiniteAmount(
            GuiGraphicsExtractor graphics,
            Font font,
            double x,
            double y,
            float amount,
            CallbackInfo ci) {
        if (amount != BlockEntityOriginJar.PER_ASPECT_AMOUNT) {
            return;
        }
        // 复刻原版 drawAmount 的定位，仅文本替换为 ∞。
        String text = "∞";
        int width = font.width(text);
        int fontHeight = font.lineHeight;
        boolean large = true;
        float textScale = large ? 1.0F : 0.5F;
        float q = large ? 0.5F : 1.0F;
        float textX = (32.0F - (float) width + (float) ((int) x) * 2.0F) * q;
        float textY = (32.0F - (float) fontHeight + (float) ((int) y) * 2.0F) * q;
        graphics.pose().pushMatrix();
        graphics.pose().scale(textScale, textScale);
        graphics.text(font, net.minecraft.network.chat.Component.literal(text), (int) (textX - 1.0F), (int) textY, 0xFF000000, false);
        graphics.text(font, net.minecraft.network.chat.Component.literal(text), (int) (textX + 1.0F), (int) textY, 0xFF000000, false);
        graphics.text(font, net.minecraft.network.chat.Component.literal(text), (int) textX, (int) (textY - 1.0F), 0xFF000000, false);
        graphics.text(font, net.minecraft.network.chat.Component.literal(text), (int) textX, (int) (textY + 1.0F), 0xFF000000, false);
        graphics.text(font, net.minecraft.network.chat.Component.literal(text), (int) textX, (int) textY, 0xFFFFFFFF, false);
        graphics.pose().popMatrix();
        ci.cancel();
    }
}
