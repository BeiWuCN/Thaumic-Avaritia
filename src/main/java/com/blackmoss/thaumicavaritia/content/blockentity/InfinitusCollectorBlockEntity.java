package com.blackmoss.thaumicavaritia.content.blockentity;

import com.blackmoss.thaumicavaritia.ThaumicAvaritia;
import com.blackmoss.thaumicavaritia.content.block.InfinitusNodeDataCache;
import com.blackmoss.thaumicavaritia.content.block.infinituscollector.InfinitusCollectorBlock;
import com.blackmoss.thaumicavaritia.content.block.infinituscollector.InfinitusCollectorTier;
import com.blackmoss.thaumicavaritia.content.menu.InfinitusCollectorMenu;
import com.blackmoss.thaumicavaritia.registry.TABlockEntities;
import com.blackmoss.thaumicavaritia.registry.TABlocks;
import com.leclowndu93150.thaumaturge.api.nodes.NodeModifier;
import com.leclowndu93150.thaumaturge.content.aura.node.NodeData;
import com.leclowndu93150.thaumaturge.registry.TCDataComponents;
import committee.nova.mods.avaritia.api.common.inventory.OnContentsChangedFunction;
import committee.nova.mods.avaritia.api.common.tile.BaseInventoryTileEntity;
import committee.nova.mods.avaritia.api.common.wrapper.ItemStackWrapper;
import committee.nova.mods.avaritia.api.iface.ITileIO;
import committee.nova.mods.avaritia.api.utils.ItemUtils;
import committee.nova.mods.avaritia.core.io.SideConfiguration;
import committee.nova.mods.avaritia.core.io.TileIOHandler;
import committee.nova.mods.avaritia.init.handler.NetworkHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.component.DataComponentGetter;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.neoforged.neoforge.transfer.ResourceHandler;
import net.neoforged.neoforge.transfer.item.ItemResource;
import net.neoforged.neoforge.transfer.transaction.Transaction;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jspecify.annotations.NonNull;

public class InfinitusCollectorBlockEntity extends BaseInventoryTileEntity implements ITileIO, WorldlyContainer {
    private SideConfiguration sideConfig = new SideConfiguration();
    private int activeOutputTick = 0;
    private static final int ACTIVE_OUTPUT_INTERVAL = 20;
    private final TileIOHandler ioHandler;
    public final ItemStackWrapper inventory;
    public SimpleContainerData data;
    private int progress;
    private InfinitusCollectorTier tier;

    // 内置"数据节点"：从原节点拷来的数据（类型/修饰/要素），用于收集器工作。
    @Nullable
    private NodeData nodeData;
    // 成形标记：有节点数据后才可工作。
    private boolean formed;

    public InfinitusCollectorBlockEntity(BlockPos pos, BlockState state) {
        super(TABlockEntities.INFINITUS_COLLECTOR.get(), pos, state);
        this.ioHandler = new TileIOHandler(this, InfinitusCollectorBlock.FACING);
        this.data = new SimpleContainerData(1);
        this.inventory = createInventoryHandler((_) -> this.setChangedAndDispatch());
        if (state.is(TABlocks.INFINITUS_COLLECTOR.get())) {
            this.tier = InfinitusCollectorTier.DEFAULT;
        } else if (state.is(TABlocks.INFINITUS_COLLECTOR_COMPACT.get())) {
            this.tier = InfinitusCollectorTier.COMPACT;
        } else if (state.is(TABlocks.INFINITUS_COLLECTOR_DENSE.get())) {
            this.tier = InfinitusCollectorTier.DENSE;
        } else if (state.is(TABlocks.INFINITUS_COLLECTOR_ENDLESS.get())) {
            this.tier = InfinitusCollectorTier.ENDLESS;
        }
    }

