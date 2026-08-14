// 物品标签定义：跨模组联动的标签引用。
package com.blackmoss.thaumicavaritia.registry;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public final class TATags {
    // Thaumaturge 的珍宝（curio）物品标签，用作阿卡西记录的注魔催化剂来源。
    public static final TagKey<Item> THAUMATURGE_CURIOS =
            TagKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath("thaumaturge", "curios"));

    private TATags() {
    }
}
