// 菜单类型注册：宇宙中子态素源质冶炼厂。
package com.blackmoss.thaumicavaritia.registry;

import com.blackmoss.thaumicavaritia.ThaumicAvaritia;
import com.blackmoss.thaumicavaritia.content.essentia.smeltery.MenuCosmicNeutroniumEssentiaSmeltery;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class TAMenus {

    public static final DeferredRegister<MenuType<?>> MENUS = DeferredRegister.create(Registries.MENU, ThaumicAvaritia.MODID);

    // 宇宙中子态素源质冶炼厂
    public static final DeferredHolder<MenuType<?>, MenuType<MenuCosmicNeutroniumEssentiaSmeltery>>
            COSMIC_NEUTRONIUM_ESSENTIA_SMELTERY =
                    MENUS.register(
                            "cosmic_neutronium_essentia_smeltery",
                            () -> IMenuTypeExtension.create(MenuCosmicNeutroniumEssentiaSmeltery::new));

    private TAMenus() {
    }

    public static void register(IEventBus modBus) {
        MENUS.register(modBus);
    }
}
