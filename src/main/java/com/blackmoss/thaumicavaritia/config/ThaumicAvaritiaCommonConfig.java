package com.blackmoss.thaumicavaritia.config;

import net.neoforged.neoforge.common.ModConfigSpec;

public final class ThaumicAvaritiaCommonConfig {
    public static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    public static final ModConfigSpec.BooleanValue USE_THAUMICAVARITIA_RECIPES = BUILDER
            .define("use_thaumicavaritia_recipes", true);
}
