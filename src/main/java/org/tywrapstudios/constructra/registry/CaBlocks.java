/*
 * MIT License
 *
 * Copyright (c) 2025 Tywrap Studios;
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package org.tywrapstudios.constructra.registry;

import net.minecraft.block.Block;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import org.tywrapstudios.constructra.api.block.ResourceBlock;
import org.tywrapstudios.constructra.block.PortableMinerBlock;
import org.tywrapstudios.constructra.util.Util;

import java.util.ArrayList;
import java.util.List;

import static net.minecraft.block.AbstractBlock.Settings.create;
import static org.tywrapstudios.constructra.util.Util.*;

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
                .lootTable(optionalLootKey("portable_miner"))
                .luminance(PortableMinerBlock::getLuminance)
                .strength(-1.0f, 3600000.0f)
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
        Util.logInitialisation();
    }
}
