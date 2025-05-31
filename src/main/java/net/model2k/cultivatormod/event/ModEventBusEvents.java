package net.model2k.cultivatormod.event;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.client.resources.PlayerSkin;
import net.minecraft.world.entity.EntityType;
import net.model2k.cultivatormod.CultivatorMod;
import net.model2k.cultivatormod.client.ModParticles;
import net.model2k.cultivatormod.client.particles.FireBallParticles;
import net.model2k.cultivatormod.entity.ModEntities;
import net.model2k.cultivatormod.entity.client.*;
import net.model2k.cultivatormod.entity.custom.SlimeEntity;
import net.model2k.cultivatormod.entity.custom.YangBearEntity;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;

@EventBusSubscriber(modid = CultivatorMod.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class ModEventBusEvents {
    @SubscribeEvent
    public static void registerLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(MindlessSlimeModel.LAYER_LOCATION, MindlessSlimeModel::createBodyLayer);
        event.registerLayerDefinition(YangBearModel.LAYER_LOCATION, YangBearModel::createBodyLayer);
        event.registerLayerDefinition(QiSlashModel.LAYER_LOCATION, QiSlashModel::createBodyLayer);
        event.registerLayerDefinition(FireBallModel.LAYER_LOCATION, FireBallModel::createBodyLayer);

    }
    @SubscribeEvent
    public static void registerLayerDefinitions(EntityRenderersEvent.AddLayers event) {
        EntityRenderDispatcher renderDispatcher = Minecraft.getInstance().getEntityRenderDispatcher();
        PlayerRenderer defaultRenderer = event.getSkin(PlayerSkin.Model.SLIM);
        if (defaultRenderer != null) {
            defaultRenderer.addLayer(new TransparentPlayerLayer(defaultRenderer));
        }
        PlayerRenderer slimRenderer = event.getSkin(PlayerSkin.Model.SLIM);
        if (slimRenderer != null) {
            slimRenderer.addLayer(new TransparentPlayerLayer(slimRenderer));
        }
    }
    @SubscribeEvent
    public static void registerParticleFactories(RegisterParticleProvidersEvent event) {
        event.registerSpriteSet(ModParticles.FIREBALL_PARTICLE, FireBallParticles::provider);
    }
    @SubscribeEvent
    public static void onRegisterRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(EntityType.ZOMBIE, CustomZombieRenderer::new);
    }
    @SubscribeEvent
    public static void registerAttributes(EntityAttributeCreationEvent event) {
        event.put(ModEntities.MINDLESS_SLIME.get(), SlimeEntity.createAttributes().build());
        event.put(ModEntities.YANG_BEAR.get(), YangBearEntity.createAttributes().build());
    }
}