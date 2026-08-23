//// 无限收集器 BE：含一个内置"数据节点"(NodeData，存于 NODE_DATA 组件，随方块物品携带)。
//// 获取方式：手持收集器物品右键灵气节点 → 节点数据被吸进收集器(NODE_DATA)，节点被替换成收集器方块；
////           或蓝图成形(mixin 暂存节点数据，首次 tick 取回)。
//// 四级进阶（LEVEL 0~3，由 blockstate 决定）：
////   0 基础：一次性——消耗内置数据节点的灵气作柴，抽干即停机(JAMMED)。
////   1 紧凑：效率增强，耗灵气更慢。
////   2 更密：吸收区块灵气稳定，不再啃噬数据节点。
////   3 无垠：与无限结晶合体，自给永动。
//// 停机恢复：挖下收集器(数据随 NODE_DATA 物品)，右键新节点换数据，再放回。
//// 弱点：很慢 / 污染(periodic polluteAura) / 噎住(随机 JAMMED，右键敲恢复) / 产出流失(rawInfinity 衰减)。
//package com.blackmoss.thaumicavaritia.content.blockentity;
//
//import com.blackmoss.thaumicavaritia.content.block.InfinitusNodeDataCache;
//import com.blackmoss.thaumicavaritia.content.block.infinituscollector.InfinitusCollectorBlock;
//import com.blackmoss.thaumicavaritia.content.block.infinituscollector.InfinitusCollectorSettings;
//import com.blackmoss.thaumicavaritia.content.menu.InfinitusCollectorMenu;
//import com.blackmoss.thaumicavaritia.registry.TABlockEntities;
//import com.blackmoss.thaumicavaritia.registry.TAItems;
//import com.leclowndu93150.thaumaturge.api.aspect.AspectInstance;
//import com.leclowndu93150.thaumaturge.api.aspect.AspectList;
//import com.leclowndu93150.thaumaturge.api.aura.AuraHelper;
//import com.leclowndu93150.thaumaturge.content.aura.node.NodeData;
//import com.leclowndu93150.thaumaturge.registry.TCDataComponents;
//import net.minecraft.core.BlockPos;
//import net.minecraft.core.component.DataComponentGetter;
//import net.minecraft.core.component.DataComponentMap;
//import net.minecraft.nbt.CompoundTag;
//import net.minecraft.network.chat.Component;
//import net.minecraft.network.protocol.Packet;
//import net.minecraft.network.protocol.game.ClientGamePacketListener;
//import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
//import net.minecraft.world.MenuProvider;
//import net.minecraft.world.SimpleContainer;
//import net.minecraft.world.entity.player.Inventory;
//import net.minecraft.world.entity.player.Player;
//import net.minecraft.world.inventory.AbstractContainerMenu;
//import net.minecraft.world.item.ItemStack;
//import net.minecraft.world.level.Level;
//import net.minecraft.world.level.block.entity.BlockEntity;
//import net.minecraft.world.level.block.state.BlockState;
//import net.minecraft.world.level.storage.ValueInput;
//import net.minecraft.world.level.storage.ValueOutput;
//import net.neoforged.neoforge.transfer.item.VanillaContainerWrapper;
//import org.jspecify.annotations.NonNull;
//import org.jspecify.annotations.Nullable;
//
//import java.util.Optional;
//
//public class InfinitusCollectorBlockEntity extends BlockEntity implements MenuProvider {
//    // 数值：凝成 1 点无限要素所需的原始无限（草案）。
//    public static final int RAW_TO_ASPECT = 64;
//    // 数值：凝成 1 枚无限结晶所需的无限要素（草案）。
//    public static final int ASPECT_TO_CRYSTAL = 16;
//    // 数值：产出流失衰减（每 40 tick 减 1 点原始无限；等级越高越慢）。
//    private static final int LOSS_INTERVAL = 40;
//
//    // 内部缓冲：原始无限（Raw Infinity）。
//    private int rawInfinity;
//    // 内部缓冲：无限要素（Infinitus 要素，凝成结晶的中间态）。
//    private int infinitusEssentia;
//    // 噎住状态：随机触发 / 节点本钱耗尽，右键敲打恢复。
//    private boolean jammed;
//    // 成形标记：有节点数据(NODE_DATA 或蓝图缓存)后才可工作。
//    private boolean formed;
//
//    // 内置"数据节点"：从原节点拷来的数据（类型/修饰/灵气），灵气可被基础级消耗。
//    private NodeData nodeData;
//
//    // 输出槽：无限结晶，可被漏斗抽走 / 右键取出。只出不进（canPlaceItem 恒 false）。
//    private final SimpleContainer output = new SimpleContainer(1) {
//        @Override
//        public boolean canPlaceItem(int index, @NonNull ItemStack stack) {
//            return false;
//        }
//    };
//
//    public InfinitusCollectorBlockEntity(BlockPos pos, BlockState state) {
//        super(TABlockEntities.INFINITUS_COLLECTOR.get(), pos, state);
//    }
//
//    public void serverTick(Level tickLevel, BlockPos pos) {
//        if (tickLevel == null || tickLevel.isClientSide()) {
//            return;
//        }
//        // 首次：尝试从蓝图缓存取回节点数据（蓝图成形路径）。
//        if (!formed && nodeData == null && level != null) {
//            InfinitusNodeDataCache.NodeSnapshot snapshot =
//                    InfinitusNodeDataCache.take(level.dimension(), pos);
//            if (snapshot != null) {
//                nodeData = new NodeData(
//                        snapshot.type(),
//                        Optional.ofNullable(snapshot.modifier()),
//                        snapshot.aspects(),
//                        snapshot.aspects());
//                formed = true;
//                setChanged();
//                syncForm(true);
//            }
//        }
//        if (!formed) {
//            return; // 无节点数据，不工作。
//        }
//        InfinitusCollectorSettings settings = ((InfinitusCollectorBlock) getBlockState().getBlock()).getSettings();
//        // 产出流失：缓冲随时间散回天地（无垠级自给，不流失）。
//        if (!settings.selfPowered() && (rawInfinity > 0 || infinitusEssentia > 0)) {
//            if (tickLevel.getGameTime() % LOSS_INTERVAL == 0) {
//                int lost = Math.min(rawInfinity, (RAW_TO_ASPECT / 8) / settings.lossDivisor());
//                rawInfinity -= lost;
//                if (lost > 0) {
//                    setChanged();
//                }
//            }
//        }
//        if (jammed) {
//            return; // 噎住 / 停机：不工作。
//        }
//        // 随机噎住（无垠级自给，不再噎住）。
//        if (!settings.selfPowered() && tickLevel.getRandom().nextInt(600) == 0) {
//            jammed = true;
//            setChanged();
//            return;
//        }
//        collect(tickLevel, pos, settings);
//        crystallize();
//    }
//
//    // 收集：按 settings 配置走不同"来路"。
//    private void collect(Level level, BlockPos pos, InfinitusCollectorSettings settings) {
//        // 累积概率（越高级越快，由 settings.collectChance 决定）。
//        if (level.getRandom().nextInt(settings.collectChance()) != 0) {
//            return;
//        }
//        // 自给永动（无垠）：不耗任何东西，直接累积。
//        if (settings.selfPowered()) {
//            rawInfinity++;
//            setChanged();
//            return;
//        }
//        // 消耗节点本钱（基础/紧凑）：耗尽即停机。
//        if (settings.aspectCost() > 0 && !consumeNodeAspect(settings.aspectCost())) {
//            jammed = true; // 节点本钱耗尽，停机。
//            setChanged();
//            return;
//        }
//        // 更密级：吸收区块灵气稳定（不耗节点本钱）。
//        if (settings.drainAura()) {
//            AuraHelper.drainVis(level, pos, RAW_TO_ASPECT / 2, false);
//        }
//        // 污染（弱点的代价）。
//        if (settings.pollution() > 0) {
//            AuraHelper.polluteAura(level, pos, settings.pollution(), true);
//        }
//        rawInfinity++;
//        setChanged();
//    }
//
//    // 从内置数据节点的灵气中消耗 amount 点。返回 false 表示本钱不足/耗尽。
//    private boolean consumeNodeAspect(int amount) {
//        if (nodeData == null) {
//            return false;
//        }
//        int total = nodeData.aspects().totalAmount();
//        if (total <= 0) {
//            return false; // 已耗尽。
//        }
//        // 扣减：从要素列表中移除一定量（尽量平均或从首个扣）。
//        AspectList remaining = nodeData.aspects();
//        int toRemove = Math.min(amount, total);
//        int remainingToRemove = toRemove;
//        AspectList newAspects = AspectList.EMPTY;
//        for (AspectInstance instance : nodeData.aspects().entries()) {
//            int amt = instance.amount();
//            if (remainingToRemove > 0) {
//                int remove = Math.min(amt, remainingToRemove);
//                amt -= remove;
//                remainingToRemove -= remove;
//            }
//            if (amt > 0) {
//                newAspects = newAspects.add(new AspectInstance(instance.aspect(), amt));
//            }
//        }
//        nodeData = new NodeData(nodeData.type(), nodeData.modifier(), newAspects, nodeData.aspectsBase());
//        setChanged();
//        return remainingToRemove <= 0; // 消耗成功（若还有未扣除说明 amount>total，但已全扣）。
//    }
//
//    // 凝结晶：原始无限 → 无限要素 → 无限结晶（进输出槽，槽空才能放入新结晶）。
//    private void crystallize() {
//        if (rawInfinity >= RAW_TO_ASPECT) {
//            infinitusEssentia++;
//            rawInfinity -= RAW_TO_ASPECT;
//            setChanged();
//        }
//        if (infinitusEssentia >= ASPECT_TO_CRYSTAL && output.getItem(0).isEmpty()) {
//            output.setItem(0, new ItemStack(TAItems.INFINITUS_CRYSTAL.get()));
//            infinitusEssentia -= ASPECT_TO_CRYSTAL;
//            setChanged();
//        }
//    }
//
//    // 同步 FORM 状态（触发模型 off/on 切换）。
//    private void syncForm(boolean value) {
//        if (level == null || level.isClientSide()) {
//            return;
//        }
//        if (getBlockState().getValue(InfinitusCollectorBlock.FORM) != value) {
//            level.setBlock(getBlockPos(), getBlockState().setValue(InfinitusCollectorBlock.FORM, value), 3);
//        }
//    }
//
//    // ---- 玩家交互 ----
//
//    // 尝试取出一枚结晶；无结晶或槽空返回 false。
//    public boolean takeCrystal() {
//        if (output.getItem(0).isEmpty()) {
//            return false;
//        }
//        output.setItem(0, ItemStack.EMPTY);
//        setChanged();
//        return true;
//    }
//
//    // 敲打（右键空手）：解除噎住状态。
//    public void knock() {
//        if (jammed) {
//            jammed = false;
//            setChanged();
//        }
//    }
//
//    public boolean isJammed() {
//        return jammed;
//    }
//
//    public boolean isFormed() {
//        return formed;
//    }
//
//    public int getRawInfinity() {
//        return rawInfinity;
//    }
//
//    public int getInfinitusEssentia() {
//        return infinitusEssentia;
//    }
//
//    public @Nullable NodeData getNodeData() {
//        return nodeData;
//    }
//
//    public net.neoforged.neoforge.transfer.ResourceHandler<net.neoforged.neoforge.transfer.item.ItemResource> getOutputHandler() {
//        return VanillaContainerWrapper.of(output);
//    }
//
//    // GUI 用：暴露 SimpleContainer 输出槽供 Menu 创建 Slot。
//    public SimpleContainer getOutputContainer() {
//        return output;
//    }
//
//    // GUI 用：结晶进度归一化到 [0, scale]，用于进度条绘制。
//    // 进度 = (rawInfinity + infinitusEssentia*RAW_TO_ASPECT) / (ASPECT_TO_CRYSTAL*RAW_TO_ASPECT)。
//    public int getCrystallizeProgressScaled(int scale) {
//        long total = rawInfinity + (long) infinitusEssentia * RAW_TO_ASPECT;
//        long max = (long) ASPECT_TO_CRYSTAL * RAW_TO_ASPECT;
//        if (max <= 0) {
//            return 0;
//        }
//        return (int) Math.min(scale, total * scale / max);
//    }
//
//    // ---- MenuProvider：服务端创建菜单，客户端显示标题。 ----
//    @Override
//    public @NonNull Component getDisplayName() {
//        return Component.translatable(getBlockState().getBlock().getDescriptionId());
//    }
//
//    @Override
//    public @Nullable AbstractContainerMenu createMenu(int containerId, @NonNull Inventory inventory, @NonNull Player player) {
//        return new InfinitusCollectorMenu(containerId, inventory, this);
//    }
//
//    // ---- 数据组件（放置读 NODE_DATA、挖掉写回 NODE_DATA）----
//
//    @Override
//    public void applyImplicitComponents(DataComponentGetter components) {
//        super.applyImplicitComponents(components);
//        NodeData data = components.get(TCDataComponents.NODE_DATA.get());
//        if (data != null) {
//            nodeData = data;
//            formed = true;
//            setChanged();
//            syncForm(true);
//        }
//    }
//
//    @Override
//    public void collectImplicitComponents(DataComponentMap.Builder components) {
//        super.collectImplicitComponents(components);
//        if (nodeData != null) {
//            components.set(TCDataComponents.NODE_DATA.get(), nodeData);
//        }
//    }
//
//    @Override
//    public void removeComponentsFromTag(ValueOutput output) {
//        output.discard("NodeData");
//    }
//
//    // ---- 序列化 ----
//
//    @Override
//    protected void loadAdditional(@NonNull ValueInput input) {
//        super.loadAdditional(input);
//        rawInfinity = input.getIntOr("RawInfinity", 0);
//        infinitusEssentia = input.getIntOr("InfinitusEssentia", 0);
//        jammed = input.getBooleanOr("Jammed", false);
//        formed = input.getBooleanOr("Formed", false);
//        nodeData = input.read("NodeData", NodeData.CODEC).orElse(null);
//        ItemStack stack = input.read("Output", ItemStack.CODEC).orElse(ItemStack.EMPTY);
//        output.setItem(0, stack);
//    }
//
//    @Override
//    protected void saveAdditional(@NonNull ValueOutput output) {
//        super.saveAdditional(output);
//        output.putInt("RawInfinity", rawInfinity);
//        output.putInt("InfinitusEssentia", infinitusEssentia);
//        output.putBoolean("Jammed", jammed);
//        output.putBoolean("Formed", formed);
//        if (nodeData != null) {
//            output.store("NodeData", NodeData.CODEC, nodeData);
//        }
//        ItemStack stack = this.output.getItem(0);
//        if (!stack.isEmpty()) {
//            output.store("Output", ItemStack.CODEC, stack);
//        }
//    }
//
//    // 客户端同步（成形态/输出可见性）。
//    @Override
//    public @NonNull CompoundTag getUpdateTag(net.minecraft.core.HolderLookup.@NonNull Provider registries) {
//        CompoundTag nbt = super.getUpdateTag(registries);
//        try (net.minecraft.util.ProblemReporter.ScopedCollector reporter =
//                     new net.minecraft.util.ProblemReporter.ScopedCollector(this.problemPath(), com.leclowndu93150.thaumaturge.Thaumaturge.LOGGER)) {
//            net.minecraft.world.level.storage.TagValueOutput out = net.minecraft.world.level.storage.TagValueOutput.createWithContext(reporter, registries);
//            saveAdditional(out);
//            nbt.merge(out.buildResult());
//        }
//        return nbt;
//    }
//
//    @Override
//    public @NonNull Packet<ClientGamePacketListener> getUpdatePacket() {
//        return ClientboundBlockEntityDataPacket.create(this);
//    }
//}
