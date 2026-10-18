package com.blackmoss.thaumicavaritia.client.model;

import com.blackmoss.thaumicavaritia.client.render.blockentity.NodeAnchorMeshes;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.mojang.serialization.MapCodec;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.special.NoDataSpecialModelRenderer;
import net.minecraft.client.renderer.special.SpecialModelRenderer;
import org.joml.Vector3f;
import org.joml.Vector3fc;
import org.jspecify.annotations.NonNull;

import java.util.function.Consumer;

public final class NodeAnchorItemSpecialRenderer implements NoDataSpecialModelRenderer {
    @Override
    public void submit(
            PoseStack poseStack,
            @NonNull SubmitNodeCollector collector,
            int lightCoords,
            int overlayCoords,
            boolean hasFoil,
            int outlineColor) {
        poseStack.pushPose();
        poseStack.translate(0.5F, 0.0F, 0.5F);
        poseStack.mulPose(Axis.XN.rotationDegrees(90.0F));
        NodeAnchorMeshes.submitMesh(0.0F, 0, poseStack, collector, lightCoords);
        poseStack.popPose();
    }

    @Override
    public void getExtents(Consumer<Vector3fc> consumer) {
        consumer.accept(new Vector3f(0.0F, 0.0F, 0.0F));
        consumer.accept(new Vector3f(1.0F, 1.0F, 1.0F));
    }

    public record Unbaked() implements NoDataSpecialModelRenderer.Unbaked {
        public static final MapCodec<Unbaked> MAP_CODEC = MapCodec.unit(Unbaked::new);

        @Override
        public @NonNull SpecialModelRenderer<Void> bake(SpecialModelRenderer.@NonNull BakingContext context) {
            return new NodeAnchorItemSpecialRenderer();
        }

        @Override
        public @NonNull MapCodec<? extends NoDataSpecialModelRenderer.Unbaked> type() {
            return MAP_CODEC;
        }
    }
}
