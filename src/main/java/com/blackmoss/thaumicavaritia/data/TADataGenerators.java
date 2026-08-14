// DataGen 入口：注册 lang、配方、标签、模型与 datamap 生成器。
// 注入 Thaumaturge 的 aspect datapack 注册表，使配方/datamap 的 lookupOrThrow 可解析。
package com.blackmoss.thaumicavaritia.data;

import com.blackmoss.thaumicavaritia.ThaumicAvaritia;
import com.blackmoss.thaumicavaritia.data.lang.TAEnUsProvider;
import com.blackmoss.thaumicavaritia.data.lang.TAZhCnProvider;
import com.blackmoss.thaumicavaritia.data.model.TAModelProvider;
import com.blackmoss.thaumicavaritia.data.recipe.TARecipeProvider;
import com.blackmoss.thaumicavaritia.data.tag.TATagsProvider;
import com.blackmoss.thaumicavaritia.data.research.TAResearchCategory;
import com.leclowndu93150.thaumaturge.api.aspect.IAspect;
import com.leclowndu93150.thaumaturge.api.research.IResearchCategory;
import com.leclowndu93150.thaumaturge.data.worldgen.aspect.AspectBootstrap;
import net.minecraft.core.RegistrySetBuilder;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.data.event.GatherDataEvent;

@EventBusSubscriber(modid = ThaumicAvaritia.MODID)
public final class TADataGenerators {

    private TADataGenerators() {
    }

    @SubscribeEvent
    public static void onGatherData(GatherDataEvent.Client event) {
        // 注入 Thaumaturge 的 aspect 与 research_category datapack 注册表，供配方/datamap/类别 provider 解析。
        RegistrySetBuilder registries = new RegistrySetBuilder()
                .add(IAspect.REGISTRY_KEY, AspectBootstrap::bootstrap)
                .add(IResearchCategory.REGISTRY_KEY, TAResearchCategory::bootstrap);
        event.createDatapackRegistryObjects(registries);

        event.createProvider(TAEnUsProvider::new);
        event.createProvider(TAZhCnProvider::new);
        event.createProvider(TARecipeProvider.Runner::new);
        event.createProvider(TAModelProvider::new);
        event.createProvider(TATagsProvider::new);

//        event.createProvider(ItemAspectsProvider::new);
    }
}
