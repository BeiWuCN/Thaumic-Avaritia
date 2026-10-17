// 万物起源之罐的 tooltip 数量显示：无限标记（32767）渲染为 ∞。
// 作用于 Thaumaturge AspectContainerTooltipHandler.renderAspectList 的 " x<数量>" 行。
package com.blackmoss.thaumicavaritia.mixin.client;

import com.blackmoss.thaumicavaritia.block.OriginJarBlockEntity;
import com.leclowndu93150.thaumaturge.client.tooltip.AspectContainerTooltipHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(AspectContainerTooltipHandler.class)
public abstract class AspectContainerTooltipHandlerMixin {

    // renderAspectList 中 line.append(Component.literal(" x" + entry.amount()))：
    // 命中 Component.literal(String)，若数量为无限标记则改为 " x∞"。
    // 其它 literal 调用（"  "、header 等）不以 " x" 开头，不受影响。
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
                if (Integer.parseInt(num) == OriginJarBlockEntity.PER_ASPECT_AMOUNT) {
                    return " x∞";
                }
            } catch (NumberFormatException ignored) {
                // 非数字，保持原样。
            }
        }
        return text;
    }
}
