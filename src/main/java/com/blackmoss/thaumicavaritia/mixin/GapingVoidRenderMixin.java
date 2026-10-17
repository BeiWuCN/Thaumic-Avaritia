
package com.blackmoss.thaumicavaritia.mixin;

import committee.nova.mods.avaritia.client.render.entity.GapingVoidRender;
import committee.nova.mods.avaritia.common.entity.GapingVoidEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(GapingVoidRender.class)
public abstract class GapingVoidRenderMixin {
    @Redirect(method = "submit*",
            at = @At(value = "INVOKE", target = "Lcommittee/nova/mods/avaritia/common/entity/GapingVoidEntity;getVoidScale(D)D"))
    private static double thaumicavaritia$largerVoidScale(double age) {
        return GapingVoidEntity.getVoidScale(age) * 2.0;
    }
}
