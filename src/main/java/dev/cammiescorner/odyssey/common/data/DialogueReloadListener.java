package dev.cammiescorner.odyssey.common.data;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.serialization.JsonOps;
import dev.cammiescorner.odyssey.Odyssey;
import dev.cammiescorner.odyssey.common.utils.Dialogue;
import net.minecraft.entity.EntityType;
import net.minecraft.registry.Registries;
import net.minecraft.resource.JsonDataLoader;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.profiler.Profiler;
import org.jetbrains.annotations.NotNull;
import org.quiltmc.qsl.resource.loader.api.reloader.IdentifiableResourceReloader;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class DialogueReloadListener extends JsonDataLoader implements IdentifiableResourceReloader {
	public DialogueReloadListener() {
		super(new GsonBuilder().create(), "odyssey_dialogue");
	}

	@Override
	protected void apply(Map<Identifier, JsonElement> prepared, ResourceManager manager, Profiler profiler) {
		Odyssey.DIALOGUES.clear();

		for(Map.Entry<Identifier, JsonElement> entry : prepared.entrySet()) {
			Identifier identifier = entry.getKey();
			JsonElement jsonElement = entry.getValue();
			EntityType<?> entityType = Registries.ENTITY_TYPE.get(identifier);
			JsonObject json = JsonHelper.asObject(jsonElement, "odyssey_dialogue");
			JsonArray array = JsonHelper.getArray(json, "dialogue");

			Optional<List<Dialogue>> optional = Dialogue.CODEC.listOf().parse(JsonOps.INSTANCE, array).resultOrPartial(Odyssey.LOGGER::error);

			optional.ifPresent(dialogues -> Odyssey.DIALOGUES.put(entityType, dialogues));
		}
	}

	@Override
	public @NotNull Identifier getQuiltId() {
		return Odyssey.id("odyssey_dialogue");
	}
}
