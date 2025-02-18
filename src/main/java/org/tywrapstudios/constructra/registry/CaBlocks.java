package org.tywrapstudios.constructra.registry;

import net.minecraft.block.Block;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import org.tywrapstudios.constructra.api.block.ResourceBlock;
import org.tywrapstudios.constructra.block.PortableMinerBlock;

import java.util.ArrayList;
import java.util.List;

import static net.minecraft.block.AbstractBlock.Settings.create;
import static org.tywrapstudios.constructra.registry.MainRegistry.*;

public class CaBlocks {
    public static final List<Block> BLOCKS = new ArrayList<>();
    public static final List<Block> CUBE_ALL = new ArrayList<>();

    public static final Block IRON_SPAWN;
    public static final Block COPPER_SPAWN;
    public static final Block PORTABLE_MINER;

    static {
        IRON_SPAWN = of("iron_spawn", new ResourceBlock(create()
                .registryKey(blockKey("iron_spawn"))));
        COPPER_SPAWN = of("copper_spawn", new ResourceBlock(create()
                .registryKey(blockKey("copper_spawn"))));
        PORTABLE_MINER = of("portable_miner", new PortableMinerBlock(create()
                .registryKey(blockKey("portable_miner"))
                .luminance(PortableMinerBlock::getLuminance)
                .strength(-1.0f, 3600000.0f)
                .noBlockBreakParticles()
                .pistonBehavior(PistonBehavior.BLOCK)), false);
    }

    private static Block of(String id, Block block) {
        return of(id, block, true);
    }

    private static Block of(String id, Block block, boolean cube) {
        BlockItem blockItem = new BlockItem(block, new Item.Settings()
                .registryKey(itemKey(id))
                .useBlockPrefixedTranslationKey());
        Registry.register(Registries.ITEM, itemKey(id), blockItem);
        BLOCKS.add(block);
        if (cube) CUBE_ALL.add(block);

        return Registry.register(Registries.BLOCK, blockKey(id), block);
    }

    public static void register() {
    }
}
