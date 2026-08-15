// 方块实体类型注册：宇宙中子态素源质冶炼厂。
package com.blackmoss.thaumicavaritia.registry;

import com.blackmoss.thaumicavaritia.ThaumicAvaritia;
import com.blackmoss.thaumicavaritia.content.essentia.smeltery.BlockEntityCosmicNeutroniumEssentiaSmeltery;
import java.util.Set;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class TABlockEntities {

    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, ThaumicAvaritia.MODID);

    // 宇宙中子态素源质冶炼厂
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<BlockEntityCosmicNeutroniumEssentiaSmeltery>>
            COSMIC_NEUTRONIUM_ESSENTIA_SMELTERY = BLOCK_ENTITIES.register(
                    "cosmic_neutronium_essentia_smeltery",
                    () -> new BlockEntityType<>(
                            BlockEntityCosmicNeutroniumEssentiaSmeltery::new,
                            Set.of(TABlocks.COSMIC_NEUTRONIUM_ESSENTIA_SMELTERY.get())));

    private TABlockEntities() {
    }

    public static void register(IEventBus modBus) {
        BLOCK_ENTITIES.register(modBus);
    }
}
