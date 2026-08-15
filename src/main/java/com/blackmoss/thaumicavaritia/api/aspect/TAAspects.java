// 本 mod 声明的要素键（仿 Thaumaturge TCAspects 模式：addon 应自行声明要素键）。
package com.blackmoss.thaumicavaritia.api.aspect;

import com.blackmoss.thaumicavaritia.ThaumicAvaritia;
import com.leclowndu93150.thaumaturge.api.aspect.IAspect;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;

public final class TAAspects {
    // 无尽：由渴望（贪婪）与虚空复合而成，象征以无限工业承载的无穷贪婪。
    public static final ResourceKey<IAspect> INFINITUS = key("infinitus");

    private TAAspects() {
    }

    private static ResourceKey<IAspect> key(String tag) {
        return ResourceKey.create(IAspect.REGISTRY_KEY, Identifier.fromNamespaceAndPath(ThaumicAvaritia.MODID, tag));
    }
}
