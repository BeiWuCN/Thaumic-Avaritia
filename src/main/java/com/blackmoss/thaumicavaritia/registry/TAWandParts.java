// 法杖部件注册：为水晶缠绕宇宙中子态素法杖提供专属杖端（水晶矩阵）与杖柄（无尽）。
package com.blackmoss.thaumicavaritia.registry;

import com.blackmoss.thaumicavaritia.ThaumicAvaritia;
import com.leclowndu93150.thaumaturge.api.aspect.TCAspects;
import com.leclowndu93150.thaumaturge.api.wands.*;
import com.leclowndu93150.thaumaturge.registry.TCDataComponents;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.List;

public final class TAWandParts {

    // 无尽杖柄容量：9_999_999 vis（Thaumaturge 内部以 centivis 存储，1 vis = 100 centivis）。
    public static final int INFINITY_CAPACITY_VIS = 9_999_999;

    public static final DeferredRegister<WandCap> CAPS = DeferredRegister.create(WandCap.REGISTRY_KEY, ThaumicAvaritia.MODID);
    public static final DeferredRegister<WandRod> RODS = DeferredRegister.create(WandRod.REGISTRY_KEY, ThaumicAvaritia.MODID);

    // 水晶矩阵杖端：90% Vis 消耗减免（成本倍率 0.1）。
    public static final DeferredHolder<WandCap, WandCap> CRYSTAL_MATRIX = CAPS.register(
            "crystal_matrix",
            () -> new WandCap(0.1F, List.of(), 0.0F, 9, capTexture("crystal_matrix")));

    // 无尽杖柄：超高容量，携带时自动回满。
    public static final DeferredHolder<WandRod, WandRod> STUDDED_COSMIC_NEUTRONIUM = RODS.register(
            "studded_cosmic_neutronium",
            () -> new WandRod(
                    INFINITY_CAPACITY_VIS,
                    9,
                    rodTexture("studded_cosmic_neutronium"),
                    new InfinityOnUpdate(),
                    true,
                    false,
                    false,
                    null));

    private TAWandParts() {
    }

    private static Identifier capTexture(String name) {
        return Identifier.fromNamespaceAndPath(ThaumicAvaritia.MODID, "textures/models/wand_cap_" + name + ".png");
    }

    private static Identifier rodTexture(String name) {
        return Identifier.fromNamespaceAndPath(ThaumicAvaritia.MODID, "textures/models/wand_rod_" + name + ".png");
    }

    public static void register(IEventBus modBus) {
        CAPS.register(modBus);
        RODS.register(modBus);
    }

    // 无尽杖柄的自动回满回调：每 tick 将全部原初要素补满。
    private static final class InfinityOnUpdate implements IWandRodOnUpdate {
        @Override
        public void onUpdate(ItemStack wand, Player player) {
            WandVis vis = WandAccess.getAllVis(wand);
            WandVis full = vis;
            for (var primal : TCAspects.PRIMALS) {
                full = full.with(primal, INFINITY_CAPACITY_VIS * 100);
            }
            if (!full.equals(vis)) {
                wand.set(TCDataComponents.WAND_VIS.get(), full);
            }
        }
    }
}
