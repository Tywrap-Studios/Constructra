package org.tywrapstudios.constructra;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.MinecraftClient;
import net.tywrapstudios.blossombridge.api.config.ConfigManager;
import org.tywrapstudios.constructra.client.key.ClientKeyBinds;
import org.tywrapstudios.constructra.client.logic.ClientNodeActionTracker;
import org.tywrapstudios.constructra.client.logic.PlayTimeSafety;
import org.tywrapstudios.constructra.client.rendering.ResourceNodeHudRenderer;
import org.tywrapstudios.constructra.client.screen.CalculatorScreen;
import org.tywrapstudios.constructra.command.CaCommandImpl;
import org.tywrapstudios.constructra.config.ConstructraClientConfig;

import java.io.File;

@Environment(EnvType.CLIENT)
public class ConstructraClient implements ClientModInitializer {
    private static final File FILE = new File(FabricLoader.getInstance().getConfigDir().toFile(), "constructra-client.json5");
    public static final ConfigManager<ConstructraClientConfig> CONFIG_MANAGER = new ConfigManager<>(ConstructraClientConfig.class, FILE);

    private static final ResourceNodeHudRenderer HUD_RENDERER = new ResourceNodeHudRenderer();

    @Override
    public void onInitializeClient() {
        CONFIG_MANAGER.loadConfig();
        CONFIG_MANAGER.saveConfig();

        ClientNodeActionTracker.initializeClient();
        ClientKeyBinds.registerClient();
        PlayTimeSafety.initializeClient();

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

        ClientCommandRegistrationCallback.EVENT.register((dispatcher, access) -> CaCommandImpl.registerClient(dispatcher));
    }

    public static ConstructraClientConfig config() {
        return CONFIG_MANAGER.getConfig();
    }
}
