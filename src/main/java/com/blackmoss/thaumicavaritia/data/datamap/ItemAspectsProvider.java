// 物品要素 datamap：为无尽贪婪（Re:Avaritia）材料与本 mod 产物补充要素构成，供神秘时代扫描/研究系统使用。
// "无尽"（infinitus）是终极注魔的门槛要素：只能从 avaritia 高级材料与本 mod 终极产物中提炼获得。
package com.blackmoss.thaumicavaritia.data.datamap;

import com.blackmoss.thaumicavaritia.ThaumicAvaritia;
import com.blackmoss.thaumicavaritia.api.aspect.TAAspects;
import com.leclowndu93150.thaumaturge.api.aspect.AspectDataMaps;
import com.leclowndu93150.thaumaturge.api.aspect.AspectList;
import com.leclowndu93150.thaumaturge.api.aspect.IAspect;
import com.leclowndu93150.thaumaturge.api.aspect.TCAspects;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.common.data.DataMapProvider;
import org.jspecify.annotations.NonNull;

import java.util.concurrent.CompletableFuture;

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

        add(b, items, "avaritia", "double_compressed_crafting_table",
                list(TCAspects.INSTRUMENTUM, 5));
        add(b, items, "avaritia", "blaze_cube",
                list(TCAspects.IGNIS, 15, TCAspects.POTENTIA, 10));
        add(b, items, "avaritia", "blaze_sword",
                list(TCAspects.IGNIS, 20, TCAspects.POTENTIA, 20, TCAspects.METALLUM, 15));
        add(b, items, "avaritia", "blaze_axe",
                list(TCAspects.IGNIS, 20, TCAspects.POTENTIA, 20, TCAspects.INSTRUMENTUM, 15));
        add(b, items, "avaritia", "blaze_pickaxe",
                list(TCAspects.IGNIS, 20, TCAspects.POTENTIA, 20, TCAspects.INSTRUMENTUM, 15));
        add(b, items, "avaritia", "blaze_shovel",
                list(TCAspects.IGNIS, 20, TCAspects.POTENTIA, 20, TCAspects.INSTRUMENTUM, 15));
        add(b, items, "avaritia", "blaze_hoe",
                list(TCAspects.IGNIS, 20, TCAspects.POTENTIA, 20, TCAspects.INSTRUMENTUM, 15));
        add(b, items, "avaritia", "blaze_bow",
                list(TCAspects.IGNIS, 20, TCAspects.AER, 20, TCAspects.POTENTIA, 15));

        // 基础材料
        add(b, items, "avaritia", "crystal_matrix_ingot",
                list(TCAspects.VITREUS, 10, TCAspects.METALLUM, 15, TCAspects.PRAECANTATIO, 5));
        add(b, items, "avaritia", "neutron_ingot",
                list(TCAspects.PRAECANTATIO, 15, TCAspects.PERDITIO, 20, TCAspects.METALLUM, 10));

        // 核心无限材料（含可观无尽）
        add(b, items, "avaritia", "infinity_ingot",
                list(TCAspects.PRAECANTATIO, 20, TCAspects.AURAM, 10, TCAspects.METALLUM, 15, TCAspects.ALIENIS, 5)
                        .add(infinitus(), 10));
        add(b, items, "avaritia", "infinity_catalyst",
                list(TCAspects.PRAECANTATIO, 50, TCAspects.ALIENIS, 25, TCAspects.AURAM, 25, TCAspects.PERDITIO, 10)
                        .add(infinitus(), 8));
        add(b, items, "avaritia", "eternal_singularity",
                list(TCAspects.PRAECANTATIO, 40, TCAspects.ALIENIS, 20, TCAspects.METALLUM, 40)
                        .add(infinitus(), 5));
        add(b, items, "avaritia", "enhancement_core",
                list(TCAspects.PRAECANTATIO, 60, TCAspects.ALIENIS, 30, TCAspects.METALLUM, 20)
                        .add(infinitus(), 10));

        // 中阶工具
        add(b, items, "avaritia", "crystal_sword",
                list(TCAspects.VITREUS, 20, TCAspects.METALLUM, 20, TCAspects.POTENTIA, 20, TCAspects.INSTRUMENTUM, 10));
        add(b, items, "avaritia", "crystal_axe",
                list(TCAspects.VITREUS, 20, TCAspects.METALLUM, 20, TCAspects.INSTRUMENTUM, 20));
        add(b, items, "avaritia", "crystal_pickaxe",
                list(TCAspects.VITREUS, 20, TCAspects.METALLUM, 20, TCAspects.INSTRUMENTUM, 20));
        add(b, items, "avaritia", "crystal_shovel",
                list(TCAspects.VITREUS, 20, TCAspects.METALLUM, 20, TCAspects.INSTRUMENTUM, 20));
        add(b, items, "avaritia", "crystal_hoe",
                list(TCAspects.VITREUS, 20, TCAspects.METALLUM, 20, TCAspects.INSTRUMENTUM, 20));
        add(b, items, "avaritia", "crystal_bow",
                list(TCAspects.VITREUS, 20, TCAspects.AER, 20, TCAspects.INSTRUMENTUM, 15));

        // 杂项
        add(b, items, "avaritia", "neutron_horse_armor",
                list(TCAspects.METALLUM, 15, TCAspects.VITREUS, 10, TCAspects.PERDITIO, 5)
                        .add(infinitus(), 16));
        add(b, items, "avaritia", "side_config_card",
                list(TCAspects.INSTRUMENTUM, 10, TCAspects.POTENTIA, 5));
        add(b, items, "avaritia", "upgrade_smithing_template",
                list(TCAspects.INSTRUMENTUM, 15, TCAspects.FABRICO, 10, TCAspects.PRAECANTATIO, 10).add(infinitus(), 3));
        add(b, items, "avaritia", "ultimate_stew",
                list(TCAspects.VICTUS, 15, TCAspects.HERBA, 10, TCAspects.PRAECANTATIO, 10));
        add(b, items, "avaritia", "cosmic_meatballs",
                list(TCAspects.VICTUS, 15, TCAspects.SPIRITUS, 10, TCAspects.PRAECANTATIO, 5));
        add(b, items, "avaritia", "infinity_clock",
                list(TCAspects.METALLUM, 15, TCAspects.VITREUS, 10, TCAspects.PERDITIO, 5)
                        .add(infinitus(), 20));
        add(b, items, "avaritia", "infinity_totem",
                list(TCAspects.METALLUM, 15, TCAspects.VITREUS, 10, TCAspects.PERDITIO, 5)
                        .add(infinitus(), 12));

        // 终极装备（含较高无尽）
        add(b, items, "avaritia", "infinity_sword",
                list(TCAspects.PRAECANTATIO, 30, TCAspects.METALLUM, 30, TCAspects.POTENTIA, 30).add(infinitus(), 16));
        add(b, items, "avaritia", "infinity_axe",
                list(TCAspects.PRAECANTATIO, 30, TCAspects.METALLUM, 30, TCAspects.INSTRUMENTUM, 30).add(infinitus(), 16));
        add(b, items, "avaritia", "infinity_pickaxe",
                list(TCAspects.PRAECANTATIO, 30, TCAspects.METALLUM, 30, TCAspects.INSTRUMENTUM, 30).add(infinitus(), 16));
        add(b, items, "avaritia", "infinity_shovel",
                list(TCAspects.PRAECANTATIO, 30, TCAspects.METALLUM, 30, TCAspects.INSTRUMENTUM, 30).add(infinitus(), 16));
        add(b, items, "avaritia", "infinity_hoe",
                list(TCAspects.PRAECANTATIO, 30, TCAspects.METALLUM, 30, TCAspects.INSTRUMENTUM, 30).add(infinitus(), 16));
        add(b, items, "avaritia", "infinity_bow",
                list(TCAspects.AER, 40, TCAspects.POTENTIA, 30, TCAspects.PRAECANTATIO, 20).add(infinitus(), 16));
        add(b, items, "avaritia", "infinity_crossbow",
                list(TCAspects.AER, 40, TCAspects.POTENTIA, 30, TCAspects.MACHINA, 20).add(infinitus(), 16));
        add(b, items, "avaritia", "infinity_shield",
                list(TCAspects.PRAEMUNIO, 30, TCAspects.METALLUM, 30).add(infinitus(), 16));
        add(b, items, "avaritia", "infinity_trident",
                list(TCAspects.AQUA, 30, TCAspects.MOTUS, 30, TCAspects.POTENTIA, 30).add(infinitus(), 16));
        add(b, items, "avaritia", "infinity_mace",
                list(TCAspects.PRAECANTATIO, 30, TCAspects.METALLUM, 30, TCAspects.POTENTIA, 30).add(infinitus(), 16));
        add(b, items, "avaritia", "infinity_bucket",
                list(TCAspects.AQUA, 20, TCAspects.METALLUM, 20)
                        .add(infinitus(), 16));
        add(b, items, "avaritia", "infinity_helmet",
                list(TCAspects.PRAEMUNIO, 20, TCAspects.METALLUM, 20)
                        .add(infinitus(), 12));
        add(b, items, "avaritia", "infinity_chestplate",
                list(TCAspects.PRAEMUNIO, 25, TCAspects.METALLUM, 25)
                        .add(infinitus(), 12));
        add(b, items, "avaritia", "infinity_pants",
                list(TCAspects.PRAEMUNIO, 20, TCAspects.METALLUM, 20)
                        .add(infinitus(), 12));
        add(b, items, "avaritia", "infinity_boots",
                list(TCAspects.PRAEMUNIO, 20, TCAspects.METALLUM, 20)
                        .add(infinitus(), 12));
        add(b, items, "avaritia", "infinity_elytra",
                list(TCAspects.AER, 30, TCAspects.MOTUS, 30).add(infinitus(), 12));

        // ---- 无限贪婪（Re:Avaritia）方块：低阶不加无尽，终极机器/无限方块适量 ----
        add(b, items, "avaritia", "compressed_crafting_table",
                list(TCAspects.FABRICO, 10, TCAspects.INSTRUMENTUM, 5, TCAspects.MACHINA, 5));
        add(b, items, "avaritia", "double_compressed_crafting_table",
                list(TCAspects.FABRICO, 15, TCAspects.INSTRUMENTUM, 10, TCAspects.MACHINA, 10));
        add(b, items, "avaritia", "sculk_crafting_table",
                list(TCAspects.TENEBRAE, 10, TCAspects.SPIRITUS, 10, TCAspects.FABRICO, 10, TCAspects.ALIENIS, 5));
        add(b, items, "avaritia", "nether_crafting_table",
                list(TCAspects.IGNIS, 10, TCAspects.PERDITIO, 10, TCAspects.FABRICO, 10, TCAspects.ALIENIS, 5));
        add(b, items, "avaritia", "end_crafting_table",
                list(TCAspects.ALIENIS, 10, TCAspects.VACUOS, 10, TCAspects.FABRICO, 10, TCAspects.TENEBRAE, 5));
        add(b, items, "avaritia", "extreme_crafting_table",
                list(TCAspects.FABRICO, 30, TCAspects.INSTRUMENTUM, 20, TCAspects.MACHINA, 20, TCAspects.PRAECANTATIO, 15)
                        .add(infinitus(), 6));
        add(b, items, "avaritia", "extreme_smithing_table",
                list(TCAspects.INSTRUMENTUM, 20, TCAspects.FABRICO, 15, TCAspects.PRAECANTATIO, 15, TCAspects.METALLUM, 10)
                        .add(infinitus(), 6));
        add(b, items, "avaritia", "extreme_anvil",
                list(TCAspects.INSTRUMENTUM, 20, TCAspects.METALLUM, 20, TCAspects.PRAECANTATIO, 10)
                        .add(infinitus(), 16));
        add(b, items, "avaritia", "compressed_chest",
                list(TCAspects.VACUOS, 15, TCAspects.METALLUM, 10, TCAspects.FABRICO, 5));
        add(b, items, "avaritia", "infinity_chest",
                list(TCAspects.VACUOS, 30, TCAspects.PRAECANTATIO, 20, TCAspects.AURAM, 15, TCAspects.METALLUM, 10)
                        .add(infinitus(), 16));
        add(b, items, "avaritia", "soul_farmland",
                list(TCAspects.SPIRITUS, 15, TCAspects.VICTUS, 10, TCAspects.HERBA, 10, TCAspects.TERRA, 5));
        add(b, items, "avaritia", "diamond_lattice_block",
                list(TCAspects.VITREUS, 15, TCAspects.METALLUM, 15, TCAspects.ORDO, 5));
        add(b, items, "avaritia", "blaze_cube_block",
                list(TCAspects.IGNIS, 20, TCAspects.POTENTIA, 15, TCAspects.PERDITIO, 5));
        add(b, items, "avaritia", "refined_coal_block",
                list(TCAspects.IGNIS, 15, TCAspects.POTENTIA, 10, TCAspects.PERDITIO, 5));
        add(b, items, "avaritia", "star_fuel_block",
                list(TCAspects.IGNIS, 25, TCAspects.POTENTIA, 20, TCAspects.AURAM, 15));
        add(b, items, "avaritia", "endless_cake",
                list(TCAspects.VICTUS, 20, TCAspects.HERBA, 10, TCAspects.PRAECANTATIO, 15)
                        .add(infinitus(), 12));
        add(b, items, "avaritia", "neutron_collector",
                list(TCAspects.MACHINA, 15, TCAspects.MOTUS, 10, TCAspects.METALLUM, 10, TCAspects.PERDITIO, 5));
        add(b, items, "avaritia", "dense_neutron_collector",
                list(TCAspects.MACHINA, 20, TCAspects.MOTUS, 15, TCAspects.METALLUM, 15, TCAspects.PERDITIO, 10)
                        .add(infinitus(), 1));
        add(b, items, "avaritia", "denser_neutron_collector",
                list(TCAspects.MACHINA, 25, TCAspects.MOTUS, 20, TCAspects.METALLUM, 20, TCAspects.PERDITIO, 15)
                        .add(infinitus(), 2));
        add(b, items, "avaritia", "densest_neutron_collector",
                list(TCAspects.MACHINA, 30, TCAspects.MOTUS, 25, TCAspects.METALLUM, 25, TCAspects.PERDITIO, 20)
                        .add(infinitus(), 3));
        add(b, items, "avaritia", "neutron_compressor",
                list(TCAspects.MACHINA, 15, TCAspects.MOTUS, 10, TCAspects.METALLUM, 10, TCAspects.PERDITIO, 5)
                        .add(aspects.getOrThrow(TCAspects.ORDO), 5));
        add(b, items, "avaritia", "dense_neutron_compressor",
                list(TCAspects.MACHINA, 20, TCAspects.MOTUS, 15, TCAspects.METALLUM, 15, TCAspects.PERDITIO, 10)
                        .add(aspects.getOrThrow(TCAspects.ORDO), 10)
                        .add(infinitus(), 1));
        add(b, items, "avaritia", "denser_neutron_compressor",
                list(TCAspects.MACHINA, 25, TCAspects.MOTUS, 20, TCAspects.METALLUM, 20, TCAspects.PERDITIO, 15)
                        .add(aspects.getOrThrow(TCAspects.ORDO), 15)
                        .add(infinitus(), 2));
        add(b, items, "avaritia", "densest_neutron_compressor",
                list(TCAspects.MACHINA, 30, TCAspects.MOTUS, 25, TCAspects.METALLUM, 25, TCAspects.PERDITIO, 20)
                        .add(aspects.getOrThrow(TCAspects.ORDO), 20)
                        .add(infinitus(), 3));

        // ---- 本 mod 联动物品 ----
        add(b, items, ThaumicAvaritia.MODID, "extremely_primordial_pearl",
                list(TCAspects.ALIENIS, 32, TCAspects.PERDITIO, 32, TCAspects.PRAECANTATIO, 16).add(infinitus(), 8));
        add(b, items, ThaumicAvaritia.MODID, "akashic_record",
                list(TCAspects.COGNITIO, 64, TCAspects.PRAECANTATIO, 48, TCAspects.AURAM, 32, TCAspects.SPIRITUS, 32)
                        .add(aspects.getOrThrow(TCAspects.DESIDERIUM), 16)
                        .add(aspects.getOrThrow(TCAspects.ALIENIS), 16)
                        .add(aspects.getOrThrow(TCAspects.METALLUM), 16)
                        .add(aspects.getOrThrow(TCAspects.VACUOS), 8)
                        .add(infinitus(), 8));
        add(b, items, ThaumicAvaritia.MODID, "wand_cap_crystal_inter",
                list(TCAspects.VITREUS, 20, TCAspects.METALLUM, 20, TCAspects.PRAECANTATIO, 10));
        add(b, items, ThaumicAvaritia.MODID, "wand_cap_crystal",
                list(TCAspects.PRAECANTATIO, 40, TCAspects.AURAM, 40, TCAspects.VITREUS, 40, TCAspects.ORDO, 40)
                        .add(infinitus(), 12));
        add(b, items, ThaumicAvaritia.MODID, "wand_rod_studded_cosmic_neutronium",
                list(TCAspects.PRAECANTATIO, 40, TCAspects.PERDITIO, 40, TCAspects.METALLUM, 40, TCAspects.AURAM, 40)
                        .add(infinitus(), 12));
        add(b, items, ThaumicAvaritia.MODID, "arcane_singularity_focus",
                list(TCAspects.PRAECANTATIO, 60, TCAspects.AURAM, 60, TCAspects.ALIENIS, 60, TCAspects.VACUOS, 60)
                        .add(infinitus(), 16));
    }

    private Holder<IAspect> infinitus() {
        return aspects.getOrThrow(TAAspects.INFINITUS);
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
