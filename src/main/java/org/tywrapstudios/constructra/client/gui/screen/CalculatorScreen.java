package org.tywrapstudios.constructra.client.gui.screen;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.text.Text;
import org.tywrapstudios.constructra.api.math.StringCalculator;

@Environment(EnvType.CLIENT)
public class CalculatorScreen extends Screen {
    public TextFieldWidget input;
    private String lastCalc;
    private String lastResult;

    public CalculatorScreen() {
        super(Text.translatable("gui.constructra.calculator"));
    }

    @Override
    protected void init() {
        input = new TextFieldWidget(this.textRenderer, this.width / 2 - 150, this.height / 2 - 70, 300, 20, Text.translatable("text.constructra.prompt.input_calculation"));

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
}
