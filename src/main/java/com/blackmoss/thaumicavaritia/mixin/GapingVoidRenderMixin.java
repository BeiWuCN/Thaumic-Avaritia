// 黑洞渲染放大：将 GapingVoidEntity 的黑洞着色器缩放翻倍（悖论珍珠等落点黑洞观感更大）。
// 仅影响渲染大小，不影响黑洞实体的吸收/吞噬逻辑。
package com.blackmoss.thaumicavaritia.mixin;

import committee.nova.mods.avaritia.client.render.entity.GapingVoidRender;
import committee.nova.mods.avaritia.common.entity.GapingVoidEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(GapingVoidRender.class)
public abstract class GapingVoidRenderMixin {

    // submit 中 getVoidScale(age) * 1.35 决定黑洞渲染缩放，此处返回值翻倍。
    @Redirect(method = "submit*",
            at = @At(value = "INVOKE", target = "Lcommittee/nova/mods/avaritia/common/entity/GapingVoidEntity;getVoidScale(D)D"))
    private static double thaumicavaritia$largerVoidScale(double age) {
        return GapingVoidEntity.getVoidScale(age) * 2.0;
    }
}
