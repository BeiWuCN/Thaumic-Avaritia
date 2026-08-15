// 笔如有神之笔与墨：研究台笔具（IScribeTools），有耐久但消耗后自动回满，实际永不损坏。
// 注1：Thaumaturge 的 ScribingToolsItem 是 final class 无法继承，故直接实现同一标记接口，
//      研究台笔槽只认 IScribeTools，行为与原版笔具完全一致。
// 注2：研究台判定笔可用要求 maxDamage > 0（BlockEntityResearchTable.consumeInk / hasInkReady），
//      因此不能真的零耐久；注册时给出巨大耐久上限，并由 inventoryTick 每 tick 回满实现"无尽"。
package com.blackmoss.thaumicavaritia.content.item;

import com.leclowndu93150.thaumaturge.api.items.IScribeTools;
import java.util.function.Consumer;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.item.TooltipFlag;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

public final class InfinityScribingToolsItem extends Item implements IScribeTools {

    // 耐久上限（与注册时的 durability 保持一致）：足够大，实际书写消耗永远达不到。
    public static final int MAX_DAMAGE = 1_000_000;

    public InfinityScribingToolsItem(Item.Properties properties) {
        super(properties);
    }

    @SuppressWarnings("deprecation") // 构建 classpath 中 5 参 appendHoverText 为唯一可覆写点（无 4 参新签名）。
    @Override
    public void appendHoverText(
            @NonNull ItemStack stack,
            Item.@NonNull TooltipContext context,
            @NonNull TooltipDisplay display,
            @NonNull Consumer<Component> tooltip,
            @NonNull TooltipFlag flag) {
        super.appendHoverText(stack, context, display, tooltip, flag);
        tooltip.accept(Component.translatable("item.thaumicavaritia.infinity_scribing_tools.desc")
                .withStyle(ChatFormatting.GRAY, ChatFormatting.ITALIC));
    }

    @Override
    public void inventoryTick(@NonNull ItemStack stack, @NonNull ServerLevel level, @NonNull Entity entity, @Nullable EquipmentSlot slot) {
        super.inventoryTick(stack, level, entity, slot);
        // 自动回复：一旦产生消耗立即回满，观感上永不损坏。
        if (stack.getDamageValue() > 0) {
            stack.setDamageValue(0);
        }
    }
}
