// 物品模型 DataGen：为无尽贪婪联动物品生成内层扁平模型与 avaritia 无尽光环（halo）物品模型。
package com.blackmoss.thaumicavaritia.data.model;

import com.blackmoss.thaumicavaritia.ThaumicAvaritia;
import com.blackmoss.thaumicavaritia.registry.TAItems;
import com.leclowndu93150.thaumaturge.client.color.FocusColorTint;
import committee.nova.mods.avaritia.client.model.loader.AvaritiaItemModelLoaders;
import java.util.List;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.client.data.models.model.ItemModelUtils;
import net.minecraft.client.data.models.model.ModelLocationUtils;
import net.minecraft.client.data.models.model.ModelTemplate;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.client.data.models.model.TextureMapping;
import net.minecraft.client.renderer.item.ItemModel;
import net.minecraft.client.resources.model.sprite.Material;
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

        // 奥法奇点核心：与原版法术核心一致的 tint 模型（颜色随核心内法术元素变化）。
        registerFocusItem(itemModels, TAItems.ARCANE_SINGULARITY_FOCUS.get());
    }

    // 法术核心模型：扁平模型 + FocusColorTint（引用 Thaumaturge 原版核心纹理，可自绘替换）。
    private static void registerFocusItem(ItemModelGenerators itemModels, Item item) {
        Identifier model = ModelTemplates.FLAT_ITEM.create(
                ModelLocationUtils.getModelLocation(item),
                TextureMapping.layer0(new Material(Identifier.fromNamespaceAndPath("thaumaturge", "item/focus_3"))),
                itemModels.modelOutput);
        itemModels.itemModelOutput.accept(item, ItemModelUtils.tintedModel(model, new FocusColorTint()));
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
