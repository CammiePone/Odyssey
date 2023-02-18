package dev.cammiescorner.odyssey.common.registries;

import dev.cammiescorner.odyssey.Odyssey;
import dev.cammiescorner.odyssey.common.screens.DialogueScreenHandler;
import dev.cammiescorner.odyssey.common.utils.Dialogue;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtOps;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;

import java.util.LinkedHashMap;

public class OdysseyScreenHandlers {
	//-----Screen Handler Map-----//
	public static final LinkedHashMap<ScreenHandlerType<?>, Identifier> SCREEN_HANDLERS = new LinkedHashMap<>();

	//-----Screen Handlers-----//
	public static final ScreenHandlerType<DialogueScreenHandler> DIALOGUE_SCREEN_HANDLER = create("dialogue_screen_handler", new ExtendedScreenHandlerType<>((i, playerInventory, buf) -> new DialogueScreenHandler(i, Dialogue.CODEC.listOf().decode(NbtOps.INSTANCE, buf.readNbt().getList("Dialogues", NbtElement.COMPOUND_TYPE)).getOrThrow(false, Odyssey.LOGGER::error).getFirst())));

	//-----Registry-----//
	public static void register() {
		SCREEN_HANDLERS.keySet().forEach(type -> Registry.register(Registries.SCREEN_HANDLER_TYPE, SCREEN_HANDLERS.get(type), type));
	}

	private static <T extends ScreenHandlerType<?>> T create(String name, T type) {
		SCREEN_HANDLERS.put(type, Odyssey.id(name));
		return type;
	}
}
