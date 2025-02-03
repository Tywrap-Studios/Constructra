package org.tywrapstudios.constructra;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import org.tywrapstudios.constructra.client.key.ClientKeyBinds;
import org.tywrapstudios.constructra.client.logic.ClientNodeActionTracker;
import org.tywrapstudios.constructra.client.rendering.ResourceNodeHudRenderer;
import org.tywrapstudios.constructra.client.screen.CalculatorScreen;

@Environment(EnvType.CLIENT)
public class ConstructraClient implements ClientModInitializer {
    private static final ResourceNodeHudRenderer HUD_RENDERER = new ResourceNodeHudRenderer();

    @Override
    public void onInitializeClient() {
        MinecraftClient client = MinecraftClient.getInstance();
        ClientNodeActionTracker.initializeClient(client);
        ClientKeyBinds.registerClient();

        HudRenderCallback.EVENT.register((drawContext, tickDelta) -> {
            if (ClientNodeActionTracker.CURRENT_NODE == null) return;
            if (ClientNodeActionTracker.WATCHING_NODE) HUD_RENDERER.render(drawContext, ClientNodeActionTracker.CURRENT_NODE);
        });

        ClientTickEvents.END_CLIENT_TICK.register(tickedClient -> {
            if (ClientKeyBinds.openCalculator.isPressed()) {
                if (tickedClient.currentScreen instanceof CalculatorScreen) tickedClient.currentScreen.close();
                else tickedClient.setScreen(new CalculatorScreen());
            }
        });
    }
}
