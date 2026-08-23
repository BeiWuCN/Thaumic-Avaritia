// 自定义配方条件：当 TAConfig.useThaumicAvaritiaRecipes() 为 true 时返回 true。
// 用于覆盖版无尽配方 JSON 的 "neoforge:conditions" 字段——Config 关闭时跳过覆盖版，
// 从而回退到 avaritia 原版配方。
package com.blackmoss.thaumicavaritia.config;

import com.mojang.serialization.MapCodec;
import net.neoforged.neoforge.common.conditions.ICondition;
import org.jspecify.annotations.NonNull;

public record TACondition() implements ICondition {

    public static final MapCodec<TACondition> CODEC = MapCodec.unit(TACondition::new);

    @Override
    public boolean test(@NonNull IContext context) {
        // 默认使用神秘无尽改写配方（Config 为 true 时加载覆盖版）。
        return TAConfigs.USE_THAUMICAVARITIA_RECIPES.get();
    }

    @Override
    public @NonNull MapCodec<? extends ICondition> codec() {
        return CODEC;
    }
}
