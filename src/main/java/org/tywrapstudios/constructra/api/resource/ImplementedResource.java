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

package org.tywrapstudios.constructra.api.resource;

import net.minecraft.block.Block;
import net.minecraft.item.ItemConvertible;
import net.minecraft.util.Identifier;

public class ImplementedResource implements Resource {
    private final Identifier identifier;
    private final ItemConvertible retrievableItem;
    private final Block harvestBlock;
    private final ResourceRarity rarity;

    /**
     * Constructs a new ImplementedResource, there is also the option of extending this class or implementing {@link Resource} to create your own Resource class.
     *
     * @param retrievableItem the item that will be harvested from this Resource.
     * @param rarity          the rarity of this type of Resource.
     * @param harvestBlock    the block that the Resource will place in the world to be harvested from.
     * @param identifier      the {@link Identifier} of this Resource, can be used upon Registry.
     */
    public ImplementedResource(ItemConvertible retrievableItem, ResourceRarity rarity, Block harvestBlock, Identifier identifier) {
        this.retrievableItem = retrievableItem;
        this.rarity = rarity;
        this.harvestBlock = harvestBlock;
        this.identifier = identifier;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "{" +
                "harvestBlock=" + getHarvestBlock().toString() +
                ", rarity=" + getRarity().asString() +
                ", retrievableItem=" + getRetrievableItem().asItem().toString() +
                '}';
    }

    @Override
    public Identifier getIdentifier() {
        return this.identifier;
    }

    @Override
    public ItemConvertible getRetrievableItem() {
        return this.retrievableItem;
    }

    @Override
    public Block getHarvestBlock() {
        return this.harvestBlock;
    }

    @Override
    public ResourceRarity getRarity() {
        return this.rarity;
    }
}
