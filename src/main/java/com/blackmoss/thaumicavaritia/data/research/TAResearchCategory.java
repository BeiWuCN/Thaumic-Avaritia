package com.blackmoss.thaumicavaritia.data.research;

import com.blackmoss.thaumicavaritia.ThaumicAvaritia;
import com.leclowndu93150.thaumaturge.api.aspect.AspectInstance;
import com.leclowndu93150.thaumaturge.api.aspect.AspectList;
import com.leclowndu93150.thaumaturge.api.aspect.IAspect;
import com.leclowndu93150.thaumaturge.api.aspect.TCAspects;
import com.leclowndu93150.thaumaturge.api.research.IResearchCategory;
import com.leclowndu93150.thaumaturge.content.research.ResearchCategory;
import net.minecraft.core.HolderGetter;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;

import java.util.List;
import java.util.Optional;

public final class TAResearchCategory {
    private TAResearchCategory() {
    }

    // 神秘无尽类别键。
    public static final ResourceKey<IResearchCategory> ARCANE_INFINITY = ResourceKey.create(
            IResearchCategory.REGISTRY_KEY,
            Identifier.fromNamespaceAndPath(ThaumicAvaritia.MODID, ThaumicAvaritia.MODID));

    public static void bootstrap(BootstrapContext<IResearchCategory> ctx) {
        HolderGetter<IAspect> aspects = ctx.lookup(IAspect.REGISTRY_KEY);
        ctx.register(
                ARCANE_INFINITY,
                new ResearchCategory(
                        Optional.of(Identifier.fromNamespaceAndPath(
                                ThaumicAvaritia.MODID, "unlock_thaumicavaritia")),
                        AspectList.ofEntries(List.of(
                                e(aspects, TCAspects.ALIENIS, 20),
                                e(aspects, TCAspects.PRAECANTATIO, 20),
                                e(aspects, TCAspects.COGNITIO, 10),
                                e(aspects, TCAspects.METALLUM, 10),
                                e(aspects, TCAspects.MACHINA, 10),
                                e(aspects, TCAspects.AURAM, 5),
                                e(aspects, TCAspects.VITIUM, 5),
                                e(aspects, TCAspects.TENEBRAE, 5))),
                        Identifier.fromNamespaceAndPath(
                                ThaumicAvaritia.MODID, "textures/item/akashic_record.png"),
                        Identifier.fromNamespaceAndPath(
                                "thaumaturge", "textures/gui/gui_research_back_over.png"),
                        Optional.of(
                                Identifier.fromNamespaceAndPath("thaumaturge", "textures/gui/gui_research_back_over.png")),
                        7));
    }

    private static AspectInstance e(HolderGetter<IAspect> aspects, ResourceKey<IAspect> key, int amount) {
        return new AspectInstance(aspects.getOrThrow(key), amount);
    }
}
