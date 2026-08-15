package com.blackmoss.thaumicavaritia.data.model;

import com.blackmoss.thaumicavaritia.ThaumicAvaritia;
import com.blackmoss.thaumicavaritia.registry.TAItems;
import committee.nova.mods.avaritia.client.model.loader.AvaritiaItemModelLoaders;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.client.data.models.model.ModelLocationUtils;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import org.jspecify.annotations.NonNull;

import java.util.List;

public final class TAModelProvider extends ModelProvider {

    public TAModelProvider(PackOutput output) {
        super(output, ThaumicAvaritia.MODID);
    }

    private static void generateHaloItem(ItemModelGenerators itemModels, Item item) {
        itemModels.createFlatItemModel(item, ModelTemplates.FLAT_ITEM);
        itemModels.itemModelOutput.accept(
                item,
                new AvaritiaItemModelLoaders.Halo(
                        ModelLocationUtils.getModelLocation(item),
                        Identifier.fromNamespaceAndPath("avaritia", "misc/halo"),
                        -16777216,
                        6,
                        false,
                        List.of()));
    }

    private static void generateHaloNoiseItem(ItemModelGenerators itemModels, Item item) {
        itemModels.createFlatItemModel(item, ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModels.itemModelOutput.accept(
                item,
                new AvaritiaItemModelLoaders.Halo(
                        ModelLocationUtils.getModelLocation(item),
                        Identifier.fromNamespaceAndPath("avaritia", "misc/halo_noise"),
                        872415231,
                        6,
                        false,
                        List.of()));
    }

    @Override
    protected void registerModels(@NonNull BlockModelGenerators blockModels, ItemModelGenerators itemModels) {
        itemModels.generateFlatItem(TAItems.WAND_CAP_CRYSTAL_MATRIX_INTER.get(), ModelTemplates.FLAT_ITEM);
        generateHaloItem(itemModels, TAItems.EXTREMELY_PRIMORDIAL_PEARL.get());
        generateHaloItem(itemModels, TAItems.AKASHIC_RECORDS.get());
        generateHaloItem(itemModels, TAItems.WAND_CAP_CRYSTAL_MATRIX.get());
        generateHaloItem(itemModels, TAItems.WAND_ROD_STUDDED_COSMIC_NEUTRONIUM.get());
        generateHaloItem(itemModels, TAItems.ARCANE_SINGULARITY_FOCUS.get());
        generateHaloItem(itemModels, TAItems.INFINITY_SCRIBING_TOOLS.get());
    }
}
