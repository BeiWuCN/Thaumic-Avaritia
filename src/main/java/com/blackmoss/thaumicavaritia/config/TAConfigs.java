package com.blackmoss.thaumicavaritia.config;

import com.blackmoss.thaumicavaritia.ThaumicAvaritia;
import net.neoforged.neoforge.common.ModConfigSpec;

public class TAConfigs {

    // 是否使用神秘无尽改写版无尽配方（默认 true）。
    public static final ModConfigSpec.Builder COMMON = new ModConfigSpec.Builder();
    private static final String NAME_KEY = ThaumicAvaritia.MODID + ".config.";

    public static final ModConfigSpec.ConfigValue<Boolean> USE_THAUMICAVARITIA_RECIPES =
            COMMON.comment("Use Thaumic Avaritia's reworked infinity recipes (true) or the original avaritia recipes (false).")
                    .translation(NAME_KEY + "useThaumicAvaritiaRecipes")
                    .define("useThaumicAvaritiaRecipes", true);
}
