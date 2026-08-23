// 主类：无尽贪婪（Re:Avaritia）与神秘时代（Thaumaturge）联动内容。
package com.blackmoss.thaumicavaritia;

import com.blackmoss.thaumicavaritia.config.TAConfigs;
import com.blackmoss.thaumicavaritia.registry.TABlockEntities;
import com.blackmoss.thaumicavaritia.registry.TABlocks;
import com.blackmoss.thaumicavaritia.registry.TACreativeModeTabs;
import com.blackmoss.thaumicavaritia.registry.TAEntities;
import com.blackmoss.thaumicavaritia.registry.TAItems;
import com.blackmoss.thaumicavaritia.registry.TAMenus;
import com.blackmoss.thaumicavaritia.registry.TAWandParts;
import com.mojang.logging.LogUtils;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/neoforge.mods.toml file
@Mod(ThaumicAvaritia.MODID)
public class ThaumicAvaritia {
    // Define mod id in a common place for everything to reference
    public static final String MODID = "thaumicavaritia";
    public static final Logger LOGGER = LogUtils.getLogger();

    public ThaumicAvaritia(IEventBus modEventBus, ModContainer modContainer) {
        modContainer.registerConfig(ModConfig.Type.COMMON, TAConfigs.COMMON.build());

        TACreativeModeTabs.register(modEventBus);
        TAItems.register(modEventBus);
        TABlocks.register(modEventBus);
        TABlockEntities.register(modEventBus);
        TAMenus.register(modEventBus);
        TAWandParts.register(modEventBus);
        TAEntities.register(modEventBus);
    }
}
