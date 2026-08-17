// 物品标签定义：跨模组联动的标签引用。
package com.blackmoss.thaumicavaritia.registry;

import com.blackmoss.thaumicavaritia.ThaumicAvaritia;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public final class TATags {
    // Thaumaturge 的珍宝（curio）物品标签，用作阿卡西记录的注魔催化剂来源。
    public static final TagKey<Item> THAUMATURGE_CURIOS =
            TagKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath("thaumaturge", "curios"));

    // 资格门禁标签：带此标签的物品必须解锁同名研究（物品注册 id 即研究条目 id）才能获得/合成。
    // 未解锁时阻止进入背包并提示"不够格"（见 mixin/TierCraftMenuMixin 与 NotQualifiedGate）。
    public static final TagKey<Item> NOT_QUALIFIED =
            TagKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(ThaumicAvaritia.MODID, "not_qualified"));

    private TATags() {
    }
}
