package org.tywrapstudios.constructra.client.gui.widget.big_button;

import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.util.Identifier;
import org.tywrapstudios.constructra.Constructra;
import org.tywrapstudios.constructra.api.client.gui.widget.ActionButtonWidget;

public abstract class GrabIconWidget extends ActionButtonWidget {
    private static final Identifier ICON = Constructra.id("textures/gui/icons/grab_icon0.png");
    private static final Identifier ICON_BLINK = Constructra.id("textures/gui/icons/grab_icon1.png");

    public GrabIconWidget(int x, int y) {
        super(x, y, ICON, ICON_BLINK);
    }

    @Override
    protected void appendClickableNarrations(NarrationMessageBuilder builder) {

    }
}
