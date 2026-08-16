package com.blackmoss.thaumicavaritia.data.recipe;

import com.blackmoss.thaumicavaritia.ThaumicAvaritia;
import com.blackmoss.thaumicavaritia.api.aspect.TAAspects;
import com.blackmoss.thaumicavaritia.registry.TAItems;
import com.blackmoss.thaumicavaritia.registry.TATags;
import com.leclowndu93150.thaumaturge.api.aspect.IAspect;
import com.leclowndu93150.thaumaturge.api.aspect.TCAspects;
import com.leclowndu93150.thaumaturge.data.recipe.builders.InfusionRecipeBuilder;
import com.leclowndu93150.thaumaturge.registry.TCItems;
import committee.nova.mods.avaritia.common.crafting.recipe.ExtremeSmithingRecipe;
import committee.nova.mods.avaritia.common.crafting.recipe.ShapedTableCraftingRecipe;
import committee.nova.mods.avaritia.init.registry.ModBlocks;
import committee.nova.mods.avaritia.init.registry.ModItems;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.ShapedRecipePattern;
import net.neoforged.neoforge.common.crafting.CompoundIngredient;
import org.jspecify.annotations.NonNull;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

public final class TARecipeProvider extends RecipeProvider {

    private TARecipeProvider(HolderLookup.Provider provider, RecipeOutput output) {
        super(provider, output);
    }

