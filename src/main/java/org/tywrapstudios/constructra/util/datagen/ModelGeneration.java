package org.tywrapstudios.constructra.util.datagen;

import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.client.data.BlockStateModelGenerator;
import net.minecraft.client.data.ItemModelGenerator;
import net.minecraft.client.data.Models;
import net.minecraft.item.Item;

public class ModelGeneration extends FabricModelProvider {
    public ModelGeneration(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator generator) {
//        for (Block block : BBlock.COMPONENT_BLOCKS) {
//            if (BBlock.COMPONENT_BLOCKS_NON_CUBE.contains(block) || block == BBlock.STEEL_BEAM) {
//                continue;
//            }
//            generator.registerSimpleCubeAll(block);
//        }
//        generator.registerNorthDefaultHorizontalRotatable(BBlock.STEEL_BEAM, TextureMap.sideFrontTop(BBlock.STEEL_BEAM));
    }

    @Override
    public void generateItemModels(ItemModelGenerator generator) {
//        for (Item item : ComponentItems.COMPONENT_ITEMS) {
//            simpleItem(item, generator);
//        }
//        for (Item item : FuelItems.BIO_FUEL_ITEMS) {
//            simpleItem(item, generator);
//        }
//        for (Item item : FuelItems.FUEL_ITEMS) {
//            simpleItem(item, generator);
//        }
    }

    public static void simpleItem(Item item, ItemModelGenerator generator) {
        generator.register(item, Models.GENERATED);
    }
}
