package net.model2k.cultivatormod.procedures;

import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.AdvancementProgress;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.model2k.cultivatormod.item.ModItems;

public class BaodingBallsTrigger {
    public static void execute(Entity entity) {
        if (entity instanceof ServerPlayer player) {
            if(player.getInventory().contains(ModItems.BAODING_BALLS.toStack())){
            AdvancementHolder adv = player.server.getAdvancements().get(ResourceLocation.parse("cultivatormod:cultivatormodtab/baoding_balls"));
            if (adv != null) {
                AdvancementProgress ap = player.getAdvancements().getOrStartProgress(adv);
                if (!ap.isDone()) {
                    for (String criteria : ap.getRemainingCriteria())
                        player.getAdvancements().award(adv, criteria);
                    }
                }
            }
        }
    }
}
