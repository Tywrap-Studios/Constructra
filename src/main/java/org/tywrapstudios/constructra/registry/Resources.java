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
