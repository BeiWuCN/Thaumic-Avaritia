// 物品注册：无尽贪婪（Re:Avaritia）与神秘时代（Thaumaturge）联动物品。
package com.blackmoss.thaumicavaritia.registry;

import com.blackmoss.thaumicavaritia.ThaumicAvaritia;
import com.blackmoss.thaumicavaritia.content.item.AkashicRecordsItem;
import com.leclowndu93150.thaumaturge.content.wands.ItemWandCap;
import com.leclowndu93150.thaumaturge.content.wands.ItemWandRod;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class TAItems {

    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(ThaumicAvaritia.MODID);

    // 极元始珍珠：由 8 颗终末珍珠注魔而成，用作高价值注魔材料。
    public static final DeferredItem<Item> EXTREMELY_PRIMORDIAL_PEARL = ITEMS.registerItem(
            "extremely_primordial_pearl",
            Item::new,
            props -> props.rarity(Rarity.EPIC));

    // 阿卡西记录（Akashic Records）：使用后给予玩家全部要素的研究点。
    public static final DeferredItem<AkashicRecordsItem> AKASHIC_RECORDS = ITEMS.registerItem(
            "akashic_record",
            AkashicRecordsItem::new,
            props -> props.rarity(Rarity.EPIC));

    // 水晶矩阵杖端：水晶缠绕宇宙中子态素法杖的杖端，提供 90% Vis 减免。
    public static final DeferredItem<ItemWandCap> WAND_CAP_CRYSTAL = ITEMS.registerItem(
            "wand_cap_crystal",
            props -> new ItemWandCap(props, TAWandParts.CAP_CRYSTAL),
            props -> props.rarity(Rarity.EPIC));

    // 宇宙杖柄：水晶缠绕宇宙中子态素法杖的杖柄，超高容量并自动回满。
    public static final DeferredItem<ItemWandRod> WAND_ROD_STUDDED_COSMIC_NEUTRONIUM = ITEMS.registerItem(
            "wand_rod_studded_cosmic_neutronium",
            props -> new ItemWandRod(props, TAWandParts.ROD_INFINITY),
            props -> props.rarity(Rarity.EPIC));

    private TAItems() {
    }

    public static void register(IEventBus modBus) {
        ITEMS.register(modBus);
    }
}
