package com.blackmoss.thaumicavaritia.block.infinituscollector;

public final class InfinitusCollectorSettings {
    private final int collectChance;
    private final int aspectCost;
    private final float pollution;
    private final int lossDivisor;
    private final boolean drainAura;
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

    public static Builder builder() {
        return new Builder();
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
