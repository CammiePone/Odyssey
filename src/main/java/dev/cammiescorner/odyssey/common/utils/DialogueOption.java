package dev.cammiescorner.odyssey.common.utils;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.dynamic.Codecs;

import java.util.Optional;

public record DialogueOption(Text text, Text response, Optional<Identifier> reward, boolean closeDialogue) {
	public static final Codec<DialogueOption> CODEC = RecordCodecBuilder.create(dialogueOptionInstance -> dialogueOptionInstance.group(
			Codecs.f_pvdffibb.fieldOf("text").forGetter(DialogueOption::text),
			Codecs.f_pvdffibb.fieldOf("response").forGetter(DialogueOption::response),
			Identifier.CODEC.optionalFieldOf("grants").forGetter(DialogueOption::reward),
			Codec.BOOL.optionalFieldOf("close_dialogue", false).forGetter(DialogueOption::closeDialogue)
	).apply(dialogueOptionInstance, DialogueOption::new));
}
