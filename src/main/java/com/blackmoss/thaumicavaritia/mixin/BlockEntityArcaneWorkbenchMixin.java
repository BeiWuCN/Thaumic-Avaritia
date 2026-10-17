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
    private void thaumicavaritia$noAuraCost(CallbackInfo ci) {
        if (thaumicavaritia$isInfinityPowered()) {
            ci.cancel();
        }
    }

    // 上方是否为无限充能器。
    @Unique
    private boolean thaumicavaritia$isInfinityPowered() {
        BlockEntityArcaneWorkbench self = (BlockEntityArcaneWorkbench) (Object) this;
        Level level = self.getLevel();
        return level != null
                && !level.isClientSide()
                && level.getBlockState(self.getBlockPos().above()).getBlock() instanceof InfinityChargerBlock;
    }
}
