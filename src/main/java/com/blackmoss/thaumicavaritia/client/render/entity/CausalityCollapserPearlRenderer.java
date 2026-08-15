// 悖论珍珠渲染器：复用 ThrownItemRenderer 渲染抛掷中的物品模型（悖论珍珠物品图标）。
package com.blackmoss.thaumicavaritia.client.render.entity;

import com.blackmoss.thaumicavaritia.content.entity.CausalityCollapserPearlEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;

public final class CausalityCollapserPearlRenderer extends ThrownItemRenderer<CausalityCollapserPearlEntity> {
    public CausalityCollapserPearlRenderer(EntityRendererProvider.Context context) {
        super(context);
    }
}
