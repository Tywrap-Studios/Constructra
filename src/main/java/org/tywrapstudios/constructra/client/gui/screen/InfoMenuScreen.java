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

package org.tywrapstudios.constructra.client.gui.screen;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.text.Text;
import org.tywrapstudios.constructra.api.math.StringCalculator;
import org.tywrapstudios.constructra.client.key.ClientKeyBinds;

@Environment(EnvType.CLIENT)
public class InfoMenuScreen extends Screen {
    public TextFieldWidget input;
    private String lastCalc;
    private String lastResult;

    public InfoMenuScreen() {
        super(Text.translatable("gui.constructra.info_menu"));
    }

    @Override
    protected void init() {
        input = new TextFieldWidget(this.textRenderer, this.width / 2 - 150, this.height / 2 - 70, 300, 20, Text.empty());

        input.setMaxLength(2000);
        addDrawableChild(input);
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);
        String calc = input.getText();
        if (!calc.equals(lastCalc)) {
            lastCalc = calc;
            calc = StringCalculator.calculateStr(calc);
            calc = calc.replaceAll("E", "*10^");
            lastResult = calc;
        }

        if (!calc.isEmpty()) context.drawTooltip(this.textRenderer, Text.literal(lastResult), mouseX, mouseY);
    }

    @Override
    public void renderBackground(DrawContext context, int mouseX, int mouseY, float delta) {
        if (this.client == null) return;
        if (this.client.world == null) this.renderPanoramaBackground(context, delta);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        boolean push = ClientKeyBinds.PUSH_CALCULATION.matchesKey(keyCode,scanCode);
        if (push) input.setText(lastResult);
        return super.keyPressed(keyCode, scanCode, modifiers);
    }
}
