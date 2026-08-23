// 注册自定义配方条件到 NeoForge 条件注册表（neoforge:condition_serializers）。
// TACondition：Config 开启时加载神秘无尽改写版无尽配方。
package com.blackmoss.thaumicavaritia.config;

import com.blackmoss.thaumicavaritia.ThaumicAvaritia;
import com.mojang.serialization.MapCodec;
import net.minecraft.resources.Identifier;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.conditions.ICondition;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import net.neoforged.neoforge.registries.RegisterEvent;

@EventBusSubscriber(modid = ThaumicAvaritia.MODID)
public final class TAConditions {

    // 配方 JSON 里用的条件类型 id：thaumicavaritia:use_thaumic_avaritia_recipes
    public static final Identifier USE_THAUMIC_AVARITIA_RECIPES =
            Identifier.fromNamespaceAndPath(ThaumicAvaritia.MODID, "use_thaumicavaritia_recipes");

    private TAConditions() {
    }

    @SubscribeEvent
    public static void onRegisterConditions(RegisterEvent event) {
        event.register(NeoForgeRegistries.CONDITION_SERIALIZERS.key(),
                helper -> helper.register(USE_THAUMIC_AVARITIA_RECIPES, TACondition.CODEC));
    }
}
