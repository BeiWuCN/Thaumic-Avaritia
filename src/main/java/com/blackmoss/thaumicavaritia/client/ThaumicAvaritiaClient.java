package com.blackmoss.thaumicavaritia.client;

import com.blackmoss.thaumicavaritia.ThaumicAvaritia;
import com.blackmoss.thaumicavaritia.client.render.blockentity.NodeAnchorBlockEntityRenderer;
import com.blackmoss.thaumicavaritia.client.render.blockentity.OriginJarBlockEntityRenderer;
import com.blackmoss.thaumicavaritia.client.render.entity.CausalityCollapserPearlRenderer;
import com.blackmoss.thaumicavaritia.client.screen.CosmicNeutroniumEssentiaSmelteryScreen;
import com.blackmoss.thaumicavaritia.client.screen.InfinitusCollectorScreen;
import com.blackmoss.thaumicavaritia.registry.TABlockEntities;
import com.blackmoss.thaumicavaritia.registry.TAEntities;
import com.blackmoss.thaumicavaritia.registry.TAItems;
import com.blackmoss.thaumicavaritia.registry.TAMenus;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;

@Mod(value = ThaumicAvaritia.MODID, dist = Dist.CLIENT)
@EventBusSubscriber(modid = ThaumicAvaritia.MODID, value = Dist.CLIENT)
public class ThaumicAvaritiaClient {
    public ThaumicAvaritiaClient(ModContainer container) {
        container.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);
    }

    @SubscribeEvent
    public static void onTooltip(ItemTooltipEvent event) {
        ItemStack stack = event.getItemStack();
        String key = null;
        if (stack.is(TAItems.CRYSTAL_MATRIX_WAND_CAP.get())) {
            key = "tooltip.thaumicavaritia.wand_cap_crystal_matrix";
        } else if (stack.is(TAItems.NEUTRONIUM_WAND_ROD.get())) {
            key = "tooltip.thaumicavaritia.wand_rod_studded_cosmic_neutronium";
        } else if (stack.is(TAItems.INFINITY_CHARGER.get())) {
            key = "tooltip.thaumicavaritia.infinity_charger";
        } else if (stack.is(TAItems.COSMIC_NEUTRONIUM_ESSENTIA_SMELTERY.get())) {
            key = "tooltip.thaumicavaritia.cosmic_neutronium_essentia_smeltery";
        } else if (stack.is(TAItems.NODE_ANCHOR.get())) {
            key = "tooltip.thaumicavaritia.node_anchor";
        } else if (stack.is(TAItems.ORIGIN_JAR.get())) {
            key = "tooltip.thaumicavaritia.origin_jar";
        } else if (stack.is(TAItems.INFINITUS_COLLECTOR.get())
                || stack.is(TAItems.INFINITUS_COLLECTOR_COMPACT.get())
                || stack.is(TAItems.INFINITUS_COLLECTOR_DENSE.get())
                || stack.is(TAItems.INFINITUS_COLLECTOR_ENDLESS.get())) {
            key = "tooltip.thaumicavaritia.infinitus_collector";
        } else if (stack.is(TAItems.LIMIT_FRAME.get())) {
            key = "tooltip.thaumicavaritia.limit_frame";
        } else if (stack.is(TAItems.CRYSTAL_MATRIX_PLATE.get())) {
            key = "tooltip.thaumicavaritia.crystal_matrix_plate";
        } else if (stack.is(TAItems.NEUTRON_PLATE.get())) {
            key = "tooltip.thaumicavaritia.neutron_plate";
        } else if (stack.is(TAItems.INFINITY_PLATE.get())) {
            key = "tooltip.thaumicavaritia.infinity_plate";
        } else if (stack.is(TAItems.LIMIT_ALCHEMICAL_CONSTRUCT.get())) {
            key = "tooltip.thaumicavaritia.limit_alchemical_construct";
        } else if (stack.is(TAItems.LIMIT_FRAME.get())) {
            key = "tooltip.thaumicavaritia.limit_frame";
        }
        if (key != null) {
            event.getToolTip().add(Component.translatable(key).withStyle(ChatFormatting.GRAY, ChatFormatting.ITALIC));
        }
    }

    @SubscribeEvent
    public static void onRegisterRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(
                TAEntities.CAUSALITY_COLLAPSER_PEARL.get(), CausalityCollapserPearlRenderer::new);
        event.registerBlockEntityRenderer(TABlockEntities.NODE_ANCHOR.get(), NodeAnchorBlockEntityRenderer::new);
        event.registerBlockEntityRenderer(TABlockEntities.ORIGIN_JAR.get(), OriginJarBlockEntityRenderer::new);
    }

    @SubscribeEvent
    public static void onRegisterMenuScreens(RegisterMenuScreensEvent event) {
        event.register(
                TAMenus.COSMIC_NEUTRONIUM_ESSENTIA_SMELTERY.get(), CosmicNeutroniumEssentiaSmelteryScreen::new);
        event.register(
                TAMenus.INFINITUS_COLLECTOR.get(), InfinitusCollectorScreen::new);
    }
}
