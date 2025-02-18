package org.tywrapstudios.constructra.client.gui.widget.big_button;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import org.tywrapstudios.constructra.Constructra;
import org.tywrapstudios.constructra.api.client.gui.widget.ActionButtonWidget;
import org.tywrapstudios.constructra.api.screen.DataScreenHandler;
import org.tywrapstudios.constructra.network.payload.PickupRequestC2SPayload;

public abstract class PickupIconWidget extends ActionButtonWidget {
    private static final Identifier ICON = Constructra.id("textures/gui/icons/pickup_icon0.png");
    private static final Identifier ICON_BLINK = Constructra.id("textures/gui/icons/pickup_icon1.png");

    public PickupIconWidget(int x, int y) {
        super(x, y, ICON, ICON_BLINK);
    }

    @Override
    protected void appendClickableNarrations(NarrationMessageBuilder builder) {
    }

    public static <T extends DataScreenHandler<BlockPos>> PickupIconWidget builder(int x, int y, HandledScreen<T> screen) {
        return new PickupIconWidget(x, y) {
            @Override
            public void runAction() {
                if (client != null && client.world != null) ClientPlayNetworking.send(new PickupRequestC2SPayload(screen.getScreenHandler().getData(), client.world.getRegistryKey().getValue()));
                screen.close();
            }
        };
    }
}
