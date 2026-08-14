package com.blackmoss.thaumicavaritia.content.item;

import com.leclowndu93150.thaumaturge.content.casters.ItemFocus;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class ArcaneSingularityFocusItem extends ItemFocus {

    public ArcaneSingularityFocusItem(Item.Properties properties) {
        // Integer.MAX_VALUE 表示无实际复杂度上限（无限内容）。
        super(properties, Integer.MAX_VALUE);
    }

    // 奥法奇点核心施法无冷却：返回 0 会清空 CasterManager 冷却与物品冷却（原版最低 5 tick）。
    @Override
    public int getActivationTime(ItemStack focusStack) {
        return 0;
    }
}
