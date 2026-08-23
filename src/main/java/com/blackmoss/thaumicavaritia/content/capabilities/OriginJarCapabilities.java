// 万物起源之罐的能力注册：源质传输（只出不进）+ 要素容器（只出不进）。
package com.blackmoss.thaumicavaritia.content.capabilities;

import com.blackmoss.thaumicavaritia.ThaumicAvaritia;
import com.blackmoss.thaumicavaritia.registry.TABlockEntities;
import com.leclowndu93150.thaumaturge.api.aspect.AspectCapabilities;
import com.leclowndu93150.thaumaturge.api.essentia.EssentiaCapabilities;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;

@EventBusSubscriber(modid = ThaumicAvaritia.MODID)
public final class OriginJarCapabilities {

    private OriginJarCapabilities() {
    }

    @SubscribeEvent
    public static void onRegisterCapabilities(RegisterCapabilitiesEvent event) {
        event.registerBlockEntity(EssentiaCapabilities.TRANSPORT, TABlockEntities.ORIGIN_JAR.get(), (be, _) -> be);
        event.registerBlockEntity(AspectCapabilities.CONTAINER, TABlockEntities.ORIGIN_JAR.get(), (be, _) -> be);
    }
}
