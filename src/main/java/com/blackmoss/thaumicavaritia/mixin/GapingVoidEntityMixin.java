package com.blackmoss.thaumicavaritia.mixin;

import com.leclowndu93150.thaumaturge.api.aspect.AspectDataMaps;
import com.leclowndu93150.thaumaturge.api.aspect.AspectInstance;
import com.leclowndu93150.thaumaturge.api.aspect.AspectList;
import com.leclowndu93150.thaumaturge.api.nodes.NodeType;
import com.leclowndu93150.thaumaturge.content.aura.node.NodeGenerator;
import committee.nova.mods.avaritia.common.entity.GapingVoidEntity;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GapingVoidEntity.class)
public abstract class GapingVoidEntityMixin {
    @Unique
    private static final int SCAN_RADIUS = 6;

    @Unique
    private static final int VOID_END_AGE = 186;

    @Unique
    private boolean thaumicavaritia$nodeSpawned = false;

    @Inject(method = "tick", at = @At("HEAD"))
    private void thaumicavaritia$spawnNodeOnVoidEnd(CallbackInfo ci) {
        GapingVoidEntity self = (GapingVoidEntity) (Object) this;
        if (thaumicavaritia$nodeSpawned
                || self.getAge() < VOID_END_AGE
                || !(self.level() instanceof ServerLevel level)) {
            return;
        }
        thaumicavaritia$nodeSpawned = true;
        AspectList aspects = AspectList.EMPTY;
        BlockPos.MutableBlockPos cursor = new BlockPos.MutableBlockPos();
        for (int dx = -SCAN_RADIUS; dx <= SCAN_RADIUS; dx++) {
            for (int dy = -SCAN_RADIUS; dy <= SCAN_RADIUS; dy++) {
                for (int dz = -SCAN_RADIUS; dz <= SCAN_RADIUS; dz++) {
                    cursor.setWithOffset(self.blockPosition(), dx, dy, dz);
                    BlockState state = level.getBlockState(cursor);
                    if (state.isAir() || state.is(Blocks.BEDROCK) || state.is(Blocks.BARRIER)) {
                        continue;
                    }
                    Item item = state.getBlock().asItem();
                    if (item == Items.AIR) {
                        continue;
                    }
                    @SuppressWarnings("deprecation")
                    AspectList blockAspects = item.builtInRegistryHolder().getData(AspectDataMaps.BASE_ASPECTS);
                    if (blockAspects == null) {
                        continue;
                    }
                    List<AspectInstance> entries = blockAspects.entries();
                    for (AspectInstance entry : entries) {
                        if (entry.aspect().unwrapKey().isPresent()) {
                            aspects = aspects.add(entry.aspect(), entry.amount());
                        }
                    }
                }
            }
        }
        if (aspects.entries().isEmpty()) {
            return;
        }
        BlockPos nodePos = self.blockPosition();
        for (int i = 0; i < 4; i++) {
            BlockState state = level.getBlockState(nodePos);
            if (state.isAir() || state.canBeReplaced()) {
                break;
            }
            nodePos = nodePos.below();
        }
        NodeType[] types = NodeType.values();
        NodeGenerator.createNodeAt(level, nodePos, types[level.getRandom().nextInt(types.length)], null, aspects);
    }
}
