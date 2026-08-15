// 客户端注册：实体渲染器、菜单 Screen。
package com.blackmoss.thaumicavaritia.client;

import com.blackmoss.thaumicavaritia.ThaumicAvaritia;
import com.blackmoss.thaumicavaritia.client.render.entity.CausalityCollapserPearlRenderer;
import com.blackmoss.thaumicavaritia.client.screen.CosmicNeutroniumEssentiaSmelteryScreen;
import com.blackmoss.thaumicavaritia.registry.TAEntities;
import com.blackmoss.thaumicavaritia.registry.TAMenus;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;

@EventBusSubscriber(modid = ThaumicAvaritia.MODID, value = Dist.CLIENT)
public final class TAClientEvents {

    private TAClientEvents() {
    }

    @SubscribeEvent
    public static void onRegisterRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(
                TAEntities.CAUSALITY_COLLAPSER_PEARL.get(), CausalityCollapserPearlRenderer::new);
    }

    @SubscribeEvent
    public static void onRegisterMenuScreens(RegisterMenuScreensEvent event) {
        event.register(
                TAMenus.COSMIC_NEUTRONIUM_ESSENTIA_SMELTERY.get(), CosmicNeutroniumEssentiaSmelteryScreen::new);
    }
}
