package com.blackmoss.thaumicavaritia.content.item;

import com.blackmoss.thaumicavaritia.content.entity.EntityCausalityCollapserPearl;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.level.Level;
import org.jspecify.annotations.NonNull;

import java.util.function.Consumer;

public final class CausalityCollapserPearlItem extends Item {
    private static final float SHOOT_POWER = 1.5F;
    private static final float THROW_VOLUME = 0.5F;

    public CausalityCollapserPearlItem(Properties properties) {
        super(properties);
    }

    @Override
    public @NonNull InteractionResult use(Level level, Player player, @NonNull InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        level.playSound(
                null,
                player.getX(),
                player.getY(),
                player.getZ(),
                SoundEvents.ENDER_PEARL_THROW,
                SoundSource.NEUTRAL,
                THROW_VOLUME,
                0.4F / (level.getRandom().nextFloat() * 0.4F + 0.8F));
        if (level instanceof ServerLevel serverLevel) {
            Projectile.spawnProjectileFromRotation(
                    EntityCausalityCollapserPearl::new, serverLevel, stack, player, 0.0F, SHOOT_POWER, 2.0F);
        }
        stack.consume(1, player);
        return InteractionResult.SUCCESS;
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
        tooltip.accept(Component.translatable("item.thaumicavaritia.causality_collapser_pearl.desc")
                .withStyle(ChatFormatting.GRAY, ChatFormatting.ITALIC));
    }
}
