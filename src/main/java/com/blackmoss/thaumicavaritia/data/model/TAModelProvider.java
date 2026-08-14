package com.blackmoss.thaumicavaritia.data.model;

import com.blackmoss.thaumicavaritia.ThaumicAvaritia;
import com.blackmoss.thaumicavaritia.registry.TAItems;
import committee.nova.mods.avaritia.client.model.loader.AvaritiaItemModelLoaders;
import java.util.List;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.client.data.models.model.ModelLocationUtils;
import net.minecraft.client.data.models.model.ModelTemplate;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import org.jspecify.annotations.NonNull;

public final class TAModelProvider extends ModelProvider {

    public TAModelProvider(PackOutput output) {
        super(output, ThaumicAvaritia.MODID);
    }

    @Override
    protected void registerModels(@NonNull BlockModelGenerators blockModels, ItemModelGenerators itemModels) {
        // 惰性水晶矩阵杖端：普通扁平物品模型（无 halo 光圈）。
        itemModels.generateFlatItem(TAItems.WAND_CAP_CRYSTAL_INTER.get(), ModelTemplates.FLAT_ITEM);

        // 全部联动物品使用 avaritia 无尽背景光圈（halo）：内层扁平/手持模型 + avaritia 的 halo 纹理。
        generateHaloItem(itemModels, TAItems.EXTREMELY_PRIMORDIAL_PEARL.get(), ModelTemplates.FLAT_ITEM);
        generateHaloItem(itemModels, TAItems.AKASHIC_RECORDS.get(), ModelTemplates.FLAT_ITEM);
        generateHaloItem(itemModels, TAItems.WAND_CAP_CRYSTAL.get(), ModelTemplates.FLAT_ITEM);
        generateHaloItem(itemModels, TAItems.WAND_ROD_STUDDED_COSMIC_NEUTRONIUM.get(), ModelTemplates.FLAT_HANDHELD_ITEM);
        generateHaloItem(itemModels, TAItems.ARCANE_SINGULARITY_FOCUS.get(), ModelTemplates.FLAT_ITEM);
    }

    // 生成内层模型（自动引用 item/<注册名>.png 纹理）+ avaritia halo 物品模型（items/<注册名>.json）。
    private static void generateHaloItem(ItemModelGenerators itemModels, Item item, ModelTemplate template) {
        itemModels.createFlatItemModel(item, template);
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
}
