package dev.cammiescorner.odyssey.client;

import dev.cammiescorner.odyssey.client.gui.screens.DialogueScreen;
import dev.cammiescorner.odyssey.common.packets.SyncDialoguesPacket;
import dev.cammiescorner.odyssey.common.registries.OdysseyScreenHandlers;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.client.ClientModInitializer;
import org.quiltmc.qsl.networking.api.client.ClientPlayNetworking;

public class OdysseyClient implements ClientModInitializer {
	@Override
	public void onInitializeClient(ModContainer mod) {
		HandledScreens.register(OdysseyScreenHandlers.DIALOGUE_SCREEN_HANDLER, DialogueScreen::new);

		ClientPlayNetworking.registerGlobalReceiver(SyncDialoguesPacket.ID, SyncDialoguesPacket::handle);
	}
}
