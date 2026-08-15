// 方块注册：无限充能器（涌流充能器，为奥术工作台提供无限灵气）。
package com.blackmoss.thaumicavaritia.registry;

import com.blackmoss.thaumicavaritia.ThaumicAvaritia;
import com.blackmoss.thaumicavaritia.content.block.InfinityChargerBlock;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class TABlocks {

    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(ThaumicAvaritia.MODID);

    // 无限充能器：置于奥术工作台上方，无限提供灵气（逻辑见 mixin）。
    public static final DeferredBlock<InfinityChargerBlock> INFINITY_CHARGER = BLOCKS.registerBlock(
            "infinity_charger",
            InfinityChargerBlock::new,
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
