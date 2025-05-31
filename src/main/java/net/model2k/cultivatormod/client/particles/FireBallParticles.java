package net.model2k.cultivatormod.client.particles;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
public class FireBallParticles extends TextureSheetParticle {
    public static FireBallParticlesProvider provider(SpriteSet spriteSet) {
        return new FireBallParticlesProvider(spriteSet);
    }
    public static class FireBallParticlesProvider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet spriteSet;
        public FireBallParticlesProvider(SpriteSet spriteSet) {
            this.spriteSet = spriteSet;
        }
        public Particle createParticle(SimpleParticleType typeIn, ClientLevel worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            return new FireBallParticles(worldIn, x, y, z, xSpeed, ySpeed, zSpeed, this.spriteSet);
        }
    }
    private final SpriteSet spriteSet;
    protected FireBallParticles(ClientLevel world, double x, double y, double z, double vx, double vy, double vz, SpriteSet spriteSet) {
        super(world, x, y, z);
        this.spriteSet = spriteSet;
        this.setSize(0.2f, 0.2f);
        this.lifetime = 7;
        this.gravity = 0f;
        this.hasPhysics = true;
        this.xd = vx * 1;
        this.yd = vy * 1;
        this.zd = vz * 1;
        this.pickSprite(spriteSet);
    }
    @Override
    public int getLightColor(float partialTick) {
        return 15728880;
    }
    @Override
    public @NotNull ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
    }
    @Override
    public void tick() {
        super.tick();
    }
}