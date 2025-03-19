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

import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.util.Identifier;
import org.tywrapstudios.blossombridge.api.config.ConfigManager;
import org.tywrapstudios.blossombridge.api.logging.LoggingHandler;
import org.tywrapstudios.constructra.api.resource.ResourceManager;
import org.tywrapstudios.constructra.config.ConstructraServerConfig;
import org.tywrapstudios.constructra.network.Network;
import org.tywrapstudios.constructra.registry.CaRegistries;
import org.tywrapstudios.constructra.registry.MainRegistry;
import org.tywrapstudios.constructra.util.Util;

import java.io.File;
import java.util.Objects;
// -8<- [start:constructra]
public class Constructra implements ModInitializer {
	public static final String MOD_ID = "constructra";

	private static final File FILE = new File(FabricLoader.getInstance().getConfigDir().toFile(), "constructra-server.json5");
	public static final ConfigManager<ConstructraServerConfig> CONFIG_MANAGER = new ConfigManager<>(ConstructraServerConfig.class, FILE);
	public static final LoggingHandler<ConstructraServerConfig> LOGGER = new LoggingHandler<>("Constructra", CONFIG_MANAGER);

	@Override
	public void onInitialize() {
		CONFIG_MANAGER.loadConfig();
		// WARNING: REMOVE BEFORE FINAL RELEASE. FOR DEV PURPOSES ONLY.
		config().util_config.debug_mode = true;
		config().resources.visualize_centres = true;
		CONFIG_MANAGER.saveConfig();

		if (!Objects.equals(config().config_version, "1.0")) {
			LOGGER.warn("Constructra config version does not match up, we may use default values");
			LOGGER.warn("Expected: 1.0");
			LOGGER.warn("Got: " + config().config_version);
		}

		// -8<- [start:constructra-initialize]
		CaRegistries.initialize();
		Network.initialize();
		MainRegistry.registerAll();
		ResourceManager.Nodes.initializeServer();
		LOGGER.info(Util.generateInitPhrase());
		Util.logInitialisation();
		// -8<- [end:constructra-initialize]
	}

	public static Identifier id(String s) {
		return Identifier.of(MOD_ID, s);
	}

	public static ConstructraServerConfig config() {
		return CONFIG_MANAGER.getConfig();
	}
}
// -8<- [end:constructra]