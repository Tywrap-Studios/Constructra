package org.tywrapstudios.constructra.client.key;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import org.lwjgl.glfw.GLFW;

@Environment(EnvType.CLIENT)
public class ClientKeyBinds {
    public static KeyBinding openCalculator;

    static {
        openCalculator = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.constructra.calculator",
                GLFW.GLFW_KEY_N,
                "key.category.constructra"));
    }

    public static void registerClient() {
    }
}
