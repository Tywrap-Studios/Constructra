package org.tywrapstudios.constructra.client.gui.hud;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import org.tywrapstudios.constructra.api.resource.ResourceNode;

import java.util.ArrayList;
import java.util.List;

public class ResourceNodeHudRenderer {
    public void render(DrawContext context, ResourceNode<?> node) {
        MinecraftClient client = MinecraftClient.getInstance();
        int x = context.getScaledWindowWidth() / 2 + 10;
        int y = context.getScaledWindowHeight() / 2;

        Text name = node.getResource().getName();
        Text purity = node.getPurity().toText();

        List<Text> texts = new ArrayList<>();
        texts.add(name);
        texts.add(purity);

        Text miningPrompt = Text.translatable("text.constructra.prompt.mining_instruction", client.options.attackKey.getBoundKeyLocalizedText().getString())
                .formatted(Formatting.GOLD);

        if (node.isObstructed()) {
            miningPrompt = Text.translatable("text.constructra.prompt.mining_instruction", client.options.attackKey.getBoundKeyLocalizedText().getString())
                    .formatted(Formatting.GOLD)
                    .append(" ")
                    .append(Text.translatable("text.constructra.info.obstructed").formatted(Formatting.DARK_RED));
        }

        context.drawTooltip(client.textRenderer, texts, x, y);
        context.drawTooltip(client.textRenderer, miningPrompt, x, y + 30);
    }
}
