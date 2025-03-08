package org.tywrapstudios.constructra.util.datagen;

import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.block.Block;
import net.minecraft.client.data.BlockStateModelGenerator;
import net.minecraft.client.data.ItemModelGenerator;
import net.minecraft.client.data.Models;
import net.minecraft.item.Item;

import static org.tywrapstudios.constructra.registry.CaBlocks.CUBE_ALL;
import static org.tywrapstudios.constructra.registry.CaItems.GENERATED;

public class ModelGeneration extends FabricModelProvider {
    public ModelGeneration(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator generator) {
        for (Block block : CUBE_ALL) generator.registerSimpleCubeAll(block);
    }

    @Override
    public void generateItemModels(ItemModelGenerator generator) {
        for (Item item : GENERATED) generator.register(item, Models.GENERATED);
    }
}
