// 万物起源之罐 Jade provider：只显示一行「要素 x∞」（不枚举各要素，避免超屏）。
// 复用本 mod lang 键 jade.thaumicavaritia.origin_jar.contents。
package com.blackmoss.thaumicavaritia.compat.jade;

import com.blackmoss.thaumicavaritia.ThaumicAvaritia;
import com.blackmoss.thaumicavaritia.content.essentia.jar.BlockEntityOriginJar;
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

    public static final Identifier UID =
            Identifier.fromNamespaceAndPath(ThaumicAvaritia.MODID, "origin_jar");

    @Override
    public @NonNull Identifier getUid() {
        return UID;
    }

    @Override
    public void appendTooltip(ITooltip tooltip, BlockAccessor accessor, IPluginConfig config) {
        if (!(accessor.getBlockEntity() instanceof BlockEntityOriginJar jar)) {
            return;
        }
        AspectList contents = jar.getAspects();
        if (contents.isEmpty()) {
            return;
        }
        // 只显示一行「要素 x∞」。
        tooltip.add(Component.translatable("jade.thaumicavaritia.origin_jar.contents"));
    }
}
