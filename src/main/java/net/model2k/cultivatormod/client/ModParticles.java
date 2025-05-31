package net.model2k.cultivatormod.client;


import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.Registries;
import net.model2k.cultivatormod.CultivatorMod;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModParticles {
    public static final DeferredRegister<ParticleType<?>> PARTICLES = DeferredRegister.create(Registries.PARTICLE_TYPE, CultivatorMod.MOD_ID);

    public static final SimpleParticleType FIREBALL_PARTICLE = new SimpleParticleType(true);
    static {PARTICLES.register("fire_ball_particle", () -> FIREBALL_PARTICLE);}

    public static void register(IEventBus event) {
        PARTICLES.register(event);
    }
}