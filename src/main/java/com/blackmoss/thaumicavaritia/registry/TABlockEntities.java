package com.blackmoss.thaumicavaritia.registry;

import com.blackmoss.thaumicavaritia.ThaumicAvaritia;
import com.blackmoss.thaumicavaritia.block.CosmicNeutroniumEssentiaSmelteryBlockEntity;
import com.blackmoss.thaumicavaritia.block.InfinitusCollectorBlockEntity;
import com.blackmoss.thaumicavaritia.block.NodeAnchorBlockEntity;
import com.blackmoss.thaumicavaritia.block.OriginJarBlockEntity;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.Set;

public final class TABlockEntities {

    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, ThaumicAvaritia.MODID);

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<CosmicNeutroniumEssentiaSmelteryBlockEntity>>
            COSMIC_NEUTRONIUM_ESSENTIA_SMELTERY = BLOCK_ENTITIES.register(
            "cosmic_neutronium_essentia_smeltery",
            () -> new BlockEntityType<>(
                    CosmicNeutroniumEssentiaSmelteryBlockEntity::new,
                    Set.of(TABlocks.COSMIC_NEUTRONIUM_ESSENTIA_SMELTERY.get())));

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<NodeAnchorBlockEntity>>
            NODE_ANCHOR = BLOCK_ENTITIES.register(
            "node_anchor",
            () -> new BlockEntityType<>(
                    NodeAnchorBlockEntity::new,
                    Set.of(TABlocks.NODE_ANCHOR.get())));

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<OriginJarBlockEntity>>
            ORIGIN_JAR = BLOCK_ENTITIES.register(
            "origin_jar",
            () -> new BlockEntityType<>(
                    OriginJarBlockEntity::new,
                    Set.of(TABlocks.ORIGIN_JAR.get())));

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<InfinitusCollectorBlockEntity>>
            INFINITUS_COLLECTOR = BLOCK_ENTITIES.register(
            "infinitus_collector",
            () -> new BlockEntityType<>(
                    InfinitusCollectorBlockEntity::new,
                    Set.of(
                            TABlocks.INFINITUS_COLLECTOR.get(),
                            TABlocks.INFINITUS_COLLECTOR_COMPACT.get(),
                            TABlocks.INFINITUS_COLLECTOR_DENSE.get(),
                            TABlocks.INFINITUS_COLLECTOR_ENDLESS.get())));

    private TABlockEntities() {
    }

    public static void register(IEventBus modBus) {
        BLOCK_ENTITIES.register(modBus);
    }
}