// 主类：无尽贪婪（Re:Avaritia）与神秘时代（Thaumaturge）联动内容。
package com.blackmoss.thaumicavaritia;

import com.blackmoss.thaumicavaritia.registry.TABlocks;
import com.blackmoss.thaumicavaritia.registry.TACreativeModeTabs;
import com.blackmoss.thaumicavaritia.registry.TAEntities;
import com.blackmoss.thaumicavaritia.registry.TAItems;
import com.blackmoss.thaumicavaritia.registry.TAWandParts;
import com.mojang.logging.LogUtils;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/neoforge.mods.toml file
@Mod(ThaumicAvaritia.MODID)
public class ThaumicAvaritia {
    // Define mod id in a common place for everything to reference
    public static final String MODID = "thaumicavaritia";
    public static final Logger LOGGER = LogUtils.getLogger();

    public ThaumicAvaritia(IEventBus modEventBus) {
        TACreativeModeTabs.register(modEventBus);
        TAItems.register(modEventBus);
        TABlocks.register(modEventBus);
        TAWandParts.register(modEventBus);
        TAEntities.register(modEventBus);
    }
}
