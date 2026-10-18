package com.blackmoss.thaumicavaritia;

import com.blackmoss.thaumicavaritia.config.TAClientConfigs;
import com.blackmoss.thaumicavaritia.config.TACommonConfigs;
import com.blackmoss.thaumicavaritia.data.ModBlockLootSubProvider;
import com.blackmoss.thaumicavaritia.data.ModModelProvider;
import com.blackmoss.thaumicavaritia.data.ModRecipeProvider;
import com.blackmoss.thaumicavaritia.data.lang.EnUsProvider;
import com.blackmoss.thaumicavaritia.data.lang.ZhCnProvider;
import com.blackmoss.thaumicavaritia.data.tag.ModBlockTagsProvider;
import com.blackmoss.thaumicavaritia.data.tag.ModItemTagsProvider;
import com.blackmoss.thaumicavaritia.registry.*;
import com.leclowndu93150.thaumaturge.api.aspect.IAspect;
import com.leclowndu93150.thaumaturge.data.worldgen.aspect.AspectBootstrap;
import com.mojang.logging.LogUtils;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import org.slf4j.Logger;

import java.util.List;
import java.util.Set;

@EventBusSubscriber(modid = ThaumicAvaritia.MODID)
@Mod(ThaumicAvaritia.MODID)
public class ThaumicAvaritia {
    public static final String MODID = "thaumicavaritia";
    public static final Logger LOGGER = LogUtils.getLogger();

    public ThaumicAvaritia(IEventBus modEventBus, ModContainer modContainer) {
        modContainer.registerConfig(ModConfig.Type.COMMON, TACommonConfigs.COMMON.build());
        modContainer.registerConfig(ModConfig.Type.CLIENT, TAClientConfigs.CLIENT.build());

        TACreativeModeTabs.register(modEventBus);
        TAItems.register(modEventBus);
        TABlocks.register(modEventBus);
        TABlockEntities.register(modEventBus);
        TAMenus.register(modEventBus);
        TAWandParts.register(modEventBus);
        TAEntities.register(modEventBus);
    }

    public static Identifier identifier(String path) {
        return Identifier.fromNamespaceAndPath(MODID, path);
    }

    @SubscribeEvent
    public static void onGatherData(GatherDataEvent.Client event) {
        event.createProvider(EnUsProvider::new);
        event.createProvider(ZhCnProvider::new);

        RegistrySetBuilder registries = new RegistrySetBuilder()
                .add(IAspect.REGISTRY_KEY, ctx -> {
                    AspectBootstrap.bootstrap(ctx);
                    TAAspects.bootstrap(ctx);
                });
        event.createDatapackRegistryObjects(registries);

        event.createProvider(ModRecipeProvider.Runner::new);
        event.createProvider(ModModelProvider::new);
//        event.createProvider(ItemAspectsProvider::new);

        event.createProvider(ModBlockTagsProvider::new);
        event.createProvider(ModItemTagsProvider::new);

        event.createProvider((output, lookupProvider) -> new LootTableProvider(
                output,
                Set.of(),
                List.of(new LootTableProvider.SubProviderEntry(
                        ModBlockLootSubProvider::new, LootContextParamSets.BLOCK)),
                lookupProvider));
    }
}