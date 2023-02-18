package dev.cammiescorner.odyssey.common.utils;

import dev.cammiescorner.odyssey.Odyssey;
import dev.cammiescorner.odyssey.common.screens.DialogueScreenHandler;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtOps;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

public interface DialogueProvider {
	default void openDialogue(PlayerEntity player, MobEntity dialogueProvider) {
		player.openHandledScreen(new ExtendedScreenHandlerFactory() {
			@Override
			public void writeScreenOpeningData(ServerPlayerEntity player, PacketByteBuf buf) {
				NbtCompound tag = new NbtCompound();
				tag.put("Dialogues", Dialogue.CODEC.listOf().encodeStart(NbtOps.INSTANCE, Odyssey.DIALOGUES.get(dialogueProvider.getType())).getOrThrow(false, Odyssey.LOGGER::error));
				buf.writeNbt(tag);
			}

			@Override
			public Text getDisplayName() {
				return dialogueProvider.getName();
			}

			@Override
			public ScreenHandler createMenu(int i, PlayerInventory playerInventory, PlayerEntity playerEntity) {
				return new DialogueScreenHandler(i, dialogueProvider, Odyssey.DIALOGUES.get(dialogueProvider.getType()));
			}
		});
	}
}