    @Override
    protected void buildRecipes() {
        HolderGetter<IAspect> aspects = registries.lookupOrThrow(IAspect.REGISTRY_KEY);
        HolderGetter<Item> items = registries.lookupOrThrow(Registries.ITEM);

        // 极元始珍珠
        new InfusionRecipeBuilder(aspects, RecipeCategory.MISC,
                new ItemStackTemplate(TAItems.EXTREMELY_PRIMORDIAL_PEARL.get()),
                Ingredient.of(TCItems.PRIMORDIAL_PEARL.get()))
                .component(Ingredient.of(TCItems.PRIMORDIAL_PEARL.get()))
                .component(Ingredient.of(TCItems.PRIMORDIAL_PEARL.get()))
                .component(Ingredient.of(TCItems.PRIMORDIAL_PEARL.get()))
                .component(Ingredient.of(TCItems.PRIMORDIAL_PEARL.get()))
                .component(Ingredient.of(TCItems.PRIMORDIAL_PEARL.get()))
                .component(Ingredient.of(TCItems.PRIMORDIAL_PEARL.get()))
                .component(Ingredient.of(TCItems.PRIMORDIAL_PEARL.get()))
                .aspect(TCAspects.ALIENIS, 640)
                .aspect(TCAspects.PERDITIO, 640)
                .aspect(TCAspects.PRAECANTATIO, 640)
                .instability(4)
                .unlockedBy("has_primordial_peral", has(TCItems.PRIMORDIAL_PEARL.get()))
                .save(output, ThaumicAvaritia.MODID + ":extremely_primordial_pearl");

        // 悖论珍珠
        new InfusionRecipeBuilder(aspects, RecipeCategory.COMBAT,
                new ItemStackTemplate(TAItems.CAUSALITY_COLLAPSER_PEARL.get()),
                Ingredient.of(TCItems.CAUSALITY_COLLAPSER.get()))
                .component(Ingredient.of(ModItems.endest_pearl.get()))
                .component(Ingredient.of(ModItems.endest_pearl.get()))
                .component(Ingredient.of(ModItems.endest_pearl.get()))
                .component(Ingredient.of(ModItems.endest_pearl.get()))
                .component(Ingredient.of(ModItems.endest_pearl.get()))
                .component(Ingredient.of(ModItems.endest_pearl.get()))
                .component(Ingredient.of(ModItems.endest_pearl.get()))
                .component(Ingredient.of(ModItems.endest_pearl.get()))
                .aspect(TCAspects.ALIENIS, 640)
                .aspect(TCAspects.PERDITIO, 640)
                .aspect(TCAspects.VACUOS, 640)
                .aspect(TCAspects.PRAECANTATIO, 640)
                .aspect(TAAspects.INFINITUS, 1)
                .instability(6)
                .unlockedBy("has_causality_collapser", has(TCItems.CAUSALITY_COLLAPSER.get()))
                .save(output, ThaumicAvaritia.MODID + ":causality_collapser_pearl");

        // 阿卡西记录
        new InfusionRecipeBuilder(aspects, RecipeCategory.MISC,
                new ItemStackTemplate(TAItems.AKASHIC_RECORDS.get()),
                Ingredient.of(items.getOrThrow(TATags.THAUMATURGE_CURIOS)))
                .component(Ingredient.of(ModItems.infinity_ingot.get()))
                .component(Ingredient.of(ModItems.neutron_ingot.get()))
                .component(Ingredient.of(ModItems.infinity_ingot.get()))
                .component(Ingredient.of(ModItems.neutron_ingot.get()))
                .component(Ingredient.of(ModItems.infinity_ingot.get()))
                .component(Ingredient.of(ModItems.neutron_ingot.get()))
                .component(Ingredient.of(ModItems.infinity_ingot.get()))
                .component(Ingredient.of(ModItems.neutron_ingot.get()))
                .aspect(TCAspects.COGNITIO, 640)
                .aspect(TCAspects.PRAECANTATIO, 640)
                .aspect(TCAspects.AURAM, 640)
                .aspect(TCAspects.SPIRITUS, 640)
                .aspect(TCAspects.ALIENIS, 640)
                .aspect(TCAspects.DESIDERIUM, 640)
                .aspect(TAAspects.INFINITUS, 3)
                .instability(4)
                .unlockedBy("has_infinity_ingot", has(ModItems.infinity_ingot.get()))
                .save(output, ThaumicAvaritia.MODID + ":akashic_record");

        // 充能水晶矩阵杖端
        new InfusionRecipeBuilder(aspects, RecipeCategory.TOOLS,
                new ItemStackTemplate(TAItems.WAND_CAP_CRYSTAL_MATRIX.get()),
                Ingredient.of(TAItems.WAND_CAP_CRYSTAL_MATRIX_INTER.get()))
                .component(Ingredient.of(TCItems.PRIMORDIAL_PEARL.get()))
                .component(Ingredient.of(TCItems.SALIS_MUNDUS.get()))
                .component(Ingredient.of(TCItems.SALIS_MUNDUS.get()))
                .component(Ingredient.of(TCItems.PRIMAL_CHARM.get()))
                .component(Ingredient.of(TCItems.FOCUS_3.get()))
                .component(Ingredient.of(TCItems.PRIMAL_CHARM.get()))
                .component(Ingredient.of(TCItems.SALIS_MUNDUS.get()))
                .component(Ingredient.of(TCItems.SALIS_MUNDUS.get()))
                .component(Ingredient.of(TCItems.PRIMORDIAL_PEARL.get()))
                .component(Ingredient.of(TCItems.SALIS_MUNDUS.get()))
                .component(Ingredient.of(TCItems.SALIS_MUNDUS.get()))
                .component(Ingredient.of(TCItems.PRIMAL_CHARM.get()))
                .component(Ingredient.of(TCItems.FOCUS_3.get()))
                .component(Ingredient.of(TCItems.PRIMAL_CHARM.get()))
                .component(Ingredient.of(TCItems.SALIS_MUNDUS.get()))
                .component(Ingredient.of(TCItems.SALIS_MUNDUS.get()))
                .component(Ingredient.of(TCItems.PRIMORDIAL_PEARL.get()))
                .component(Ingredient.of(TCItems.SALIS_MUNDUS.get()))
                .component(Ingredient.of(TCItems.SALIS_MUNDUS.get()))
                .component(Ingredient.of(TCItems.PRIMAL_CHARM.get()))
                .component(Ingredient.of(TCItems.FOCUS_3.get()))
                .component(Ingredient.of(TCItems.PRIMAL_CHARM.get()))
                .component(Ingredient.of(TCItems.SALIS_MUNDUS.get()))
                .component(Ingredient.of(TCItems.SALIS_MUNDUS.get()))
                .component(Ingredient.of(TCItems.PRIMORDIAL_PEARL.get()))
                .component(Ingredient.of(TCItems.SALIS_MUNDUS.get()))
                .component(Ingredient.of(TCItems.SALIS_MUNDUS.get()))
                .component(Ingredient.of(TCItems.PRIMAL_CHARM.get()))
                .component(Ingredient.of(TCItems.FOCUS_3.get()))
                .component(Ingredient.of(TCItems.PRIMAL_CHARM.get()))
                .component(Ingredient.of(TCItems.SALIS_MUNDUS.get()))
                .component(Ingredient.of(TCItems.SALIS_MUNDUS.get()))
                .aspect(TCAspects.PRAECANTATIO, 640)
                .aspect(TCAspects.AURAM, 640)
                .aspect(TCAspects.INSTRUMENTUM, 640)
                .aspect(TCAspects.ORDO, 640)
                .aspect(TCAspects.POTENTIA, 640)
                .aspect(TAAspects.INFINITUS, 1)
                .instability(6)
                .unlockedBy("has_infinity_ingot", has(ModItems.infinity_ingot.get()))
                .save(output, ThaumicAvaritia.MODID + ":wand_cap_crystal");

        // 惰性水晶矩阵杖端
        ShapedRecipePattern wandCapCrystalPattern = ShapedRecipePattern.of(Map.of(
                        'Q', Ingredient.of(TCItems.NUGGET_QUICKSILVER.get()),
                        'L', Ingredient.of(ModItems.crystal_matrix_ingot.get()),
                        'B', Ingredient.of(ModBlocks.crystal_matrix.get()),
                        'V', Ingredient.of(TCItems.WAND_CAP_VOID.get()),
                        'P', Ingredient.of(TCItems.PRIMORDIAL_PEARL.get()),
                        'S', Ingredient.of(TCItems.SALIS_MUNDUS.get())),
                "QQLLLLLQQ",
                "QLLBBBLLQ",
                "LLBBBBBLL",
                "LBBVPVBBL",
                "LBBBBBBBL",
                "LBBBLBBBL",
                "LBLLSLLBL",
                "LLLSSSLLL",
                "SSSQQQSSS");
        output.accept(ResourceKey.create(Registries.RECIPE,
                        Identifier.fromNamespaceAndPath(ThaumicAvaritia.MODID, "wand_cap_crystal_inter")),
                new ShapedTableCraftingRecipe(wandCapCrystalPattern,
                        new ItemStackTemplate(TAItems.WAND_CAP_CRYSTAL_MATRIX_INTER.get()),
                        4, false), null);

        // 中子态素杖柄
        ShapedRecipePattern wandRodStuddedCosmicNeutronium = ShapedRecipePattern.of(Map.of(
                        'N', Ingredient.of(ModItems.infinity_nugget.get()),
                        'n', Ingredient.of(ModItems.neutron_nugget.get()),
                        'i', Ingredient.of(ModItems.neutron_ingot.get()),
                        'C', Ingredient.of(ModItems.infinity_catalyst.get()),
                        'b', Ingredient.of(ModBlocks.neutron.get()),
                        'P', Ingredient.of(TAItems.EXTREMELY_PRIMORDIAL_PEARL.get()),
                        'R', Ingredient.of(TCItems.STAFF_ROD_PRIMAL.get()),
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
        output.accept(ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(
                        ThaumicAvaritia.MODID, "wand_rod_studded_cosmic_neutronium")),
                new ShapedTableCraftingRecipe(wandRodStuddedCosmicNeutronium,
                        new ItemStackTemplate(TAItems.WAND_ROD_STUDDED_COSMIC_NEUTRONIUM.get()),
                        4, false), null);

        // 奥法奇点核心
        output.accept(ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(
                        ThaumicAvaritia.MODID, "arcane_singularity_focus")),
                new ExtremeSmithingRecipe(Ingredient.of(ModItems.upgrade_smithing_template.get()),
                        Ingredient.of(TCItems.FOCUS_3.get()),
                        CompoundIngredient.of(Ingredient.of(TAItems.EXTREMELY_PRIMORDIAL_PEARL.get()),
                                Ingredient.of(ModItems.enhancement_core.get()),
                                Ingredient.of(TCItems.CAUSALITY_COLLAPSER.get())),
                        new ItemStackTemplate(TAItems.ARCANE_SINGULARITY_FOCUS.get())), null);

        // 下笔如有神之笔与墨
        new InfusionRecipeBuilder(aspects, RecipeCategory.TOOLS,
                new ItemStackTemplate(TAItems.INFINITY_SCRIBING_TOOLS.get()),
                Ingredient.of(TCItems.SCRIBING_TOOLS.get()))
                .component(Ingredient.of(ModItems.neutron_ingot.get()))
                .component(Ingredient.of(ModItems.infinity_ingot.get()))
                .component(Ingredient.of(ModItems.neutron_ingot.get()))
                .component(Ingredient.of(ModItems.infinity_ingot.get()))
                .aspect(TCAspects.COGNITIO, 128)
                .aspect(TCAspects.PRAECANTATIO, 128)
                .aspect(TCAspects.AURAM, 128)
                .aspect(TCAspects.SPIRITUS, 128)
                .aspect(TCAspects.ALIENIS, 128)
                .aspect(TCAspects.DESIDERIUM, 128)
                .aspect(TAAspects.INFINITUS, 3)
                .instability(4)
                .unlockedBy("has_infinity_ingot", has(ModItems.infinity_ingot.get()))
                .save(output, ThaumicAvaritia.MODID + ":infinity_scribing_tools");

        // 涌流充能器
        new InfusionRecipeBuilder(aspects, RecipeCategory.MISC,
                new ItemStackTemplate(TAItems.INFINITY_CHARGER.get()),
                Ingredient.of(TCItems.ARCANE_WORKBENCH_CHARGER.get()))
                .component(Ingredient.of(ModItems.infinity_ingot.get()))
                .component(Ingredient.of(ModItems.neutron_ingot.get()))
                .component(Ingredient.of(TCItems.JAR_NODE.get()))
                .component(Ingredient.of(ModItems.infinity_ingot.get()))
                .component(Ingredient.of(ModItems.neutron_ingot.get()))
                .component(Ingredient.of(TCItems.JAR_NODE.get()))
                .component(Ingredient.of(ModItems.infinity_ingot.get()))
                .component(Ingredient.of(ModItems.neutron_ingot.get()))
                .component(Ingredient.of(TCItems.JAR_NODE.get()))
                .component(Ingredient.of(ModItems.infinity_ingot.get()))
                .component(Ingredient.of(ModItems.neutron_ingot.get()))
                .component(Ingredient.of(TCItems.JAR_NODE.get()))
                .aspect(TCAspects.AURAM, 640)
                .aspect(TCAspects.PRAECANTATIO, 640)
                .aspect(TCAspects.VACUOS, 640)
                .aspect(TCAspects.MACHINA, 640)
                .aspect(TAAspects.INFINITUS, 16)
                .instability(5)
                .unlockedBy("has_infinity_ingot", has(ModItems.infinity_ingot.get()))
                .save(output, ThaumicAvaritia.MODID + ":infinity_charger");

        // 宇宙中子态素源质冶炼厂
        new InfusionRecipeBuilder(aspects, RecipeCategory.MISC,
                new ItemStackTemplate(TAItems.COSMIC_NEUTRONIUM_ESSENTIA_SMELTERY.get()),
                Ingredient.of(TCItems.SMELTER_VOID.get()))
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
                .aspect(TCAspects.AURAM, 640)
                .aspect(TCAspects.PRAECANTATIO, 640)
                .aspect(TCAspects.VACUOS, 640)
                .aspect(TCAspects.MACHINA, 640)
                .aspect(TAAspects.INFINITUS, 16)
                .instability(5)
                .unlockedBy("has_infinity_ingot", has(ModItems.infinity_ingot.get()))
                .save(output, ThaumicAvaritia.MODID + ":cosmic_neutronium_essentia_smeltery");

        // 节点锚
        new InfusionRecipeBuilder(aspects, RecipeCategory.MISC,
                new ItemStackTemplate(TAItems.NODE_ANCHOR.get()),
                Ingredient.of(ModItems.eternal_singularity.get()))
                .component(Ingredient.of(TCItems.NODE_STABILIZER.get()))
                .component(Ingredient.of(ModItems.neutron_ingot.get()))
                .component(Ingredient.of(TCItems.NODE_TRANSDUCER.get()))
                .component(Ingredient.of(ModItems.neutron_ingot.get()))
                .component(Ingredient.of(TCItems.NODE_STABILIZER.get()))
                .component(Ingredient.of(ModItems.neutron_ingot.get()))
                .component(Ingredient.of(TCItems.NODE_TRANSDUCER.get()))
                .component(Ingredient.of(ModItems.neutron_ingot.get()))
                .aspect(TCAspects.AURAM, 640)
                .aspect(TCAspects.POTENTIA, 480)
                .aspect(TCAspects.ORDO, 480)
                .aspect(TCAspects.MACHINA, 480)
                .aspect(TAAspects.INFINITUS, 16)
                .instability(6)
                .unlockedBy("has_node_transducer", has(TCItems.NODE_TRANSDUCER.get()))
                .save(output, ThaumicAvaritia.MODID + ":node_anchor");
    }

    public static final class Runner extends RecipeProvider.Runner {
        public Runner(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
            super(output, lookupProvider);
        }

        @Override
        protected @NonNull RecipeProvider createRecipeProvider(HolderLookup.@NonNull Provider provider, @NonNull RecipeOutput output) {
            return new TARecipeProvider(provider, output);
        }

        @Override
        public @NonNull String getName() {
            return "Thaumic Avaritia Recipes";
        }
    }
}
