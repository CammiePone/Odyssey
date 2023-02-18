package dev.cammiescorner.odyssey;

import dev.cammiescorner.odyssey.common.data.DialogueReloadListener;
import dev.cammiescorner.odyssey.common.registries.OdysseyScreenHandlers;
import dev.cammiescorner.odyssey.common.utils.Dialogue;
import dev.cammiescorner.odyssey.common.utils.DialogueProvider;
import net.fabricmc.fabric.api.event.player.UseEntityCallback;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.resource.ResourceType;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.ModInitializer;
import org.quiltmc.qsl.resource.loader.api.ResourceLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Odyssey implements ModInitializer {
	public static final String MOD_ID = "odyssey";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	public static final Map<EntityType<?>, List<Dialogue>> DIALOGUES = new HashMap<>();

	@Override
	public void onInitialize(ModContainer mod) {
		OdysseyScreenHandlers.register();
		ResourceLoader.get(ResourceType.SERVER_DATA).registerReloader(new DialogueReloadListener());

		UseEntityCallback.EVENT.register((player, world, hand, entity, hitResult) -> {
			if(entity instanceof MobEntity mob && mob instanceof DialogueProvider provider) {
				provider.openDialogue(player, mob);
				return ActionResult.success(world.isClient());
			}

			return ActionResult.CONSUME;
		});
	}

	public static Identifier id(String name) {
		return new Identifier(MOD_ID, name);
	}

	public static MutableText translate(@Nullable String prefix, String... value) {
		String translationKey = MOD_ID + "." + String.join(".", value);
		return Text.translatable(prefix != null ? (prefix + "." + translationKey) : translationKey);
	}
}
