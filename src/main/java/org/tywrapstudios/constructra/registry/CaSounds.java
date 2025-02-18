package org.tywrapstudios.constructra.registry;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import org.tywrapstudios.constructra.Constructra;

public class CaSounds {
    public static final SoundEvent BIG_BUTTON_CLICK;

    static {
        BIG_BUTTON_CLICK = create("big_button_click");
    }

    public static SoundEvent create(String id) {
        SoundEvent event = SoundEvent.of(Constructra.id(id));
        return Registry.register(Registries.SOUND_EVENT, Constructra.id(id), event);
    }

    public static void register() {
    }
}
