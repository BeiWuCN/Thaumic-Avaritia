package com.blackmoss.thaumicavaritia.registry;

import com.blackmoss.thaumicavaritia.ThaumicAvaritia;
import com.blackmoss.thaumicavaritia.menu.CosmicNeutroniumEssentiaSmelteryMenu;
import com.blackmoss.thaumicavaritia.menu.InfinitusCollectorMenu;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class TAMenus {

    public static final DeferredRegister<MenuType<?>> MENUS = DeferredRegister.create(Registries.MENU, ThaumicAvaritia.MODID);

    public static final DeferredHolder<MenuType<?>, MenuType<CosmicNeutroniumEssentiaSmelteryMenu>>
            COSMIC_NEUTRONIUM_ESSENTIA_SMELTERY =
            MENUS.register(
                    "cosmic_neutronium_essentia_smeltery",
                    () -> IMenuTypeExtension.create(CosmicNeutroniumEssentiaSmelteryMenu::new));

    public static final DeferredHolder<MenuType<?>, MenuType<InfinitusCollectorMenu>>
            INFINITUS_COLLECTOR =
            MENUS.register(
                    "infinitus_collector",
                    () -> IMenuTypeExtension.create(InfinitusCollectorMenu::new));

    private TAMenus() {
    }

    public static void register(IEventBus modBus) {
        MENUS.register(modBus);
    }
}
