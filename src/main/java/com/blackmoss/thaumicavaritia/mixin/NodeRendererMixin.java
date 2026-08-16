// 充能节点渲染缩小：energized（换能器充能）状态下节点渲染缩放减半，形成"凝聚/压缩"观感
// （类似悖论珍珠黑洞的视觉压缩，但仅改渲染大小，不影响节点本身逻辑与数据）。
// 充能后节点要素量大，NodeRenderer.forEachLayer 中 scale=(0.2+scale*(amount/50))*size 会显著放大，
// 此处直接把全局缩放因子 state.size 减半以抵消。
package com.blackmoss.thaumicavaritia.mixin;

import com.leclowndu93150.thaumaturge.client.render.blockentity.NodeRenderState;
import com.leclowndu93150.thaumaturge.client.render.blockentity.NodeRenderer;
import com.leclowndu93150.thaumaturge.content.aura.node.BlockEntityNode;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(NodeRenderer.class)
public abstract class NodeRendererMixin {
    // energized 节点的渲染缩放倍率（0.5 = 减半）。
    @Unique
    private static final float ENERGIZED_SIZE_SCALE = 0.5F;

    @Inject(method = "extractRenderState*", at = @At("RETURN"))
    private void thaumicavaritia$shrinkEnergizedNode(
            BlockEntityNode node,
            NodeRenderState state,
            float partialTicks,
            Vec3 cameraPosition,
            ModelFeatureRenderer.CrumblingOverlay breakProgress,
            CallbackInfo ci) {
        if (state.energized) {
            state.size *= ENERGIZED_SIZE_SCALE;
        }
    }
}
