package com.blackmoss.thaumicavaritia.mixin;

import com.blackmoss.thaumicavaritia.block.infinituscollector.InfinitusCollectorBlock;
import com.blackmoss.thaumicavaritia.block.InfinitusNodeDataCache;
import com.leclowndu93150.thaumaturge.content.aura.node.BlockEntityNode;
import com.leclowndu93150.thaumaturge.content.recipe.dust.DustTriggerTickHandler;
import com.leclowndu93150.thaumaturge.content.recipe.dust.PendingSwap;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(DustTriggerTickHandler.class)
public abstract class DustTriggerTickHandlerMixin {
    @Inject(method = "complete", at = @At("HEAD"))
    private static void thaumicavaritia$captureNodeData(ServerLevel level, PendingSwap entry, CallbackInfo ci) {
        BlockState resultState = entry.stateResult();
        if (resultState == null || !(resultState.getBlock() instanceof InfinitusCollectorBlock)) {
            return;
        }
        BlockPos pos = entry.pos();
        if (level.getBlockEntity(pos) instanceof BlockEntityNode node) {
            InfinitusNodeDataCache.store(
                    level.dimension(),
                    pos,
                    node.getAspects(),
                    node.getNodeType(),
                    node.getNodeModifier());
        }
    }
}
