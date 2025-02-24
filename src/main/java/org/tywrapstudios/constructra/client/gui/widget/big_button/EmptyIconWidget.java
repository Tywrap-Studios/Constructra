package org.tywrapstudios.constructra.client.gui.widget.big_button;

import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.client.gui.screen.narration.NarrationPart;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.tywrapstudios.constructra.Constructra;
import org.tywrapstudios.constructra.api.client.gui.widget.ActionButtonWidget;

public abstract class EmptyIconWidget extends ActionButtonWidget {
    private static final Identifier ICON = Constructra.id("textures/gui/empty_icon.png");

    public EmptyIconWidget(int x, int y) {
        super(x, y, ICON, ICON);
    }

    @Override
    protected void appendClickableNarrations(NarrationMessageBuilder builder) {
        builder.put(NarrationPart.USAGE, Text.translatable("narration.constructra.empty_button"));
    }
}
