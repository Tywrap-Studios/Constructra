/*
 * MIT License
 *
 * Copyright (c) 2025 Tywrap Studios;
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package org.tywrapstudios.constructra.client.gui.hud;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import org.tywrapstudios.constructra.Constructra;
import org.tywrapstudios.constructra.api.resource.ResourceNode;

import java.util.ArrayList;
import java.util.List;

@Environment(EnvType.CLIENT)
public class ResourceNodeHudRenderer {
    protected static final Identifier TEXTURE = Constructra.id("science_tooltip");

    public void render(DrawContext context, ResourceNode<?> node) {
        MinecraftClient client = MinecraftClient.getInstance();
        int x = context.getScaledWindowWidth() / 2 + 10;
        int y = context.getScaledWindowHeight() / 2;

        Text name = node.getResource().getName();
        Text purity = node.getPurity().toText();

        List<Text> texts = new ArrayList<>();
        texts.add(name);
        texts.add(purity);

        MutableText prompt = Text.translatable("text.constructra.prompt.mining_instruction", client.options.attackKey.getBoundKeyLocalizedText().getString()).formatted(Formatting.GOLD);
        if (node.isObstructed()) prompt.append(" ").append(Text.translatable("text.constructra.info.obstructed").formatted(Formatting.DARK_RED));

        context.drawTooltip(client.textRenderer, texts, x, y, TEXTURE);
        context.drawTooltip(client.textRenderer, prompt, x, y + 30, TEXTURE);
    }
}
