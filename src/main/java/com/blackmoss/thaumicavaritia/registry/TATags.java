package com.blackmoss.thaumicavaritia.registry;

import com.blackmoss.thaumicavaritia.ThaumicAvaritia;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public final class TATags {
    public static final TagKey<Item> THAUMATURGE_CURIOS = TagKey.create(Registries.ITEM, ThaumicAvaritia.identifier("curios"));

    private TATags() {
    }
}
