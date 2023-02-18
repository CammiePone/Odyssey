package dev.cammiescorner.odyssey.mixin;

import dev.cammiescorner.odyssey.common.utils.DialogueProvider;
import net.minecraft.entity.mob.PiglinEntity;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(PiglinEntity.class)
public class PiglinEntityMixin implements DialogueProvider {
}