    public static void serverTick(Level level, BlockPos pos, BlockState state, InfinitusCollectorBlockEntity tile) {
        if (!level.isClientSide()) {
            // 首次 tick：尝试从蓝图缓存取回节点数据（蓝图成形路径）。
            if (!tile.formed && tile.nodeData == null) {
                InfinitusNodeDataCache.NodeSnapshot snapshot =
                        InfinitusNodeDataCache.take(level.dimension(), pos);
                if (snapshot != null) {
                    tile.nodeData = new NodeData(
                            snapshot.type(),
                            java.util.Optional.ofNullable(snapshot.modifier()),
                            snapshot.aspects(),
                            snapshot.aspects());
                    tile.formed = true;
                    tile.setChangedAndDispatch();
                }
            }
            if (!tile.formed) {
                return; // 无节点数据，不工作。
            }

            ++tile.activeOutputTick;
            if (tile.activeOutputTick >= 20) {
                tile.activeOutputTick = 0;
                tile.handleActiveOutput();
            }
        }

        if (tile.canWork()) {
            ItemStack result = tile.inventory.getStackInSlot(0);
            ItemStack stack = tile.tier.createProductionStack();
            ++tile.progress;
            tile.data.set(0, tile.progress);
            if (tile.progress >= tile.tier.production_ticks) {
                if (result.isEmpty()) {
                    tile.inventory.setStackInSlot(0, stack.copyWithCount(1));
                } else if (ItemStack.isSameItemSameComponents(result, stack) && result.getCount() < 64) {
                    tile.inventory.setStackInSlot(0, ItemUtils.grow(result, 1));
                }

                tile.progress = 0;
                tile.setChangedAndDispatch();
            }
        }

    }

    public static ItemStackWrapper createInventoryHandler(OnContentsChangedFunction onContentsChanged) {
        return ItemStackWrapper.create(9, onContentsChanged, (_) -> {
        });
    }

    protected void loadAdditional(@NotNull ValueInput input) {
        super.loadAdditional(input);
        this.progress = input.getIntOr("progress", 0);
        this.formed = input.getBooleanOr("formed", false);
        input.read("SideConfig", CompoundTag.CODEC).ifPresent((tag) -> this.sideConfig = SideConfiguration.fromNBT(tag));
        this.nodeData = input.read("NodeData", NodeData.CODEC).orElse(null);
    }

    protected void saveAdditional(@NotNull ValueOutput output) {
        super.saveAdditional(output);
        output.putInt("progress", this.progress);
        output.putBoolean("formed", this.formed);
        output.store("SideConfig", CompoundTag.CODEC, this.sideConfig.toNBT());
        if (this.nodeData != null) {
            output.store("NodeData", NodeData.CODEC, this.nodeData);
        }
    }

    // 放置时从物品 NODE_DATA 组件读取节点数据（手持右键节点路径）。
    @Override
    public void applyImplicitComponents(@NonNull DataComponentGetter components) {
        super.applyImplicitComponents(components);
        NodeData data = components.get(TCDataComponents.NODE_DATA.get());
        if (data != null) {
            this.nodeData = data;
            this.formed = true;
            this.setChangedAndDispatch();
        }
    }

    // 挖下时将节点数据写回物品 NODE_DATA 组件。
    @Override
    public void collectImplicitComponents(DataComponentMap.@NonNull Builder components) {
        super.collectImplicitComponents(components);
        if (this.nodeData != null) {
            components.set(TCDataComponents.NODE_DATA.get(), this.nodeData);
        }
    }

    public @NotNull ItemStackWrapper getInventory() {
        return this.inventory;
    }

    protected boolean canWork() {
        return this.inventory.getStackInSlot(0).isEmpty() || this.inventory.getStackInSlot(0).getCount() < 64;
    }

    public @NotNull Component getDisplayName() {
        return this.getBlockState().getBlock().getName();
    }

    public boolean isFormed() {
        return this.formed;
    }

    @Nullable
    public NodeData getNodeData() {
        return this.nodeData;
    }

    // 供 Item 右键节点后手动设置节点数据（绕过 applyImplicitComponents）。
    public void setNodeDataFromItem(NodeData data) {
        this.nodeData = data;
        this.formed = true;
        ThaumicAvaritia.LOGGER.info("[InfinitusCollectorBE] setNodeDataFromItem: type={}, modifier={}, aspects={}",
                data.type().getSerializedName(),
                data.modifier().map(NodeModifier::getSerializedName).orElse("none"),
                data.aspects() != null
                        ? data.aspects().entries().size()
                        : "null");
        this.setChangedAndDispatch();
    }

    public @Nullable AbstractContainerMenu createMenu(int windowId, @NotNull Inventory playerInventory) {
        return new InfinitusCollectorMenu(windowId, playerInventory, this.getBlockPos(), this.data);
    }

    public InfinitusCollectorTier getTier() {
        return this.tier;
    }

    public void setTier(InfinitusCollectorTier tier) {
        this.tier = tier;
    }

    public int getProductionTicks() {
        return this.tier.production_ticks;
    }

    public int getProgress() {
        return this.progress;
    }

    public ItemStack getProduction() {
        return this.tier.createProductionStack();
    }

