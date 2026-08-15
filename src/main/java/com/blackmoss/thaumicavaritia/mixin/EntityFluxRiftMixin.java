// 为 Thaumaturge 的 EntityFluxRift 注入本 mod 接口 IFluxRiftCollapsible，
// 使悖论珍珠等本 mod 代码能通过接口触发裂隙坍缩，而无需直接依赖 content 内部类。
// 同时支持"同步坍缩"：按指定时长加速裂隙每 tick 收缩量，使裂隙与黑洞以相同比例消亡。
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

    // 同步坍缩速率：每 tick 收缩量（默认 1，即原版行为）。
    @Unique
    private int thaumicavaritia$collapseSpeed = 1;

    @Override
    public void thaumicavaritia$collapse(int durationTicks) {
        setCollapse(true); // 记录 maxSize = 当前 size
        int maxSize = getRiftSize();
        if (durationTicks > 0) {
            thaumicavaritia$collapseSpeed = Math.max(1, (int) Math.ceil((double) maxSize / durationTicks));
        }
    }

    // serverTick 中 setRiftSize 有"坍缩递减"与"增长递增"两处调用：
    // 仅当参数为 getRiftSize() - 1（坍缩分支）时按速率加速，其余原样透传。
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
