package org.tywrapstudios.constructra.util.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.block.Blocks;
import net.minecraft.registry.RegistryWrapper;
import org.tywrapstudios.constructra.registry.Tags;

import java.util.concurrent.CompletableFuture;

public class TagGeneration {
    public static class IItems extends FabricTagProvider.ItemTagProvider {
        public IItems(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> completableFuture) {
            super(output, completableFuture, null);
        }

        @Override
        protected void configure(RegistryWrapper.WrapperLookup wrapperLookup) {
//            for (Item item : ComponentItems.COMPONENT_ITEMS) {
//                getOrCreateTagBuilder(Tags.IItems.CRAFTING_COMPONENTS.get())
//                        .add(item);
//                Constructra.LOGGER.debug("You're it! Component Item: " + item);
//            }
//            for (Block block : ComponentItems.BBlock.COMPONENT_BLOCKS) {
//                getOrCreateTagBuilder(Tags.IItems.CRAFTING_COMPONENTS.get())
//                        .add(block.asItem());
//                Constructra.LOGGER.debug("You're it! Component BlockItem: " + block);
//            }
//            for (Block block : ComponentItems.BBlock.COMPONENT_BLOCKS_NON_CUBE) {
//                getOrCreateTagBuilder(Tags.IItems.CRAFTING_COMPONENTS.get())
//                        .add(block.asItem());
//                Constructra.LOGGER.debug("You're it! Component BlockItem: " + block);
//            }
//            for (Item item : FuelItems.BIO_FUEL_ITEMS) {
//                getOrCreateTagBuilder(Tags.IItems.BIOLOGICAL_FUELS.get())
//                        .add(item);
//                Constructra.LOGGER.debug("You're it! Biofuel Item: " + item);
//            }
//            for (Item item : FuelItems.FUEL_ITEMS) {
//                getOrCreateTagBuilder(Tags.IItems.INDUSTRIAL_FUELS.get())
//                        .add(item);
//                Constructra.LOGGER.debug("You're it! Fuel Item: " + item);
//            }
            getOrCreateTagBuilder(Tags.BBlocks.HARVESTABLE.asItemTag())
                    .add(Blocks.IRON_ORE.asItem())
                    .add(Blocks.GOLD_ORE.asItem());
        }
    }

    public static class BBlocks extends FabricTagProvider.BlockTagProvider {
        public BBlocks(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
            super(output, registriesFuture);
        }

        @Override
        protected void configure(RegistryWrapper.WrapperLookup wrapperLookup) {
            getOrCreateTagBuilder(Tags.BBlocks.HARVESTABLE.get())
                    .add(Blocks.IRON_ORE)
                    .add(Blocks.GOLD_ORE);
        }
    }
}
