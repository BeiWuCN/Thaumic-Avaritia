package com.blackmoss.thaumicavaritia.registry;

import com.blackmoss.thaumicavaritia.ThaumicAvaritia;
import com.leclowndu93150.thaumaturge.api.aspect.TTAspects;
import com.leclowndu93150.thaumaturge.api.wands.*;
import com.leclowndu93150.thaumaturge.registry.TTDataComponents;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.List;

public final class TAWandParts {
    public static final DeferredRegister<WandCap> CAPS = DeferredRegister.create(WandCap.REGISTRY_KEY, ThaumicAvaritia.MODID);
    public static final DeferredRegister<WandRod> RODS = DeferredRegister.create(WandRod.REGISTRY_KEY, ThaumicAvaritia.MODID);

    public static final DeferredHolder<WandCap, WandCap> CRYSTAL_MATRIX = CAPS.register(
            "crystal_matrix",
            () -> new WandCap(
                    0.0F,
                    List.of(),
                    0.0F,
                    9,
                    ThaumicAvaritia.identifier("textures/models/crystal_matrix_wand_cap.png")));

    public static final DeferredHolder<WandRod, WandRod> NEUTRONIUM = RODS.register(
            "neutronium",
            () -> new WandRod(
                    Integer.MAX_VALUE,
                    9,
                    ThaumicAvaritia.identifier("textures/models/neutronium_wand_rod.png"),
                    new InfinityOnUpdate(),
                    true,
                    false,
                    false,
                    null));

    private static final class InfinityOnUpdate implements IWandRodOnUpdate {
        @Override
        public void onUpdate(ItemStack wand, Player player) {
            WandVis vis = WandAccess.getAllVis(wand);
            WandVis full = vis;
            for (var primal : TTAspects.PRIMALS) {
                full = full.with(primal, Integer.MAX_VALUE);
            }
            if (!full.equals(vis)) {
                wand.set(TTDataComponents.WAND_VIS.get(), full);
            }
        }
    }
}
