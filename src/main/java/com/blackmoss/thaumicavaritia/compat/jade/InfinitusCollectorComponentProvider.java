package com.blackmoss.thaumicavaritia.compat.jade;

import com.blackmoss.thaumicavaritia.ThaumicAvaritia;
import com.blackmoss.thaumicavaritia.block.InfinitusCollectorBlockEntity;
import com.leclowndu93150.thaumaturge.api.aspect.AspectComponents;
import com.leclowndu93150.thaumaturge.api.aspect.Aspects;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.player.Player;
import org.jspecify.annotations.NonNull;
import snownee.jade.api.BlockAccessor;
import snownee.jade.api.IBlockComponentProvider;
import snownee.jade.api.ITooltip;
import snownee.jade.api.config.IPluginConfig;

public enum InfinitusCollectorComponentProvider implements IBlockComponentProvider {
    INSTANCE;

    public static final Identifier UID = ThaumicAvaritia.identifier("infinitus_collector");

    @Override
    public @NonNull Identifier getUid() {
        return UID;
    }

    @Override
    public void appendTooltip(
            @NonNull ITooltip tooltip,
            @NonNull BlockAccessor accessor,
            @NonNull IPluginConfig config) {
        if (!(accessor.getBlockEntity() instanceof InfinitusCollectorBlockEntity be)) {
            return;
        }
        CompoundTag data = accessor.getServerData();

        boolean formed = data.getBooleanOr("formed", false);
        if (!formed) {
            tooltip.add(Component.translatable("jade.thaumicavaritia.infinitus_collector.unformed")
                    .withStyle(ChatFormatting.RED));
            return;
        }

        tooltip.add(Component.translatable("jade.thaumicavaritia.infinitus_collector.formed")
                .withStyle(ChatFormatting.GREEN));

        if (data.contains("node_type")) {
            String nodeType = data.getStringOr("node_type", "");
            if (!"normal".equals(nodeType)) {
                MutableComponent typeText = Component.translatable("jade.thaumaturge.node.type." + nodeType);
                if (data.contains("node_modifier")) {
                    String modifier = data.getStringOr("node_modifier", "");
                    typeText = Component.translatable("jade.thaumaturge.node.modified",
                            Component.translatable("jade.thaumaturge.node.modifier." + modifier),
                            typeText);
                }
                tooltip.add(typeText);
            }
        }

        if (data.contains("node_aspects")) {
            data.getList("node_aspects").ifPresent(aspectsList -> {
                if (!aspectsList.isEmpty()) {
                    Player player = accessor.getPlayer();
                    HolderLookup.Provider registries = player.level().registryAccess();

                    MutableComponent list = Component.literal("");
                    boolean[] first = {true};
                    for (int i = 0; i < aspectsList.size(); i++) {
                        aspectsList.getCompound(i).ifPresent(entryTag -> {
                            String aspectId = entryTag.getStringOr("id", "");
                            int amount = entryTag.getIntOr("amount", 0);

                            ResourceKey<com.leclowndu93150.thaumaturge.api.aspect.IAspect> key =
                                    ResourceKey.create(com.leclowndu93150.thaumaturge.api.aspect.IAspect.REGISTRY_KEY, Identifier.parse(aspectId));
                            Holder<com.leclowndu93150.thaumaturge.api.aspect.IAspect> holder = Aspects.resolve(registries, key);
                            if (holder != null) {
                                if (!first[0]) {
                                    list.append(Component.translatable("jade.thaumaturge.aspect_separator"));
                                }
                                list.append(Component.translatable("jade.thaumaturge.aspect_amount",
                                        AspectComponents.name(holder), amount));
                                first[0] = false;
                            }
                        });
                    }
                    if (!first[0]) {
                        tooltip.add(Component.translatable("jade.thaumaturge.node.aspects", list));
                    }
                }
            });
        }

        int progress = data.getIntOr("progress", 0);
        int productionTicks = data.getIntOr("production_ticks", 0);
        if (progress > 0 && productionTicks > 0) {
            int percent = progress * 100 / productionTicks;
            tooltip.add(Component.translatable("jade.thaumicavaritia.infinitus_collector.progress", percent));
        }

        if (data.contains("remaining_aspects")) {
            data.getList("remaining_aspects").ifPresent(aspectsList -> {
                if (!aspectsList.isEmpty()) {
                    Player player = accessor.getPlayer();
                    HolderLookup.Provider registries = player.level().registryAccess();

                    MutableComponent list = Component.literal("");
                    boolean[] first = {true};
                    for (int i = 0; i < aspectsList.size(); i++) {
                        aspectsList.getCompound(i).ifPresent(entryTag -> {
                            String aspectId = entryTag.getStringOr("id", "");
                            int amount = entryTag.getIntOr("amount", 0);

                            ResourceKey<com.leclowndu93150.thaumaturge.api.aspect.IAspect> key =
                                    ResourceKey.create(com.leclowndu93150.thaumaturge.api.aspect.IAspect.REGISTRY_KEY, Identifier.parse(aspectId));
                            Holder<com.leclowndu93150.thaumaturge.api.aspect.IAspect> holder = Aspects.resolve(registries, key);
                            if (holder != null) {
                                if (!first[0]) {
                                    list.append(Component.translatable("jade.thaumaturge.aspect_separator"));
                                }
                                list.append(Component.translatable("jade.thaumaturge.aspect_amount",
                                        AspectComponents.name(holder), amount));
                                first[0] = false;
                            }
                        });
                    }
                    if (!first[0]) {
                        tooltip.add(Component.translatable("jade.thaumicavaritia.infinitus_collector.remaining_aspects", list));
                    }
                }
            });
        }
    }
}