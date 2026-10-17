package com.blackmoss.thaumicavaritia.mixin;

import com.blackmoss.thaumicavaritia.block.CosmicNeutroniumEssentiaSmelteryBlockEntity;
import com.leclowndu93150.thaumaturge.content.essentia.smeltery.BlockEntitySmelter;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BlockEntitySmelter.class)
public abstract class BlockEntitySmelterMixin {
    @Inject(method = "tick", at = @At("HEAD"), cancellable = true)
    private void thaumicavaritia$cosmicTick(CallbackInfo ci) {
        if ((Object) this instanceof CosmicNeutroniumEssentiaSmelteryBlockEntity cosmic) {
            cosmic.cosmicTick();
            ci.cancel();
        }
    }
}
