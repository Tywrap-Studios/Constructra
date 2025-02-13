package org.tywrapstudios.constructra.client.key;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import org.lwjgl.glfw.GLFW;

@Environment(EnvType.CLIENT)
public class ClientKeyBinds {
    public static KeyBinding openCalculator;
    public static KeyBinding pushCalculation;

    static {
        openCalculator = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.constructra.calculator",
                GLFW.GLFW_KEY_N,
                "key.category.constructra"));
        pushCalculation = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.constructra.push_calc",
                GLFW.GLFW_KEY_ENTER,
                "key.category.constructra"));
    }

    public static void registerClient() {
    }
}
