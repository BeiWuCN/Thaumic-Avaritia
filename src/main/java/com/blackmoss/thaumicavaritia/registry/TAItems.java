package com.blackmoss.thaumicavaritia.registry;

import com.blackmoss.thaumicavaritia.ThaumicAvaritia;
import com.blackmoss.thaumicavaritia.item.*;
import com.leclowndu93150.thaumaturge.content.wands.ItemWandCap;
import com.leclowndu93150.thaumaturge.content.wands.ItemWandRod;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class TAItems {

    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(ThaumicAvaritia.MODID);

    // 极元始珍珠
    public static final DeferredItem<ExtremelyPrimordialPearlItem> EXTREMELY_PRIMORDIAL_PEARL = ITEMS.registerItem(
            "extremely_primordial_pearl",
            ExtremelyPrimordialPearlItem::new,
            props -> props.rarity(Rarity.EPIC));

    // 阿卡西记录
    public static final DeferredItem<AkashicRecordsItem> AKASHIC_RECORDS = ITEMS.registerItem(
            "akashic_record",
            AkashicRecordsItem::new,
            props -> props.rarity(Rarity.EPIC));

    // 惰性水晶矩阵杖端
    public static final DeferredItem<Item> INTER_CRYSTAL_MATRIX_WAND_CAP = ITEMS.registerSimpleItem("inter_crystal_matrix_wand_cap");

    // 水晶矩阵杖端
    public static final DeferredItem<ItemWandCap> CRYSTAL_MATRIX_WAND_CAP = ITEMS.registerItem(
            "crystal_matrix_wand_cap",
            props -> new ItemWandCap(props, TAWandParts.CRYSTAL_MATRIX),
            props -> props
                    .rarity(Rarity.EPIC));

    // 宇宙中子态素杖柄
    public static final DeferredItem<ItemWandRod> NEUTRONIUM_WAND_ROD = ITEMS.registerItem(
            "neutronium_wand_rod",
            props -> new ItemWandRod(props, TAWandParts.NEUTRONIUM),
            props -> props
                    .rarity(Rarity.EPIC));

    // 奥法奇点核心
    public static final DeferredItem<ArcaneSingularityFocusItem> ARCANE_SINGULARITY_FOCUS = ITEMS.registerItem(
            "arcane_singularity_focus",
            ArcaneSingularityFocusItem::new,
            props -> props
                    .stacksTo(1)
                    .rarity(Rarity.EPIC));

    // 下笔如有神之笔与墨
    public static final DeferredItem<InfinityScribingToolsItem> INFINITY_SCRIBING_TOOLS = ITEMS.registerItem(
            "infinity_scribing_tools",
            InfinityScribingToolsItem::new,
            props -> props
                    .stacksTo(1)
                    .rarity(Rarity.EPIC)
                    .durability(InfinityScribingToolsItem.MAX_DAMAGE));

    // 悖论珍珠
    public static final DeferredItem<CausalityCollapserPearlItem> CAUSALITY_COLLAPSER_PEARL = ITEMS.registerItem(
            "causality_collapser_pearl",
            CausalityCollapserPearlItem::new,
            props -> props
                    .stacksTo(16)
                    .rarity(Rarity.EPIC));
    // 无限结晶
    public static final DeferredItem<InfinitusCrystalItem> INFINITUS_CRYSTAL = ITEMS.registerItem(
            "infinitus_crystal",
            InfinitusCrystalItem::new,
            props -> props
                    .stacksTo(64)
                    .rarity(Rarity.EPIC));

    // 板
    public static final DeferredItem<Item> CRYSTAL_MATRIX_PLATE = ITEMS.registerSimpleItem("crystal_matrix_plate");
    public static final DeferredItem<Item> NEUTRON_PLATE = ITEMS.registerSimpleItem("neutron_plate");
    public static final DeferredItem<Item> INFINITY_PLATE = ITEMS.registerSimpleItem("infinity_plate");

    //极限炼金构件
    public static final DeferredItem<BlockItem> LIMIT_ALCHEMICAL_CONSTRUCT =
            ITEMS.registerSimpleBlockItem(TABlocks.LIMIT_ALCHEMICAL_CONSTRUCT);

    // 极限框架
    public static final DeferredItem<BlockItem> LIMIT_FRAME =
            ITEMS.registerSimpleBlockItem(TABlocks.LIMIT_FRAME);

    // 涌流充能器（lore 由 TATooltipHandler 统一添加）
    public static final DeferredItem<BlockItem> INFINITY_CHARGER =
            ITEMS.registerSimpleBlockItem(TABlocks.INFINITY_CHARGER);

    // 宇宙中子态素源质冶炼厂（lore 由 TATooltipHandler 统一添加）
    public static final DeferredItem<BlockItem> COSMIC_NEUTRONIUM_ESSENTIA_SMELTERY =
            ITEMS.registerSimpleBlockItem(TABlocks.COSMIC_NEUTRONIUM_ESSENTIA_SMELTERY);

    // 节点锚（lore 由 TATooltipHandler 统一添加）
    public static final DeferredItem<BlockItem> NODE_ANCHOR =
            ITEMS.registerSimpleBlockItem(TABlocks.NODE_ANCHOR);

    // 万物起源之罐（lore 由 TATooltipHandler 统一添加）
    public static final DeferredItem<BlockItem> ORIGIN_JAR =
            ITEMS.registerSimpleBlockItem(TABlocks.ORIGIN_JAR);

    // 无限收集器核心（四级独立方块物品，共享 InfinitusCollectorItem：手持右键灵气节点可把节点数据吸进收集器，
    // 节点被替换成收集器方块）。useBlockDescriptionPrefix 使显示名用 block. 键（方块本地化）。
    // 基础：一次性——吸干节点本钱即停。
    public static final DeferredItem<InfinitusCollectorItem> INFINITUS_COLLECTOR = ITEMS.registerItem(
            "infinitus_collector",
            props -> new InfinitusCollectorItem(TABlocks.INFINITUS_COLLECTOR.get(), props),
            props -> props.useBlockDescriptionPrefix().rarity(Rarity.RARE));
    // 紧凑：效率增强，耗灵气更慢。
    public static final DeferredItem<InfinitusCollectorItem> INFINITUS_COLLECTOR_COMPACT = ITEMS.registerItem(
            "infinitus_collector_compact",
            props -> new InfinitusCollectorItem(TABlocks.INFINITUS_COLLECTOR_COMPACT.get(), props),
            props -> props.useBlockDescriptionPrefix().rarity(Rarity.RARE));
    // 更密：吸收要素稳定。
    public static final DeferredItem<InfinitusCollectorItem> INFINITUS_COLLECTOR_DENSE = ITEMS.registerItem(
            "infinitus_collector_dense",
            props -> new InfinitusCollectorItem(TABlocks.INFINITUS_COLLECTOR_DENSE.get(), props),
            props -> props.useBlockDescriptionPrefix().rarity(Rarity.RARE));
    // 无垠：与无限结晶合体，自给永动。
    public static final DeferredItem<InfinitusCollectorItem> INFINITUS_COLLECTOR_ENDLESS = ITEMS.registerItem(
            "infinitus_collector_endless",
            props -> new InfinitusCollectorItem(TABlocks.INFINITUS_COLLECTOR_ENDLESS.get(), props),
            props -> props.useBlockDescriptionPrefix().rarity(Rarity.EPIC));

    private TAItems() {
    }

    public static void register(IEventBus modBus) {
        ITEMS.register(modBus);
    }
}
