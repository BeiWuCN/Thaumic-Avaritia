package com.blackmoss.thaumicavaritia.client.render.blockentity;

import com.blackmoss.thaumicavaritia.ThaumicAvaritia;
import com.leclowndu93150.thaumaturge.TTIds;
import com.leclowndu93150.thaumaturge.client.golem.GolemMeshes;
import com.leclowndu93150.thaumaturge.client.model.mesh.TTMesh;
import com.leclowndu93150.thaumaturge.client.model.mesh.TTMeshPart;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;
import org.jspecify.annotations.Nullable;

public final class NodeAnchorMeshes {
    private static final Identifier MODEL = TTIds.rl("models/mesh/node_stabilizer.ttmesh");
    private static final Identifier TEXTURE = ThaumicAvaritia.identifier("textures/block/node_anchor.png");
    private static final Identifier OVERLAY_TEXTURE = TTIds.rl("textures/block/node_stabilizer_over.png");
    private static final RenderType BASE = RenderTypes.entityCutout(TEXTURE);
    private static final RenderType OVERLAY = RenderTypes.entityTranslucent(OVERLAY_TEXTURE);

    private NodeAnchorMeshes() {
    }

    public static void submitMesh(float ticks, PoseStack poseStack, SubmitNodeCollector collector, int light) {
        TTMesh mesh = GolemMeshes.get(MODEL);
        TTMeshPart lock = findPart(mesh, "lock");
        TTMeshPart piston = findPart(mesh, "piston");
        if (lock != null) {
            PoseStack.Pose lockPose = poseStack.last().copy();
            collector.submitCustomGeometry(
                    poseStack, BASE, (pose, buffer) -> GolemMeshes.renderPart(lock, lockPose, buffer, light, -1));
        }
        if (piston != null) {
            for (int arm = 0; arm < 4; ++arm) {
                poseStack.pushPose();
                poseStack.mulPose(Axis.ZP.rotationDegrees(90.0F * arm));
                poseStack.mulPose(Axis.YP.rotationDegrees(45.0F));
                poseStack.translate(0.0F, 0.0F, 0.0F);
                PoseStack.Pose armPose = poseStack.last().copy();
                collector.submitCustomGeometry(
                        poseStack, BASE, (pose, buffer) -> GolemMeshes.renderPart(piston, armPose, buffer, light, -1));
                float pulse = Mth.sin((ticks + arm * 5.0F) / 3.0F) * 0.1F + 0.9F;
                int glowLight = 50 + (int) (170.0F * pulse);
                collector.submitCustomGeometry(
                        poseStack, OVERLAY, (pose, buffer) -> GolemMeshes.renderPart(piston, armPose, buffer, glowLight, -1));
                poseStack.popPose();
            }
        }
    }

    private static @Nullable TTMeshPart findPart(TTMesh mesh, String name) {
        for (TTMeshPart part : mesh.parts()) {
            if (name.equals(part.name())) {
                return part;
            }
        }
        return null;
    }
}
