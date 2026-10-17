package com.blackmoss.thaumicavaritia.client.render.blockentity;

import com.blackmoss.thaumicavaritia.block.NodeAnchorBlockEntity;
import com.leclowndu93150.thaumaturge.content.aura.node.BlockEntityNode;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.Identifier;
import net.minecraft.util.ARGB;
import net.minecraft.util.Mth;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

public final class NodeAnchorBlockEntityRenderer
        implements BlockEntityRenderer<NodeAnchorBlockEntity, NodeAnchorBlockEntityRenderState> {
    private static final Identifier BUBBLE_TEXTURE =
            Identifier.fromNamespaceAndPath("thaumaturge", "textures/misc/node_bubble.png");
    private static final RenderType BUBBLE = RenderTypes.entityTranslucentEmissive(BUBBLE_TEXTURE);
    private static final float BUBBLE_HALF = 0.9F;
    private static final float BUBBLE_ALPHA_BASE = 0.5F;
    private static final float BUBBLE_ALPHA_PULSE = 0.1F;
    private static final float BUBBLE_PULSE_PERIOD = 8.0F;
    private static final int BUBBLE_LIGHT = 0x00F000F0;

    public NodeAnchorBlockEntityRenderer(BlockEntityRendererProvider.Context context) {
    }

    @Override
    public @NonNull NodeAnchorBlockEntityRenderState createRenderState() {
        return new NodeAnchorBlockEntityRenderState();
    }

    @Override
    public void extractRenderState(
            @NonNull NodeAnchorBlockEntity anchor,
            @NonNull NodeAnchorBlockEntityRenderState state,
            float partialTicks,
            @NonNull Vec3 cameraPosition,
            ModelFeatureRenderer.@Nullable CrumblingOverlay breakProgress) {
        BlockEntityRenderer.super.extractRenderState(anchor, state, partialTicks, cameraPosition, breakProgress);
        LocalPlayer player = Minecraft.getInstance().player;
        state.ticks = player == null ? 0.0F : player.tickCount + partialTicks;
        state.light = state.lightCoords;
        Level level = anchor.getLevel();
        BlockPos pos = anchor.getBlockPos();
        boolean up = level != null && level.getBlockEntity(pos.above()) instanceof BlockEntityNode;
        boolean down = level != null && level.getBlockEntity(pos.below()) instanceof BlockEntityNode;
        state.hasNode = up || down;
        state.bubbleY = up ? 1.5F : (down ? -0.5F : 0.0F);
    }

    @Override
    public void submit(
            NodeAnchorBlockEntityRenderState state,
            @NonNull PoseStack poseStack,
            @NonNull SubmitNodeCollector collector,
            @NonNull CameraRenderState camera) {
        if (!state.hasNode) {
            return;
        }
        float pulse = Mth.sin(state.ticks / BUBBLE_PULSE_PERIOD) * BUBBLE_ALPHA_PULSE + BUBBLE_ALPHA_BASE;
        int color = ARGB.color((int) (pulse * 255.0F), 0xFFFFFF);
        poseStack.pushPose();
        poseStack.translate(0.5F, state.bubbleY, 0.5F);
        poseStack.mulPose(camera.orientation);
        collector.submitCustomGeometry(
                poseStack,
                BUBBLE,
                (pose, buffer) -> emitQuad(pose, buffer, color, state.light));
        poseStack.popPose();
    }

    private static void emitQuad(PoseStack.Pose pose, VertexConsumer buffer, int color, int light) {
        buffer.addVertex(pose, -BUBBLE_HALF, -BUBBLE_HALF, 0.0F)
                .setUv(0.0F, 1.0F)
                .setColor(color)
                .setOverlay(OverlayTexture.NO_OVERLAY)
                .setLight(light)
                .setNormal(pose, 0.0F, 0.0F, 1.0F);
        buffer.addVertex(pose, BUBBLE_HALF, -BUBBLE_HALF, 0.0F)
                .setUv(1.0F, 1.0F)
                .setColor(color)
                .setOverlay(OverlayTexture.NO_OVERLAY)
                .setLight(light)
                .setNormal(pose, 0.0F, 0.0F, 1.0F);
        buffer.addVertex(pose, BUBBLE_HALF, BUBBLE_HALF, 0.0F)
                .setUv(1.0F, 0.0F)
                .setColor(color)
                .setOverlay(OverlayTexture.NO_OVERLAY)
                .setLight(light)
                .setNormal(pose, 0.0F, 0.0F, 1.0F);
        buffer.addVertex(pose, -BUBBLE_HALF, BUBBLE_HALF, 0.0F)
                .setUv(0.0F, 0.0F)
                .setColor(color)
                .setOverlay(OverlayTexture.NO_OVERLAY)
                .setLight(light)
                .setNormal(pose, 0.0F, 0.0F, 1.0F);
    }
}