    private void handleActiveOutput() {
        this.ioHandler.handleActiveIO();
    }

    public SideConfiguration getSideConfiguration() {
        return this.sideConfig;
    }

    public void setSideConfiguration(SideConfiguration config) {
        for (Direction direction : Direction.values()) {
            SideConfiguration.SideMode mode = config.getSideMode(direction);
            if (mode != SideConfiguration.SideMode.PASSIVE_OUTPUT && mode != SideConfiguration.SideMode.ACTIVE_OUTPUT) {
                config.setSideMode(direction, SideConfiguration.SideMode.OFF);
            }
        }

        this.sideConfig = config;
        this.setChangedAndDispatch();
        if (this.level != null && !this.level.isClientSide()) {
            NetworkHandler.sendSideConfigSync(this.worldPosition, config);
        }

    }

    public void setIOChange() {
        this.setChangedAndDispatch();
    }

    public void cycleSideModeForNeutronCollector(Direction direction) {
        SideConfiguration.SideMode current = this.sideConfig.getSideMode(direction);
        SideConfiguration.SideMode nextMode;
        if (current == SideConfiguration.SideMode.OFF) {
            nextMode = SideConfiguration.SideMode.PASSIVE_OUTPUT;
        } else if (current == SideConfiguration.SideMode.PASSIVE_OUTPUT) {
            nextMode = SideConfiguration.SideMode.ACTIVE_OUTPUT;
        } else {
            nextMode = SideConfiguration.SideMode.OFF;
        }

        this.sideConfig.setSideMode(direction, nextMode);
        this.setChangedAndDispatch();
        if (this.level != null && !this.level.isClientSide()) {
            NetworkHandler.sendSideConfigSync(this.worldPosition, this.sideConfig);
        }
    }

    public void extractFromHandler(ResourceHandler<ItemResource> externalHandler, Direction fromSide) {
    }

    public void insertToHandler(ResourceHandler<ItemResource> externalHandler, Direction toSide) {
        ItemStack outputSlot = this.inventory.getStackInSlot(0);
        if (!outputSlot.isEmpty()) {
            try (Transaction tx = Transaction.openRoot()) {
                int transferred = externalHandler.insert(ItemResource.of(outputSlot), outputSlot.getCount(), tx);
                if (transferred <= 0) {
                    return;
                }

                outputSlot.shrink(transferred);
                tx.commit();
                this.setChanged();
            }

        }
    }

    public int @NotNull [] getSlotsForFace(@NotNull Direction direction) {
        return this.sideConfig.getSideMode(direction).canOutput()
                ? new int[]{0} : new int[1];
    }

    public boolean canPlaceItemThroughFace(int i, @NotNull ItemStack itemStack, @Nullable Direction direction) {
        return this.ioHandler.shouldAllowPassiveIO(direction);
    }

    public boolean canTakeItemThroughFace(int index, @NotNull ItemStack itemStack, @NotNull Direction direction) {
        if (itemStack.isEmpty()) {
            return false;
        } else if (index == 0) {
            ItemStack result = this.getInventory().getStackInSlot(0);
            return ItemStack.isSameItemSameComponents(result, this.getProduction());
        } else {
            return false;
        }
    }

    public int getContainerSize() {
        return this.getInventory().getSlots();
    }

    public boolean isEmpty() {
        return this.getInventory().getStacks().isEmpty();
    }

    public @NotNull ItemStack getItem(int slot) {
        return slot == 0
                ? this.getInventory().toRecipeInventory().getItem(0)
                : ItemStack.EMPTY;
    }

    public @NotNull ItemStack removeItem(int slot, int amount) {
        return slot == 0
                ? this.getInventory().toRecipeInventory().removeItem(0, amount)
                : ItemStack.EMPTY;
    }

    public @NotNull ItemStack removeItemNoUpdate(int slot) {
        return slot == 0
                ? this.getInventory().toRecipeInventory().removeItemNoUpdate(0)
                : ItemStack.EMPTY;
    }

    public void setItem(int slot, @NotNull ItemStack itemStack) {
        if (slot == 0) {
            this.getInventory().toRecipeInventory().setItem(0, itemStack);
        }

    }

    public boolean stillValid(@NotNull Player player) {
        BlockPos blockPos = this.getBlockPos();
        return player.distanceToSqr((double) blockPos.getX() + (double) 0.5F, (double) blockPos.getY() + (double) 0.5F, (double) blockPos.getZ() + (double) 0.5F) <= (double) 64.0F;
    }

    public void clearContent() {
    }
}