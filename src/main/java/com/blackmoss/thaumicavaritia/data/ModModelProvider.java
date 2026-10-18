package com.blackmoss.thaumicavaritia.data;

import com.blackmoss.thaumicavaritia.ThaumicAvaritia;
import com.blackmoss.thaumicavaritia.block.NodeAnchorBlock;
import com.blackmoss.thaumicavaritia.client.model.NodeAnchorItemSpecialRenderer;
import com.blackmoss.thaumicavaritia.registry.TABlocks;
import com.blackmoss.thaumicavaritia.registry.TAItems;
import committee.nova.mods.avaritia.Const;
import committee.nova.mods.avaritia.client.model.loader.AvaritiaItemModelLoaders;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.client.data.models.MultiVariant;
import net.minecraft.client.data.models.blockstates.MultiVariantGenerator;
import net.minecraft.client.data.models.blockstates.PropertyDispatch;
import net.minecraft.client.data.models.model.ModelLocationUtils;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.client.renderer.block.dispatch.Variant;
import net.minecraft.client.renderer.block.dispatch.VariantMutator;
import net.minecraft.client.renderer.item.SpecialModelWrapper;
import net.minecraft.core.Direction;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.Identifier;
import net.minecraft.util.random.WeightedList;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import org.jspecify.annotations.NonNull;

import java.util.List;
import java.util.Optional;

public final class ModModelProvider extends ModelProvider {
    public ModModelProvider(PackOutput output) {
        super(output, ThaumicAvaritia.MODID);
    }

    private static void haloItem(ItemModelGenerators itemModels, Item item, Identifier innerModel) {
        itemModels.itemModelOutput.accept(
                item,
                new AvaritiaItemModelLoaders.Halo(
                        innerModel,
                        Const.rl("misc/halo"),
                        -16777216,
                        6,
                        false,
                        List.of()));
    }

    private static void haloItem(ItemModelGenerators itemModels, Item item) {
        itemModels.createFlatItemModel(item, ModelTemplates.FLAT_ITEM);
        haloItem(itemModels, item, ModelLocationUtils.getModelLocation(item));
    }

    private static void haloItem(ItemModelGenerators itemModels, Block block) {
        haloItem(itemModels, block.asItem(), ModelLocationUtils.getModelLocation(block));
    }

