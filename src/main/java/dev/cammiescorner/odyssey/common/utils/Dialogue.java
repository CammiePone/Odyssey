package dev.cammiescorner.odyssey.common.utils;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.Identifier;

import java.util.List;

public record Dialogue(Identifier prerequisite, List<DialogueOption> options) {
	public static final Codec<Dialogue> CODEC = RecordCodecBuilder.create(dialogueInstance -> dialogueInstance.group(
			Identifier.CODEC.optionalFieldOf("prerequisite", null).forGetter(Dialogue::prerequisite),
			DialogueOption.CODEC.listOf().fieldOf("dialogue_options").forGetter(Dialogue::options)
	).apply(dialogueInstance, Dialogue::new));
}
