// 无尽工作台（TierCraftMenu）资格门禁：结果槽产出带 #thaumicavaritia:not_qualified 标签的物品时，
// 若玩家未解锁同名研究，则清空结果槽——物品无法被取走，材料留在网格不被消耗。
package com.blackmoss.thaumicavaritia.mixin;

import com.blackmoss.thaumicavaritia.content.NotQualifiedGate;
import committee.nova.mods.avaritia.common.menu.TierCraftMenu;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ResultContainer;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TierCraftMenu.class)
public abstract class TierCraftMenuMixin {

    @Shadow
    @Final
    private ResultContainer result;

    @Shadow
    @Final
    private Player player;

    @Inject(method = "slotsChanged", at = @At("TAIL"))
    private void thaumicavaritia$clearGatedResult(Container container, CallbackInfo ci) {
        ItemStack out = result.getItem(0);
        if (NotQualifiedGate.isGated(out)) {
            return;
        }
        if (!NotQualifiedGate.isResearchUnlocked(player, out)) {
            // 未解锁研究：清空结果槽，物品无法被取走，材料留在网格。
            result.setItem(0, ItemStack.EMPTY);
        }
    }
}
