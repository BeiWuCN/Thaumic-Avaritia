package com.blackmoss.thaumicavaritia.compat.jade;

import com.blackmoss.thaumicavaritia.block.OriginJarBlock;
import com.blackmoss.thaumicavaritia.block.infinituscollector.InfinitusCollectorBlock;
import org.jspecify.annotations.NonNull;
import snownee.jade.api.IWailaClientRegistration;
import snownee.jade.api.IWailaCommonRegistration;
import snownee.jade.api.IWailaPlugin;
import snownee.jade.api.WailaPlugin;

@WailaPlugin
public final class ThaumicAvaritiaJadePlugin implements IWailaPlugin {

    @Override
    public void register(@NonNull IWailaCommonRegistration registration) {
        registration.registerBlockDataProvider(InfinitusCollectorDataProvider.INSTANCE, InfinitusCollectorBlock.class);
    }

    @Override
    public void registerClient(IWailaClientRegistration registration) {
        registration.registerBlockComponent(OriginJarComponentProvider.INSTANCE, OriginJarBlock.class);
        registration.registerBlockComponent(InfinitusCollectorComponentProvider.INSTANCE, InfinitusCollectorBlock.class);
    }
}