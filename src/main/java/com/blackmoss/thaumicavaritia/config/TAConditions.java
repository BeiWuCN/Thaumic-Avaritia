package com.blackmoss.thaumicavaritia.config;

import com.blackmoss.thaumicavaritia.ThaumicAvaritia;
import net.minecraft.resources.Identifier;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import net.neoforged.neoforge.registries.RegisterEvent;

@EventBusSubscriber(modid = ThaumicAvaritia.MODID)
public final class TAConditions {
    public static final Identifier USE_THAUMIC_AVARITIA_RECIPES = ThaumicAvaritia.identifier("use_thaumicavaritia_recipes");

    private TAConditions() {
    }

    @SubscribeEvent
    public static void onRegisterConditions(RegisterEvent event) {
        event.register(NeoForgeRegistries.CONDITION_SERIALIZERS.key(),
                helper -> helper.register(USE_THAUMIC_AVARITIA_RECIPES, TACondition.CODEC));
    }
}
