package com.blackmoss.thaumicavaritia.block.infinituscollector;

public final class InfinitusCollectorSettings {
    // 收集概率分母：每 tick 有 1/collectChance 概率累积 1 点原始无限（越大越慢）。
    private final int collectChance;
    // 每次收集消耗的"节点灵气"量（>0 时耗尽节点本钱即停机）。
    private final int aspectCost;
    // 每次收集的污染值。
    private final float pollution;
    // 产出流失除数：值越大流失越慢。
    private final int lossDivisor;
    // 是否从区块灵气抽能（用于稳定，不再消耗节点本钱）。
    private final boolean drainAura;
    // 是否自给永动（不耗节点、不耗区块、无污染、不流失、不噎住）。
    private final boolean selfPowered;

    private InfinitusCollectorSettings(int collectChance, int aspectCost, float pollution,
            int lossDivisor, boolean drainAura, boolean selfPowered) {
        this.collectChance = collectChance;
        this.aspectCost = aspectCost;
        this.pollution = pollution;
        this.lossDivisor = lossDivisor;
        this.drainAura = drainAura;
        this.selfPowered = selfPowered;
    }

    public int collectChance() {
        return collectChance;
    }

    public int aspectCost() {
        return aspectCost;
    }

    public float pollution() {
        return pollution;
    }

    public int lossDivisor() {
        return lossDivisor;
    }

    public boolean drainAura() {
        return drainAura;
    }

    public boolean selfPowered() {
        return selfPowered;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private int collectChance = 200;
        private int aspectCost = 0;
        private float pollution = 0.0F;
        private int lossDivisor = 1;
        private boolean drainAura = false;
        private boolean selfPowered = false;

        private Builder() {
        }

        public Builder collectChance(int value) {
            this.collectChance = value;
            return this;
        }

        public Builder aspectCost(int value) {
            this.aspectCost = value;
            return this;
        }

        public Builder pollution(float value) {
            this.pollution = value;
            return this;
        }

        public Builder lossDivisor(int value) {
            this.lossDivisor = value;
            return this;
        }

        public Builder drainAura(boolean value) {
            this.drainAura = value;
            return this;
        }

        public Builder selfPowered(boolean value) {
            this.selfPowered = value;
            return this;
        }

        public InfinitusCollectorSettings build() {
            return new InfinitusCollectorSettings(collectChance, aspectCost, pollution, lossDivisor, drainAura, selfPowered);
        }
    }
}
