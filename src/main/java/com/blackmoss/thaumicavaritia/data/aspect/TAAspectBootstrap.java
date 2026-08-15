// 要素注册：先注册 Thaumaturge 本体全部要素，再补充本 mod 的"无尽"要素（供 DataGen lookup 解析）。
// 运行时由 datapack JSON（data/thaumicavaritia/thaumaturge/aspect/infinitus.json）提供同一要素。
package com.blackmoss.thaumicavaritia.data.aspect;

import com.blackmoss.thaumicavaritia.ThaumicAvaritia;
import com.blackmoss.thaumicavaritia.api.aspect.TAAspects;
import com.leclowndu93150.thaumaturge.api.aspect.IAspect;
import com.leclowndu93150.thaumaturge.api.aspect.TCAspects;
import com.leclowndu93150.thaumaturge.content.aspect.Aspect;
import com.leclowndu93150.thaumaturge.data.worldgen.aspect.AspectBootstrap;
import java.util.List;
import java.util.Optional;
import net.minecraft.core.Holder;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.Identifier;

public final class TAAspectBootstrap {
    private TAAspectBootstrap() {
    }

    public static void bootstrap(BootstrapContext<IAspect> ctx) {
        // 先注册 Thaumaturge 全部要素，保证父要素 lookup 可解析。
        AspectBootstrap.bootstrap(ctx);
        // 无尽 = 渴望（贪婪）+ 虚空。
        Holder<IAspect> desiderium = ctx.lookup(IAspect.REGISTRY_KEY).getOrThrow(TCAspects.DESIDERIUM);
        Holder<IAspect> vacuos = ctx.lookup(IAspect.REGISTRY_KEY).getOrThrow(TCAspects.VACUOS);
        ctx.register(
                TAAspects.INFINITUS,
                new Aspect(
                        "infinitus",
                        0x1E1E1E,
                        List.of(desiderium, vacuos),
                        Optional.empty(),
                        Identifier.fromNamespaceAndPath(ThaumicAvaritia.MODID, "textures/aspects/infinitus.png"),
                        Aspect.DEFAULT_BLEND));
    }
}
