// 实体注册：本 mod 抛掷物实体。
package com.blackmoss.thaumicavaritia.registry;

import com.blackmoss.thaumicavaritia.ThaumicAvaritia;
import com.blackmoss.thaumicavaritia.entity.CausalityCollapserPearlEntity;
import java.util.function.Supplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class TAEntities {
    public static final DeferredRegister<EntityType<?>> ENTITIES =
            DeferredRegister.create(Registries.ENTITY_TYPE, ThaumicAvaritia.MODID);

    // 悖论珍珠抛掷物
    public static final DeferredHolder<EntityType<?>, EntityType<CausalityCollapserPearlEntity>> CAUSALITY_COLLAPSER_PEARL =
            register(
                    "causality_collapser_pearl",
                    () -> EntityType.Builder.<CausalityCollapserPearlEntity>of(
                                    CausalityCollapserPearlEntity::new, MobCategory.MISC)
                            .sized(0.25F, 0.25F)
                            .clientTrackingRange(4)
                            .updateInterval(10));

    private TAEntities() {
    }

    private static <T extends Entity> DeferredHolder<EntityType<?>, EntityType<T>> register(
            String name, Supplier<EntityType.Builder<T>> builderSupplier) {
        return ENTITIES.register(
                name,
                () -> builderSupplier
                        .get()
                        .build(ResourceKey.create(
                                Registries.ENTITY_TYPE,
                                Identifier.fromNamespaceAndPath(ThaumicAvaritia.MODID, name))));
    }

    public static void register(IEventBus modBus) {
        ENTITIES.register(modBus);
    }
}
