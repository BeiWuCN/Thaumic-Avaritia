// 无限收集器成形：蓝图把灵气节点替换成收集器核心前，拦截 DustTriggerTickHandler.complete，
// 读取原节点数据(要素/类型/修饰)暂存进 InfinitusNodeDataCache，供收集器 BE 首次 tick 取回。
// 这样节点被蓝图替换后，其数据不丢失，且能随收集器方块"带走"（破坏收集器时数据仍在 BE 内）。
package com.blackmoss.thaumicavaritia.mixin;

import com.blackmoss.thaumicavaritia.content.block.infinituscollector.InfinitusCollectorBlock;
import com.blackmoss.thaumicavaritia.content.block.InfinitusNodeDataCache;
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

    // complete(ServerLevel, PendingSwap) 开头拦截：若 resultState 是无限收集器且该位置仍是节点，
    // 则读取节点数据暂存，供收集器 BE 成形后取用。
    @Inject(method = "complete", at = @At("HEAD"))
    private static void thaumicavaritia$captureNodeData(ServerLevel level, PendingSwap entry, CallbackInfo ci) {
        BlockState resultState = entry.stateResult();
        if (resultState == null || !(resultState.getBlock() instanceof InfinitusCollectorBlock)) {
            return;
        }
        BlockPos pos = entry.pos();
        // 此刻节点 BE 仍在（替换发生在 complete 的 setBlock 之前），读数据暂存。
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
