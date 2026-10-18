package com.blackmoss.thaumicavaritia.data;

import com.blackmoss.thaumicavaritia.ThaumicAvaritia;
import com.blackmoss.thaumicavaritia.registry.TAAspects;
import com.blackmoss.thaumicavaritia.registry.TAItems;
import com.leclowndu93150.thaumaturge.TTIds;
import com.leclowndu93150.thaumaturge.api.aspect.AspectInstance;
import com.leclowndu93150.thaumaturge.api.aspect.IAspect;
import com.leclowndu93150.thaumaturge.api.aspect.TTAspects;
import com.leclowndu93150.thaumaturge.api.recipe.ResearchGate;
import com.leclowndu93150.thaumaturge.data.recipe.builders.CrucibleRecipeBuilder;
import com.leclowndu93150.thaumaturge.data.recipe.builders.InfusionRecipeBuilder;
import com.leclowndu93150.thaumaturge.data.recipe.builders.workbench.ArcaneWorkbenchShapedRecipeBuilder;
import com.leclowndu93150.thaumaturge.data.recipe.builders.workbench.ArcaneWorkbenchShapelessRecipeBuilder;
import com.leclowndu93150.thaumaturge.registry.TTDataComponents;
import com.leclowndu93150.thaumaturge.registry.TTItems;
import committee.nova.mods.avaritia.common.crafting.recipe.ExtremeSmithingRecipe;
import committee.nova.mods.avaritia.common.crafting.recipe.ShapedTableCraftingRecipe;
import committee.nova.mods.avaritia.init.registry.ModBlocks;
import committee.nova.mods.avaritia.init.registry.ModItems;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.ShapedRecipePattern;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.common.crafting.CompoundIngredient;
import net.neoforged.neoforge.common.crafting.DataComponentIngredient;
import org.jspecify.annotations.NonNull;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public final class ModRecipeProvider extends RecipeProvider {
    private final HolderLookup.Provider lookupProvider;
    private final HolderGetter<IAspect> aspects;

    private ModRecipeProvider(HolderLookup.Provider provider, RecipeOutput output) {
        super(provider, output);
        this.lookupProvider = provider;
        this.aspects = registries.lookupOrThrow(IAspect.REGISTRY_KEY);
    }

    private static ResearchGate taGate(String path) {
        return new ResearchGate(ThaumicAvaritia.identifier(path), Optional.empty(), false);
    }

    private static ResearchGate ttGate(String path) {
        return new ResearchGate(TTIds.rl(path), Optional.empty(), false);
    }

    @Override
    protected void buildRecipes() {
        // 极元始珍珠
        infusion(TAItems.EXTREMELY_PRIMORDIAL_PEARL, TTItems.PRIMORDIAL_PEARL)
                .component(Ingredient.of(TTItems.PRIMORDIAL_PEARL.get()))
                .component(Ingredient.of(TTItems.PRIMORDIAL_PEARL.get()))
                .component(Ingredient.of(TTItems.PRIMORDIAL_PEARL.get()))
                .component(Ingredient.of(TTItems.PRIMORDIAL_PEARL.get()))
                .component(Ingredient.of(TTItems.PRIMORDIAL_PEARL.get()))
                .component(Ingredient.of(TTItems.PRIMORDIAL_PEARL.get()))
                .component(Ingredient.of(TTItems.PRIMORDIAL_PEARL.get()))
                .aspect(TTAspects.ALIENIS, 640)
                .aspect(TTAspects.PERDITIO, 640)
                .aspect(TTAspects.PRAECANTATIO, 640)
                .instability(4)
                .unlockedBy("has", has(TTItems.PRIMORDIAL_PEARL.get()))
                .save(output);

        // 悖论珍珠
        infusion(TAItems.CAUSALITY_COLLAPSER_PEARL, TTItems.CAUSALITY_COLLAPSER)
                .component(Ingredient.of(ModItems.endest_pearl.get()))
                .component(Ingredient.of(ModItems.endest_pearl.get()))
                .component(Ingredient.of(ModItems.endest_pearl.get()))
                .component(Ingredient.of(ModItems.endest_pearl.get()))
                .component(Ingredient.of(ModItems.endest_pearl.get()))
                .component(Ingredient.of(ModItems.endest_pearl.get()))
                .component(Ingredient.of(ModItems.endest_pearl.get()))
                .component(Ingredient.of(ModItems.endest_pearl.get()))
                .aspect(TTAspects.ALIENIS, 640)
                .aspect(TTAspects.PERDITIO, 640)
                .aspect(TTAspects.VACUOS, 640)
                .aspect(TTAspects.PRAECANTATIO, 640)
                .aspect(TAAspects.INFINITUS, 1)
                .instability(6)
                .unlockedBy("has", has(TTItems.CAUSALITY_COLLAPSER.get()))
                .save(output);

        // 阿卡西记录
        infusion(TAItems.AKASHIC_RECORDS, TTItems.THAUMONOMICON_LINKING)
                .component(Ingredient.of(TTItems.CURIO_ARCANE.get()))
                .component(Ingredient.of(ModItems.infinity_ingot.get()))
                .component(Ingredient.of(TTItems.CURIO_PRESERVED.get()))
                .component(Ingredient.of(ModItems.infinity_ingot.get()))
                .component(Ingredient.of(TTItems.CURIO_ANCIENT.get()))
                .component(Ingredient.of(ModItems.infinity_ingot.get()))
                .component(Ingredient.of(TTItems.CURIO_KNOWLEDGE.get()))
                .component(Ingredient.of(ModItems.infinity_ingot.get()))
                .component(Ingredient.of(TTItems.CURIO_TWISTED.get()))
                .component(Ingredient.of(ModItems.infinity_ingot.get()))
                .component(Ingredient.of(TTItems.CURIO_RITES.get()))
                .component(Ingredient.of(ModItems.infinity_ingot.get()))
                .component(Ingredient.of(TTItems.PECH_WAND.get()))
                .component(Ingredient.of(ModItems.infinity_ingot.get()))
                .aspect(TTAspects.COGNITIO, 640)
                .aspect(TTAspects.PRAECANTATIO, 640)
                .aspect(TTAspects.AURAM, 640)
                .aspect(TTAspects.SPIRITUS, 640)
                .aspect(TTAspects.ALIENIS, 640)
                .aspect(TTAspects.DESIDERIUM, 640)
                .aspect(TAAspects.INFINITUS, 3)
                .instability(4)
                .unlockedBy("has", has(ModItems.infinity_ingot.get()))
                .save(output);

        // 充能水晶矩阵杖端
        infusion(TAItems.CRYSTAL_MATRIX_WAND_CAP, TAItems.INTER_CRYSTAL_MATRIX_WAND_CAP)
                .component(Ingredient.of(TTItems.PRIMORDIAL_PEARL.get()))
                .component(Ingredient.of(TTItems.SALIS_MUNDUS.get()))
                .component(Ingredient.of(TTItems.SALIS_MUNDUS.get()))
                .component(Ingredient.of(TTItems.PRIMAL_CHARM.get()))
                .component(Ingredient.of(TTItems.FOCUS_3.get()))
                .component(Ingredient.of(TTItems.PRIMAL_CHARM.get()))
                .component(Ingredient.of(TTItems.SALIS_MUNDUS.get()))
                .component(Ingredient.of(TTItems.SALIS_MUNDUS.get()))
                .component(Ingredient.of(TTItems.PRIMORDIAL_PEARL.get()))
                .component(Ingredient.of(TTItems.SALIS_MUNDUS.get()))
                .component(Ingredient.of(TTItems.SALIS_MUNDUS.get()))
                .component(Ingredient.of(TTItems.PRIMAL_CHARM.get()))
                .component(Ingredient.of(TTItems.FOCUS_3.get()))
                .component(Ingredient.of(TTItems.PRIMAL_CHARM.get()))
                .component(Ingredient.of(TTItems.SALIS_MUNDUS.get()))
                .component(Ingredient.of(TTItems.SALIS_MUNDUS.get()))
                .component(Ingredient.of(TTItems.PRIMORDIAL_PEARL.get()))
                .component(Ingredient.of(TTItems.SALIS_MUNDUS.get()))
                .component(Ingredient.of(TTItems.SALIS_MUNDUS.get()))
                .component(Ingredient.of(TTItems.PRIMAL_CHARM.get()))
                .component(Ingredient.of(TTItems.FOCUS_3.get()))
                .component(Ingredient.of(TTItems.PRIMAL_CHARM.get()))
                .component(Ingredient.of(TTItems.SALIS_MUNDUS.get()))
                .component(Ingredient.of(TTItems.SALIS_MUNDUS.get()))
                .component(Ingredient.of(TTItems.PRIMORDIAL_PEARL.get()))
                .component(Ingredient.of(TTItems.SALIS_MUNDUS.get()))
                .component(Ingredient.of(TTItems.SALIS_MUNDUS.get()))
                .component(Ingredient.of(TTItems.PRIMAL_CHARM.get()))
                .component(Ingredient.of(TTItems.FOCUS_3.get()))
                .component(Ingredient.of(TTItems.PRIMAL_CHARM.get()))
                .component(Ingredient.of(TTItems.SALIS_MUNDUS.get()))
                .component(Ingredient.of(TTItems.SALIS_MUNDUS.get()))
                .aspect(TTAspects.PRAECANTATIO, 640)
                .aspect(TTAspects.AURAM, 640)
                .aspect(TTAspects.INSTRUMENTUM, 640)
                .aspect(TTAspects.ORDO, 640)
                .aspect(TTAspects.POTENTIA, 640)
                .aspect(TAAspects.INFINITUS, 1)
                .instability(6)
                .unlockedBy("has", has(ModItems.infinity_ingot.get()))
                .save(output);

        // 惰性水晶矩阵杖端
        ShapedRecipePattern wandCapCrystalPattern = ShapedRecipePattern.of(Map.of(
                        'Q', Ingredient.of(TTItems.NUGGET_QUICKSILVER.get()),
                        'L', Ingredient.of(ModItems.crystal_matrix_ingot.get()),
                        'B', Ingredient.of(ModBlocks.crystal_matrix.get()),
                        'V', Ingredient.of(TTItems.WAND_CAP_VOID.get()),
                        'P', Ingredient.of(TTItems.PRIMORDIAL_PEARL.get()),
                        'S', Ingredient.of(TTItems.SALIS_MUNDUS.get())),
                "QQLLLLLQQ",
                "QLLBBBLLQ",
                "LLBBBBBLL",
                "LBBVPVBBL",
                "LBBBBBBBL",
                "LBBBLBBBL",
                "LBLLSLLBL",
                "LLLSSSLLL",
                "SSSQQQSSS");
        output.accept(ResourceKey.create(Registries.RECIPE, ThaumicAvaritia.identifier("crystal_inter_wand_cap")),
                new ShapedTableCraftingRecipe(wandCapCrystalPattern,
                        new ItemStackTemplate(TAItems.INTER_CRYSTAL_MATRIX_WAND_CAP.get()),
                        4, false), null);

        // 中子态素杖柄
        ShapedRecipePattern wandRodStuddedCosmicNeutronium = ShapedRecipePattern.of(Map.of(
                        'N', Ingredient.of(ModItems.infinity_nugget.get()),
                        'n', Ingredient.of(ModItems.neutron_nugget.get()),
                        'i', Ingredient.of(ModItems.neutron_ingot.get()),
                        'C', Ingredient.of(ModItems.infinity_catalyst.get()),
                        'b', Ingredient.of(ModBlocks.neutron.get()),
                        'P', Ingredient.of(TAItems.EXTREMELY_PRIMORDIAL_PEARL.get()),
                        'R', Ingredient.of(TTItems.STAFF_ROD_PRIMAL.get()),
                        'I', Ingredient.of(ModItems.infinity_ingot.get())),
                "    NnnNN",
                "   NniNCN",
                "  NnibiNn",
                " NniIRbin",
                "NniIPIinN",
                "nibRIinN ",
                "nNibinN  ",
                "NCNinN   ",
                "NNnnN    ");
        output.accept(ResourceKey.create(Registries.RECIPE, ThaumicAvaritia.identifier("neutronium_wand_rod")),
                new ShapedTableCraftingRecipe(wandRodStuddedCosmicNeutronium,
                        new ItemStackTemplate(TAItems.NEUTRONIUM_WAND_ROD.get()),
                        4, false), null);

        // 奥法奇点核心
        output.accept(ResourceKey.create(Registries.RECIPE, ThaumicAvaritia.identifier("arcane_singularity_focus")),
                new ExtremeSmithingRecipe(Ingredient.of(ModItems.upgrade_smithing_template.get()),
                        Ingredient.of(TTItems.FOCUS_3.get()),
                        CompoundIngredient.of(Ingredient.of(TAItems.EXTREMELY_PRIMORDIAL_PEARL.get()),
                                Ingredient.of(ModItems.enhancement_core.get()),
                                Ingredient.of(TTItems.CAUSALITY_COLLAPSER.get())),
                        new ItemStackTemplate(TAItems.ARCANE_SINGULARITY_FOCUS.get())), null);

        // 下笔如有神之笔与墨
        infusion(TAItems.INFINITY_SCRIBING_TOOLS, TTItems.SCRIBING_TOOLS)
                .component(Ingredient.of(ModItems.neutron_ingot.get()))
                .component(Ingredient.of(ModItems.infinity_ingot.get()))
                .component(Ingredient.of(ModItems.neutron_ingot.get()))
                .component(Ingredient.of(ModItems.infinity_ingot.get()))
                .aspect(TTAspects.COGNITIO, 128)
                .aspect(TTAspects.PRAECANTATIO, 128)
                .aspect(TTAspects.AURAM, 128)
                .aspect(TTAspects.SPIRITUS, 128)
                .aspect(TTAspects.ALIENIS, 128)
                .aspect(TTAspects.DESIDERIUM, 128)
                .aspect(TAAspects.INFINITUS, 3)
                .instability(4)
                .unlockedBy("has", has(ModItems.infinity_ingot.get()))
                .save(output);

        // 涌流充能器
        infusion(TAItems.INFINITY_CHARGER, TTItems.ARCANE_WORKBENCH_CHARGER)
                .component(Ingredient.of(ModItems.infinity_ingot.get()))
                .component(Ingredient.of(ModItems.neutron_ingot.get()))
                .component(Ingredient.of(TTItems.JAR_NODE.get()))
                .component(Ingredient.of(ModItems.infinity_ingot.get()))
                .component(Ingredient.of(ModItems.neutron_ingot.get()))
                .component(Ingredient.of(TTItems.JAR_NODE.get()))
                .component(Ingredient.of(ModItems.infinity_ingot.get()))
                .component(Ingredient.of(ModItems.neutron_ingot.get()))
                .component(Ingredient.of(TTItems.JAR_NODE.get()))
                .component(Ingredient.of(ModItems.infinity_ingot.get()))
                .component(Ingredient.of(ModItems.neutron_ingot.get()))
                .component(Ingredient.of(TTItems.JAR_NODE.get()))
                .aspect(TTAspects.AURAM, 640)
                .aspect(TTAspects.PRAECANTATIO, 640)
                .aspect(TTAspects.VACUOS, 640)
                .aspect(TTAspects.MACHINA, 640)
                .aspect(TAAspects.INFINITUS, 16)
                .instability(5)
                .unlockedBy("has", has(ModItems.infinity_ingot.get()))
                .save(output);

        // 宇宙中子态素源质冶炼厂
        infusion(TAItems.COSMIC_NEUTRONIUM_ESSENTIA_SMELTERY, TTItems.SMELTER_VOID)
                .component(Ingredient.of(ModItems.neutron_ingot.get()))
                .component(Ingredient.of(ModBlocks.neutron_collector.get()))
                .component(Ingredient.of(ModItems.full_matter_cluster.get()))
                .component(Ingredient.of(ModItems.neutron_ingot.get()))
                .component(Ingredient.of(ModBlocks.neutron_compressor.get()))
                .component(Ingredient.of(ModItems.full_matter_cluster.get()))
                .component(Ingredient.of(ModItems.neutron_ingot.get()))
                .component(Ingredient.of(ModBlocks.neutron_collector.get()))
                .component(Ingredient.of(ModItems.full_matter_cluster.get()))
                .component(Ingredient.of(ModItems.neutron_ingot.get()))
                .component(Ingredient.of(ModBlocks.neutron_compressor.get()))
                .component(Ingredient.of(ModItems.full_matter_cluster.get()))
                .aspect(TTAspects.AURAM, 640)
                .aspect(TTAspects.PRAECANTATIO, 640)
                .aspect(TTAspects.VACUOS, 640)
                .aspect(TTAspects.MACHINA, 640)
                .aspect(TAAspects.INFINITUS, 16)
                .instability(5)
                .unlockedBy("has", has(ModItems.infinity_ingot.get()))
                .save(output);

        // 节点锚
        infusion(TAItems.NODE_ANCHOR, ModItems.eternal_singularity)
                .component(Ingredient.of(TTItems.NODE_STABILIZER.get()))
                .component(Ingredient.of(ModItems.neutron_ingot.get()))
                .component(Ingredient.of(TTItems.NODE_TRANSDUCER.get()))
                .component(Ingredient.of(ModItems.neutron_ingot.get()))
                .component(Ingredient.of(TTItems.NODE_STABILIZER.get()))
                .component(Ingredient.of(ModItems.neutron_ingot.get()))
                .component(Ingredient.of(TTItems.NODE_TRANSDUCER.get()))
                .component(Ingredient.of(ModItems.neutron_ingot.get()))
                .aspect(TTAspects.AURAM, 640)
                .aspect(TTAspects.POTENTIA, 480)
                .aspect(TTAspects.ORDO, 480)
                .aspect(TTAspects.MACHINA, 480)
                .aspect(TAAspects.INFINITUS, 16)
                .instability(6)
                .unlockedBy("has", has(TTItems.NODE_TRANSDUCER.get()))
                .save(output);

        // 万物起源之罐
        infusion(TAItems.ORIGIN_JAR, TTItems.JAR_VOID)
                .component(Ingredient.of(ModItems.infinity_ingot.get()))
                .component(Ingredient.of(TTItems.PRIMORDIAL_PEARL.get()))
                .component(Ingredient.of(ModItems.infinity_ingot.get()))
                .component(Ingredient.of(TTItems.SALIS_MUNDUS.get()))
                .component(Ingredient.of(ModItems.infinity_ingot.get()))
                .component(Ingredient.of(TTItems.PRIMORDIAL_PEARL.get()))
                .component(Ingredient.of(ModItems.infinity_ingot.get()))
                .component(Ingredient.of(TTItems.SALIS_MUNDUS.get()))
                .aspect(TTAspects.AURAM, 640)
                .aspect(TTAspects.PRAECANTATIO, 640)
                .aspect(TTAspects.VACUOS, 640)
                .aspect(TTAspects.ALIENIS, 640)
                .aspect(TAAspects.INFINITUS, 16)
                .instability(6)
                .unlockedBy("has", has(TTItems.JAR_VOID.get()))
                .save(output);

//        // ==================== 无限收集器（Infinitus Collector）====================
//        // 基础：框架笼住节点，核心架于出口。以节点自身灵气为柴，吸干即止。
//        infusion(.MISC,
//                new ItemStackTemplate(TAItems.INFINITUS_COLLECTOR.get()),
//                Ingredient.of(TAItems.NODE_ANCHOR.get()))
//                .component(Ingredient.of(ModBlocks.neutron_collector.get()))
//                .component(Ingredient.of(TTItems.NODE_STABILIZER.get()))
//                .component(Ingredient.of(ModItems.neutron_ingot.get()))
//                .component(Ingredient.of(ModBlocks.neutron_collector.get()))
//                .component(Ingredient.of(TTItems.NODE_TRANSDUCER.get()))
//                .component(Ingredient.of(ModItems.neutron_ingot.get()))
//                .component(Ingredient.of(ModBlocks.neutron_collector.get()))
//                .component(Ingredient.of(TTItems.NODE_STABILIZER.get()))
//                .component(Ingredient.of(ModItems.neutron_ingot.get()))
//                .component(Ingredient.of(ModBlocks.neutron_collector.get()))
//                .component(Ingredient.of(TTItems.NODE_TRANSDUCER.get()))
//                .component(Ingredient.of(ModItems.neutron_ingot.get()))
//                .aspect(TTAspects.AURAM, 640)
//                .aspect(TTAspects.VACUOS, 640)
//                .aspect(TTAspects.PRAECANTATIO, 640)
//                .aspect(TTAspects.MACHINA, 480)
//                .aspect(TAAspects.INFINITUS, 16)
//                .instability(6)
//                .unlockedBy("has_node_anchor", has(TAItems.NODE_ANCHOR.get()))
//                .save(output, ThaumicAvaritia.MODID + ":infinitus_collector");

        // 极限框架
        infusion(TAItems.LIMIT_FRAME, ModBlocks.crystal_matrix)
                .component(Ingredient.of(ModItems.neutron_ingot.get()))
                .component(Ingredient.of(TTItems.SALIS_MUNDUS.get()))
                .component(Ingredient.of(ModItems.neutron_ingot.get()))
                .component(Ingredient.of(TTItems.SALIS_MUNDUS.get()))
                .component(Ingredient.of(ModItems.neutron_ingot.get()))
                .component(Ingredient.of(TTItems.SALIS_MUNDUS.get()))
                .component(Ingredient.of(ModItems.neutron_ingot.get()))
                .component(Ingredient.of(TTItems.SALIS_MUNDUS.get()))
                .aspect(TTAspects.ORDO, 480)
                .aspect(TTAspects.FABRICO, 480)
                .aspect(TTAspects.MACHINA, 320)
                .aspect(TAAspects.INFINITUS, 4)
                .instability(4)
                .unlockedBy("has", has(ModItems.neutron_ingot.get()))
                .save(output);

//        // 升级Ⅰ：紧凑——效率增强，耗灵气更慢。
//        infusion(.MISC,
//                new ItemStackTemplate(TAItems.INFINITUS_COLLECTOR_COMPACT.get()),
//                Ingredient.of(TAItems.INFINITUS_COLLECTOR.get()))
//                .component(Ingredient.of(ModItems.neutron_ingot.get()))
//                .component(Ingredient.of(TTItems.NODE_STABILIZER.get()))
//                .component(Ingredient.of(ModItems.neutron_ingot.get()))
//                .component(Ingredient.of(TTItems.NODE_TRANSDUCER.get()))
//                .component(Ingredient.of(ModItems.neutron_ingot.get()))
//                .component(Ingredient.of(TTItems.NODE_STABILIZER.get()))
//                .component(Ingredient.of(ModItems.neutron_ingot.get()))
//                .component(Ingredient.of(TTItems.NODE_TRANSDUCER.get()))
//                .aspect(TTAspects.AURAM, 640)
//                .aspect(TTAspects.MACHINA, 480)
//                .aspect(TAAspects.INFINITUS, 16)
//                .instability(5)
//                .unlockedBy("has_infinitus_collector", has(TAItems.INFINITUS_COLLECTOR.get()))
//                .save(output, ThaumicAvaritia.MODID + ":infinitus_collector_compact");

        // 升级Ⅱ：更密——吸收要素稳定，不再啃噬节点灵气。
//        infusion(.MISC,
//                new ItemStackTemplate(TAItems.INFINITUS_COLLECTOR_DENSE.get()),
//                Ingredient.of(TAItems.INFINITUS_COLLECTOR_COMPACT.get()))
//                .component(Ingredient.of(ModItems.eternal_singularity.get()))
//                .component(Ingredient.of(TAItems.INFINITUS_CRYSTAL.get()))
//                .component(Ingredient.of(ModItems.neutron_ingot.get()))
//                .component(Ingredient.of(TTItems.PRIMORDIAL_PEARL.get()))
//                .component(Ingredient.of(ModItems.eternal_singularity.get()))
//                .component(Ingredient.of(TAItems.INFINITUS_CRYSTAL.get()))
//                .component(Ingredient.of(ModItems.neutron_ingot.get()))
//                .component(Ingredient.of(TTItems.PRIMORDIAL_PEARL.get()))
//                .aspect(TTAspects.PRAECANTATIO, 640)
//                .aspect(TTAspects.ORDO, 480)
//                .aspect(TTAspects.POTENTIA, 480)
//                .aspect(TAAspects.INFINITUS, 32)
//                .instability(6)
//                .unlockedBy("has_infinitus_crystal", has(TAItems.INFINITUS_CRYSTAL.get()))
//                .save(output, ThaumicAvaritia.MODID + ":infinitus_collector_dense");

        // 升级Ⅲ：无垠——与无限结晶合体，自给永动。
//        infusion(.MISC,
//                new ItemStackTemplate(TAItems.INFINITUS_COLLECTOR_ENDLESS.get()),
//                Ingredient.of(TAItems.INFINITUS_COLLECTOR_DENSE.get()))
//                .component(Ingredient.of(ModItems.infinity_catalyst.get()))
//                .component(Ingredient.of(TAItems.INFINITUS_CRYSTAL.get()))
//                .component(Ingredient.of(ModItems.eternal_singularity.get()))
//                .component(Ingredient.of(TAItems.INFINITUS_CRYSTAL.get()))
//                .component(Ingredient.of(ModItems.infinity_catalyst.get()))
//                .component(Ingredient.of(TAItems.INFINITUS_CRYSTAL.get()))
//                .component(Ingredient.of(ModItems.eternal_singularity.get()))
//                .component(Ingredient.of(TAItems.INFINITUS_CRYSTAL.get()))
//                .aspect(TTAspects.PRAECANTATIO, 640)
//                .aspect(TTAspects.AURAM, 640)
//                .aspect(TTAspects.VACUOS, 640)
//                .aspect(TAAspects.INFINITUS, 64)
//                .instability(7)
//                .unlockedBy("has_infinity_catalyst", has(ModItems.infinity_catalyst.get()))
//                .save(output, ThaumicAvaritia.MODID + ":infinitus_collector_endless");
    }

    private InfusionRecipeBuilder infusion(ItemLike result, RecipeCategory category, ItemLike catalyst) {
        return (InfusionRecipeBuilder) new InfusionRecipeBuilder(
                aspects,
                category,
                new ItemStackTemplate(result.asItem()),
                Ingredient.of(catalyst))
                .unlockedBy("has", this.has(result));
    }

    private InfusionRecipeBuilder infusion(ItemLike result, ItemLike catalyst) {
        return infusion(result, RecipeCategory.MISC, catalyst);
    }

    private CrucibleRecipeBuilder crucible(ItemLike result, RecipeCategory category, ItemLike catalyst) {
        return (CrucibleRecipeBuilder) new CrucibleRecipeBuilder(
                aspects,
                category,
                new ItemStackTemplate(result.asItem()),
                Ingredient.of(catalyst))
                .unlockedBy("has", this.has(result));
    }

    private CrucibleRecipeBuilder crucible(ItemLike result, ItemLike catalyst) {
        return crucible(result, RecipeCategory.MISC, catalyst);
    }

    private ArcaneWorkbenchShapelessRecipeBuilder arcaneShapeless(RecipeCategory recipeCategory, ItemStackTemplate result, int vis) {
        return new ArcaneWorkbenchShapelessRecipeBuilder(recipeCategory,
                result,
                aspects,
                vis,
                registries.lookupOrThrow(Registries.ITEM));
    }

    private ArcaneWorkbenchShapelessRecipeBuilder arcaneShapeless(RecipeCategory recipeCategory, ItemLike result, int vis) {
        return arcaneShapeless(recipeCategory, new ItemStackTemplate(result.asItem()), vis);
    }

    private ArcaneWorkbenchShapedRecipeBuilder arcaneShaped(RecipeCategory recipeCategory, ItemStackTemplate result, int vis) {
        return new ArcaneWorkbenchShapedRecipeBuilder(recipeCategory,
                result,
                this.items,
                aspects,
                vis);
    }

    private ArcaneWorkbenchShapedRecipeBuilder arcaneShaped(RecipeCategory recipeCategory, ItemLike result, int vis) {
        return (ArcaneWorkbenchShapedRecipeBuilder) arcaneShaped(recipeCategory, new ItemStackTemplate(result.asItem()), vis)
                .unlockedBy("has", this.has(result.asItem()));
    }

    private Ingredient crystal(ResourceKey<IAspect> aspect) {
        return DataComponentIngredient.of(
                TTDataComponents.CRYSTAL_ASPECT.get(),
                new AspectInstance(aspects.getOrThrow(aspect), 1),
                TTItems.ESSENTIA_CRYSTAL.get());
    }

    private HolderSet<Item> tag(String space, String path) {
        return this.lookupProvider.getOrThrow(TagKey.create(
                Registries.ITEM, Identifier.fromNamespaceAndPath(
                        space, path)));
    }

    public static final class Runner extends RecipeProvider.Runner {
        public Runner(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
            super(output, lookupProvider);
        }

        @Override
        protected @NonNull RecipeProvider createRecipeProvider(HolderLookup.@NonNull Provider provider, @NonNull RecipeOutput output) {
            return new ModRecipeProvider(provider, output);
        }

        @Override
        public @NonNull String getName() {
            return "Thaumic Avaritia Recipes";
        }
    }
}
