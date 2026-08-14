// 物品标签 DataGen：联动物品标签声明。
package com.blackmoss.thaumicavaritia.data.tag;

import com.blackmoss.thaumicavaritia.ThaumicAvaritia;
import com.blackmoss.thaumicavaritia.registry.TAItems;
import com.blackmoss.thaumicavaritia.registry.TATags;
import com.leclowndu93150.thaumaturge.registry.TCItems;
import committee.nova.mods.avaritia.init.registry.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.IntrinsicHolderTagsProvider;
import net.minecraft.world.item.Item;
import org.jspecify.annotations.NonNull;

import java.util.concurrent.CompletableFuture;

public final class TATagsProvider implements DataProvider {
    private final IntrinsicHolderTagsProvider<Item> itemTags;

    @Override
    public @NonNull CompletableFuture<?> run(@NonNull CachedOutput cache) {
        return itemTags.run(cache);
    }

    @Override
    public @NonNull String getName() {
        return "Thaumic Avaritia Tags";
    }

    public TATagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookup) {
        this.itemTags = new IntrinsicHolderTagsProvider<>(output, Registries.ITEM, lookup,
                item -> item.builtInRegistryHolder().key(), ThaumicAvaritia.MODID) {
            @Override
            protected void addTags(HolderLookup.@NonNull Provider provider) {
                // 无尽联动：联动物品标记为无尽物品（#avaritia:endless / immortal）。
                tag(ModTags.IMMORTAL_ITEM).add(
                        TAItems.EXTREMELY_PRIMORDIAL_PEARL.get(),
                        TAItems.AKASHIC_RECORDS.get(),
                        TAItems.WAND_CAP_CRYSTAL.get(),
                        TAItems.WAND_ROD_STUDDED_COSMIC_NEUTRONIUM.get()
                );

                // Thaumaturge 珍宝（curio）均加入 curio 标签，供阿卡西记录配方使用。
                tag(TATags.THAUMATURGE_CURIOS).add(
                        TCItems.CURIO_ARCANE.get(),
                        TCItems.CURIO_PRESERVED.get(),
                        TCItems.CURIO_ANCIENT.get(),
                        TCItems.CURIO_ELDRITCH.get(),
                        TCItems.CURIO_KNOWLEDGE.get(),
                        TCItems.CURIO_TWISTED.get(),
                        TCItems.CURIO_RITES.get());
            }
        };
    }
}
