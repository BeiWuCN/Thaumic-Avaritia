package com.blackmoss.thaumicavaritia.compat.jade;

import com.blackmoss.thaumicavaritia.ThaumicAvaritia;
import com.blackmoss.thaumicavaritia.block.OriginJarBlockEntity;
import com.leclowndu93150.thaumaturge.api.aspect.AspectList;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import org.jspecify.annotations.NonNull;
import snownee.jade.api.BlockAccessor;
import snownee.jade.api.IBlockComponentProvider;
import snownee.jade.api.ITooltip;
import snownee.jade.api.config.IPluginConfig;

public enum OriginJarComponentProvider implements IBlockComponentProvider {
    INSTANCE;

    public static final Identifier UID = ThaumicAvaritia.identifier("origin_jar");

    @Override
    public @NonNull Identifier getUid() {
        return UID;
    }

    @Override
    public void appendTooltip(
            @NonNull ITooltip tooltip,
            @NonNull BlockAccessor accessor,
            @NonNull IPluginConfig config) {
        if (!(accessor.getBlockEntity() instanceof OriginJarBlockEntity jar)) {
            return;
        }
        AspectList contents = jar.getAspects();
        if (contents.isEmpty()) {
            return;
        }
        tooltip.add(Component.translatable("jade.thaumicavaritia.origin_jar.contents"));
    }
}