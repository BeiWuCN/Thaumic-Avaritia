package com.blackmoss.thaumicavaritia.block.infinituscollector;

import com.blackmoss.thaumicavaritia.block.InfinitusCollectorBlockEntity;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.inventory.ContainerData;

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
//       return be.getCrystallizeProgressScaled(18);
        return 0;
    }

    @Override
    public void set(int index, int value) {
    }

    @Override
    public int getCount() {
        return 1;
    }

    public SimpleContainer getOutput() {
        return output;
    }
}