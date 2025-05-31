package net.model2k.cultivatormod.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.model2k.cultivatormod.CultivatorMod;
import net.model2k.cultivatormod.entity.custom.FireballEntity;
import org.jetbrains.annotations.NotNull;

public class FireBallRenderer extends EntityRenderer<FireballEntity> {
    private static final ResourceLocation texture = ResourceLocation.fromNamespaceAndPath(CultivatorMod.MOD_ID,"textures/entity/fireball/fireball.png");
    private final FireBallModel model;
    public FireBallRenderer(EntityRendererProvider.Context context) {
        super(context);
        model = new FireBallModel(context.bakeLayer(FireBallModel.LAYER_LOCATION));
    }
    @Override
    public void render(@NotNull FireballEntity entityIn, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource bufferIn, int packedLightIn) {
        VertexConsumer vb = bufferIn.getBuffer(RenderType.entityCutout(this.getTextureLocation(entityIn)));
        poseStack.pushPose();
        poseStack.mulPose(Axis.YP.rotationDegrees(20+Mth.lerp(partialTicks, entityIn.yRotO, entityIn.getYRot())));
        poseStack.mulPose(Axis.ZP.rotationDegrees( 20+Mth.lerp(partialTicks, entityIn.xRotO, entityIn.getXRot())));
        model.renderToBuffer(poseStack, vb, packedLightIn, OverlayTexture.NO_OVERLAY);
        poseStack.popPose();
        super.render(entityIn, entityYaw, partialTicks, poseStack, bufferIn, packedLightIn);
    }
    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull FireballEntity entity) {
        return texture;
    }
}