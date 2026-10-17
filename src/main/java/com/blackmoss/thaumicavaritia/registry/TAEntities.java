package com.blackmoss.thaumicavaritia.registry;

import com.blackmoss.thaumicavaritia.ThaumicAvaritia;
import com.blackmoss.thaumicavaritia.entity.CausalityCollapserPearlEntity;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public final class TAEntities {
    public static final DeferredRegister<EntityType<?>> ENTITIES =
            DeferredRegister.create(Registries.ENTITY_TYPE, ThaumicAvaritia.MODID);

    public static final DeferredHolder<EntityType<?>, EntityType<CausalityCollapserPearlEntity>> CAUSALITY_COLLAPSER_PEARL =
            register(
                    () -> EntityType.Builder.<CausalityCollapserPearlEntity>of(
                                    CausalityCollapserPearlEntity::new, MobCategory.MISC)
                            .sized(0.25F, 0.25F)
                            .clientTrackingRange(4)
                            .updateInterval(10));

    private TAEntities() {
    }

    private static <T extends Entity> DeferredHolder<EntityType<?>, EntityType<T>> register(
            Supplier<EntityType.Builder<T>> builderSupplier) {
        return ENTITIES.register("causality_collapser_pearl",
                () -> builderSupplier.get()
                        .build(ResourceKey.create(
                                Registries.ENTITY_TYPE,
                                ThaumicAvaritia.identifier("causality_collapser_pearl"))));
    }

    public static void register(IEventBus modBus) {
        ENTITIES.register(modBus);
    }
}
