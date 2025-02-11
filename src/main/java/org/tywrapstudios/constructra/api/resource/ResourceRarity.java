package org.tywrapstudios.constructra.api.resource;

import net.minecraft.util.StringIdentifiable;

/**
 * Resources can have different Rarities, depending on these, they might be harder to get, or might spawn/generate less often.
 * <p>Starter means that a Node with this Resource will always be present near the Spawn area.
 */
public enum ResourceRarity implements StringIdentifiable {
    NONE("none"),
    STARTER("starter"),
    COMMON("common"),
    UNCOMMON("uncommon"),
    RARE("rare"),
    EPIC("epic");

    private final String name;

    ResourceRarity(final String name) {
        this.name = name;
    }

    @Override
    public String asString() {
        return name;
    }
}
