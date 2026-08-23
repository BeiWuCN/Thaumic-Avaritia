// 万物起源之罐 BER：在罐体内部渲染一个静态 avaritia 黑洞（终望珍珠 GapingVoid 同款 shader，无动画）。
// 复刻 GapingVoidRender.submit：BLACK_HOLE 渲染类型 + SimpleMesh quad + AvaritiaShaderUniforms（time=0 静态）。
package com.blackmoss.thaumicavaritia.client.render.blockentity;

import com.blackmoss.thaumicavaritia.content.blockentity.OriginJarBlockEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import committee.nova.mods.avaritia.client.render.mesh.SimpleMesh;
import committee.nova.mods.avaritia.client.render.mesh.SimpleMesh.Vertex;
import committee.nova.mods.avaritia.client.shader.AvaritiaRenderTypes;
import committee.nova.mods.avaritia.client.shader.AvaritiaShaderUniforms;
import java.util.List;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.blockentity.state.BlockEntityRenderState;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.ARGB;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

public final class OriginJarBlockEntityRenderer
        implements BlockEntityRenderer<OriginJarBlockEntity, BlockEntityRenderState> {

    private static final int FULL_BRIGHT = 0x00F000F0;
    private static final int WHITE_RGBA = ARGB.white(1.0F);
    // 黑洞在罐体内的固定显示比例（用户校准后的静态大小，无生长/收缩动画）。
    private static final float BLACK_HOLE_SCALE = 0.20F;
    // 罐体中心点：从下往上第 5 px（罐体高 12px，中心在 5/16 高度）。
    private static final float CENTER_Y = 5.0F / 16.0F;

    // 复刻 GapingVoidRender.blackHoleModel()：-1..1 平面 quad（billboard 由相机朝向控制）。
    private static final SimpleMesh BLACK_HOLE_MESH = new SimpleMesh(List.of(
            new Vertex(-1.0F, -1.0F, 0.0F, 0.0F, 1.0F, 0.0F, 0.0F, 1.0F),
            new Vertex(1.0F, -1.0F, 0.0F, 1.0F, 1.0F, 0.0F, 0.0F, 1.0F),
            new Vertex(1.0F, 1.0F, 0.0F, 1.0F, 0.0F, 0.0F, 0.0F, 1.0F),
            new Vertex(-1.0F, 1.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1.0F)));

    public OriginJarBlockEntityRenderer(BlockEntityRendererProvider.Context context) {
    }

    @Override
    public @NonNull BlockEntityRenderState createRenderState() {
        return new BlockEntityRenderState();
    }

    @Override
    public void extractRenderState(
            @NonNull OriginJarBlockEntity blockEntity,
            @NonNull BlockEntityRenderState state,
            float partialTicks,
            @NonNull Vec3 cameraPosition,
            ModelFeatureRenderer.@Nullable CrumblingOverlay breakProgress) {
        BlockEntityRenderer.super.extractRenderState(blockEntity, state, partialTicks, cameraPosition, breakProgress);
    }

    @Override
    public void submit(
            @NonNull BlockEntityRenderState state,
            PoseStack poseStack,
            SubmitNodeCollector collector,
            CameraRenderState camera) {
        // 黑洞在罐体内部中心（0.5, CENTER_Y, 0.5）。
        float x = 0.5F;
        float y = CENTER_Y;
        float z = 0.5F;
        double dx = x - camera.pos.x;
        double dy = y - camera.pos.y;
        double dz = z - camera.pos.z;
        float yaw = (float) Math.atan2(dx, dz);
        float pitch = (float) Math.atan2(Math.sqrt(dx * dx + dz * dz), dy);

        poseStack.pushPose();
        poseStack.translate(x, y, z);
        poseStack.scale(BLACK_HOLE_SCALE, BLACK_HOLE_SCALE, BLACK_HOLE_SCALE);
        poseStack.mulPose(camera.orientation);
        poseStack.mulPose(Axis.YP.rotationDegrees(180.0F));

        // 黑洞 shader uniforms：time=0（静态，无旋转/噪声动画），吸收/蒸发为 0（保持完整稳定）。
        AvaritiaShaderUniforms.set(
                AvaritiaRenderTypes.BLACK_HOLE,
                AvaritiaShaderUniforms.Effect.BLACK_HOLE,
                0.0F,
                yaw,
                pitch,
                0.0F,
                0.0F,
                null);

        RenderType blackHole = AvaritiaRenderTypes.BLACK_HOLE;
        collector.submitCustomGeometry(poseStack, blackHole, (pose, buffer) ->
                BLACK_HOLE_MESH.render(pose, buffer, WHITE_RGBA, FULL_BRIGHT, OverlayTexture.NO_OVERLAY));
        poseStack.popPose();
    }
}
