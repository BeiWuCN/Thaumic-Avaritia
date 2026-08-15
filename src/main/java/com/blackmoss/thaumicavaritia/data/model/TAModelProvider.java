package com.blackmoss.thaumicavaritia.data.model;

import com.blackmoss.thaumicavaritia.ThaumicAvaritia;
import com.blackmoss.thaumicavaritia.registry.TABlocks;
import com.blackmoss.thaumicavaritia.registry.TAItems;
import committee.nova.mods.avaritia.client.model.loader.AvaritiaItemModelLoaders;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.client.data.models.blockstates.MultiVariantGenerator;
import net.minecraft.client.data.models.model.ModelLocationUtils;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
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
        generateHaloItem(itemModels, TAItems.CAUSALITY_COLLAPSER_PEARL.get());

        // 无限充能器：blockstate 由 DataGen 生成（引用手写的 tcmesh block 模型），
        // 物品模型用 Halo 光环包装（内层指向该 block 模型，非扁平贴图）。
        Block infinityCharger = TABlocks.INFINITY_CHARGER.get();
        Identifier infinityChargerModel = ModelLocationUtils.getModelLocation(infinityCharger);
        blockModels.blockStateOutput.accept(BlockModelGenerators.createSimpleBlock(
                infinityCharger,
                BlockModelGenerators.plainVariant(infinityChargerModel)));
        itemModels.itemModelOutput.accept(
                infinityCharger.asItem(),
                new AvaritiaItemModelLoaders.Halo(
                        infinityChargerModel,
                        Identifier.fromNamespaceAndPath("avaritia", "misc/halo"),
                        -16777216,
                        6,
                        false,
                        List.of()));

        // 宇宙中子态素源质冶炼厂：block 模型手写 off/on 两版（复用原版冶炼炉网格，
        // 正面/侧面/顶面各自绘纹理，lit 切换正面），blockstate 由 DataGen 按 facing 旋转 + lit 切换生成。
        Block cosmicSmeltery = TABlocks.COSMIC_NEUTRONIUM_ESSENTIA_SMELTERY.get();
        Identifier smelteryOff = ModelLocationUtils.getModelLocation(cosmicSmeltery).withSuffix("_off");
        Identifier smelteryOn = ModelLocationUtils.getModelLocation(cosmicSmeltery).withSuffix("_on");
        blockModels.blockStateOutput.accept(
                MultiVariantGenerator.dispatch(cosmicSmeltery)
                        .with(BlockModelGenerators.createBooleanModelDispatch(
                                BlockStateProperties.LIT,
                                BlockModelGenerators.plainVariant(smelteryOn),
                                BlockModelGenerators.plainVariant(smelteryOff)))
                        .with(BlockModelGenerators.ROTATION_HORIZONTAL_FACING));
        // 物品模型用 Halo 光环包装（内层指向 off 版 block 模型）。
        itemModels.itemModelOutput.accept(
                cosmicSmeltery.asItem(),
                new AvaritiaItemModelLoaders.Halo(
                        smelteryOff,
                        Identifier.fromNamespaceAndPath("avaritia", "misc/halo"),
                        -16777216,
                        6,
                        false,
                        List.of()));
    }
}
