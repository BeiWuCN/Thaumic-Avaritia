// 本 mod 接口：供 EntityCausalityCollapserPearl 通过接口触发灵气裂隙坍缩，
// 避免直接引用 Thaumaturge content 包内部类（API 边界规范）。
// 由 EntityFluxRiftMixin 以 @Mixin 注入到 Thaumaturge 的 EntityFluxRift 上。
package com.blackmoss.thaumicavaritia.api.interfaces;

public interface IFluxRiftCollapsible {
    // 触发裂隙坍缩，并在指定 tick 数内（durationTicks）同步坍缩完毕，与黑洞生命周期等长。
    void thaumicavaritia$collapse(int durationTicks);
}
