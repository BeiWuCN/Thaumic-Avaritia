package com.blackmoss.thaumicavaritia.data;

import com.blackmoss.thaumicavaritia.registry.TABlocks;
import java.util.Set;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;
import org.jspecify.annotations.NonNull;

// 方块战利品表：本 mod 的全部方块破坏后均掉落自身。
public final class TABlockLootSubProvider extends BlockLootSubProvider {

    private final HolderLookup.Provider lookupProvider;

    public TABlockLootSubProvider(HolderLookup.Provider lookupProvider) {
        super(Set.of(), FeatureFlags.DEFAULT_FLAGS, lookupProvider);
        this.lookupProvider = lookupProvider;
    }

    @Override
    protected @NonNull Iterable<Block> getKnownBlocks() {
        return TABlocks.BLOCKS.getEntries().stream().map(holder -> (Block) holder.value()).toList();
    }

    @Override
    protected void generate() {
        dropSelf(TABlocks.INFINITY_CHARGER.get());
        dropSelf(TABlocks.COSMIC_NEUTRONIUM_ESSENTIA_SMELTERY.get());
        dropSelf(TABlocks.NODE_ANCHOR.get());
        dropSelf(TABlocks.ORIGIN_JAR.get());
        dropSelf(TABlocks.LIMIT_FRAME.get());
        dropSelf(TABlocks.LIMIT_ALCHEMICAL_CONSTRUCT.get());
        dropSelf(TABlocks.INFINITUS_COLLECTOR.get());
        dropSelf(TABlocks.INFINITUS_COLLECTOR_COMPACT.get());
        dropSelf(TABlocks.INFINITUS_COLLECTOR_DENSE.get());
        dropSelf(TABlocks.INFINITUS_COLLECTOR_ENDLESS.get());
    }

    public HolderLookup.Provider getLookupProvider() {
        return lookupProvider;
    }
}
