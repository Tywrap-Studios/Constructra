package org.tywrapstudios.constructra;

import net.fabricmc.api.ModInitializer;

import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.util.Identifier;
import net.tywrapstudios.blossombridge.api.config.ConfigManager;
import net.tywrapstudios.blossombridge.api.logging.LoggingHandler;
import org.tywrapstudios.constructra.api.resource.ResourceManager;
import org.tywrapstudios.constructra.config.ConstructraServerConfig;
import org.tywrapstudios.constructra.registry.CaRegistries;
import org.tywrapstudios.constructra.registry.MainRegistry;
import org.tywrapstudios.constructra.util.Util;

import java.io.File;
import java.util.Objects;

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

		CaRegistries.initialize();
		MainRegistry.registerAll();
		ResourceManager.Nodes.initializeServer();
		LOGGER.info(Util.generateInitPhrase());
	}

	public static Identifier id(String s) {
		return Identifier.of(MOD_ID, s);
	}

	public static ConstructraServerConfig config() {
		return CONFIG_MANAGER.getConfig();
	}
}