    private static void noiseItem(ItemModelGenerators itemModels, Item item) {
        itemModels.createFlatItemModel(item, ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModels.itemModelOutput.accept(
                item,
                new AvaritiaItemModelLoaders.Halo(
                        ModelLocationUtils.getModelLocation(item),
                        Const.rl("misc/halo_noise"),
                        872415231,
                        6,
                        false,
                        List.of()));
    }

    private static void haloBlock(BlockModelGenerators blockModels, ItemModelGenerators itemModels, Block block) {
        blockModels.createTrivialCube(block);
        haloItem(itemModels, block);
    }

    @Override
    protected void registerModels(@NonNull BlockModelGenerators blockModels, ItemModelGenerators itemModels) {
        itemModels.generateFlatItem(TAItems.INTER_CRYSTAL_MATRIX_WAND_CAP.get(), ModelTemplates.FLAT_ITEM);
        haloItem(itemModels, TAItems.EXTREMELY_PRIMORDIAL_PEARL.get());
        haloItem(itemModels, TAItems.AKASHIC_RECORDS.get());
        haloItem(itemModels, TAItems.CRYSTAL_MATRIX_WAND_CAP.get());
        haloItem(itemModels, TAItems.NEUTRONIUM_WAND_ROD.get());
        haloItem(itemModels, TAItems.ARCANE_SINGULARITY_FOCUS.get());
        haloItem(itemModels, TAItems.INFINITY_SCRIBING_TOOLS.get());
        haloItem(itemModels, TAItems.CAUSALITY_COLLAPSER_PEARL.get());

        Block infinityCharger = TABlocks.INFINITY_CHARGER.get();
        Identifier infinityChargerModel = ModelLocationUtils.getModelLocation(infinityCharger);
        blockModels.blockStateOutput.accept(BlockModelGenerators.createSimpleBlock(
                infinityCharger,
                BlockModelGenerators.plainVariant(infinityChargerModel)));
        haloItem(itemModels, infinityCharger.asItem(), infinityChargerModel);

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
        haloItem(itemModels, cosmicSmeltery.asItem(), smelteryOff);

        Block nodeAnchor = TABlocks.NODE_ANCHOR.get();
        Identifier anchorModel = ModelLocationUtils.getModelLocation(nodeAnchor);
        Identifier anchorChargedModel = ModelLocationUtils.getModelLocation(nodeAnchor).withSuffix("_charged");
        blockModels.blockStateOutput.accept(
                MultiVariantGenerator.dispatch(nodeAnchor)
                        .with(PropertyDispatch.initial(NodeAnchorBlock.FACING, NodeAnchorBlock.CHARGED)
                                .generate((facing, charged) -> {
                                    Identifier model = charged
                                            ? anchorChargedModel
                                            : anchorModel;
                                    VariantMutator rotation;
                                    if (facing == Direction.DOWN) {
                                        rotation = BlockModelGenerators.X_ROT_180;
                                    } else {
                                        rotation = BlockModelGenerators.NOP;
                                    }
                                    return new MultiVariant(WeightedList.of(rotation.apply(new Variant(model))));
                                })));
        itemModels.itemModelOutput.accept(
                nodeAnchor.asItem(),
                new SpecialModelWrapper.Unbaked(
                        anchorModel,
                        Optional.empty(),
                        new NodeAnchorItemSpecialRenderer.Unbaked()));

        Block originJar = TABlocks.ORIGIN_JAR.get();
        Identifier originJarModel = ModelLocationUtils.getModelLocation(originJar);
        blockModels.blockStateOutput.accept(BlockModelGenerators.createSimpleBlock(
                originJar, BlockModelGenerators.plainVariant(originJarModel)));
        haloItem(itemModels, originJar.asItem(), originJarModel);

        for (Block collectorBlock : List.of(
                TABlocks.INFINITUS_COLLECTOR.get(),
                TABlocks.INFINITUS_COLLECTOR_COMPACT.get(),
                TABlocks.INFINITUS_COLLECTOR_DENSE.get(),
                TABlocks.INFINITUS_COLLECTOR_ENDLESS.get())) {

            Identifier collectorOff = ModelLocationUtils.getModelLocation(collectorBlock).withSuffix("_off");
            Identifier collectorOn = ModelLocationUtils.getModelLocation(collectorBlock).withSuffix("_on");
            blockModels.blockStateOutput.accept(
                    MultiVariantGenerator.dispatch(collectorBlock)
                            .with(BlockModelGenerators.createBooleanModelDispatch(
                                    BlockStateProperties.LIT,
                                    BlockModelGenerators.plainVariant(collectorOn),
                                    BlockModelGenerators.plainVariant(collectorOff)))
                            .with(BlockModelGenerators.ROTATION_HORIZONTAL_FACING));
            haloItem(itemModels, collectorBlock.asItem(), collectorOff);
        }

        haloBlock(blockModels, itemModels, TABlocks.LIMIT_FRAME.get());
        haloBlock(blockModels, itemModels, TABlocks.LIMIT_ALCHEMICAL_CONSTRUCT.get());

        haloItem(itemModels, TAItems.INFINITUS_CRYSTAL.get());

        // 三种板
        haloItem(itemModels, TAItems.CRYSTAL_MATRIX_PLATE.get());
        noiseItem(itemModels, TAItems.NEUTRON_PLATE.get());
        haloItem(itemModels, TAItems.INFINITY_PLATE.get());
    }


    @Override
    public @NonNull String getName() {
        return "Thaumic Avaritia Models";
    }
}
