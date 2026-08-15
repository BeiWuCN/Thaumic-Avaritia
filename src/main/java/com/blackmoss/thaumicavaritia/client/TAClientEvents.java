// 客户端注册：实体渲染器。
package com.blackmoss.thaumicavaritia.client;

import com.blackmoss.thaumicavaritia.ThaumicAvaritia;
import com.blackmoss.thaumicavaritia.client.render.entity.CausalityCollapserPearlRenderer;
import com.blackmoss.thaumicavaritia.registry.TAEntities;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;

@EventBusSubscriber(modid = ThaumicAvaritia.MODID, value = Dist.CLIENT)
public final class TAClientEvents {

    private TAClientEvents() {
    }

    @SubscribeEvent
    public static void onRegisterRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(
                TAEntities.CAUSALITY_COLLAPSER_PEARL.get(), CausalityCollapserPearlRenderer::new);
    }
}
