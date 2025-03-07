package org.tywrapstudios.constructra.registry;

import net.minecraft.block.Block;
import net.minecraft.item.ItemConvertible;
import net.minecraft.util.Identifier;
import org.tywrapstudios.constructra.api.resource.Resource;
import org.tywrapstudios.constructra.api.resource.ResourceManager.Registries;
import org.tywrapstudios.constructra.api.resource.ResourceRarity;
import org.tywrapstudios.constructra.util.Util;

import static org.tywrapstudios.constructra.Constructra.id;

public class Resources {
    public static final Resource IRON;
    public static final Resource COPPER;

    static {
        IRON = Registries.register(CaItems.IRON_ORE, ResourceRarity.STARTER, CaBlocks.IRON_SPAWN, Identifier.ofVanilla("iron"));
        COPPER = Registries.register(CaItems.COPPER_ORE, ResourceRarity.STARTER, CaBlocks.COPPER_SPAWN, Identifier.ofVanilla("copper"));
    }

    private static Resource create(ItemConvertible retrievableItem, ResourceRarity rarity, Block harvestBlock, String id) {
        return Registries.register(retrievableItem, rarity, harvestBlock, id(id));
    }

    public static void register() {
        Util.logInitialisation();
    }
}
