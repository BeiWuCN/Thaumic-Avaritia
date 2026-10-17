package com.blackmoss.thaumicavaritia.registry;

import com.blackmoss.thaumicavaritia.ThaumicAvaritia;
import com.leclowndu93150.thaumaturge.content.wands.ItemWand;
import com.leclowndu93150.thaumaturge.registry.TTItems;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class TACreativeModeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS;
    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> THAUMIC_AVARITIA;

    static {
        CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, ThaumicAvaritia.MODID);
        THAUMIC_AVARITIA = CREATIVE_MODE_TABS.register(ThaumicAvaritia.MODID,
                () -> CreativeModeTab.builder()
                        .title(Component.translatable("itemGroup.thaumicavaritia"))
                        .icon(() -> TAItems.AKASHIC_RECORDS.get().getDefaultInstance())
                        .displayItems((_, output) -> {
                            output.accept(TAItems.AKASHIC_RECORDS.get());
                            output.accept(TAItems.EXTREMELY_PRIMORDIAL_PEARL.get());
                            output.accept(TAItems.INTER_CRYSTAL_MATRIX_WAND_CAP.get());
                            output.accept(TAItems.CRYSTAL_MATRIX_WAND_CAP.get());
                            output.accept(TAItems.NEUTRONIUM_WAND_ROD.get());
                            output.accept(ItemWand.create(
                                    TTItems.WAND.get(),
                                    TAWandParts.CRYSTAL_MATRIX.get(),
                                    TAWandParts.NEUTRONIUM.get(),
                                    false));
                            output.accept(TAItems.CRYSTAL_MATRIX_PLATE.get());
                            output.accept(TAItems.NEUTRON_PLATE.get());
                            output.accept(TAItems.INFINITY_PLATE.get());
                            output.accept(TAItems.INFINITY_SCRIBING_TOOLS.get());
                            output.accept(TAItems.ARCANE_SINGULARITY_FOCUS.get());
                            output.accept(TAItems.CAUSALITY_COLLAPSER_PEARL.get());
                            output.accept(TAItems.LIMIT_FRAME.get());
                            output.accept(TAItems.LIMIT_ALCHEMICAL_CONSTRUCT.get());
                            output.accept(TAItems.INFINITY_CHARGER.get());
                            output.accept(TAItems.COSMIC_NEUTRONIUM_ESSENTIA_SMELTERY.get());
                            output.accept(TAItems.NODE_ANCHOR.get());
                            output.accept(TAItems.ORIGIN_JAR.get());
                            output.accept(TAItems.INFINITUS_CRYSTAL.get());
                            output.accept(TAItems.INFINITUS_COLLECTOR.get());
                            output.accept(TAItems.INFINITUS_COLLECTOR_COMPACT.get());
                            output.accept(TAItems.INFINITUS_COLLECTOR_DENSE.get());
                            output.accept(TAItems.INFINITUS_COLLECTOR_ENDLESS.get());
                        }).build());
    }

    public static void register(IEventBus modBus) {
        CREATIVE_MODE_TABS.register(modBus);
    }
}
