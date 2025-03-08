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

import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import org.tywrapstudios.constructra.Constructra;
import org.tywrapstudios.constructra.api.item.EasyItemGroup;
import org.tywrapstudios.constructra.util.Util;

import java.util.ArrayList;
import java.util.List;

import static org.tywrapstudios.constructra.util.Util.itemKey;

public class CaItems {
    public static final List<Item> ITEMS = new ArrayList<>();
    public static final List<Item> GENERATED = new ArrayList<>();

    public static final Item IRON_ORE;
    public static final Item IRON_INGOT;
    public static final Item IRON_PLATE;
    public static final Item IRON_ROD;
    public static final Item SCREWS;
    public static final Item REINFORCED_IRON_PLATE;
    public static final Item COPPER_ORE;
    public static final Item COPPER_INGOT;

    static {
        IRON_ORE = create("iron_ore");
        IRON_INGOT = create("iron_ingot");
        IRON_PLATE = create("iron_plate");
        IRON_ROD = create("iron_rod");
        SCREWS = create("screws");
        REINFORCED_IRON_PLATE = create("reinforced_iron_plate");
        COPPER_ORE = create("copper_ore");
        COPPER_INGOT = create("copper_ingot");
    }

    private static Item create(String id) {
        return create(id, true);
    }

    private static Item create(String id, boolean generated) {
        Item item = new Item(new Item.Settings()
                .registryKey(itemKey(id)));
        return create(id, item, generated);
    }

    private static Item create(String id, Item item, boolean generated) {
        ITEMS.add(item);
        if (generated) GENERATED.add(item);
        return Registry.register(Registries.ITEM, itemKey(id), item);
    }

    public static void register() {
        Group.INSTANCE.register();
        Util.logInitialisation();
    }

    public static class Group extends EasyItemGroup {
        private static final List<ItemConvertible> ALL = new ArrayList<>();

        private Group() {
            super(Constructra.id("main"), SCREWS, ALL);
        }

        @Override
        public void register() {
            ALL.addAll(ITEMS);
            ALL.addAll(CaBlocks.BLOCKS);
            ENTRIES.addAll(ALL);
            super.register();
        }

        public static final Group INSTANCE = new Group();
    }
}
