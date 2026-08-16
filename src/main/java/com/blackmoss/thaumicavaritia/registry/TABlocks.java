package com.blackmoss.thaumicavaritia.registry;

import com.blackmoss.thaumicavaritia.ThaumicAvaritia;
import com.blackmoss.thaumicavaritia.content.block.CosmicNeutroniumEssentiaSmelteryBlock;
import com.blackmoss.thaumicavaritia.content.block.InfinityChargerBlock;
import com.blackmoss.thaumicavaritia.content.block.NodeAnchorBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class TABlocks {

    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(ThaumicAvaritia.MODID);

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

    private TABlocks() {
    }

    public static void register(IEventBus modBus) {
        BLOCKS.register(modBus);
    }
}
