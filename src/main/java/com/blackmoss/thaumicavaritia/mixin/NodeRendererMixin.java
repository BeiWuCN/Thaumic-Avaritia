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
