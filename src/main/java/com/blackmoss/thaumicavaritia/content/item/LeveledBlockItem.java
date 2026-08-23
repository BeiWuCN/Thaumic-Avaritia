// 带等级的方块物品（占位，当前收集器已改为四级独立方块，本类暂未被使用）。
// 放置时把指定等级写入方块状态（若方块有等级状态）。
package com.blackmoss.thaumicavaritia.content.item;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.state.BlockState;
import org.jspecify.annotations.Nullable;

public class LeveledBlockItem extends BlockItem {
    private final int level;

    public LeveledBlockItem(net.minecraft.world.level.block.Block block, Properties properties, int level) {
        super(block, properties);
        this.level = level;
    }

    @Override
    @Nullable
    protected BlockState getPlacementState(BlockPlaceContext context) {
        return super.getPlacementState(context);
    }
}
