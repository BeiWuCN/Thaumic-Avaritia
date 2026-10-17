package com.blackmoss.thaumicavaritia.block;

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

    public static void store(ResourceKey<Level> dimension, BlockPos pos, AspectList aspects, NodeType type, @Nullable NodeModifier modifier) {
        CACHE.computeIfAbsent(dimension, _ -> new ConcurrentHashMap<>())
                .put(pos.immutable(), new NodeSnapshot(aspects, type, modifier));
    }

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

    public record NodeSnapshot(AspectList aspects, NodeType type, @Nullable NodeModifier modifier) {
    }
}
