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
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import org.tywrapstudios.constructra.Constructra;

public class Tags {
    public enum IItems {
        EMPTY(null);

        private final TagKey<Item> tagKey;

        IItems(String name) {
            this.tagKey = of(Constructra.id(name));
        }

        public TagKey<Item> get() {
            return tagKey;
        }

        private static TagKey<Item> of(Identifier id) {
            return TagKey.of(RegistryKeys.ITEM, id);
        }
    }

    public enum BBlocks {
        HARVESTABLE("harvestable"),;

        private final TagKey<Block> tagKey;

        BBlocks(String name) {
            this.tagKey = of(Constructra.id(name));
        }

        public TagKey<Block> get() {
            return tagKey;
        }

        private static TagKey<Block> of(Identifier id) {
            return TagKey.of(RegistryKeys.BLOCK, id);
        }
    }
}
