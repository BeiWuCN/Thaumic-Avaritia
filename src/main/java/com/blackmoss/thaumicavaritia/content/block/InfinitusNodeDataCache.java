// 节点数据暂存缓存：蓝图把节点替换成收集器核心前，mixin 将节点数据(要素/类型/修饰)暂存于此，
// 收集器 BE 首次 tick 时消费并存入内置"数据节点"。
// 设计：成形时节点方块被替换、节点 BE 销毁，故数据必须跨"替换"暂存。静态 Map 按 世界+位置 键控。
package com.blackmoss.thaumicavaritia.content.block;

import com.leclowndu93150.thaumaturge.api.aspect.AspectList;
import com.leclowndu93150.thaumaturge.api.nodes.NodeModifier;
import com.leclowndu93150.thaumaturge.api.nodes.NodeType;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;
import org.jspecify.annotations.Nullable;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class InfinitusNodeDataCache {

    private static final Map<ResourceKey<Level>, Map<BlockPos, NodeSnapshot>> CACHE = new ConcurrentHashMap<>();

    private InfinitusNodeDataCache() {
    }

    // mixin 调用：暂存从节点读到的数据。
    public static void store(ResourceKey<Level> dimension, BlockPos pos, AspectList aspects, NodeType type, @Nullable NodeModifier modifier) {
        CACHE.computeIfAbsent(dimension, _ -> new ConcurrentHashMap<>())
                .put(pos.immutable(), new NodeSnapshot(aspects, type, modifier));
    }

    // 收集器 BE 调用：取走并移除（消费后即删，避免滞留）。
    public static @Nullable NodeSnapshot take(ResourceKey<Level> dimension, BlockPos pos) {
        Map<BlockPos, NodeSnapshot> byPos = CACHE.get(dimension);
        if (byPos == null) {
            return null;
        }
        NodeSnapshot snapshot = byPos.remove(pos);
        if (byPos.isEmpty()) {
            CACHE.remove(dimension);
        }
        return snapshot;
    }

    // 蓝图成形时节点数据快照。
    public record NodeSnapshot(AspectList aspects, NodeType type, @Nullable NodeModifier modifier) {
    }
}
