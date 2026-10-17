package com.blackmoss.thaumicavaritia.data;

import com.blackmoss.thaumicavaritia.ThaumicAvaritia;
import com.blackmoss.thaumicavaritia.block.NodeAnchorBlock;
import com.blackmoss.thaumicavaritia.registry.TABlocks;
import com.blackmoss.thaumicavaritia.registry.TAItems;
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
import net.minecraft.core.Direction;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.Identifier;
import net.minecraft.util.random.WeightedList;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import org.jspecify.annotations.NonNull;

import java.util.List;

public final class ModModelProvider extends ModelProvider {

    public ModModelProvider(PackOutput output) {
        super(output, ThaumicAvaritia.MODID);
    }

    // 基础 halo 包装：内层指向给定模型。
    private static void haloItem(ItemModelGenerators itemModels, Item item, Identifier innerModel) {
        itemModels.itemModelOutput.accept(
                item,
                new AvaritiaItemModelLoaders.Halo(
                        innerModel,
                        Identifier.fromNamespaceAndPath("avaritia", "misc/halo"),
                        -16777216,
                        6,
                        false,
                        List.of()));
    }

    // 扁平物品 + 默认 halo（内层指向物品自身模型位置）。
    private static void haloItem(ItemModelGenerators itemModels, Item item) {
        itemModels.createFlatItemModel(item, ModelTemplates.FLAT_ITEM);
        haloItem(itemModels, item, ModelLocationUtils.getModelLocation(item));
    }

    // 方块物品 + 默认 halo（内层直接指向方块的 block 模型，复用方块已生成的模型位置；
    // 不再生成扁平 item/<name>.json 模型，因为方块纹理在 block/ 路径而非 item/）。
    private static void haloItem(ItemModelGenerators itemModels, Block block) {
        haloItem(itemModels, block.asItem(), ModelLocationUtils.getModelLocation(block));
    }

    // 手持扁平物品 + 噪声 halo（内层指向物品自身模型位置）。
    private static void noiseItem(ItemModelGenerators itemModels, Item item) {
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
        itemModels.generateFlatItem(TAItems.INTER_CRYSTAL_MATRIX_WAND_CAP.get(), ModelTemplates.FLAT_ITEM);
        haloItem(itemModels, TAItems.EXTREMELY_PRIMORDIAL_PEARL.get());
        haloItem(itemModels, TAItems.AKASHIC_RECORDS.get());
        haloItem(itemModels, TAItems.CRYSTAL_MATRIX_WAND_CAP.get());
        haloItem(itemModels, TAItems.NEUTRONIUM_WAND_ROD.get());
        haloItem(itemModels, TAItems.ARCANE_SINGULARITY_FOCUS.get());
        haloItem(itemModels, TAItems.INFINITY_SCRIBING_TOOLS.get());
        haloItem(itemModels, TAItems.CAUSALITY_COLLAPSER_PEARL.get());

        // 涌流充能器：blockstate 由 DataGen 生成（引用手写的 tcmesh block 模型），
        // 物品模型用 Halo 光环包装（内层指向该 block 模型，非扁平贴图）。
        Block infinityCharger = TABlocks.INFINITY_CHARGER.get();
        Identifier infinityChargerModel = ModelLocationUtils.getModelLocation(infinityCharger);
        blockModels.blockStateOutput.accept(BlockModelGenerators.createSimpleBlock(
                infinityCharger,
                BlockModelGenerators.plainVariant(infinityChargerModel)));
        haloItem(itemModels, infinityCharger.asItem(), infinityChargerModel);

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
        // smeltery 仅有 _off/_on 变体无 base 模型，必须显式传 smelteryOff。
        haloItem(itemModels, cosmicSmeltery.asItem(), smelteryOff);

        // 节点锚：两个属性分派 4 种组合。
        // - FACING(UP/DOWN)：爪朝向，放置时决定（默认 UP，Shift 放置 DOWN）。
        //   模型本身按朝上(UP)姿态建模，故 UP→不旋转(NOP)，DOWN→绕X翻转180°(X_ROT_180)。
        // - CHARGED：true=上方两格有涌流充能器（BE tick 维护），切换 node_anchor ↔ node_anchor_charged 造型。
        // 原版稳定器/换能器是两块方块两套 BER，本 mod 用这两个属性 + 同一底座旋转/造型区分。
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
        // 物品模型用 Halo 光环包装（内层指向未充能模型）。
        haloItem(itemModels, nodeAnchor);

        // 万物起源之罐：block 模型手写（复用 jar 几何，纹理自绘 origin_jar_*），
        // blockstate 由 DataGen 生成；物品模型用 Halo 光环包装（内层指向 block 模型）。
        Block originJar = TABlocks.ORIGIN_JAR.get();
        Identifier originJarModel = ModelLocationUtils.getModelLocation(originJar);
        blockModels.blockStateOutput.accept(BlockModelGenerators.createSimpleBlock(
                originJar, BlockModelGenerators.plainVariant(originJarModel)));
        haloItem(itemModels, originJar.asItem(), originJarModel);

        // 无限收集器：四个独立方块，block 模型手写（models/block/infinitus_collector{,_compact,_dense,_endless}.json）。
        // FACING 用 ROTATION_HORIZONTAL_FACING 自动按水平朝向旋转模型（仿冶炼厂，放置时跟玩家 facing 相反）。
        // 物品模型 Halo 内层指向对应手写模型。
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

        // 极限框架：普通立方体方块（DataGen 生成模型 + blockstate），物品 Halo。
        Block limitFrame = TABlocks.LIMIT_FRAME.get();
        blockModels.createTrivialCube(limitFrame);
        haloItem(itemModels, limitFrame);

        // 极限炼金构材：普通立方体方块（DataGen 生成模型 + blockstate），物品 Halo。
        Block limitConstruct = TABlocks.LIMIT_ALCHEMICAL_CONSTRUCT.get();
        blockModels.createTrivialCube(limitConstruct);
        haloItem(itemModels, limitConstruct);

        // 无限结晶：扁平物品模型 + Halo 光环。
        haloItem(itemModels, TAItems.INFINITUS_CRYSTAL.get());

        // 三种板
        haloItem(itemModels, TAItems.CRYSTAL_MATRIX_PLATE.get());
        noiseItem(itemModels, TAItems.NEUTRON_PLATE.get());
        haloItem(itemModels, TAItems.INFINITY_PLATE.get());
    }
}
