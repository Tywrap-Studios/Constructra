package org.tywrapstudios.constructra.util.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.minecraft.registry.RegistryWrapper;
import org.tywrapstudios.constructra.registry.CaBlocks;

import java.util.concurrent.CompletableFuture;

public class LootTableGeneration extends FabricBlockLootTableProvider {
    protected LootTableGeneration(FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
        super(dataOutput, registryLookup);
    }

    @Override
    public void generate() {
        addDrop(CaBlocks.PORTABLE_MINER);
    }
}
