package com.blackmoss.thaumicavaritia.content.block.infinituscollector;

import com.blackmoss.thaumicavaritia.registry.TAItems;
import net.minecraft.world.item.ItemStack;

public enum InfinitusCollectorTier {
    DEFAULT("infinitus_collector", TAItems.INFINITUS_CRYSTAL, 36000),
    COMPACT("infinitus_collector_compact", TAItems.INFINITUS_CRYSTAL, 27000),
    DENSE("infinitus_collector_dense", TAItems.INFINITUS_CRYSTAL, 18000),
    ENDLESS("infinitus_collector_endless", TAItems.INFINITUS_CRYSTAL, 90000);

    public final int production_ticks;
    public final net.minecraft.world.level.ItemLike production;
    public final String name;

    InfinitusCollectorTier(String name, net.minecraft.world.level.ItemLike production, int production_ticks) {
        this.production_ticks = production_ticks;
        this.production = production;
        this.name = name;
    }

    public ItemStack createProductionStack() {
        return new ItemStack(production);
    }
}