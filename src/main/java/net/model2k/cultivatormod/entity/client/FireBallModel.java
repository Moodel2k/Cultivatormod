package net.model2k.cultivatormod.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.model2k.cultivatormod.CultivatorMod;
import net.model2k.cultivatormod.entity.custom.FireballEntity;
import org.jetbrains.annotations.NotNull;

public class FireBallModel<T extends FireballEntity> extends HierarchicalModel<FireballEntity> {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation( ResourceLocation.fromNamespaceAndPath(CultivatorMod.MOD_ID, "fireball"), "main");
    private final ModelPart fireball;
    public FireBallModel(ModelPart fireball) {
        this.fireball = fireball.getChild("fireball");
    }
    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();
        PartDefinition FIreball = partdefinition.addOrReplaceChild("fireball", CubeListBuilder.create().texOffs(0, 0).addBox(-5.0F, -9.0F, -5.0F, 10.0F, 9.0F, 10.0F, new CubeDeformation(0.0F))
                .texOffs(0, 35).addBox(5.0F, -8.0F, -4.0F, 1.0F, 7.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(18, 35).addBox(-6.0F, -8.0F, -4.0F, 1.0F, 7.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));
        PartDefinition cube_r1 = FIreball.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(18, 19).addBox(0.0F, -5.0F, -4.0F, 1.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1.5708F));
        PartDefinition cube_r2 = FIreball.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(0, 19).addBox(-1.0F, -5.0F, -4.0F, 1.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, -9.0F, 0.0F, 0.0F, 0.0F, 1.5708F));
        PartDefinition cube_r3 = FIreball.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(36, 34).addBox(0.0F, -4.0F, -4.0F, 1.0F, 7.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -4.0F, 6.0F, 0.0F, 1.5708F, 0.0F));
        PartDefinition cube_r4 = FIreball.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(36, 19).addBox(0.0F, -4.0F, -4.0F, 1.0F, 7.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -4.0F, -5.0F, 0.0F, 1.5708F, 0.0F));
        return LayerDefinition.create(meshdefinition, 64, 64);
    }
    @Override
    public void setupAnim(@NotNull FireballEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
    }
    @Override
    public void renderToBuffer(@NotNull PoseStack poseStack, @NotNull VertexConsumer vertexConsumer, int packedLight, int packedOverlay, int color) {
        fireball.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
    }
    @Override
    public ModelPart root() {
        return fireball;
    }
}