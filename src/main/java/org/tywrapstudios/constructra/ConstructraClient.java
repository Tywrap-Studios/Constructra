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

package org.tywrapstudios.constructra;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.tywrapstudios.blossombridge.api.config.ConfigManager;
import org.tywrapstudios.constructra.client.gui.hud.ResourceNodeHudRenderer;
import org.tywrapstudios.constructra.client.gui.screen.InfoMenuScreen;
import org.tywrapstudios.constructra.client.gui.screen.PortableMinerScreen;
import org.tywrapstudios.constructra.client.key.ClientKeyBinds;
import org.tywrapstudios.constructra.client.logic.ClientNodeActionTracker;
import org.tywrapstudios.constructra.client.logic.PlayTime;
import org.tywrapstudios.constructra.command.CaCommandImpl;
import org.tywrapstudios.constructra.config.ConstructraClientConfig;
import org.tywrapstudios.constructra.registry.CaScreenHandlers;

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

        Constructra.LOGGER.debug("Validate test: " + CONFIG_MANAGER.getConfig().play_time_safety.interval);
        ClientNodeActionTracker.initializeClient();
        ClientKeyBinds.registerClient();
        PlayTime.initializeClient();

        HudRenderCallback.EVENT.register((drawContext, tickDelta) -> {
            if (ClientNodeActionTracker.CURRENT_NODE == null) return;
            if (ClientNodeActionTracker.WATCHING_NODE) HUD_RENDERER.render(drawContext, ClientNodeActionTracker.CURRENT_NODE);
        });

        ClientTickEvents.END_CLIENT_TICK.register(tickedClient -> {
            if (ClientKeyBinds.OPEN_INFO_MENU.isPressed()) {
                if (tickedClient.currentScreen instanceof InfoMenuScreen) tickedClient.currentScreen.close();
                else tickedClient.setScreen(new InfoMenuScreen());
            }
        });

        ClientCommandRegistrationCallback.EVENT.register((dispatcher, access) -> CaCommandImpl.registerClient(dispatcher));

        HandledScreens.register(CaScreenHandlers.PORTABLE_MINER_HANDLER, PortableMinerScreen::new);
    }

    public static ConstructraClientConfig config() {
        return CONFIG_MANAGER.getConfig();
    }
}
