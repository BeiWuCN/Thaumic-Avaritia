package com.blackmoss.thaumicavaritia.mixin;

import com.blackmoss.thaumicavaritia.api.interfaces.IFluxRiftCollapsible;
import com.leclowndu93150.thaumaturge.content.entity.EntityFluxRift;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(EntityFluxRift.class)
public abstract class EntityFluxRiftMixin implements IFluxRiftCollapsible {

    @Shadow
    public abstract void setCollapse(boolean collapse);

    @Shadow
    public abstract int getRiftSize();

    @Unique
    private int thaumicavaritia$collapseSpeed = 1;

    @Override
    public void thaumicavaritia$collapse(int durationTicks) {
        setCollapse(true);
        int maxSize = getRiftSize();
        if (durationTicks > 0) {
            thaumicavaritia$collapseSpeed = Math.max(1, (int) Math.ceil((double) maxSize / durationTicks));
        }
    }

    @Redirect(
            method = "serverTick",
            at = @At(value = "INVOKE", target = "Lcom/leclowndu93150/thaumaturge/content/entity/EntityFluxRift;setRiftSize(I)V"))
    private void thaumicavaritia$redirectRiftSize(EntityFluxRift self, int size) {
        if (size == self.getRiftSize() - 1 && thaumicavaritia$collapseSpeed > 1) {
            self.setRiftSize(size - (thaumicavaritia$collapseSpeed - 1));
        } else {
            self.setRiftSize(size);
        }
    }
}
