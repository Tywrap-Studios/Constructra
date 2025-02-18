package org.tywrapstudios.constructra.client.key;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import org.lwjgl.glfw.GLFW;

@Environment(EnvType.CLIENT)
public class ClientKeyBinds {
    public static final KeyBinding OPEN_INFO_MENU;
    public static final KeyBinding PUSH_CALCULATION;

    static {
        OPEN_INFO_MENU = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.constructra.info_menu",
                GLFW.GLFW_KEY_N,
                "key.category.constructra"));
        PUSH_CALCULATION = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.constructra.push_calc",
                GLFW.GLFW_KEY_ENTER,
                "key.category.constructra"));
    }

    public static void registerClient() {
    }
}
