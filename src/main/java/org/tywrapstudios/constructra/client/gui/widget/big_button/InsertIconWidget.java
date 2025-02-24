package org.tywrapstudios.constructra.client.gui.widget.big_button;

import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.client.gui.screen.narration.NarrationPart;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.tywrapstudios.constructra.Constructra;
import org.tywrapstudios.constructra.api.client.gui.widget.ActionButtonWidget;

public abstract class InsertIconWidget extends ActionButtonWidget {
    private static final Identifier ICON = Constructra.id("textures/gui/icons/insert_icon0.png");
    private static final Identifier ICON_BLINK = Constructra.id("textures/gui/icons/insert_icon1.png");

    public InsertIconWidget(int x, int y) {
        super(x, y, ICON, ICON_BLINK);
    }

    @Override
    protected void appendClickableNarrations(NarrationMessageBuilder builder) {
        builder.put(NarrationPart.USAGE, Text.translatable("narration.constructra.insert_button"));
    }
}
