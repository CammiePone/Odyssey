package dev.cammiescorner.odyssey.common.screens;

import dev.cammiescorner.odyssey.common.registries.OdysseyScreenHandlers;
import dev.cammiescorner.odyssey.common.utils.Dialogue;
import net.minecraft.entity.ai.brain.EntityLookTarget;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.WalkTarget;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;

import java.util.List;

public class DialogueScreenHandler  extends ScreenHandler {
	public MobEntity entity;
	public List<Dialogue> dialogues;

	public DialogueScreenHandler(int syncId, List<Dialogue> dialogues) {
		super(OdysseyScreenHandlers.DIALOGUE_SCREEN_HANDLER, syncId);
		this.dialogues = dialogues;
	}

	public DialogueScreenHandler(int syncId, MobEntity entity, List<Dialogue> dialogues) {
		this(syncId, dialogues);
		this.entity = entity;
	}

	@Override
	public ItemStack quickTransfer(PlayerEntity player, int fromIndex) {
		return ItemStack.EMPTY;
	}

	@Override
	public boolean canUse(PlayerEntity player) {
		entity.getBrain().remember(MemoryModuleType.WALK_TARGET, new WalkTarget(player, entity.getMovementSpeed() * 3, 2));
		entity.getBrain().remember(MemoryModuleType.LOOK_TARGET, new EntityLookTarget(player, true));
		return player.distanceTo(entity) <= 8 && !entity.isRemoved();
	}

	@Override
	public void close(PlayerEntity player) {
		if(entity != null) {
			entity.getBrain().forget(MemoryModuleType.WALK_TARGET);
			entity.getBrain().forget(MemoryModuleType.LOOK_TARGET);
		}

		super.close(player);
	}

	public List<Dialogue> getDialogues() {
		return dialogues;
	}
}
