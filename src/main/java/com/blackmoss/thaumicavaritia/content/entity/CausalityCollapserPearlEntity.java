package com.blackmoss.thaumicavaritia.content.entity;

import com.blackmoss.thaumicavaritia.api.interfaces.IFluxRiftCollapsible;
import com.blackmoss.thaumicavaritia.registry.TAEntities;
import com.blackmoss.thaumicavaritia.registry.TAItems;
import com.leclowndu93150.thaumaturge.content.effect.Effects;
import com.leclowndu93150.thaumaturge.content.particle.ShieldSparkParticleOptions;
import committee.nova.mods.avaritia.common.entity.GapingVoidEntity;
import net.minecraft.core.Direction;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.throwableitemprojectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.NonNull;

public final class CausalityCollapserPearlEntity extends ThrowableItemProjectile {
    private static final float EXPLOSION_STRENGTH = 16.0F;
    private static final double RIFT_COLLAPSE_RANGE = 24.0;
    private static final double DAMAGE_RANGE = 32.0;
    private static final float DAMAGE_AMOUNT = 32.0F;
    // 拖尾参数（同悖论物质）
    private static final int TRAIL_STEPS = 3;
    private static final float TRAIL_JITTER = 0.0125F;
    private static final float MOTE_ALPHA = 0.5F;
    private static final float MOTE_SCALE = 4.0F;
    private static final int SPARK_AGE = 8;
    private static final float SPARK_ALPHA = 0.7F;
    private static final float SPARK_SCALE = 0.3F;
    private static final double SPARK_SPREAD = 0.2;

    public CausalityCollapserPearlEntity(EntityType<? extends CausalityCollapserPearlEntity> type, Level level) {
        super(type, level);
    }

    public CausalityCollapserPearlEntity(Level level, LivingEntity shooter, ItemStack stack) {
        super(TAEntities.CAUSALITY_COLLAPSER_PEARL.get(), shooter, level, stack);
    }

    @Override
    protected @NonNull Item getDefaultItem() {
        return TAItems.CAUSALITY_COLLAPSER_PEARL.get();
    }

    // 拖尾特效：复用悖论物质的火尘 + 护盾火花粒子，沿轨迹插值生成。
    @Override
    public void tick() {
        super.tick();
        if (this.level().isClientSide()) {
            for (int i = 0; i < TRAIL_STEPS; i++) {
                double coeff = (double) i / TRAIL_STEPS;
                this.level()
                        .addParticle(
                                Effects.fireMoteData(
                                        this.random,
                                        TRAIL_JITTER * (this.random.nextFloat() - 0.5F),
                                        TRAIL_JITTER * (this.random.nextFloat() - 0.5F),
                                        TRAIL_JITTER * (this.random.nextFloat() - 0.5F),
                                        0.8F + this.random.nextFloat() * 0.2F,
                                        0.3F + this.random.nextFloat() * 0.1F,
                                        this.random.nextFloat() * 0.1F,
                                        MOTE_ALPHA,
                                        MOTE_SCALE),
                                this.xOld + (this.getX() - this.xOld) * coeff,
                                this.yOld + (this.getY() - this.yOld) * coeff + this.getBbHeight() / 2.0F,
                                this.zOld + (this.getZ() - this.zOld) * coeff,
                                0.0,
                                0.0,
                                0.0);
                this.level()
                        .addParticle(
                                new ShieldSparkParticleOptions(0xFFFFFF, SPARK_ALPHA, SPARK_SCALE, SPARK_AGE, false),
                                this.getX() + this.random.nextGaussian() * SPARK_SPREAD,
                                this.getY() + this.random.nextGaussian() * SPARK_SPREAD,
                                this.getZ() + this.random.nextGaussian() * SPARK_SPREAD,
                                0.0,
                                0.0,
                                0.0);
            }
        }
    }

    @Override
    protected void onHit(@NonNull HitResult hitResult) {
        super.onHit(hitResult);
        if (this.level().isClientSide()) {
            return;
        }
        // 裂隙坍缩（悖论物质原 3.0 → 24.0），与黑洞生命周期同步：在黑洞结束前以相同比例速度消亡。
        // 通过本 mod 接口触发，不直接依赖 Thaumaturge content。
        for (Entity entity : this.level()
                .getEntities(this, this.getBoundingBox()
                        .inflate(RIFT_COLLAPSE_RANGE), e -> e instanceof IFluxRiftCollapsible)) {
            ((IFluxRiftCollapsible) entity).thaumicavaritia$collapse(GapingVoidEntity.maxLifetime);
        }
        // 范围伤害（终望珍珠加强：8 格内 20 点）。
        Entity owner = this.getOwner();
        LivingEntity shooter = owner instanceof LivingEntity living
                ? living
                : null;
        DamageSource source = shooter != null
                ? this.damageSources().indirectMagic(this, shooter)
                : this.damageSources().magic();
        for (LivingEntity target : this.level()
                .getEntitiesOfClass(LivingEntity.class, this.getBoundingBox().inflate(DAMAGE_RANGE))) {
            if (target != shooter) {
                target.hurt(source, DAMAGE_AMOUNT);
            }
        }
        // 加强爆炸（悖论物质原 2.0 → 4.0，破坏地形）。
        this.level()
                .explode(
                        this,
                        this.getX(),
                        this.getY(),
                        this.getZ(),
                        EXPLOSION_STRENGTH,
                        Level.ExplosionInteraction.BLOCK);
        // 落点黑洞：复用终望珍珠的 GapingVoidEntity（自带黑洞着色器渲染 + 吞噬效果）。
        GapingVoidEntity gapingVoid = shooter != null
                ? new GapingVoidEntity(this.level(), shooter)
                : new GapingVoidEntity(this.level());
        if (shooter != null) {
            gapingVoid.setUser(shooter);
        }
        Vec3 voidPos = hitResult.getLocation();
        if (hitResult instanceof net.minecraft.world.phys.BlockHitResult blockHit) {
            Direction hitDirection = blockHit.getDirection();
            voidPos = new Vec3(
                    blockHit.getLocation().x + hitDirection.getStepX() * 0.25,
                    blockHit.getLocation().y + hitDirection.getStepY() * 0.25,
                    blockHit.getLocation().z + hitDirection.getStepZ() * 0.25);
        }
        gapingVoid.moveOrInterpolateTo(voidPos, this.getYRot(), 0.0F);
        this.level().addFreshEntity(gapingVoid);
        this.discard();
    }
}
