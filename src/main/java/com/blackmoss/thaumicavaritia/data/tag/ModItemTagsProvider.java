package com.blackmoss.thaumicavaritia.data.tag;

import com.blackmoss.thaumicavaritia.ThaumicAvaritia;
import com.blackmoss.thaumicavaritia.registry.TAItems;
import com.blackmoss.thaumicavaritia.registry.TATags;
import com.leclowndu93150.thaumaturge.registry.TTItemTags;
import com.leclowndu93150.thaumaturge.registry.TTItems;
import committee.nova.mods.avaritia.init.registry.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.ItemTagsProvider;
import org.jspecify.annotations.NonNull;

import java.util.concurrent.CompletableFuture;

public final class ModItemTagsProvider extends ItemTagsProvider {
    public ModItemTagsProvider(
            PackOutput output,
            CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, lookupProvider, ThaumicAvaritia.MODID);
    }

    @Override
    protected void addTags(HolderLookup.@NonNull Provider provider) {
        tag(ModTags.IMMORTAL_ITEM).add(
                TAItems.EXTREMELY_PRIMORDIAL_PEARL.get(),
                TAItems.AKASHIC_RECORDS.get(),
                TAItems.CRYSTAL_MATRIX_WAND_CAP.get(),
                TAItems.NEUTRONIUM_WAND_ROD.get(),
                TAItems.ARCANE_SINGULARITY_FOCUS.get(),
                TAItems.INFINITY_SCRIBING_TOOLS.get(),
                TAItems.COSMIC_NEUTRONIUM_ESSENTIA_SMELTERY.get()
        );

        tag(TATags.CURIOSITIES).add(
                TTItems.CURIO_ARCANE.get(),
                TTItems.CURIO_PRESERVED.get(),
                TTItems.CURIO_ANCIENT.get(),
                TTItems.CURIO_ELDRITCH.get(),
                TTItems.CURIO_KNOWLEDGE.get(),
                TTItems.CURIO_TWISTED.get(),
                TTItems.CURIO_RITES.get(),
                TTItems.PECH_WAND.get());

        tag(TTItemTags.SCRIBING_TOOLS)
                .add(TAItems.INFINITY_SCRIBING_TOOLS.get());

        // 资格门禁：未解锁同名研究前无法获得/合成的物品。
//        tag(TATags.NOT_QUALIFIED).add(
//                TAItems.WAND_ROD_STUDDED_COSMIC_NEUTRONIUM.get(),
//                TAItems.ARCANE_SINGULARITY_FOCUS.get());
    }

    @Override
    public @NonNull String getName() {
        return "Thaumic Avaritia Item Tags";
    }
}