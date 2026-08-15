// 宇宙中子态素源质冶炼厂：原版 BlockEntitySmelter.tick 为 private，无法继承覆写。
// 在 tick HEAD 判断当前 BE 是否为自定义冶炼厂，是则改走 cosmicTick()（0 燃料/0 污染/瞬间）并取消原逻辑。
package com.blackmoss.thaumicavaritia.mixin;

import com.blackmoss.thaumicavaritia.content.essentia.smeltery.BlockEntityCosmicNeutroniumEssentiaSmeltery;
import com.leclowndu93150.thaumaturge.content.essentia.smeltery.BlockEntitySmelter;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BlockEntitySmelter.class)
public abstract class BlockEntitySmelterMixin {

    @Inject(method = "tick", at = @At("HEAD"), cancellable = true)
    private void thaumicavaritia$cosmicTick(CallbackInfo ci) {
        if ((Object) this instanceof BlockEntityCosmicNeutroniumEssentiaSmeltery cosmic) {
            cosmic.cosmicTick();
            ci.cancel();
        }
    }
}
