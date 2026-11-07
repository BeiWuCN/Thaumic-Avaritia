package com.blackmoss.thaumicavaritia.mixin;

import com.blackmoss.thaumicavaritia.api.interfaces.IFluxRiftCollapsible;
import com.leclowndu93150.thaumaturge.content.entity.EntityFluxRift;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(EntityFluxRift.class)
public abstract class EntityFluxRiftMixin implements IFluxRiftCollapsible {
    @Unique
    private int thaumicavaritia$collapseSpeed = 1;

    @Shadow
    public abstract void beginCollapse();

    @Shadow
    public abstract int currentSize();

    @Shadow
    public abstract boolean isCollapsing();

    @Override
    public void thaumicavaritia$collapse(int durationTicks) {
        beginCollapse();
        int maxSize = currentSize();
        if (durationTicks > 0) {
            thaumicavaritia$collapseSpeed = Math.max(1, (int) Math.ceil((double) maxSize / durationTicks));
        }
    }

    @ModifyVariable(method = "resize(I)V", at = @At("HEAD"), argsOnly = true, name = "size")
    private int thaumicavaritia$accelerateCollapse(int size) {
        if (thaumicavaritia$collapseSpeed > 1
                && isCollapsing()
                && size == currentSize() - 1) {
            return Math.max(1, size - (thaumicavaritia$collapseSpeed - 1));
        }
        return size;
    }
}
