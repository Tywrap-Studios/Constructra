package org.tywrapstudios.constructra.registry;

import net.minecraft.block.Block;
import net.minecraft.item.ItemConvertible;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import org.tywrapstudios.constructra.api.resource.ImplementedResource;
import org.tywrapstudios.constructra.api.resource.Resource;
import org.tywrapstudios.constructra.api.resource.ResourceRarity;

import static org.tywrapstudios.constructra.Constructra.id;

public class Resources {
    public static final Resource IRON;
    public static final Resource COPPER;

    static {
        IRON = create(CaItems.IRON_ORE, ResourceRarity.STARTER, CaBlocks.IRON_SPAWN, Identifier.ofVanilla("iron"));
        COPPER = create(CaItems.COPPER_ORE, ResourceRarity.STARTER, CaBlocks.COPPER_SPAWN, Identifier.ofVanilla("copper"));
    }

    private static Resource create(ItemConvertible retrievableItem, ResourceRarity rarity, Block harvestBlock, String id) {
        return create(retrievableItem, rarity, harvestBlock, id(id));
    }

    public static Resource create(ItemConvertible retrievableItem, ResourceRarity rarity, Block harvestBlock, Identifier identifier) {
        return create(new ImplementedResource(retrievableItem, rarity, harvestBlock, identifier));
    }

    public static Resource create(Resource resource) {
        return Registry.register(CaRegistries.RESOURCE, MainRegistry.resourceKey(resource.identifier()), resource);
    }

    public static void register() {
    }
}
