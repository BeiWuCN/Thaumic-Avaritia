// 黑洞结束时生成灵气节点：扫描黑洞周围被爆炸/吞噬影响的方块（含悖论珍珠爆炸破坏的方块），
// 聚合它们的 BASE_ASPECTS 要素构成，节点类型随机（NORMAL/UNSTABLE/DARK/TAINTED/HUNGRY/PURE）。
// 注入方式：@At("HEAD") + 年龄阈值判断（GapingVoidEntity.tick 开头 getAge() >= 186 即进入
// 黑洞消亡分支，remove(KILLED) 在该 tick 内稍后执行）。相比 @At(INVOKE) 定位 remove 调用，
// HEAD 注入对 IDE mixin 插件（无法解析 flatDir 本地 jar 字节码）与运行时都更稳妥。
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

    // 扫描半径：与黑洞吞噬/爆炸影响范围一致。
    @Unique
    private static final int SCAN_RADIUS = 6;
    // 黑洞生命周期：maxLifetime(144) + 蒸发(42) = 186，tick() 中 age >= 186 即消亡。
    @Unique
    private static final int VOID_END_AGE = 186;
    // 防重复：节点生成后置位，避免同实体再次 tick 误触发。
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
        // 聚合周围被吞噬/炸毁方块（其物品形态）的要素。
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
                    @SuppressWarnings("deprecation") // 与 Thaumaturge AspectIndexBuilder 一致，查询 item datamap。
                    AspectList blockAspects = item.builtInRegistryHolder().getData(AspectDataMaps.BASE_ASPECTS);
                    if (blockAspects == null) {
                        continue;
                    }
                    List<AspectInstance> entries = blockAspects.entries();
                    for (AspectInstance entry : entries) {
                        // 只累计已注册的要素（防御：datamap 可能引用本 mod 尚未加载的要素）。
                        if (entry.aspect().unwrapKey().isPresent()) {
                            aspects = aspects.add(entry.aspect(), entry.amount());
                        }
                    }
                }
            }
        }
        // 空节点不生成（纯基岩/空气环境）。
        if (aspects.entries().isEmpty()) {
            return;
        }
        // 在黑洞中心偏下找可放置位置生成节点。
        BlockPos nodePos = self.blockPosition();
        for (int i = 0; i < 4; i++) {
            BlockState state = level.getBlockState(nodePos);
            if (state.isAir() || state.canBeReplaced()) {
                break;
            }
            nodePos = nodePos.below();
        }
        // 节点类型随机（NORMAL/UNSTABLE/DARK/TAINTED/HUNGRY/PURE），不按要素决定。
        NodeType[] types = NodeType.values();
        NodeGenerator.createNodeAt(level, nodePos, types[level.getRandom().nextInt(types.length)], null, aspects);
    }
}
