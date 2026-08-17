// 节点锚 BER：在锚上方一格的灵气节点位置绘制 billboard 气泡光圈（呼应原版稳定器/换能器对节点的光环效果）。
// 复用 Thaumaturge 的 node_bubble.png 纹理，仅用标准 MC 渲染 API（不依赖 Thaumaturge 内部渲染类）。
package com.blackmoss.thaumicavaritia.client.render.blockentity;

import com.blackmoss.thaumicavaritia.content.block.NodeAnchorBlockEntity;
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
    // 半透明发光渲染（对齐原版稳定器 OVERLAY 的 entityTranslucentEmissive 层级，气泡应发光且正确排序）。
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
        // 节点可能在锚上方一格（爪朝上）或下方一格（爪朝下），气泡画在节点所在格的中心。
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
        // 锚坐标 → 节点所在格的中心（相对高度由 bubbleY 决定，节点在上=+1.5，在下=-0.5）。
        poseStack.translate(0.5F, state.bubbleY, 0.5F);
        // billboard：朝向相机。
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
