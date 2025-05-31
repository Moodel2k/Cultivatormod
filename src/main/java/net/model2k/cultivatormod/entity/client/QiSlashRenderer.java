package net.model2k.cultivatormod.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.model2k.cultivatormod.CultivatorMod;
import net.model2k.cultivatormod.entity.custom.QiSlashEntity;
import org.jetbrains.annotations.NotNull;

public class QiSlashRenderer extends EntityRenderer<QiSlashEntity> {
    private static final ResourceLocation TEXTURE =  ResourceLocation.fromNamespaceAndPath(CultivatorMod.MOD_ID, "textures/entity/qi_slash/qi_slash.png");
    private final QiSlashModel model;
    public QiSlashRenderer(EntityRendererProvider.Context context) {
        super(context);
        model = new QiSlashModel(context.bakeLayer(QiSlashModel.LAYER_LOCATION));
    }
    @Override
    public void render(@NotNull QiSlashEntity entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
        poseStack.pushPose();
        poseStack.translate(0, -1.5, 0); // Offset if needed
        poseStack.mulPose(this.entityRenderDispatcher.cameraOrientation());
        var vertexConsumer = bufferSource.getBuffer(model.renderType(getTextureLocation(entity)));
        model.renderToBuffer(poseStack, vertexConsumer, packedLight, OverlayTexture.NO_OVERLAY,
                1);
        poseStack.popPose();
    }
    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull QiSlashEntity entity) {
        return TEXTURE;
    }
}