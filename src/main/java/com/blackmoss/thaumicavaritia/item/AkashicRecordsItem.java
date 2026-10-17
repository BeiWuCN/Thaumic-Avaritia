package com.blackmoss.thaumicavaritia.item;

import com.leclowndu93150.thaumaturge.api.aspect.AspectInstance;
import com.leclowndu93150.thaumaturge.api.aspect.AspectList;
import com.leclowndu93150.thaumaturge.api.aspect.IAspect;
import com.leclowndu93150.thaumaturge.api.research.pool.AspectPoolAccess;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.level.Level;
import org.jspecify.annotations.NonNull;

import java.util.function.Consumer;

public class AkashicRecordsItem extends Item {
    private static final int GRANT_AMOUNT = 999;

    public AkashicRecordsItem(Properties properties) {
        super(properties);
    }

    private static void grantAllAspects(ServerPlayer player) {
        Registry<IAspect> registry = player.level().registryAccess().lookup(IAspect.REGISTRY_KEY).orElse(null);
        if (registry == null) {
            return;
        }
        AspectList grants = AspectList.EMPTY;
        for (Holder.Reference<IAspect> holder : registry.listElements().toList()) {
            grants = grants.add(new AspectInstance(holder, GRANT_AMOUNT));
        }
        AspectPoolAccess.grantAll(player, grants);
    }

    @SuppressWarnings("deprecation")
    @Override
    public void appendHoverText(
            @NonNull ItemStack stack,
            Item.@NonNull TooltipContext context,
            @NonNull TooltipDisplay display,
            @NonNull Consumer<Component> tooltip,
            @NonNull TooltipFlag flag) {
        super.appendHoverText(stack, context, display, tooltip, flag);
        tooltip.accept(Component.translatable("tooltip.thaumicavaritia.akashic_record")
                .withStyle(ChatFormatting.GRAY, ChatFormatting.ITALIC));
    }

    @Override
    public @NonNull InteractionResult use(@NonNull Level level, @NonNull Player player, @NonNull InteractionHand hand) {
        if (!(player instanceof ServerPlayer serverPlayer)) {
            return InteractionResult.PASS;
        }
        grantAllAspects(serverPlayer);
        player.getItemInHand(hand).shrink(1);
        return InteractionResult.SUCCESS;
    }
}
