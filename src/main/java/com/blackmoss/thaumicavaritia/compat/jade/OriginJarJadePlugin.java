// 万物起源之罐 Jade 扩展：注册自定义 provider（逐行显示「要素 x∞」）。
package com.blackmoss.thaumicavaritia.compat.jade;

import com.blackmoss.thaumicavaritia.content.block.OriginJarBlock;
import org.jspecify.annotations.NonNull;
import snownee.jade.api.IWailaClientRegistration;
import snownee.jade.api.IWailaCommonRegistration;
import snownee.jade.api.IWailaPlugin;
import snownee.jade.api.WailaPlugin;

@WailaPlugin
public final class OriginJarJadePlugin implements IWailaPlugin {

    @Override
    public void register(@NonNull IWailaCommonRegistration registration) {
        // 万物起源之罐的内容由 BE 直接提供（客户端可读），无需服务端数据同步。
    }

    @Override
    public void registerClient(IWailaClientRegistration registration) {
        registration.registerBlockComponent(OriginJarComponentProvider.INSTANCE, OriginJarBlock.class);
    }
}
