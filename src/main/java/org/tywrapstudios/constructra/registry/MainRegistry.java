package org.tywrapstudios.constructra.registry;

import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.loot.LootTable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import org.tywrapstudios.constructra.Constructra;
import org.tywrapstudios.constructra.command.CaCommandImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.tywrapstudios.constructra.Constructra.id;

public class MainRegistry {
    public static List<ItemConvertible> ALL_ITEM_CONVERTIBLE_CONTENT = new ArrayList<>();

    public static void registerAll() {
        CaItems.register();
        CaBlocks.register();
        CaBlockEntities.register();
        CaScreenHandlers.register();
        CaSounds.register();
        ALL_ITEM_CONVERTIBLE_CONTENT.addAll(CaItems.ITEMS);
        ALL_ITEM_CONVERTIBLE_CONTENT.addAll(CaBlocks.BLOCKS);
        CommandRegistrationCallback.EVENT.register((dispatcher, access, env) -> CaCommandImpl.register(dispatcher, access));
        Resources.register();
        Constructra.LOGGER.debug("ItemConvertible List size: " + ALL_ITEM_CONVERTIBLE_CONTENT.size());
    }
}
