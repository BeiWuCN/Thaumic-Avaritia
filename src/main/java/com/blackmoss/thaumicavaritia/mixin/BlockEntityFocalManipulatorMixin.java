package com.blackmoss.thaumicavaritia.mixin;

import com.blackmoss.thaumicavaritia.content.item.ArcaneSingularityFocusItem;
import com.leclowndu93150.thaumaturge.content.casters.BlockEntityFocalManipulator;
import com.leclowndu93150.thaumaturge.content.casters.ItemFocus;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(BlockEntityFocalManipulator.class)
public abstract class BlockEntityFocalManipulatorMixin {

    // 奥法奇点核心组装时不消耗 maxComplexity 带来的基础 Vis（其余核心保持原样）。
    @Redirect(
            method = "startCraft",
            at = @At(
                    value = "INVOKE",
                    target = "Lcom/leclowndu93150/thaumaturge/content/casters/ItemFocus;getMaxComplexity()I"))
    private int thaumicavaritia$waiveAssemblyVisForSingularityFocus(ItemFocus focusItem) {
        if (focusItem instanceof ArcaneSingularityFocusItem) {
            return 0;
        }
        return focusItem.getMaxComplexity();
    }
}
