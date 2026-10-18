package com.blackmoss.thaumicavaritia.registry;

import com.blackmoss.thaumicavaritia.ThaumicAvaritia;
import com.blackmoss.thaumicavaritia.block.CosmicNeutroniumEssentiaSmelteryBlock;
import com.blackmoss.thaumicavaritia.block.InfinityChargerBlock;
import com.blackmoss.thaumicavaritia.block.NodeAnchorBlock;
import com.blackmoss.thaumicavaritia.block.OriginJarBlock;
import com.blackmoss.thaumicavaritia.block.infinituscollector.InfinitusCollectorBlock;
import com.blackmoss.thaumicavaritia.block.infinituscollector.InfinitusCollectorSettings;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class TABlocks {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(ThaumicAvaritia.MODID);
    public static final DeferredBlock<Block> LIMIT_ALCHEMICAL_CONSTRUCT = BLOCKS.registerSimpleBlock("limit_alchemical_construct");
    public static final DeferredBlock<Block> LIMIT_FRAME = BLOCKS.registerSimpleBlock("limit_frame");
    public static final DeferredBlock<InfinityChargerBlock> INFINITY_CHARGER = BLOCKS.registerBlock(
            "infinity_charger",
            InfinityChargerBlock::new,
            props -> props.mapColor(MapColor.METAL)
                    .strength(1.5F, 10.0F)
                    .sound(SoundType.METAL)
                    .noOcclusion()
                    .pushReaction(PushReaction.BLOCK));

    public static final DeferredBlock<CosmicNeutroniumEssentiaSmelteryBlock> COSMIC_NEUTRONIUM_ESSENTIA_SMELTERY = BLOCKS.registerBlock(
            "cosmic_neutronium_essentia_smeltery",
            CosmicNeutroniumEssentiaSmelteryBlock::new,
            props -> props.mapColor(MapColor.METAL)
                    .strength(1.5F, 10.0F)
                    .sound(SoundType.METAL)
                    .noOcclusion()
                    .pushReaction(PushReaction.BLOCK));

    public static final DeferredBlock<NodeAnchorBlock> NODE_ANCHOR = BLOCKS.registerBlock(
            "node_anchor",
            NodeAnchorBlock::new,
            props -> props.mapColor(MapColor.METAL)
                    .strength(1.5F, 10.0F)
                    .sound(SoundType.METAL)
                    .noOcclusion()
                    .pushReaction(PushReaction.BLOCK));

    public static final DeferredBlock<OriginJarBlock> ORIGIN_JAR = BLOCKS.registerBlock(
            "origin_jar",
            OriginJarBlock::new,
            props -> props.mapColor(MapColor.COLOR_CYAN)
                    .strength(1.5F, 10.0F)
                    .sound(SoundType.GLASS)
                    .noOcclusion()
                    .pushReaction(PushReaction.BLOCK));

    public static final DeferredBlock<InfinitusCollectorBlock> INFINITUS_COLLECTOR = registerCollector(
            "infinitus_collector", InfinitusCollectorSettings.builder()
                    .collectChance(200)
                    .aspectCost(64)
                    .pollution(0.5F)
                    .lossDivisor(1)
                    .build());
    public static final DeferredBlock<InfinitusCollectorBlock> INFINITUS_COLLECTOR_COMPACT = registerCollector(
            "infinitus_collector_compact", InfinitusCollectorSettings.builder()
                    .collectChance(150)
                    .aspectCost(32)
                    .pollution(0.25F)
                    .lossDivisor(2)
                    .build());
    public static final DeferredBlock<InfinitusCollectorBlock> INFINITUS_COLLECTOR_DENSE = registerCollector(
            "infinitus_collector_dense", InfinitusCollectorSettings.builder()
                    .collectChance(100)
                    .pollution(0.1F)
                    .lossDivisor(4)
                    .drainAura(true)
                    .build());
    public static final DeferredBlock<InfinitusCollectorBlock> INFINITUS_COLLECTOR_ENDLESS = registerCollector(
            "infinitus_collector_endless", InfinitusCollectorSettings.builder()
                    .collectChance(60)
                    .selfPowered(true)
                    .build());

    private TABlocks() {
    }

    private static DeferredBlock<InfinitusCollectorBlock> registerCollector(String name, InfinitusCollectorSettings settings) {
        return BLOCKS.registerBlock(name,
                InfinitusCollectorBlock::new,
                props -> props.mapColor(MapColor.METAL)
                        .strength(1.5F, 10.0F)
                        .sound(SoundType.METAL)
                        .noOcclusion()
                        .pushReaction(PushReaction.BLOCK)
        );
    }

    public static void register(IEventBus modBus) {
        BLOCKS.register(modBus);
    }
}
