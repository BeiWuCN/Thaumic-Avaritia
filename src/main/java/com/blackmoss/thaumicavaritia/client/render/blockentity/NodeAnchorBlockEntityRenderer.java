package com.blackmoss.thaumicavaritia.client.render.blockentity;

import com.blackmoss.thaumicavaritia.block.NodeAnchorBlock;
import com.blackmoss.thaumicavaritia.block.NodeAnchorBlockEntity;
import com.leclowndu93150.thaumaturge.TTIds;
import com.leclowndu93150.thaumaturge.client.effect.LateWorldRenderQueue;
import com.leclowndu93150.thaumaturge.client.effect.rendertype.TTFXRenderTypes;
import com.leclowndu93150.thaumaturge.content.aura.node.BlockEntityNode;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.Identifier;
import net.minecraft.util.ARGB;
import net.minecraft.util.Mth;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

public final class NodeAnchorBlockEntityRenderer
        implements BlockEntityRenderer<NodeAnchorBlockEntity, NodeAnchorBlockEntityRenderState> {
    private static final Identifier BUBBLE_TEXTURE = TTIds.rl("textures/misc/node_bubble.png");
    private static final RenderType BUBBLE = TTFXRenderTypes.additiveSorted(BUBBLE_TEXTURE);
    private static final float BUBBLE_HALF = 0.9F;
    private static final float BUBBLE_LIFT_UP = 1.5F;
    private static final float BUBBLE_LIFT_DOWN = -0.5F;
    private static final float BUBBLE_ALPHA_BASE = 0.5F;
    private static final float BUBBLE_ALPHA_PULSE = 0.1F;
    private static final float BUBBLE_PULSE_PERIOD = 8.0F;
    private static final int BUBBLE_LIGHT = 220;

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
        state.facing = anchor.getBlockState().getValue(NodeAnchorBlock.FACING);
        state.count = anchor.count;
        Level level = anchor.getLevel();
        BlockPos pos = anchor.getBlockPos();
        boolean up = level != null && level.getBlockEntity(pos.above()) instanceof BlockEntityNode;
        boolean down = level != null && level.getBlockEntity(pos.below()) instanceof BlockEntityNode;
        state.hasNode = up || down;
        state.bubbleY = up ? BUBBLE_LIFT_UP : (down ? BUBBLE_LIFT_DOWN : 0.0F);
    }

    @Override
    public void submit(
            NodeAnchorBlockEntityRenderState state,
            @NonNull PoseStack poseStack,
            @NonNull SubmitNodeCollector collector,
            @NonNull CameraRenderState camera) {
        poseStack.pushPose();
        if (state.facing == Direction.DOWN) {
            poseStack.translate(0.5F, 0.5F, 0.5F);
            poseStack.mulPose(Axis.XP.rotationDegrees(180.0F));
            poseStack.translate(-0.5F, -0.5F, -0.5F);
        }
        poseStack.translate(0.5F, 0.0F, 0.5F);
        poseStack.mulPose(Axis.XN.rotationDegrees(90.0F));
        NodeAnchorMeshes.submitMesh(state.ticks, state.count, poseStack, collector, state.light);
        poseStack.popPose();

        if (!state.hasNode) {
            return;
        }
        float pulse = Mth.sin(state.ticks / BUBBLE_PULSE_PERIOD) * BUBBLE_ALPHA_PULSE + BUBBLE_ALPHA_BASE;
        int color = ARGB.color((int) (pulse * 255.0F), 0xFFFFFF);
        Vec3 origin = Vec3.atCenterOf(state.blockPos).add(0.0D, state.bubbleY - 0.5D, 0.0D);
        LateWorldRenderQueue.enqueue(origin, (latePose, buffers) -> drawBubble(latePose, buffers, color));
    }

    private static void drawBubble(PoseStack poseStack, MultiBufferSource buffers, int color) {
        poseStack.pushPose();
        poseStack.mulPose(Minecraft.getInstance().gameRenderer.getMainCamera().rotation());
        PoseStack.Pose pose = poseStack.last();
        VertexConsumer buffer = buffers.getBuffer(BUBBLE);
        buffer.addVertex(pose, -BUBBLE_HALF, -BUBBLE_HALF, 0.0F)
                .setUv(0.0F, 1.0F)
                .setColor(color)
                .setLight(BUBBLE_LIGHT);
        buffer.addVertex(pose, BUBBLE_HALF, -BUBBLE_HALF, 0.0F)
                .setUv(1.0F, 1.0F)
                .setColor(color)
                .setLight(BUBBLE_LIGHT);
        buffer.addVertex(pose, BUBBLE_HALF, BUBBLE_HALF, 0.0F)
                .setUv(1.0F, 0.0F)
                .setColor(color)
                .setLight(BUBBLE_LIGHT);
        buffer.addVertex(pose, -BUBBLE_HALF, BUBBLE_HALF, 0.0F)
                .setUv(0.0F, 0.0F)
                .setColor(color)
                .setLight(BUBBLE_LIGHT);
        poseStack.popPose();
    }

    @Override
    public boolean shouldRenderOffScreen() {
        return true;
    }

    @Override
    public @NonNull AABB getRenderBoundingBox(@NonNull NodeAnchorBlockEntity anchor) {
        BlockPos pos = anchor.getBlockPos();
        Vec3 above = Vec3.atCenterOf(pos).add(0.0D, BUBBLE_LIFT_UP - 0.5D, 0.0D);
        Vec3 below = Vec3.atCenterOf(pos).add(0.0D, BUBBLE_LIFT_DOWN - 0.5D, 0.0D);
        AABB box = new AABB(pos);
        box = box.minmax(AABB.ofSize(above, BUBBLE_HALF * 2.0D, BUBBLE_HALF * 2.0D, BUBBLE_HALF * 2.0D));
        box = box.minmax(AABB.ofSize(below, BUBBLE_HALF * 2.0D, BUBBLE_HALF * 2.0D, BUBBLE_HALF * 2.0D));
        return box;
    }
}
