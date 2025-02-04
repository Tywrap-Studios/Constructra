package org.tywrapstudios.constructra.registry;

import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import org.tywrapstudios.constructra.api.block.v1.ResourceBlock;

import java.util.ArrayList;
import java.util.List;

import static org.tywrapstudios.constructra.registry.MainRegistry.blockKey;
import static org.tywrapstudios.constructra.registry.MainRegistry.itemKey;

public class CaBlocks {
    public static final List<Block> BLOCKS = new ArrayList<>();

    public static final Block IRON_ORE;
    public static final Block COPPER_ORE;

    static {
        IRON_ORE = create("iron_ore", new ResourceBlock());
        COPPER_ORE = create("copper_ore", new ResourceBlock());
    }

    private static Block create(String id, Block block) {
        BlockItem blockItem = new BlockItem(block, new Item.Settings()
                .registryKey(itemKey(id))
                .useBlockPrefixedTranslationKey());
        Registry.register(Registries.ITEM, itemKey(id), blockItem);
        BLOCKS.add(block);

        return Registry.register(Registries.BLOCK, blockKey(id), block);
    }
}
