package com.blackmoss.thaumicavaritia.registry;

import com.blackmoss.thaumicavaritia.ThaumicAvaritia;
import com.leclowndu93150.thaumaturge.api.aspect.IAspect;
import com.leclowndu93150.thaumaturge.content.aspect.Aspect;
import com.leclowndu93150.thaumaturge.data.worldgen.aspect.AspectBootstrap;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;

import java.util.List;
import java.util.Optional;

public final class TAAspects {
    public static final ResourceKey<IAspect> INFINITUS = ResourceKey.create(IAspect.REGISTRY_KEY, ThaumicAvaritia.identifier("infinitus"));

    public static void bootstrap(BootstrapContext<IAspect> ctx) {
        AspectBootstrap.bootstrap(ctx);
        ctx.register(
                INFINITUS,
                new Aspect(
                        "infinitus",
                        0x1E1E1E,
                        List.of(),
                        Optional.empty(),
                        Identifier.fromNamespaceAndPath(ThaumicAvaritia.MODID, "textures/aspects/infinitus.png"),
                        Aspect.DEFAULT_BLEND));
    }

    private TAAspects() {
    }
}