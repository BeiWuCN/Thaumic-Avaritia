package com.blackmoss.thaumicavaritia.capabilities;

import com.blackmoss.thaumicavaritia.ThaumicAvaritia;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;

@EventBusSubscriber(modid = ThaumicAvaritia.MODID)
public final class InfinitusCollectorCapabilities {
    private InfinitusCollectorCapabilities() {
    }

    @SubscribeEvent
    public static void onRegisterCapabilities(RegisterCapabilitiesEvent event) {
//        event.registerBlockEntity(
//                Capabilities.Item.BLOCK,
//                TABlockEntities.INFINITUS_COLLECTOR.get(),
//                (be, side) -> be.getOutputHandler());
    }
}
