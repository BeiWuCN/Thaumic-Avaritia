package com.blackmoss.thaumicavaritia.mixin;

import committee.nova.mods.avaritia.common.menu.TierCraftMenu;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ResultContainer;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(TierCraftMenu.class)
public abstract class TierCraftMenuMixin {
    @Shadow
    @Final
    private ResultContainer result;

    @Shadow
    @Final
    private Player player;

//    @Inject(method = "slotsChanged", at = @At("TAIL"))
//    private void thaumicavaritia$clearGatedResult(Container matrix, CallbackInfo ci) {
//        ItemStack out = result.getItem(0);
//        if (NotQualifiedGate.isGated(out)) {
//            return;
//        }
//        if (!NotQualifiedGate.isResearchUnlocked(player, out)) {
//            result.setItem(0, ItemStack.EMPTY);
//        }
//    }
}
