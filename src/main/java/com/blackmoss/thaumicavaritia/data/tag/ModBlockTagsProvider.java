package com.blackmoss.thaumicavaritia.data.tag;

import com.blackmoss.thaumicavaritia.ThaumicAvaritia;
import com.blackmoss.thaumicavaritia.registry.TABlocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import org.jspecify.annotations.NonNull;

import java.util.concurrent.CompletableFuture;

public final class ModBlockTagsProvider extends BlockTagsProvider {
    public ModBlockTagsProvider(
            PackOutput output,
            CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, lookupProvider, ThaumicAvaritia.MODID);
    }

    protected void addTags(HolderLookup.@NonNull Provider provider) {
        tag(BlockTags.MINEABLE_WITH_PICKAXE).add(
                TABlocks.INFINITY_CHARGER.get(),
                TABlocks.COSMIC_NEUTRONIUM_ESSENTIA_SMELTERY.get(),
                TABlocks.INFINITUS_COLLECTOR.get(),
                TABlocks.INFINITUS_COLLECTOR_COMPACT.get(),
                TABlocks.INFINITUS_COLLECTOR_DENSE.get(),
                TABlocks.INFINITUS_COLLECTOR_ENDLESS.get(),
                TABlocks.NODE_ANCHOR.get(),
                TABlocks.LIMIT_FRAME.get(),
                TABlocks.LIMIT_ALCHEMICAL_CONSTRUCT.get());
    }

    @Override
    public @NonNull String getName() {
        return "Thaumic Avaritia Block Tags";
    }
}
