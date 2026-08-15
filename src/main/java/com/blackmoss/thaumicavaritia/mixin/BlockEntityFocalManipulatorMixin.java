package com.blackmoss.thaumicavaritia.mixin;

import com.blackmoss.thaumicavaritia.content.block.InfinityChargerBlock;
import com.blackmoss.thaumicavaritia.content.item.ArcaneSingularityFocusItem;
import com.leclowndu93150.thaumaturge.content.casters.BlockEntityFocalManipulator;
import com.leclowndu93150.thaumaturge.content.casters.ItemFocus;
import net.minecraft.server.level.ServerLevel;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

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

    // 无限充能器：焦点操纵器上方放置时，灵气抽取视为全额满足（不消耗世界灵气）。
    @Inject(method = "spendAura", at = @At("HEAD"), cancellable = true)
    private void thaumicavaritia$infiniteAura(ServerLevel level, float amount, CallbackInfoReturnable<Float> cir) {
        BlockEntityFocalManipulator self = (BlockEntityFocalManipulator) (Object) this;
        if (level.getBlockState(self.getBlockPos().above()).getBlock() instanceof InfinityChargerBlock) {
            cir.setReturnValue(amount);
        }
    }
}
