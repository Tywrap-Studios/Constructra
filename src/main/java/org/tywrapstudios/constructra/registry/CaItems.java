package org.tywrapstudios.constructra.registry;

import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import org.tywrapstudios.constructra.Constructra;
import org.tywrapstudios.constructra.api.item.EasyItemGroup;

import java.util.ArrayList;
import java.util.List;

import static org.tywrapstudios.constructra.registry.MainRegistry.itemKey;

public class CaItems {
    public static final List<Item> ITEMS = new ArrayList<>();

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
        Item item = new Item(new Item.Settings()
                .registryKey(itemKey(id)));
        return create(id, item);
    }

    private static Item create(String id, Item item) {
        ITEMS.add(item);
        return Registry.register(Registries.ITEM, itemKey(id), item);
    }

    public static void register() {
        Group.INSTANCE.register();
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
