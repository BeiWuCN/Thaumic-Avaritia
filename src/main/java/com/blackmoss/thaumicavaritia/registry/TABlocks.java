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

    // 极限炼金构件
    public static final DeferredBlock<Block> LIMIT_ALCHEMICAL_CONSTRUCT = BLOCKS.registerSimpleBlock("limit_alchemical_construct");

    // 极限框架
    public static final DeferredBlock<Block> LIMIT_FRAME = BLOCKS.registerSimpleBlock("limit_frame");

    // 无限充能器
    public static final DeferredBlock<InfinityChargerBlock> INFINITY_CHARGER = BLOCKS.registerBlock(
            "infinity_charger",
            InfinityChargerBlock::new,
            props -> props.mapColor(MapColor.METAL)
                    .strength(1.5F, 10.0F)
                    .sound(SoundType.METAL)
                    .noOcclusion()
                    .pushReaction(PushReaction.BLOCK));

    // 宇宙中子态素源质冶炼厂
    public static final DeferredBlock<CosmicNeutroniumEssentiaSmelteryBlock> COSMIC_NEUTRONIUM_ESSENTIA_SMELTERY = BLOCKS.registerBlock(
            "cosmic_neutronium_essentia_smeltery",
            CosmicNeutroniumEssentiaSmelteryBlock::new,
            props -> props.mapColor(MapColor.METAL)
                    .strength(1.5F, 10.0F)
                    .sound(SoundType.METAL)
                    .noOcclusion()
                    .pushReaction(PushReaction.BLOCK));

    // 节点锚：放在灵气节点下方，上方两格放涌流充能器时把节点每种要素固定为 256，否则锁定节点状态。
    public static final DeferredBlock<NodeAnchorBlock> NODE_ANCHOR = BLOCKS.registerBlock(
            "node_anchor",
            NodeAnchorBlock::new,
            props -> props.mapColor(MapColor.METAL)
                    .strength(1.5F, 10.0F)
                    .sound(SoundType.METAL)
                    .noOcclusion()
                    .pushReaction(PushReaction.BLOCK));

    // 万物起源之罐：除「无限」外所有源质各 32767 点，只出不进。
    public static final DeferredBlock<OriginJarBlock> ORIGIN_JAR = BLOCKS.registerBlock(
            "origin_jar",
            OriginJarBlock::new,
            props -> props.mapColor(MapColor.COLOR_CYAN)
                    .strength(1.5F, 10.0F)
                    .sound(SoundType.GLASS)
                    .noOcclusion()
                    .pushReaction(PushReaction.BLOCK));

    // 无限收集器核心（独立方块，共享同一类，注册时传各自行为参数 Settings；显示名/等级由 lang 决定）。
    // 第一档：一次性——耗节点本钱作柴，抽干即停。
    public static final DeferredBlock<InfinitusCollectorBlock> INFINITUS_COLLECTOR = registerCollector(
            "infinitus_collector", InfinitusCollectorSettings.builder()
                    .collectChance(200)
                    .aspectCost(64)
                    .pollution(0.5F)
                    .lossDivisor(1)
                    .build());
    // 第二档：效率增强，耗节点本钱更慢。
    public static final DeferredBlock<InfinitusCollectorBlock> INFINITUS_COLLECTOR_COMPACT = registerCollector(
            "infinitus_collector_compact", InfinitusCollectorSettings.builder()
                    .collectChance(150)
                    .aspectCost(32)
                    .pollution(0.25F)
                    .lossDivisor(2)
                    .build());
    // 第三档：吸收区块灵气稳定，不再啃噬节点本钱。
    public static final DeferredBlock<InfinitusCollectorBlock> INFINITUS_COLLECTOR_DENSE = registerCollector(
            "infinitus_collector_dense", InfinitusCollectorSettings.builder()
                    .collectChance(100)
                    .pollution(0.1F)
                    .lossDivisor(4)
                    .drainAura(true)
                    .build());
    // 第四档：自给永动。
    public static final DeferredBlock<InfinitusCollectorBlock> INFINITUS_COLLECTOR_ENDLESS = registerCollector(
            "infinitus_collector_endless", InfinitusCollectorSettings.builder()
                    .collectChance(60)
                    .selfPowered(true)
                    .build());

    // 收集器方块注册辅助：同材质，注册时传行为配置（便于自定义扩展）。
    private static DeferredBlock<InfinitusCollectorBlock> registerCollector(String name, InfinitusCollectorSettings settings) {
        return BLOCKS.registerBlock(name,
                InfinitusCollectorBlock::new,  // 使用新的构造
                props -> props.mapColor(MapColor.METAL)
                        .strength(1.5F, 10.0F)
                        .sound(SoundType.METAL)
                        .noOcclusion()
                        .pushReaction(PushReaction.BLOCK)
        );
    }

    private TABlocks() {
    }

    public static void register(IEventBus modBus) {
        BLOCKS.register(modBus);
    }
}
