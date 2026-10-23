package com.blackmoss.thaumicavaritia.registry;

import com.leclowndu93150.thaumaturge.TTIds;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public final class TATags {
    public static final TagKey<Item> CURIOSITIES = TagKey.create(Registries.ITEM, TTIds.rl("curiosities"));

    private TATags() {
    }
}
