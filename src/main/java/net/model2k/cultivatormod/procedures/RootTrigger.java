package net.model2k.cultivatormod.procedures;

import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.AdvancementProgress;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;

public class RootTrigger {
    public static void execute(Entity entity) {
        if (entity instanceof ServerPlayer player) {
            AdvancementHolder root = player.server.getAdvancements().get(ResourceLocation.parse("cultivatormod:cultivatormodtab/root"));
            if (root != null) {
                AdvancementProgress ap = player.getAdvancements().getOrStartProgress(root);
                if (!ap.isDone()) {
                    for (String criteria : ap.getRemainingCriteria())
                        player.getAdvancements().award(root, criteria);

                }
            }
        }
    }
}
