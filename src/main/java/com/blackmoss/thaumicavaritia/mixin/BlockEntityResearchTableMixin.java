package com.blackmoss.thaumicavaritia.mixin;

import com.blackmoss.thaumicavaritia.item.InfinityScribingToolsItem;
import com.leclowndu93150.thaumaturge.content.research.table.BlockEntityResearchTable;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(BlockEntityResearchTable.class)
public abstract class BlockEntityResearchTableMixin {
    @Redirect(method = "consumeInk",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;setDamageValue(I)V"))
    private void thaumicavaritia$keepInfiniteInkFull(ItemStack stack, int value) {
        if (stack.getItem() instanceof InfinityScribingToolsItem) {
            stack.setDamageValue(0);
        } else {
            stack.setDamageValue(value);
        }
    }
}
