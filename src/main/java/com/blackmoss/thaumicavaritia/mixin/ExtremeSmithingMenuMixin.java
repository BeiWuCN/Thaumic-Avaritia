// 无尽锻造台（ExtremeSmithingMenu）资格门禁：结果槽产出带 #thaumicavaritia:not_qualified 标签的物品时，
// 若玩家未解锁同名研究，则清空结果槽——物品无法被取走，锻造材料留在输入槽不被消耗。
package com.blackmoss.thaumicavaritia.mixin;

import com.blackmoss.thaumicavaritia.content.NotQualifiedGate;
import committee.nova.mods.avaritia.common.menu.ExtremeSmithingMenu;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ExtremeSmithingMenu.class)
public abstract class ExtremeSmithingMenuMixin {

    // resultSlots/player 声明在父类 ItemCombinerMenu，@Shadow 无法解析，改用 ItemCombinerMenuAccessor。
    @Inject(method = "createResult", at = @At("TAIL"))
    private void thaumicavaritia$clearGatedSmithingResult(CallbackInfo ci) {
        ItemCombinerMenuAccessorMixin accessor = (ItemCombinerMenuAccessorMixin) this;
        ItemStack out = accessor.thaumicavaritia$getResultSlots().getItem(0);
        if (NotQualifiedGate.isGated(out)) {
            return;
        }
        if (!NotQualifiedGate.isResearchUnlocked(accessor.thaumicavaritia$getPlayer(), out)) {
            // 未解锁研究：清空结果槽，物品无法被取走，锻造材料保留。
            accessor.thaumicavaritia$getResultSlots().setItem(0, ItemStack.EMPTY);
        }
    }
}
