package com.blackmoss.thaumicavaritia.mixin;

import com.blackmoss.thaumicavaritia.block.InfinityChargerBlock;
import com.blackmoss.thaumicavaritia.item.ArcaneSingularityFocusItem;
import com.leclowndu93150.thaumaturge.content.spell.item.FocusItem;
import com.leclowndu93150.thaumaturge.content.spell.manipulator.BlockEntityFocalManipulator;
import net.minecraft.server.level.ServerLevel;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BlockEntityFocalManipulator.class)
public abstract class BlockEntityFocalManipulatorMixin {
    @Redirect(
            method = "startInscribing",
            at = @At(
                    value = "INVOKE",
                    target = "Lcom/leclowndu93150/thaumaturge/content/casters/ItemFocus;getMaxComplexity()I"))
    private int thaumicavaritia$waiveAssemblyVisForSingularityFocus(FocusItem focusItem) {
        if (focusItem instanceof ArcaneSingularityFocusItem) {
            return 0;
        }
        return focusItem.getMaxComplexity();
    }

    @Inject(method = "spendAura", at = @At("HEAD"), cancellable = true)
    private void thaumicavaritia$infiniteAura(ServerLevel level, float amount, CallbackInfoReturnable<Float> cir) {
        BlockEntityFocalManipulator self = (BlockEntityFocalManipulator) (Object) this;
        if (level.getBlockState(self.getBlockPos().above()).getBlock() instanceof InfinityChargerBlock) {
            cir.setReturnValue(amount);
        }
    }
}
