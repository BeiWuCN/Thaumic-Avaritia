package com.blackmoss.thaumicavaritia.content.essentia.jar;

import com.blackmoss.thaumicavaritia.api.aspect.TAAspects;
import com.blackmoss.thaumicavaritia.registry.TABlockEntities;
import com.leclowndu93150.thaumaturge.Thaumaturge;
import com.leclowndu93150.thaumaturge.api.aspect.AspectInstance;
import com.leclowndu93150.thaumaturge.api.aspect.AspectList;
import com.leclowndu93150.thaumaturge.api.aspect.IAspect;
import com.leclowndu93150.thaumaturge.api.aspect.IAspectSource;
import com.leclowndu93150.thaumaturge.api.essentia.IEssentiaTransport;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.util.ProblemReporter;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.TagValueOutput;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.util.Objects;

public final class BlockEntityOriginJar extends BlockEntity implements IEssentiaTransport, IAspectSource {

    // 每种要素的存量上限：32767（short 上限，足够大又便于计算，避免 Integer.MAX_VALUE 溢出风险）。
    public static final int PER_ASPECT_AMOUNT = 32767;

    private static final Codec<AspectList> CONTENTS_CODEC = AspectList.CODEC;

    // 懒加载：首次访问时用注册表全部要素（除 infinitus）填充。
    private @Nullable AspectList contents;

    public BlockEntityOriginJar(BlockPos pos, BlockState state) {
        super(TABlockEntities.ORIGIN_JAR.get(), pos, state);
    }

    // 用注册表中除 infinitus 外全部要素各 PER_ASPECT_AMOUNT 填充。
    private AspectList ensureContents() {
        if (contents != null) {
            return contents;
        }
        if (level == null) {
            return AspectList.EMPTY;
        }
        Registry<IAspect> registry = level.registryAccess().lookup(IAspect.REGISTRY_KEY).orElse(null);
        AspectList filled = AspectList.EMPTY;
        if (registry != null) {
            for (Holder.Reference<IAspect> holder : registry.listElements().toList()) {
                if (Objects.equals(holder.unwrapKey().orElse(null), TAAspects.INFINITUS)) {
                    continue; // 排除「无限」
                }
                filled = filled.add(new AspectInstance(holder, PER_ASPECT_AMOUNT));
            }
        }
        contents = filled;
        setChanged();
        return contents;
    }

    public AspectList originContents() {
        return ensureContents();
    }

    // ---- IEssentiaTransport：只出不进 ----

    @Override
    public boolean isConnectable(Direction face) {
        return face == Direction.UP;
    }

    @Override
    public boolean canInputFrom(Direction face) {
        return false; // 只出不进
    }

    @Override
    public boolean canOutputTo(Direction face) {
        return face == Direction.UP;
    }

    @Override
    public void setSuction(@Nullable Holder<IAspect> aspect, int amount) {
    }

    @Override
    public @Nullable Holder<IAspect> getSuctionType(Direction face) {
        return null;
    }

    @Override
    public int getSuctionAmount(Direction face) {
        return 0;
    }

    @Override
    public int getMinimumSuction() {
        return 0;
    }

    @Override
    public int takeEssentia(Holder<IAspect> aspect, int amount, Direction face) {
        if (!canOutputTo(face) || aspect == null) {
            return 0;
        }
        AspectList list = ensureContents();
        int have = list.amountOf(aspect);
        int taken = Math.min(amount, have);
        if (taken <= 0) {
            return 0;
        }
        contents = list.remove(aspect, taken);
        setChanged();
        syncToClient();
        return taken;
    }

    @Override
    public int addEssentia(Holder<IAspect> aspect, int amount, Direction face) {
        return amount; // 只出不进：拒绝一切注入
    }

    @Override
    public @Nullable Holder<IAspect> getEssentiaType(Direction face) {
        return null;
    }

    @Override
    public int getEssentiaAmount(Direction face) {
        return 0;
    }

    @Override
    public int spaceFor(Holder<IAspect> aspect, Direction face) {
        return 0;
    }

    // ---- IAspectSource / IAspectContainer ----

    @Override
    public AspectList getAspects() {
        return ensureContents();
    }

    @Override
    public void setAspects(AspectList aspects) {
        // 忽略：罐子内容固定，不可被写入。
    }

    @Override
    public boolean doesContainerAccept(Holder<IAspect> aspect) {
        return false; // 不接受注入
    }

    @Override
    public int addToContainer(Holder<IAspect> aspect, int amount) {
        return amount; // 只出不进：全部退回
    }

    @Override
    public boolean takeFromContainer(Holder<IAspect> aspect, int amount) {
        AspectList list = ensureContents();
        if (list.amountOf(aspect) < amount) {
            return false;
        }
        contents = list.remove(aspect, amount);
        setChanged();
        syncToClient();
        return true;
    }

    @Override
    public boolean doesContainerContainAmount(Holder<IAspect> aspect, int amount) {
        return ensureContents().amountOf(aspect) >= amount;
    }

    @Override
    public int containerContains(Holder<IAspect> aspect) {
        return ensureContents().amountOf(aspect);
    }

    @Override
    public boolean isBlocked() {
        return false;
    }

    // ---- 序列化 / 同步 ----

    private void syncToClient() {
        if (level == null || level.isClientSide()) {
            return;
        }
        BlockState current = getBlockState();
        level.sendBlockUpdated(getBlockPos(), current, current, 3);
    }

    @Override
    protected void loadAdditional(@NonNull ValueInput input) {
        super.loadAdditional(input);
        contents = input.read("Contents", CONTENTS_CODEC).orElse(null);
    }

    @Override
    protected void saveAdditional(@NonNull ValueOutput output) {
        super.saveAdditional(output);
        if (contents != null && !contents.isEmpty()) {
            output.store("Contents", CONTENTS_CODEC, contents);
        }
    }

    @Override
    public @NonNull CompoundTag getUpdateTag(net.minecraft.core.HolderLookup.@NonNull Provider registries) {
        CompoundTag nbt = super.getUpdateTag(registries);
        try (ProblemReporter.ScopedCollector reporter =
                     new ProblemReporter.ScopedCollector(this.problemPath(), Thaumaturge.LOGGER)) {
            TagValueOutput output = TagValueOutput.createWithContext(reporter, registries);
            saveAdditional(output);
            nbt.merge(output.buildResult());
        }
        return nbt;
    }

    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }
}
