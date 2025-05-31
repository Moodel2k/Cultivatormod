package net.model2k.cultivatormod.entity.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.ItemSupplier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

public class QiSlashEntity extends AbstractArrow implements ItemSupplier {
    private int knockback = 1;
    private int tick = 0;
    public QiSlashEntity(EntityType<? extends QiSlashEntity> type, Level level) {
        super(type, level);
    }
    @Override
    @OnlyIn(Dist.CLIENT)
    public @NotNull ItemStack getItem() {
        return null;
    }
    @Override
    protected @NotNull ItemStack getDefaultPickupItem() {
        return null;
    }
    @Override
    protected void doPostHurtEffects(@NotNull LivingEntity entity) {
        super.doPostHurtEffects(entity);
    }
    public void setKnockback(int knockback) {
        this.knockback = knockback;
    }
    @Override
    protected void doKnockback(@NotNull LivingEntity livingEntity, @NotNull DamageSource damageSource) {
        if (knockback > 0.0) {
            double d1 = Math.max(0.0, 1.0 - livingEntity.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE));
            Vec3 vec3 = this.getDeltaMovement().multiply(1.0, 0.0, 1.0).normalize().scale(knockback * 0.6 * d1);
            if (vec3.lengthSqr() > 0.0) {
                livingEntity.push(vec3.x, 0.1, vec3.z);
            }
        } else {
            super.doKnockback(livingEntity, damageSource);
        }
    }
    @Override
    public boolean isNoGravity() {
        return true;
    }
    @Override
    public void onHitBlock(@NotNull BlockHitResult blockHitResult) {
        super.onHitBlock(blockHitResult);
        if (!this.level().isClientSide) {
            Level level = this.level();
            BlockPos center = blockHitResult.getBlockPos();
            level.destroyBlock(center, false, this);
            this.discard();
        }
    }
    @Override
    protected void onHitEntity(@NotNull EntityHitResult result) {
        super.onHitEntity(result);

        if (!this.level().isClientSide) {
            Entity target = result.getEntity();
            Entity owner = this.getOwner();
            DamageSource source = owner != null
                    ? level().damageSources().indirectMagic(this, owner)
                    : level().damageSources().magic();
            if (target instanceof LivingEntity living) {
                this.doPostHurtEffects(living);
            }
            this.discard();
        }
    }

    @Override
    public void tick() {
        tick++;
        super.tick();
        if( tick >= 100){
            this.discard();
            tick = 0;
        }
        else if (this.inGround )
            this.discard();
    }
}