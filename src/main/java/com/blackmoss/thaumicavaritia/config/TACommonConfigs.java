package com.blackmoss.thaumicavaritia.config;

import net.neoforged.neoforge.common.ModConfigSpec;

public final class TACommonConfigs {
    public static final ModConfigSpec.Builder COMMON = new ModConfigSpec.Builder();

    public static final ModConfigSpec.BooleanValue USE_THAUMICAVARITIA_RECIPES = COMMON
            .define("use_thaumicavaritia_recipes", true);

    public TACommonConfigs() {
    }
}
