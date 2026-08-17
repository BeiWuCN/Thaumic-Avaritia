// ItemCombinerMenu 字段访问器：暴露父类 protected 字段 resultSlots/player，
// 供 ExtremeSmithingMenuMixin 等子类 mixin 使用（@Shadow 无法解析父类字段）。
package com.blackmoss.thaumicavaritia.mixin;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ItemCombinerMenu;
import net.minecraft.world.inventory.ResultContainer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(ItemCombinerMenu.class)
public interface ItemCombinerMenuAccessorMixin {

    @Accessor("resultSlots")
    ResultContainer thaumicavaritia$getResultSlots();

    @Accessor("player")
    Player thaumicavaritia$getPlayer();
}
