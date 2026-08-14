package com.blackmoss.thaumicavaritia.data.recipe;

import com.blackmoss.thaumicavaritia.ThaumicAvaritia;
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
                .aspect(TCAspects.ALIENIS, 64)
                .aspect(TCAspects.PERDITIO, 64)
                .instability(4)
                .unlockedBy("has_primordial_peral", has(TCItems.PRIMORDIAL_PEARL.get()))
                .save(output, ThaumicAvaritia.MODID + ":extremely_primordial_pearl");

        // 阿卡西记录
        new InfusionRecipeBuilder(aspects, RecipeCategory.MISC,
                new ItemStackTemplate(TAItems.AKASHIC_RECORDS.get()),
                Ingredient.of(items.getOrThrow(TATags.THAUMATURGE_CURIOS)))
                .component(Ingredient.of(ModItems.infinity_ingot.get()))
                .component(Ingredient.of(ModItems.infinity_ingot.get()))
                .component(Ingredient.of(ModItems.infinity_ingot.get()))
                .component(Ingredient.of(ModItems.infinity_ingot.get()))
                .aspect(TCAspects.COGNITIO, 64)
                .aspect(TCAspects.PRAECANTATIO, 64)
                .aspect(TCAspects.ALIENIS, 32)
                .instability(4)
                .unlockedBy("has_infinity_ingot", has(ModItems.infinity_ingot.get()))
                .save(output, ThaumicAvaritia.MODID + ":akashic_record");

        // 充能水晶矩阵杖端
        new InfusionRecipeBuilder(aspects, RecipeCategory.TOOLS,
                new ItemStackTemplate(TAItems.WAND_CAP_CRYSTAL.get()),
                Ingredient.of(TAItems.WAND_CAP_CRYSTAL_INTER.get()))
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
                .aspect(TCAspects.AURAM, 320)
                .aspect(TCAspects.INSTRUMENTUM, 320)
                .aspect(TCAspects.ORDO, 320)
                .aspect(TCAspects.POTENTIA, 320)
                .instability(6)
                .unlockedBy("has_infinity_ingot", has(ModItems.infinity_ingot.get()))
                .save(output, ThaumicAvaritia.MODID + ":wand_cap_crystal");

        // 惰性水晶矩阵杖端
        ShapedRecipePattern wandCapCrystalPattern = ShapedRecipePattern.of(Map.of(
                        'Q', Ingredient.of(TCItems.NUGGET_QUICKSILVER.get()),
                        'L', Ingredient.of(ModItems.diamond_lattice.get()),
                        'B', Ingredient.of(ModBlocks.diamond_lattice_block.get()),
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
                        new ItemStackTemplate(TAItems.WAND_CAP_CRYSTAL_INTER.get()),
                        4, false), null);

        // 中子态素杖柄
        ShapedRecipePattern wandRodStuddedCosmicNeutronium = ShapedRecipePattern.of(Map.of(
                        'N', Ingredient.of(ModItems.infinity_nugget.get()),
                        'n', Ingredient.of(ModItems.neutron_nugget.get()),
                        'I', Ingredient.of(ModItems.neutron_ingot.get()),
                        'C', Ingredient.of(ModItems.infinity_catalyst.get()),
                        'b', Ingredient.of(ModBlocks.neutron.get()),
                        'P', Ingredient.of(TCItems.PRIMORDIAL_PEARL.get()),
                        'R', Ingredient.of(TCItems.STAFF_ROD_PRIMAL.get()),
                        'B', Ingredient.of(ModBlocks.infinity.get())),
                "    NnnNN",
                "   NnINCN",
                "  NnIbINn",
                " NnIPRbIn",
                "NnIPBPInN",
                "nIbRPInN ",
                "nNIbInN  ",
                "NCNInN   ",
                "NNnnN    ");
        output.accept(ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(
                        ThaumicAvaritia.MODID, "wand_rod_studded_cosmic_neutronium")),
                new ShapedTableCraftingRecipe(wandRodStuddedCosmicNeutronium,
                        new ItemStackTemplate(TAItems.WAND_ROD_STUDDED_COSMIC_NEUTRONIUM.get()),
                        4, false), null);

        // 奥法奇点核心：无尽锻造。base=大师核心，template=升级锻造模板，
        // addition=极元始珍珠 + 无瑕核心 + 悖论物质（compound，三选一）。
        output.accept(ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(
                        ThaumicAvaritia.MODID, "arcane_singularity_focus")),
                new ExtremeSmithingRecipe(Ingredient.of(ModItems.upgrade_smithing_template.get()),
                        Ingredient.of(TCItems.FOCUS_3.get()),
                        CompoundIngredient.of(Ingredient.of(TAItems.EXTREMELY_PRIMORDIAL_PEARL.get()),
                                Ingredient.of(ModItems.enhancement_core.get()),
                                Ingredient.of(TCItems.CAUSALITY_COLLAPSER.get())),
                        new ItemStackTemplate(TAItems.ARCANE_SINGULARITY_FOCUS.get())), null);
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
