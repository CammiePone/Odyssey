package dev.cammiescorner.odyssey.client.gui.screens;

import com.mojang.blaze3d.systems.RenderSystem;
import dev.cammiescorner.odyssey.Odyssey;
import dev.cammiescorner.odyssey.client.gui.widgets.DialogueOptionWidget;
import dev.cammiescorner.odyssey.common.screens.DialogueScreenHandler;
import dev.cammiescorner.odyssey.common.utils.Dialogue;
import dev.cammiescorner.odyssey.common.utils.DialogueOption;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;

public class DialogueScreen  extends HandledScreen<DialogueScreenHandler> {
	public static final Identifier BACKGROUND = Odyssey.id("textures/gui/dialogue_box.png");
	private final Text entityName;

	public DialogueScreen(DialogueScreenHandler handler, PlayerInventory inventory, Text title) {
		super(handler, inventory, Text.empty());
		entityName = MutableText.create(title.asComponent()).formatted(Formatting.UNDERLINE);
	}

	@Override
	protected void init() {
		super.init();
		x = (width - 256) / 2;
		y = (height - 64) - 4;
		playerInventoryTitleY = -10000;

		for(int size = handler.getDialogues().size(); size > 0; size--) {
			Dialogue dialogue = handler.getDialogues().get(size - 1);
			int yOffset = 0;

			for(int i = 0; i < dialogue.options().size(); i++) {
				DialogueOption option = dialogue.options().get(i);
				DialogueOptionWidget widget = new DialogueOptionWidget(x + 3, y + 3 + yOffset, option.text(), buttonWidget -> {
					if(option.closeDialogue())
						closeScreen();
				});
				addDrawableChild(widget);
				yOffset += widget.getHeight() + 2;
			}
		}
	}

	@Override
	protected void drawBackground(MatrixStack matrices, float delta, int mouseX, int mouseY) {
		RenderSystem.setShader(GameRenderer::getPositionTexShader);
		RenderSystem.setShaderColor(1F, 1F, 1F, 1F);
		RenderSystem.setShaderTexture(0, BACKGROUND);
		DrawableHelper.drawTexture(matrices, x, y, 0, 0, 256, 64, 256, 256);
	}

	@Override
	protected void drawForeground(MatrixStack matrices, int mouseX, int mouseY) {
		super.drawForeground(matrices, mouseX, mouseY);
		drawTextWithShadow(matrices, textRenderer, entityName, 3, -10, 0xffffff);

		//		TODO wizard dialogue
		// 		clearChildren();
		//		List<OrderedText> lines = textRenderer.wrapLines(Text.literal("I am Guybrush Threepwood, Grand Wizard of the realm! Do you truly wish to learn more about the arcane?"), 244);
		//
		//		for(int i = 0; i < lines.size(); i++) {
		//			OrderedText text = lines.get(i);
		//			textRenderer.drawWithShadow(matrices, text, 6, 6 + (10 * i), 0xffffff);
		//		}
		//
		//		RenderSystem.setShader(GameRenderer::getPositionTexShader);
		//		RenderSystem.setShaderColor(1F, 1F, 1F, 1F);
		//		RenderSystem.setShaderTexture(0, BACKGROUND);
		//		DrawableHelper.drawTexture(matrices, 248, client.world.getTime() / 10 % 2 == 0 ? 55 : 54, 0, 176, 5, 4, 256, 256);
	}

	@Override
	public boolean isPauseScreen() {
		return false;
	}
}
