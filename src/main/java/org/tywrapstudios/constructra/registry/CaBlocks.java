package org.tywrapstudios.constructra.registry;

import net.minecraft.block.Block;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import org.tywrapstudios.constructra.api.block.v1.ResourceBlock;
import org.tywrapstudios.constructra.block.PortableMinerBlock;

import java.util.ArrayList;
import java.util.List;

import static org.tywrapstudios.constructra.registry.MainRegistry.blockKey;
import static org.tywrapstudios.constructra.registry.MainRegistry.itemKey;
import static net.minecraft.block.AbstractBlock.Settings.create;

public class CaBlocks {
    public static final List<Block> BLOCKS = new ArrayList<>();

    public static final Block IRON_ORE;
    public static final Block COPPER_ORE;
    public static final Block PORTABLE_MINER;

    static {
        IRON_ORE = of("iron_ore_block", new ResourceBlock(create()
                .registryKey(blockKey("iron_ore_block"))));
        COPPER_ORE = of("copper_ore_block", new ResourceBlock(create()
                .registryKey(blockKey("copper_ore_block"))));
        PORTABLE_MINER = of("portable_miner", new PortableMinerBlock(create()
                .registryKey(blockKey("portable_miner"))
                .strength(-1.0f, 3600000.0f)
                .dropsNothing()
                .noBlockBreakParticles()
                .pistonBehavior(PistonBehavior.BLOCK)));
    }

    private static Block of(String id, Block block) {
        BlockItem blockItem = new BlockItem(block, new Item.Settings()
                .registryKey(itemKey(id))
                .useBlockPrefixedTranslationKey());
        Registry.register(Registries.ITEM, itemKey(id), blockItem);
        BLOCKS.add(block);

        return Registry.register(Registries.BLOCK, blockKey(id), block);
    }

    public static void register() {
    }
}
