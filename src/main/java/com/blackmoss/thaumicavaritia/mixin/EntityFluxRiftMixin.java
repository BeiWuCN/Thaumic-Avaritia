// 为 Thaumaturge 的 EntityFluxRift 注入本 mod 接口 IFluxRiftCollapsible，
// 使悖论珍珠等本 mod 代码能通过接口触发裂隙坍缩，而无需直接依赖 content 内部类。
package com.blackmoss.thaumicavaritia.mixin;

import com.blackmoss.thaumicavaritia.api.interfaces.IFluxRiftCollapsible;
import com.leclowndu93150.thaumaturge.content.entity.EntityFluxRift;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(EntityFluxRift.class)
public abstract class EntityFluxRiftMixin implements IFluxRiftCollapsible {

    @Shadow
    public abstract void setCollapse(boolean collapse);

    @Override
    public void thaumicavaritia$collapse() {
        setCollapse(true);
    }
}
