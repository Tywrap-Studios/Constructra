package org.tywrapstudios.constructra.util.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.registry.RegistryWrapper;
import org.tywrapstudios.constructra.registry.CaBlocks;
import org.tywrapstudios.constructra.registry.Tags;

import java.util.concurrent.CompletableFuture;

public class TagGeneration {
    public static class IItems extends FabricTagProvider.ItemTagProvider {
        public IItems(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> completableFuture) {
            super(output, completableFuture, null);
        }

        @Override
        protected void configure(RegistryWrapper.WrapperLookup wrapperLookup) {

        }
    }

    public static class BBlocks extends FabricTagProvider.BlockTagProvider {
        public BBlocks(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
            super(output, registriesFuture);
        }

        @Override
        protected void configure(RegistryWrapper.WrapperLookup wrapperLookup) {
            getOrCreateTagBuilder(Tags.BBlocks.HARVESTABLE.get())
                    .add(CaBlocks.IRON_SPAWN)
                    .add(CaBlocks.COPPER_SPAWN);
        }
    }
}
