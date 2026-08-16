package com.blackmoss.thaumicavaritia.client.render.blockentity;

import net.minecraft.client.renderer.blockentity.state.BlockEntityRenderState;

public class NodeAnchorRenderState extends BlockEntityRenderState {
    // 客户端累计 tick（用于光圈脉冲动画）。
    public float ticks;
    // 光照参数。
    public int light;
    // 锚上方一格是否为灵气节点（是才画光圈）。
    public boolean hasNode;
    // 气泡相对锚坐标的高度偏移（节点在上方=1.5，节点在下方=-0.5）。
    public float bubbleY;
}
