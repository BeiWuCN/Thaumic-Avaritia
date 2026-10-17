package com.blackmoss.thaumicavaritia.config;

import com.mojang.serialization.MapCodec;
import net.neoforged.neoforge.common.conditions.ICondition;
import org.jspecify.annotations.NonNull;

public record TACondition() implements ICondition {

    public static final MapCodec<TACondition> CODEC = MapCodec.unit(TACondition::new);

    @Override
    public boolean test(@NonNull IContext context) {
        return TAConfigs.USE_THAUMICAVARITIA_RECIPES.get();
    }

    @Override
    public @NonNull MapCodec<? extends ICondition> codec() {
        return CODEC;
    }
}
