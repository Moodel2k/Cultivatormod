package net.model2k.cultivatormod.entity.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.ItemSupplier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.model2k.cultivatormod.client.ModParticles;
import net.model2k.cultivatormod.entity.ModEntities;
import net.model2k.cultivatormod.network.packet.PlayerStatsClient;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import javax.annotation.Nullable;

public class FireballEntity extends AbstractArrow implements ItemSupplier {
    public static final ItemStack PROJECTILE_ITEM = new ItemStack(Items.STICK);
    private int knockback = 0;
    public FireballEntity(EntityType<? extends FireballEntity> type, Level level) {
        super(type, level);
    }
    public FireballEntity(EntityType<? extends FireballEntity> type, double x, double y, double z, Level world, @Nullable ItemStack firedFromWeapon) {
        super(type, x, y, z, world, PROJECTILE_ITEM, firedFromWeapon);
        if (firedFromWeapon != null)
            setKnockback(EnchantmentHelper.getItemEnchantmentLevel(world.registryAccess().lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(Enchantments.KNOCKBACK), firedFromWeapon));
    }
    public FireballEntity(EntityType<? extends FireballEntity> type, LivingEntity entity, Level world, @Nullable ItemStack firedFromWeapon) {
        super(type, entity, world, PROJECTILE_ITEM, firedFromWeapon);
        if (firedFromWeapon != null)
            setKnockback(EnchantmentHelper.getItemEnchantmentLevel(world.registryAccess().lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(Enchantments.KNOCKBACK), firedFromWeapon));
    }
    @Override
    @OnlyIn(Dist.CLIENT)
    public @NotNull ItemStack getItem() {
        return PROJECTILE_ITEM;
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
    public void onHitBlock(@NotNull BlockHitResult blockHitResult) {
        super.onHitBlock(blockHitResult);
        if (!this.level().isClientSide) {
            BlockPos pos = blockHitResult.getBlockPos();
            Level level = this.level();
            level.destroyBlock(pos, true, this);
            this.discard();
        }
    }
    @Override
    public void tick() {
        if (!this.level().isClientSide) {
            if (this.inGround) {
                this.discard();
            }
        } else {
            this.level().addParticle(
                    ModParticles.FIREBALL_PARTICLE,
                    this.getX(),
                    this.getY(),
                    this.getZ(),
                    0.0, 0.0, 0.0
            );
        }
        super.tick();
    }
    public static void  shoot(LivingEntity entity, LivingEntity target) {
        FireballEntity entityarrow = new FireballEntity(ModEntities.FIREBALL.get(), entity, entity.level(), null);
        double dx = target.getX() - entity.getX();
        double dy = target.getY() + target.getEyeHeight() - 1.1;
        double dz = target.getZ() - entity.getZ();
        entityarrow.shoot(dx, dy - entityarrow.getY() + Math.hypot(dx, dz) * 0.2F, dz, 0.5f * 2, 12.0F);
        entityarrow.setSilent(true);
        entityarrow.setBaseDamage((double) PlayerStatsClient.getMaxQi() /10);
        entityarrow.setKnockback(1);
        entity.level().addFreshEntity(entityarrow);
        entity.level().playSound(null, entity.getX(), entity.getY(), entity.getZ(), BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("item.firecharge.use")), SoundSource.PLAYERS, 1, 1f / (RandomSource.create().nextFloat() * 0.5f + 1));
    }
}