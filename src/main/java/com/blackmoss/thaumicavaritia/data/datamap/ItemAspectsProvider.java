// 物品要素 datamap：为无尽贪婪（Re:Avaritia）材料与本 mod 产物补充要素构成，供神秘时代扫描/研究系统使用。
// 要素依据 avaritia 合成配方材料酌情补充（对照 data/avaritia/recipe/*.json 核实）。
// 量级规划：基础材料 10-40 → 中阶材料 30-50 → 核心材料 60-100 → 终极材料/装备 100-150。
// "无尽"（infinitus）是终极注魔的门槛要素：只能从 avaritia 高级材料与本 mod 终极产物中提炼获得。
package com.blackmoss.thaumicavaritia.data.datamap;

import com.blackmoss.thaumicavaritia.ThaumicAvaritia;
import com.blackmoss.thaumicavaritia.api.aspect.TAAspects;
import com.leclowndu93150.thaumaturge.api.aspect.AspectDataMaps;
import com.leclowndu93150.thaumaturge.api.aspect.AspectList;
import com.leclowndu93150.thaumaturge.api.aspect.IAspect;
import com.leclowndu93150.thaumaturge.api.aspect.TCAspects;
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

        // ==================== 工作台 / 机器 ====================
        add(b, items, "avaritia", "sculk_crafting_table",
                aspects(
                        TCAspects.TENEBRAE, 25,
                        TCAspects.SPIRITUS, 25,
                        TCAspects.COGNITIO, 15,
                        TCAspects.ALIENIS, 10,
                        TCAspects.FABRICO, 20,
                        TCAspects.SENSUS, 10));
        add(b, items, "avaritia", "nether_crafting_table",
                aspects(
                        TCAspects.IGNIS, 30,
                        TCAspects.TENEBRAE, 25,
                        TCAspects.PERDITIO, 20,
                        TCAspects.FABRICO, 20,
                        TCAspects.MORTUUS, 15,
                        TCAspects.ALIENIS, 10));
        add(b, items, "avaritia", "end_crafting_table",
                aspects(
                        TCAspects.ALIENIS, 35,
                        TCAspects.VACUOS, 30,
                        TCAspects.TENEBRAE, 20,
                        TCAspects.FABRICO, 20,
                        TCAspects.PRAECANTATIO, 20,
                        TCAspects.MACHINA, 15));
        add(b, items, "avaritia", "double_compressed_crafting_table",
                aspects(
                        TCAspects.INSTRUMENTUM, 20,
                        TCAspects.FABRICO, 25,
                        TCAspects.MACHINA, 20));
        add(b, items, "avaritia", "extreme_crafting_table",
                aspects(
                        TCAspects.FABRICO, 80,
                        TCAspects.INSTRUMENTUM, 60,
                        TCAspects.MACHINA, 60,
                        TCAspects.PRAECANTATIO, 50,
                        TCAspects.VITREUS, 40,
                        TCAspects.METALLUM, 40,
                        TCAspects.AURAM, 30));
        add(b, items, "avaritia", "extreme_smithing_table",
                aspects(
                        TCAspects.INSTRUMENTUM, 60,
                        TCAspects.FABRICO, 50,
                        TCAspects.PRAECANTATIO, 50,
                        TCAspects.METALLUM, 40,
                        TCAspects.VITREUS, 40,
                        TCAspects.PERDITIO, 30,
                        TCAspects.MACHINA, 40,
                        TAAspects.INFINITUS, 16));
        add(b, items, "avaritia", "extreme_anvil",
                aspects(
                        TCAspects.INSTRUMENTUM, 60,
                        TCAspects.METALLUM, 60,
                        TCAspects.PRAECANTATIO, 40,
                        TCAspects.PERMUTATIO, 40,
                        TCAspects.PERDITIO, 30,
                        TAAspects.INFINITUS, 16));
        add(b, items, "avaritia", "compressed_chest",
                aspects(
                        TCAspects.VACUOS, 40,
                        TCAspects.METALLUM, 30,
                        TCAspects.FABRICO, 20,
                        TCAspects.MACHINA, 25));
        add(b, items, "avaritia", "infinity_chest",
                aspects(
                        TCAspects.VACUOS, 100,
                        TCAspects.PRAECANTATIO, 80,
                        TCAspects.AURAM, 60,
                        TCAspects.METALLUM, 50,
                        TCAspects.PERDITIO, 30,
                        TCAspects.VITREUS, 30,
                        TAAspects.INFINITUS, 16));
        add(b, items, "avaritia", "neutron_collector",
                aspects(
                        TCAspects.MACHINA, 40,
                        TCAspects.MOTUS, 30,
                        TCAspects.METALLUM, 30,
                        TCAspects.PERDITIO, 20));
        add(b, items, "avaritia", "dense_neutron_collector",
                aspects(
                        TCAspects.MACHINA, 55,
                        TCAspects.MOTUS, 40,
                        TCAspects.METALLUM, 40,
                        TCAspects.PERDITIO, 30,
                        TAAspects.INFINITUS, 1));
        add(b, items, "avaritia", "denser_neutron_collector",
                aspects(
                        TCAspects.MACHINA, 70,
                        TCAspects.MOTUS, 55,
                        TCAspects.METALLUM, 55,
                        TCAspects.PERDITIO, 40,
                        TAAspects.INFINITUS, 2));
        add(b, items, "avaritia", "densest_neutron_collector",
                aspects(
                        TCAspects.MACHINA, 90,
                        TCAspects.MOTUS, 70,
                        TCAspects.METALLUM, 70,
                        TCAspects.PERDITIO, 50,
                        TAAspects.INFINITUS, 3));
        add(b, items, "avaritia", "neutron_compressor",
                aspects(
                        TCAspects.MACHINA, 40,
                        TCAspects.MOTUS, 30,
                        TCAspects.METALLUM, 30,
                        TCAspects.PERDITIO, 20,
                        TCAspects.ORDO, 15));
        add(b, items, "avaritia", "dense_neutron_compressor",
                aspects(
                        TCAspects.MACHINA, 55,
                        TCAspects.MOTUS, 40,
                        TCAspects.METALLUM, 40,
                        TCAspects.PERDITIO, 30,
                        TCAspects.ORDO, 25,
                        TAAspects.INFINITUS, 1));
        add(b, items, "avaritia", "denser_neutron_compressor",
                aspects(
                        TCAspects.MACHINA, 70,
                        TCAspects.MOTUS, 55,
                        TCAspects.METALLUM, 55,
                        TCAspects.PERDITIO, 40,
                        TCAspects.ORDO, 35,
                        TAAspects.INFINITUS, 2));
        add(b, items, "avaritia", "densest_neutron_compressor",
                aspects(
                        TCAspects.MACHINA, 90,
                        TCAspects.MOTUS, 70,
                        TCAspects.METALLUM, 70,
                        TCAspects.PERDITIO, 50,
                        TCAspects.ORDO, 45,
                        TAAspects.INFINITUS, 3));

        // ==================== 基础方块 ====================
        add(b, items, "avaritia", "soul_farmland",
                aspects(
                        TCAspects.SPIRITUS, 40,
                        TCAspects.VICTUS, 30,
                        TCAspects.HERBA, 30,
                        TCAspects.TERRA, 20));
        add(b, items, "avaritia", "diamond_lattice_block",
                aspects(
                        TCAspects.VITREUS, 40,
                        TCAspects.METALLUM, 40,
                        TCAspects.ORDO, 15));
        add(b, items, "avaritia", "blaze_cube_block",
                aspects(
                        TCAspects.IGNIS, 50,
                        TCAspects.POTENTIA, 40,
                        TCAspects.PERDITIO, 15));
        add(b, items, "avaritia", "refined_coal_block",
                aspects(
                        TCAspects.IGNIS, 40,
                        TCAspects.POTENTIA, 30,
                        TCAspects.PERDITIO, 15));
        add(b, items, "avaritia", "star_fuel_block",
                aspects(
                        TCAspects.IGNIS, 60,
                        TCAspects.POTENTIA, 50,
                        TCAspects.AURAM, 40,
                        TCAspects.PRAECANTATIO, 30,
                        TCAspects.PERDITIO, 20));

        // ==================== 基础材料 ====================
        add(b, items, "avaritia", "diamond_lattice",
                aspects(
                        TCAspects.VITREUS, 12,
                        TCAspects.METALLUM, 12,
                        TCAspects.ORDO, 5));
        add(b, items, "avaritia", "crystal_matrix_ingot",
                aspects(
                        TCAspects.VITREUS, 30,
                        TCAspects.METALLUM, 25,
                        TCAspects.PRAECANTATIO, 15,
                        TCAspects.SPIRITUS, 10));
        add(b, items, "avaritia", "neutron_nugget",
                aspects(
                        TCAspects.PERDITIO, 8,
                        TCAspects.METALLUM, 6));
        add(b, items, "avaritia", "neutron_pile",
                aspects(
                        TCAspects.PERDITIO, 12,
                        TCAspects.PRAECANTATIO, 5));
        add(b, items, "avaritia", "neutron_ingot",
                aspects(
                        TCAspects.PRAECANTATIO, 40,
                        TCAspects.PERDITIO, 40,
                        TCAspects.METALLUM, 25));
        add(b, items, "avaritia", "refined_coal",
                aspects(
                        TCAspects.IGNIS, 25,
                        TCAspects.POTENTIA, 15,
                        TCAspects.PERDITIO, 10));
        add(b, items, "avaritia", "neutron_gear",
                aspects(
                        TCAspects.METALLUM, 35,
                        TCAspects.MACHINA, 35,
                        TCAspects.ORDO, 25,
                        TCAspects.VITREUS, 20,
                        TCAspects.PERDITIO, 20));
        add(b, items, "avaritia", "neutron_ring",
                aspects(
                        TCAspects.METALLUM, 40,
                        TCAspects.VITREUS, 30,
                        TCAspects.PRAECANTATIO, 30,
                        TCAspects.ALIENIS, 20,
                        TCAspects.VACUOS, 20,
                        TAAspects.INFINITUS, 4));

        // ==================== 核心无限材料 ====================
        add(b, items, "avaritia", "infinity_nugget",
                aspects(
                        TCAspects.PRAECANTATIO, 15,
                        TCAspects.METALLUM, 12,
                        TCAspects.PERDITIO, 8,
                        TAAspects.INFINITUS, 1));
        add(b, items, "avaritia", "infinity_ingot",
                aspects(
                        TCAspects.PRAECANTATIO, 80,
                        TCAspects.METALLUM, 60,
                        TCAspects.AURAM, 50,
                        TCAspects.ALIENIS, 30,
                        TCAspects.VITREUS, 20,
                        TCAspects.PERDITIO, 20,
                        TAAspects.INFINITUS, 9));
        add(b, items, "avaritia", "endest_pearl",
                aspects(
                        TCAspects.ALIENIS, 80,
                        TCAspects.VACUOS, 60,
                        TCAspects.PRAECANTATIO, 50,
                        TCAspects.TENEBRAE, 40,
                        TCAspects.SPIRITUS, 30));
        add(b, items, "avaritia", "record_fragment",
                aspects(
                        TCAspects.COGNITIO, 30,
                        TCAspects.SENSUS, 30,
                        TCAspects.PERDITIO, 15));
        add(b, items, "avaritia", "star_fuel",
                aspects(
                        TCAspects.IGNIS, 60,
                        TCAspects.POTENTIA, 50,
                        TCAspects.PERDITIO, 40,
                        TCAspects.PRAECANTATIO, 30,
                        TAAspects.INFINITUS, 5));
        add(b, items, "avaritia", "infinity_catalyst",
                aspects(
                        TCAspects.PRAECANTATIO, 120,
                        TCAspects.ALIENIS, 80,
                        TCAspects.AURAM, 80,
                        TCAspects.PERDITIO, 50,
                        TCAspects.VICTUS, 40,
                        TCAspects.COGNITIO, 30,
                        TCAspects.VACUOS, 30,
                        TAAspects.INFINITUS, 8));
        add(b, items, "avaritia", "eternal_singularity",
                aspects(
                        TCAspects.PRAECANTATIO, 100,
                        TCAspects.ALIENIS, 60,
                        TCAspects.METALLUM, 100,
                        TCAspects.PERDITIO, 50,
                        TCAspects.ORDO, 30,
                        TAAspects.INFINITUS, 5));
        add(b, items, "avaritia", "enhancement_core",
                aspects(
                        TCAspects.PRAECANTATIO, 150,
                        TCAspects.ALIENIS, 100,
                        TCAspects.METALLUM, 80,
                        TCAspects.VITREUS, 60,
                        TCAspects.PERDITIO, 50,
                        TCAspects.VACUOS, 40,
                        TAAspects.INFINITUS, 10));
        add(b, items, "avaritia", "infinity_upgrade",
                aspects(
                        TCAspects.PRAECANTATIO, 80,
                        TCAspects.POTENTIA, 80,
                        TCAspects.ALIENIS, 50,
                        TCAspects.METALLUM, 40,
                        TAAspects.INFINITUS, 12));
        add(b, items, "avaritia", "matter_cluster",
                aspects(
                        TCAspects.PERMUTATIO, 40,
                        TCAspects.PRAECANTATIO, 40,
                        TCAspects.VACUOS, 30,
                        TCAspects.PERDITIO, 20));
        add(b, items, "avaritia", "full_matter_cluster",
                aspects(
                        TCAspects.PERMUTATIO, 80,
                        TCAspects.PRAECANTATIO, 80,
                        TCAspects.VACUOS, 60,
                        TCAspects.PERDITIO, 40));

        // ==================== 烈焰系（tier 2）====================
        add(b, items, "avaritia", "blaze_cube",
                aspects(
                        TCAspects.IGNIS, 40,
                        TCAspects.POTENTIA, 30,
                        TCAspects.MORTUUS, 20,
                        TCAspects.PERDITIO, 10));
        add(b, items, "avaritia", "blaze_sword",
                aspects(
                        TCAspects.IGNIS, 60,
                        TCAspects.POTENTIA, 50,
                        TCAspects.METALLUM, 40,
                        TCAspects.MORTUUS, 30,
                        TCAspects.VITREUS, 20,
                        TCAspects.SPIRITUS, 15));
        add(b, items, "avaritia", "blaze_axe",
                aspects(
                        TCAspects.IGNIS, 60,
                        TCAspects.POTENTIA, 50,
                        TCAspects.INSTRUMENTUM, 40,
                        TCAspects.MORTUUS, 30,
                        TCAspects.VITREUS, 20,
                        TCAspects.SPIRITUS, 15));
        add(b, items, "avaritia", "blaze_pickaxe",
                aspects(
                        TCAspects.IGNIS, 60,
                        TCAspects.POTENTIA, 50,
                        TCAspects.INSTRUMENTUM, 40,
                        TCAspects.MORTUUS, 30,
                        TCAspects.VITREUS, 20,
                        TCAspects.SPIRITUS, 15));
        add(b, items, "avaritia", "blaze_shovel",
                aspects(
                        TCAspects.IGNIS, 60,
                        TCAspects.POTENTIA, 50,
                        TCAspects.INSTRUMENTUM, 40,
                        TCAspects.MORTUUS, 30,
                        TCAspects.VITREUS, 20,
                        TCAspects.SPIRITUS, 15));
        add(b, items, "avaritia", "blaze_hoe",
                aspects(
                        TCAspects.IGNIS, 60,
                        TCAspects.POTENTIA, 50,
                        TCAspects.INSTRUMENTUM, 40,
                        TCAspects.MORTUUS, 30,
                        TCAspects.VITREUS, 20,
                        TCAspects.SPIRITUS, 15));
        add(b, items, "avaritia", "blaze_bow",
                aspects(
                        TCAspects.IGNIS, 60,
                        TCAspects.AER, 50,
                        TCAspects.POTENTIA, 40,
                        TCAspects.MORTUUS, 30,
                        TCAspects.VITREUS, 20,
                        TCAspects.SPIRITUS, 15));

        // ==================== 水晶系（tier 3）====================
        add(b, items, "avaritia", "crystal_sword",
                aspects(
                        TCAspects.VITREUS, 80,
                        TCAspects.METALLUM, 60,
                        TCAspects.POTENTIA, 50,
                        TCAspects.INSTRUMENTUM, 40,
                        TCAspects.PERDITIO, 30));
        add(b, items, "avaritia", "crystal_axe",
                aspects(
                        TCAspects.VITREUS, 80,
                        TCAspects.METALLUM, 60,
                        TCAspects.INSTRUMENTUM, 50,
                        TCAspects.PERDITIO, 30));
        add(b, items, "avaritia", "crystal_pickaxe",
                aspects(
                        TCAspects.VITREUS, 80,
                        TCAspects.METALLUM, 60,
                        TCAspects.INSTRUMENTUM, 50,
                        TCAspects.PERDITIO, 30));
        add(b, items, "avaritia", "crystal_shovel",
                aspects(
                        TCAspects.VITREUS, 80,
                        TCAspects.METALLUM, 60,
                        TCAspects.INSTRUMENTUM, 50,
                        TCAspects.PERDITIO, 30));
        add(b, items, "avaritia", "crystal_hoe",
                aspects(
                        TCAspects.VITREUS, 80,
                        TCAspects.METALLUM, 60,
                        TCAspects.INSTRUMENTUM, 50,
                        TCAspects.PERDITIO, 30));
        add(b, items, "avaritia", "crystal_bow",
                aspects(
                        TCAspects.VITREUS, 80,
                        TCAspects.AER, 60,
                        TCAspects.INSTRUMENTUM, 40,
                        TCAspects.PERDITIO, 30));

        // ==================== 杂项 ====================
        add(b, items, "avaritia", "neutron_horse_armor",
                aspects(
                        TCAspects.METALLUM, 50,
                        TCAspects.VITREUS, 40,
                        TCAspects.PERDITIO, 20,
                        TCAspects.MOTUS, 30,
                        TCAspects.AQUA, 30,
                        TAAspects.INFINITUS, 16));
        add(b, items, "avaritia", "side_config_card",
                aspects(
                        TCAspects.INSTRUMENTUM, 25,
                        TCAspects.POTENTIA, 15,
                        TCAspects.VITREUS, 20,
                        TCAspects.METALLUM, 20,
                        TCAspects.AURAM, 15));
        add(b, items, "avaritia", "upgrade_smithing_template",
                aspects(
                        TCAspects.INSTRUMENTUM, 40,
                        TCAspects.FABRICO, 30,
                        TCAspects.PRAECANTATIO, 30,
                        TCAspects.METALLUM, 30,
                        TCAspects.VITREUS, 30,
                        TAAspects.INFINITUS, 20));
        add(b, items, "avaritia", "ultimate_stew",
                aspects(
                        TCAspects.VICTUS, 50,
                        TCAspects.HERBA, 40,
                        TCAspects.PRAECANTATIO, 30,
                        TCAspects.PERDITIO, 20,
                        TCAspects.PERMUTATIO, 20));
        add(b, items, "avaritia", "cosmic_meatballs",
                aspects(
                        TCAspects.VICTUS, 50,
                        TCAspects.SPIRITUS, 40,
                        TCAspects.PRAECANTATIO, 25,
                        TCAspects.MORTUUS, 30,
                        TCAspects.PERDITIO, 20));
        add(b, items, "avaritia", "endless_cake",
                aspects(
                        TCAspects.VICTUS, 60,
                        TCAspects.HERBA, 40,
                        TCAspects.PRAECANTATIO, 40,
                        TCAspects.AURAM, 30,
                        TCAspects.ALIENIS, 25,
                        TAAspects.INFINITUS, 12));
        add(b, items, "avaritia", "infinity_clock",
                aspects(
                        TCAspects.ORDO, 60,
                        TCAspects.MOTUS, 60,
                        TCAspects.PERMUTATIO, 50,
                        TCAspects.AURAM, 40,
                        TCAspects.VICTUS, 30,
                        TCAspects.PRAECANTATIO, 30,
                        TCAspects.METALLUM, 25,
                        TCAspects.VITREUS, 25,
                        TAAspects.INFINITUS, 20));
        add(b, items, "avaritia", "infinity_totem",
                aspects(
                        TCAspects.SPIRITUS, 100,
                        TCAspects.VICTUS, 100,
                        TCAspects.PRAECANTATIO, 80,
                        TCAspects.AURAM, 60,
                        TCAspects.METALLUM, 40,
                        TCAspects.VITREUS, 40,
                        TAAspects.INFINITUS, 12));
        add(b, items, "avaritia", "infinity_umbrella",
                aspects(
                        TCAspects.PRAEMUNIO, 80,
                        TCAspects.AER, 60,
                        TCAspects.AQUA, 50,
                        TCAspects.MOTUS, 40,
                        TCAspects.VITREUS, 30,
                        TCAspects.IGNIS, 30,
                        TAAspects.INFINITUS, 16));

        // ==================== 终极装备 ====================
        add(b, items, "avaritia", "infinity_sword",
                aspects(
                        TCAspects.PRAECANTATIO, 150,
                        TCAspects.METALLUM, 120,
                        TCAspects.POTENTIA, 100,
                        TCAspects.VITREUS, 80,
                        TCAspects.PERDITIO, 60,
                        TCAspects.ALIENIS, 40,
                        TAAspects.INFINITUS, 16));
        add(b, items, "avaritia", "infinity_axe",
                aspects(
                        TCAspects.PRAECANTATIO, 120,
                        TCAspects.METALLUM, 120,
                        TCAspects.INSTRUMENTUM, 100,
                        TCAspects.VITREUS, 80,
                        TCAspects.PERDITIO, 60,
                        TAAspects.INFINITUS, 16));
        add(b, items, "avaritia", "infinity_pickaxe",
                aspects(
                        TCAspects.PRAECANTATIO, 120,
                        TCAspects.METALLUM, 120,
                        TCAspects.INSTRUMENTUM, 100,
                        TCAspects.VITREUS, 80,
                        TCAspects.PERDITIO, 60,
                        TAAspects.INFINITUS, 16));
        add(b, items, "avaritia", "infinity_shovel",
                aspects(
                        TCAspects.PRAECANTATIO, 120,
                        TCAspects.METALLUM, 120,
                        TCAspects.INSTRUMENTUM, 100,
                        TCAspects.VITREUS, 80,
                        TCAspects.PERDITIO, 60,
                        TAAspects.INFINITUS, 16));
        add(b, items, "avaritia", "infinity_hoe",
                aspects(
                        TCAspects.PRAECANTATIO, 120,
                        TCAspects.METALLUM, 120,
                        TCAspects.INSTRUMENTUM, 100,
                        TCAspects.VITREUS, 80,
                        TCAspects.PERDITIO, 60,
                        TAAspects.INFINITUS, 16));
        add(b, items, "avaritia", "infinity_bow",
                aspects(
                        TCAspects.AER, 100,
                        TCAspects.POTENTIA, 80,
                        TCAspects.PRAECANTATIO, 60,
                        TCAspects.VITREUS, 50,
                        TCAspects.PERDITIO, 30,
                        TAAspects.INFINITUS, 16));
        add(b, items, "avaritia", "infinity_crossbow",
                aspects(
                        TCAspects.AER, 100,
                        TCAspects.POTENTIA, 80,
                        TCAspects.MACHINA, 60,
                        TCAspects.VITREUS, 50,
                        TCAspects.PERDITIO, 30,
                        TAAspects.INFINITUS, 16));
        add(b, items, "avaritia", "infinity_shield",
                aspects(
                        TCAspects.PRAEMUNIO, 100,
                        TCAspects.METALLUM, 80,
                        TCAspects.VITREUS, 60,
                        TCAspects.PERDITIO, 40,
                        TCAspects.PRAECANTATIO, 40,
                        TAAspects.INFINITUS, 16));
        add(b, items, "avaritia", "infinity_trident",
                aspects(
                        TCAspects.AQUA, 100,
                        TCAspects.MOTUS, 80,
                        TCAspects.POTENTIA, 60,
                        TCAspects.PRAECANTATIO, 50,
                        TCAspects.SPIRITUS, 30,
                        TAAspects.INFINITUS, 16));
        add(b, items, "avaritia", "infinity_mace",
                aspects(
                        TCAspects.PRAECANTATIO, 100,
                        TCAspects.METALLUM, 80,
                        TCAspects.POTENTIA, 60,
                        TCAspects.AER, 30,
                        TCAspects.VITREUS, 40,
                        TAAspects.INFINITUS, 16));
        add(b, items, "avaritia", "infinity_bucket",
                aspects(
                        TCAspects.AQUA, 80,
                        TCAspects.METALLUM, 60,
                        TCAspects.IGNIS, 40,
                        TCAspects.GELUM, 40,
                        TCAspects.VITREUS, 30,
                        TAAspects.INFINITUS, 16));
        add(b, items, "avaritia", "infinity_helmet",
                aspects(
                        TCAspects.PRAEMUNIO, 120,
                        TCAspects.METALLUM, 100,
                        TCAspects.PERDITIO, 60,
                        TCAspects.VITREUS, 60,
                        TCAspects.PRAECANTATIO, 40,
                        TAAspects.INFINITUS, 16));
        add(b, items, "avaritia", "infinity_chestplate",
                aspects(
                        TCAspects.PRAEMUNIO, 150,
                        TCAspects.METALLUM, 120,
                        TCAspects.PERDITIO, 80,
                        TCAspects.VITREUS, 80,
                        TCAspects.PRAECANTATIO, 50,
                        TAAspects.INFINITUS, 16));
        add(b, items, "avaritia", "infinity_pants",
                aspects(
                        TCAspects.PRAEMUNIO, 120,
                        TCAspects.METALLUM, 100,
                        TCAspects.PERDITIO, 60,
                        TCAspects.VITREUS, 60,
                        TCAspects.PRAECANTATIO, 40,
                        TAAspects.INFINITUS, 16));
        add(b, items, "avaritia", "infinity_boots",
                aspects(
                        TCAspects.PRAEMUNIO, 120,
                        TCAspects.METALLUM, 100,
                        TCAspects.PERDITIO, 60,
                        TCAspects.VITREUS, 60,
                        TCAspects.PRAECANTATIO, 40,
                        TAAspects.INFINITUS, 16));
        add(b, items, "avaritia", "infinity_elytra",
                aspects(
                        TCAspects.AER, 100,
                        TCAspects.MOTUS, 80,
                        TCAspects.VITREUS, 60,
                        TCAspects.PERDITIO, 40,
                        TCAspects.PRAECANTATIO, 40,
                        TAAspects.INFINITUS, 16));

        // ==================== 本 mod 联动物品 ====================
        add(b, items, ThaumicAvaritia.MODID, "extremely_primordial_pearl",
                aspects(
                        TCAspects.ALIENIS, 80,
                        TCAspects.PERDITIO, 80,
                        TCAspects.PRAECANTATIO, 50,
                        TAAspects.INFINITUS, 1));
        add(b, items, ThaumicAvaritia.MODID, "akashic_record",
                aspects(
                        TCAspects.COGNITIO, 128,
                        TCAspects.PRAECANTATIO, 96,
                        TCAspects.AURAM, 64,
                        TCAspects.SPIRITUS, 64,
                        TCAspects.DESIDERIUM, 32,
                        TCAspects.ALIENIS, 32,
                        TCAspects.METALLUM, 32,
                        TCAspects.VACUOS, 16,
                        TAAspects.INFINITUS, 8));
        add(b, items, ThaumicAvaritia.MODID, "wand_cap_crystal_matrix_inter",
                aspects(
                        TCAspects.VITREUS, 40,
                        TCAspects.METALLUM, 40,
                        TCAspects.PRAECANTATIO, 20));
        add(b, items, ThaumicAvaritia.MODID, "wand_cap_crystal_matrix",
                aspects(
                        TCAspects.PRAECANTATIO, 100,
                        TCAspects.AURAM, 100,
                        TCAspects.VITREUS, 100,
                        TCAspects.ORDO, 80,
                        TAAspects.INFINITUS, 12));
        add(b, items, ThaumicAvaritia.MODID, "wand_rod_studded_cosmic_neutronium",
                aspects(
                        TCAspects.PRAECANTATIO, 100,
                        TCAspects.PERDITIO, 100,
                        TCAspects.METALLUM, 100,
                        TCAspects.AURAM, 80,
                        TAAspects.INFINITUS, 12));
        add(b, items, ThaumicAvaritia.MODID, "arcane_singularity_focus",
                aspects(
                        TCAspects.PRAECANTATIO, 150,
                        TCAspects.AURAM, 150,
                        TCAspects.ALIENIS, 150,
                        TCAspects.VACUOS, 150,
                        TAAspects.INFINITUS, 16));
        add(b, items, ThaumicAvaritia.MODID, "causality_collapser_pearl",
                aspects(
                        TCAspects.ALIENIS, 60,
                        TCAspects.VACUOS, 60,
                        TCAspects.PRAECANTATIO, 60,
                        TCAspects.PERDITIO, 40,
                        TCAspects.TENEBRAE, 30,
                        TAAspects.INFINITUS, 16));
    }

    // 统一构造器：键/量交替的变参，替代 list() 多重重载与零散 .add() 链式。
    @SuppressWarnings("unchecked")
    private AspectList aspects(Object... pairs) {
        AspectList result = AspectList.EMPTY;
        for (int i = 0; i < pairs.length; i += 2) {
            result = result.add(aspects.getOrThrow((ResourceKey<IAspect>) pairs[i]), (Integer) pairs[i + 1]);
        }
        return result;
    }

    // 按物品所属命名空间与注册 id 添加 datamap 条目。
    private void add(Builder<AspectList, Item> b, HolderLookup.RegistryLookup<Item> items, String namespace, String itemId, AspectList value) {
        items.get(ResourceKey.create(Registries.ITEM, net.minecraft.resources.Identifier.fromNamespaceAndPath(namespace, itemId)))
                .ifPresent(holder -> b.add(holder, value, false));
    }
}
