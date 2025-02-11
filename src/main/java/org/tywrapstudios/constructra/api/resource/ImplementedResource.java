package org.tywrapstudios.constructra.api.resource;

import net.minecraft.block.Block;
import net.minecraft.item.ItemConvertible;
import net.minecraft.util.Identifier;

public record ImplementedResource(ItemConvertible retrievableItem, ResourceRarity rarity, Block harvestBlock, Identifier identifier) implements Resource {
    /**
     * Constructs a new ImplementedResource, there is also the option of extending this class or implementing {@link Resource} to create your own Resource class.
     *
     * @param retrievableItem the item that will be harvested from this Resource.
     * @param rarity          the rarity of this type of Resource.
     * @param harvestBlock    the block that the Resource will place in the world to be harvested from.
     * @param identifier      the {@link Identifier} of this Resource, can be used upon Registry.
     */
    public ImplementedResource {
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "{" +
                "harvestBlock=" + harvestBlock +
                ", rarity=" + rarity +
                ", retrievableItem=" + retrievableItem +
                '}';
    }
}
