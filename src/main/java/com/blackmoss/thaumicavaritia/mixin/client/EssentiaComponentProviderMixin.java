// 万物起源之罐：抑制 Thaumaturge 默认 Jade 要素数量显示（数字），
// 由 OriginJarComponentProvider 按玩家配置显示 ∞ 或数字，避免重复。
package com.blackmoss.thaumicavaritia.mixin.client;

import com.blackmoss.thaumicavaritia.content.blockentity.OriginJarBlockEntity;
import com.leclowndu93150.thaumaturge.compat.jade.EssentiaComponentProvider;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import snownee.jade.api.BlockAccessor;
import snownee.jade.api.ITooltip;
import snownee.jade.api.config.IPluginConfig;

@Mixin(EssentiaComponentProvider.class)
public abstract class EssentiaComponentProviderMixin {

    @Inject(method = "appendTooltip*", at = @At("HEAD"), cancellable = true)
    private void thaumicavaritia$skipOriginJar(
            ITooltip tooltip, BlockAccessor accessor, IPluginConfig config, CallbackInfo ci) {
        BlockEntity be = accessor.getBlockEntity();
        if (be instanceof OriginJarBlockEntity) {
            // 万物起源之罐由 OriginJarComponentProvider 负责显示（∞ 或数字按玩家配置）。
            ci.cancel();
        }
    }
}
