package org.tywrapstudios.constructra.client.gui.widget.big_button;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.client.gui.screen.narration.NarrationPart;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.tywrapstudios.constructra.Constructra;
import org.tywrapstudios.constructra.api.client.gui.widget.ActionButtonWidget;
import org.tywrapstudios.constructra.network.payload.GrabRequestC2SPayload;

public abstract class GrabIconWidget extends ActionButtonWidget {
    private static final Identifier ICON = Constructra.id("textures/gui/icons/grab_icon0.png");
    private static final Identifier ICON_BLINK = Constructra.id("textures/gui/icons/grab_icon1.png");

    public GrabIconWidget(int x, int y) {
        super(x, y, ICON, ICON_BLINK);
    }

    @Override
    protected void appendClickableNarrations(NarrationMessageBuilder builder) {
        builder.put(NarrationPart.USAGE, Text.translatable("narration.constructra.grab_button"));
    }

    public static <T extends ScreenHandler> GrabIconWidget builder(int x, int y, HandledScreen<T> screen) {
        return new GrabIconWidget(x, y) {
            @Override
            public void runAction() {
                T handler = screen.getScreenHandler();

                ClientPlayNetworking.send(new GrabRequestC2SPayload(handler.syncId));
            }
        };
    }
}
