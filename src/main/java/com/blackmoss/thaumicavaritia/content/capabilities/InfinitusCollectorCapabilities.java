// 无限收集器能力注册：输出槽（无限结晶）可被漏斗等物品传输机制抽走。
package com.blackmoss.thaumicavaritia.content.capabilities;

import com.blackmoss.thaumicavaritia.ThaumicAvaritia;
import com.blackmoss.thaumicavaritia.registry.TABlockEntities;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.capabilities.Capabilities;
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
