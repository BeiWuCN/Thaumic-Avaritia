package com.blackmoss.thaumicavaritia.data;

import com.blackmoss.thaumicavaritia.ThaumicAvaritia;
import com.blackmoss.thaumicavaritia.data.datamap.ItemAspectsProvider;
import com.blackmoss.thaumicavaritia.data.lang.TAEnUsProvider;
import com.blackmoss.thaumicavaritia.data.lang.TAZhCnProvider;
import com.leclowndu93150.thaumaturge.api.aspect.IAspect;
import java.util.List;
import java.util.Set;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.data.event.GatherDataEvent;

@EventBusSubscriber(modid = ThaumicAvaritia.MODID)
public final class TADataGenerators {

    private TADataGenerators() {
    }

    @SubscribeEvent
    public static void onGatherData(GatherDataEvent.Client event) {
        RegistrySetBuilder registries = new RegistrySetBuilder()
                .add(IAspect.REGISTRY_KEY, TAAspectBootstrap::bootstrap);
        event.createDatapackRegistryObjects(registries);

        event.createProvider(TAEnUsProvider::new);
        event.createProvider(TAZhCnProvider::new);
        event.createProvider(TARecipeProvider.Runner::new);
        event.createProvider(TAModelProvider::new);
        event.createProvider(TATagsProvider::new);
        event.createProvider(ItemAspectsProvider::new);

        event.createProvider((output, lookupProvider) -> new LootTableProvider(
                output,
                Set.of(),
                List.of(new LootTableProvider.SubProviderEntry(
                        TABlockLootSubProvider::new, LootContextParamSets.BLOCK)),
                lookupProvider));
    }
}
