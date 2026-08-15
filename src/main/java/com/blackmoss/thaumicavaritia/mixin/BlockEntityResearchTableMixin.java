// 研究台扣墨逻辑：无尽笔与墨（InfinityScribingToolsItem）书写/擦除时不产生耐久损耗，
// 保持满耐久观感（其 inventoryTick 亦每 tick 回满，双保险）。
package com.blackmoss.thaumicavaritia.mixin;

import com.blackmoss.thaumicavaritia.content.item.InfinityScribingToolsItem;
import com.leclowndu93150.thaumaturge.content.research.table.BlockEntityResearchTable;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(BlockEntityResearchTable.class)
public abstract class BlockEntityResearchTableMixin {

    // consumeInk 中扣墨的 setDamageValue(damage + 1) 被重定向：
    // 无尽笔写回 0（满耐久），其余笔照常扣耐久。
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
