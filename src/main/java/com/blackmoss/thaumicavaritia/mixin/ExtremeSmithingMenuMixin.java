package com.blackmoss.thaumicavaritia.mixin;

import committee.nova.mods.avaritia.common.menu.ExtremeSmithingMenu;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(ExtremeSmithingMenu.class)
public abstract class ExtremeSmithingMenuMixin {
//    @Inject(method = "createResult", at = @At("TAIL"))
//    private void thaumicavaritia$clearGatedSmithingResult(CallbackInfo ci) {
//        ItemCombinerMenuAccessorMixin accessor = (ItemCombinerMenuAccessorMixin) this;
//        ItemStack out = accessor.thaumicavaritia$getResultSlots().getItem(0);
//        if (NotQualifiedGate.isGated(out)) {
//            return;
//        }
//        if (!NotQualifiedGate.isResearchUnlocked(accessor.thaumicavaritia$getPlayer(), out)) {
//            accessor.thaumicavaritia$getResultSlots().setItem(0, ItemStack.EMPTY);
//        }
//    }
}
