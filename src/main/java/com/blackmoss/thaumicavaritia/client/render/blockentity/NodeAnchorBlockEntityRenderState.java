package com.blackmoss.thaumicavaritia.client.render.blockentity;

import net.minecraft.client.renderer.blockentity.state.BlockEntityRenderState;
import net.minecraft.core.Direction;

public class NodeAnchorBlockEntityRenderState extends BlockEntityRenderState {
    public float ticks;
    public int light;
    public boolean hasNode;
    public float bubbleY;
    public Direction facing = Direction.UP;
}
