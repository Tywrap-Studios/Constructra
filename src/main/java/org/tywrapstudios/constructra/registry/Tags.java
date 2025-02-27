package org.tywrapstudios.constructra.registry;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
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
