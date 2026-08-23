package com.blackmoss.thaumicavaritia.content.block.infinituscollector;

import com.blackmoss.thaumicavaritia.content.blockentity.InfinitusCollectorBlockEntity;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.inventory.ContainerData;

/**
 * 无限收集器 ContainerData 桥接类。
 * 将 BE 的凝晶进度值（已缩放到像素）通过 ContainerData 同步到客户端。
 */
public final class InfinitusCollectorContainerData implements ContainerData {
    private final SimpleContainer output;
    private final InfinitusCollectorBlockEntity be;

    public InfinitusCollectorContainerData(SimpleContainer output, InfinitusCollectorBlockEntity be) {
        this.output = output;
        this.be = be;
    }

    @Override
    public int get(int index) {
        if (index != 0 || be == null) return 0;
        // 返回已缩放到 18px 的进度值
//        return be.getCrystallizeProgressScaled(18);
        return 0;
    }

    @Override
    public void set(int index, int value) {
        // 只读，不支持写入
    }

    @Override
    public int getCount() {
        return 1;
    }
}