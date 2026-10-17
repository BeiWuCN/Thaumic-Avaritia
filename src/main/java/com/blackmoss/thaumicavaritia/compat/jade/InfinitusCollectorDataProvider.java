package com.blackmoss.thaumicavaritia.compat.jade;

import com.blackmoss.thaumicavaritia.ThaumicAvaritia;
import com.blackmoss.thaumicavaritia.block.InfinitusCollectorBlockEntity;
import com.leclowndu93150.thaumaturge.api.aspect.AspectInstance;
import com.leclowndu93150.thaumaturge.api.aspect.AspectList;
import com.leclowndu93150.thaumaturge.api.aspect.IAspect;
import net.minecraft.core.Holder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.resources.Identifier;
import org.jspecify.annotations.NonNull;
import snownee.jade.api.BlockAccessor;
import snownee.jade.api.IServerDataProvider;

public enum InfinitusCollectorDataProvider implements IServerDataProvider<BlockAccessor> {
    INSTANCE;

    public static final Identifier UID = ThaumicAvaritia.identifier("infinitus_collector");

    @Override
    public @NonNull Identifier getUid() {
        return UID;
    }

    @Override
    public void appendServerData(@NonNull CompoundTag tag, @NonNull BlockAccessor accessor) {
        if (!(accessor.getBlockEntity() instanceof InfinitusCollectorBlockEntity be)) {
            return;
        }
        tag.putBoolean("formed", be.isFormed());
        tag.putInt("progress", be.getProgress());
        tag.putInt("production_ticks", be.getProductionTicks());
        if (be.getNodeData() != null) {
            tag.putString("node_type", be.getNodeData().type().getSerializedName());
            if (be.getNodeData().modifier().isPresent()) {
                tag.putString("node_modifier", be.getNodeData().modifier().get().getSerializedName());
            }
            AspectList aspects = be.getNodeData().aspects();
            if (aspects != null && !aspects.isEmpty()) {
                ListTag aspectsList = new ListTag();
                for (AspectInstance entry : aspects.entries()) {
                    CompoundTag entryTag = new CompoundTag();
                    Holder<IAspect> holder = entry.aspect();
                    String aspectId = holder.unwrapKey()
                            .map(key -> key.identifier().toString())
                            .orElse("");
                    entryTag.putString("id", aspectId);
                    entryTag.putInt("amount", entry.amount());
                    aspectsList.add(entryTag);
                }
                tag.put("node_aspects", aspectsList);
            }
        }
        AspectList remainingAspects = be.getRemainingAspects();
        if (remainingAspects != null && !remainingAspects.isEmpty()) {
            ListTag aspectsList = new ListTag();
            for (AspectInstance entry : remainingAspects.entries()) {
                CompoundTag entryTag = new CompoundTag();
                Holder<IAspect> holder = entry.aspect();
                String aspectId = holder.unwrapKey()
                        .map(key -> key.identifier().toString())
                        .orElse("");
                entryTag.putString("id", aspectId);
                entryTag.putInt("amount", entry.amount());
                aspectsList.add(entryTag);
            }
            tag.put("remaining_aspects", aspectsList);
        }
    }
}