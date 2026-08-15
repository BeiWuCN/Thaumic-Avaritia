// 物品要素 datamap：为无尽贪婪（Re:Avaritia）物品与联动物品补充要素构成，供神秘时代扫描/研究系统使用。
package com.blackmoss.thaumicavaritia.data.datamap;

import com.blackmoss.thaumicavaritia.ThaumicAvaritia;
import com.leclowndu93150.thaumaturge.api.aspect.AspectDataMaps;
import com.leclowndu93150.thaumaturge.api.aspect.AspectList;
import com.leclowndu93150.thaumaturge.api.aspect.IAspect;
import com.leclowndu93150.thaumaturge.api.aspect.TCAspects;
import java.util.concurrent.CompletableFuture;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.common.data.DataMapProvider;
import org.jspecify.annotations.NonNull;

public final class ItemAspectsProvider extends DataMapProvider {

    private HolderGetter<IAspect> aspects;

    public ItemAspectsProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(packOutput, lookupProvider);
    }

    @Override
    public @NonNull String getName() {
        return "Thaumic Avaritia Item Aspects Data Maps";
    }

    @Override
    protected void gather(HolderLookup.Provider provider) {
        aspects = provider.lookupOrThrow(IAspect.REGISTRY_KEY);
        HolderLookup.RegistryLookup<Item> items = provider.lookupOrThrow(Registries.ITEM);
        Builder<AspectList, Item> b = builder(AspectDataMaps.BASE_ASPECTS);

        add(b, items, "avaritia", "infinity_ingot",
                list(TCAspects.PRAECANTATIO, 20, TCAspects.AURAM, 10, TCAspects.METALLUM, 15, TCAspects.ALIENIS, 5));
        add(b, items, "avaritia", "neutron_ingot", list(TCAspects.PRAECANTATIO, 15, TCAspects.PERDITIO, 20, TCAspects.METALLUM, 10));
        add(b, items, "avaritia", "endest_pearl", list(TCAspects.ALIENIS, 15, TCAspects.PERDITIO, 10, TCAspects.AURAM, 5));
        add(b, items, "avaritia", "infinity_catalyst",
                list(TCAspects.PRAECANTATIO, 50, TCAspects.ALIENIS, 25, TCAspects.AURAM, 25, TCAspects.PERDITIO, 10));
        add(b, items, "avaritia", "crystal_matrix_ingot",
                list(TCAspects.VITREUS, 10, TCAspects.METALLUM, 15, TCAspects.PRAECANTATIO, 5));
        add(b, items, "avaritia", "neutron_pile", list(TCAspects.PERDITIO, 5, TCAspects.PRAECANTATIO, 2));
        add(b, items, "avaritia", "infinity_nugget", list(TCAspects.PRAECANTATIO, 5, TCAspects.METALLUM, 5));

        // 阿卡西记录（全知之书）：认知 64 / 魔力 48 / 灵气 32 / 灵魂 32 / 渴望 16 / 异域 16 / 金属 16 / 虚空 8
        add(b, items, ThaumicAvaritia.MODID, "akashic_record",
                list(TCAspects.COGNITIO, 64, TCAspects.PRAECANTATIO, 48, TCAspects.AURAM, 32, TCAspects.SPIRITUS, 32)
                        .add(aspects.getOrThrow(TCAspects.DESIDERIUM), 16)
                        .add(aspects.getOrThrow(TCAspects.ALIENIS), 16)
                        .add(aspects.getOrThrow(TCAspects.METALLUM), 16)
                        .add(aspects.getOrThrow(TCAspects.VACUOS), 8));
    }

    // 按物品所属命名空间与注册 id 添加 datamap 条目。
    private void add(Builder<AspectList, Item> b, HolderLookup.RegistryLookup<Item> items, String namespace, String itemId, AspectList value) {
        items.get(ResourceKey.create(Registries.ITEM, net.minecraft.resources.Identifier.fromNamespaceAndPath(namespace, itemId)))
                .ifPresent(holder -> b.add(holder, value, false));
    }

    private AspectList list(ResourceKey<IAspect> a1, int n1) {
        return AspectList.EMPTY.add(aspects.getOrThrow(a1), n1);
    }

    private AspectList list(ResourceKey<IAspect> a1, int n1, ResourceKey<IAspect> a2, int n2) {
        return list(a1, n1).add(aspects.getOrThrow(a2), n2);
    }

    private AspectList list(
            ResourceKey<IAspect> a1, int n1, ResourceKey<IAspect> a2, int n2, ResourceKey<IAspect> a3, int n3) {
        return list(a1, n1, a2, n2).add(aspects.getOrThrow(a3), n3);
    }

    private AspectList list(
            ResourceKey<IAspect> a1,
            int n1,
            ResourceKey<IAspect> a2,
            int n2,
            ResourceKey<IAspect> a3,
            int n3,
            ResourceKey<IAspect> a4,
            int n4) {
        return list(a1, n1, a2, n2, a3, n3).add(aspects.getOrThrow(a4), n4);
    }
}
