package com.blackmoss.thaumicavaritia.mixin;

import com.blackmoss.thaumicavaritia.block.InfinityChargerBlock;
import com.leclowndu93150.thaumaturge.content.spell.manipulator.BlockEntityFocalManipulator;
import net.minecraft.server.level.ServerLevel;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BlockEntityFocalManipulator.class)
public abstract class BlockEntityFocalManipulatorMixin {
    @Inject(method = "drain", at = @At("HEAD"), cancellable = true)
    private void thaumicavaritia$infiniteAura(ServerLevel server, float amount, CallbackInfoReturnable<Float> cir) {
        BlockEntityFocalManipulator self = (BlockEntityFocalManipulator) (Object) this;
        if (server.getBlockState(self.getBlockPos().above()).getBlock() instanceof InfinityChargerBlock) {
            cir.setReturnValue(amount);
        }
    }
}
