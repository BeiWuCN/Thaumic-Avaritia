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

    /**
     * 物品是否受资格门禁限制。
     *
     * @param stack 物品
     * @return 带 not_qualified 标签返回 true
     */
    public static boolean isGated(@Nullable ItemStack stack) {
        return stack != null && stack.is(TATags.NOT_QUALIFIED);
    }

    /**
     * 门禁物品对应的研究条目 id：与物品注册 id 相同（含命名空间）。
     *
     * @param stack 已确认带 not_qualified 标签的物品
     * @return 研究条目 Identifier；物品未注册时返回 null
     */
    @SuppressWarnings("deprecation") // builtInRegistryHolder 是获取已注册物品注册 key 的标准途径（同 TATooltipHandler）
    public static @NonNull Identifier researchId(ItemStack stack) {
        return stack.getItem().builtInRegistryHolder().key().identifier();
    }

    /**
     * 玩家是否已解锁该门禁物品对应研究。
     *
     * @param player 玩家
     * @param stack  带 not_qualified 标签的物品
     * @return 已解锁返回 true；知识访问器尚未绑定或研究 id 不可用时保守放行（true）
     */
    public static boolean isResearchUnlocked(@Nullable Player player, @Nullable ItemStack stack) {
        if (player == null || !isGated(stack)) {
            return true;
        }
        Identifier research = researchId(stack);
        try {
            return KnowledgeAccess.of(player).hasResearchFlag(research, ResearchFlag.RESEARCH);
        } catch (IllegalStateException ignored) {
            // KnowledgeAccess 尚未绑定（极端时序），保守放行以免误吞合成物。
            return true;
        }
    }
}
