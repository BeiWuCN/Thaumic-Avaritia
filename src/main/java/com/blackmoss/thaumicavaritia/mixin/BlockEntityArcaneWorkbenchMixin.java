package com.blackmoss.thaumicavaritia.mixin;

import com.blackmoss.thaumicavaritia.block.InfinityChargerBlock;
import com.leclowndu93150.thaumaturge.content.workbench.BlockEntityArcaneWorkbench;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BlockEntityArcaneWorkbench.class)
public abstract class BlockEntityArcaneWorkbenchMixin {
    @Shadow
    public int auraVis;

    @Inject(method = "refreshAura", at = @At("HEAD"), cancellable = true)
    private void thaumicavaritia$infiniteAuraOnRefresh(CallbackInfo ci) {
        if (thaumicavaritia$isInfinityPowered()) {
            auraVis = Integer.MAX_VALUE;
            ci.cancel();
        }
    }

    @Inject(method = "spendAura", at = @At("HEAD"), cancellable = true)
    private void thaumicavaritia$noAuraCost(CallbackInfoReturnable<Boolean> cir) {
        if (thaumicavaritia$isInfinityPowered()) {
            cir.setReturnValue(true);
        }
    }

    @Unique
    private boolean thaumicavaritia$isInfinityPowered() {
        BlockEntityArcaneWorkbench self = (BlockEntityArcaneWorkbench) (Object) this;
        Level level = self.getLevel();
        return level != null
                && !level.isClientSide()
                && level.getBlockState(self.getBlockPos().above()).getBlock() instanceof InfinityChargerBlock;
    }
}
