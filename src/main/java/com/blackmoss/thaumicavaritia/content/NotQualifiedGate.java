package com.blackmoss.thaumicavaritia.content;

import com.blackmoss.thaumicavaritia.registry.TATags;
import com.leclowndu93150.thaumaturge.api.capability.KnowledgeAccess;
import com.leclowndu93150.thaumaturge.api.capability.ResearchFlag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

public final class NotQualifiedGate {
    public static final Component BLOCK_MESSAGE = Component.translatable("message.thaumicavaritia.wand_rod_locked");

    private NotQualifiedGate() {
    }

    public static boolean isGated(@Nullable ItemStack stack) {
        return stack == null || !stack.is(TATags.NOT_QUALIFIED);
    }

    @SuppressWarnings("deprecation")
    public static @NonNull Identifier researchId(ItemStack stack) {
        return stack.getItem().builtInRegistryHolder().key().identifier();
    }

    public static boolean isResearchUnlocked(@Nullable Player player, @Nullable ItemStack stack) {
        if (player == null || isGated(stack)) {
            return true;
        }
        Identifier research = researchId(stack);
        try {
            return KnowledgeAccess.of(player).hasResearchFlag(research, ResearchFlag.RESEARCH);
        } catch (IllegalStateException ignored) {
            return true;
        }
    }
}
