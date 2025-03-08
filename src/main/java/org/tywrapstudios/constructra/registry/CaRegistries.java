package org.tywrapstudios.constructra.registry;

import net.fabricmc.fabric.api.event.registry.FabricRegistryBuilder;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;
import org.tywrapstudios.constructra.Constructra;
import org.tywrapstudios.constructra.api.resource.Resource;
import org.tywrapstudios.constructra.util.Util;

public class CaRegistries {
    public static final Registry<Resource> RESOURCE;

    static {
        RESOURCE = FabricRegistryBuilder.createDefaulted(Keys.RESOURCE, Identifier.ofVanilla("iron")).buildAndRegister();
    }

    public static class Keys {
        public static final RegistryKey<Registry<Resource>> RESOURCE = of("resource");

        private static <T> RegistryKey<Registry<T>> of(String id) {
            return RegistryKey.ofRegistry(Constructra.id(id));
        }
    }

    public static void initialize() {
        Util.logInitialisation();
    }
}
