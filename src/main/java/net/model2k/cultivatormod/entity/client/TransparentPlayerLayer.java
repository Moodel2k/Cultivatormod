
package net.model2k.cultivatormod.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import org.jetbrains.annotations.NotNull;

public class TransparentPlayerLayer extends RenderLayer<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>> {

    private static final float TRANSPARENCY_LEVEL = 0.5F;

    public TransparentPlayerLayer(RenderLayerParent<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>> parent) {
        super(parent);
    }

    @Override
    public void render(@NotNull PoseStack poseStack, @NotNull MultiBufferSource buffer, int packedLight, @NotNull AbstractClientPlayer player, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        RenderType renderType = RenderType.entityTranslucent(player.getSkin().texture());
        VertexConsumer vertexConsumer = buffer.getBuffer(renderType);
        VertexConsumer transparentConsumer = new TransparentVertexConsumer(vertexConsumer, TRANSPARENCY_LEVEL);
        PlayerModel<AbstractClientPlayer> model = this.getParentModel();
        model.setupAnim(player, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
        model.renderToBuffer(poseStack, transparentConsumer, packedLight, OverlayTexture.NO_OVERLAY, 1);
    }
    public static float getTransparencyLevel() {
        return TRANSPARENCY_LEVEL;
    }
    private static class TransparentVertexConsumer implements VertexConsumer {
        private final VertexConsumer delegate;
        private final float alpha;

        public TransparentVertexConsumer(VertexConsumer delegate, float alpha) {
            this.delegate = delegate;
            this.alpha = alpha;
        }

        @Override
        public @NotNull VertexConsumer addVertex(float x, float y, float z) {
            return delegate.addVertex(x, y, z);
        }

        @Override
        public @NotNull VertexConsumer setColor(int red, int green, int blue, int alpha) {
            // Apply our fixed transparency level
            int alphaValue = (int)(this.alpha * 255);
            return delegate.setColor(red, green, blue, alphaValue);
        }

        @Override
        public @NotNull VertexConsumer setColor(float red, float green, float blue, float alpha) {
            // Apply our fixed transparency level
            return delegate.setColor(red, green, blue, this.alpha);
        }

        @Override
        public @NotNull VertexConsumer setUv(float u, float v) {
            return delegate.setUv(u, v);
        }

        @Override
        public @NotNull VertexConsumer setUv1(int u, int v) {
            return delegate.setUv1(u, v);
        }

        @Override
        public @NotNull VertexConsumer setUv2(int u, int v) {
            return delegate.setUv2(u, v);
        }

        @Override
        public @NotNull VertexConsumer setNormal(float normalX, float normalY, float normalZ) {
            return delegate.setNormal(normalX, normalY, normalZ);
        }
    }
}