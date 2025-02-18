package org.tywrapstudios.constructra.util.datagen;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

public class ConstructraDataGenerator implements DataGeneratorEntrypoint {
	@Override
	public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
		FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();

		pack.addProvider(ModelGeneration::new);
		pack.addProvider(TagGeneration.IItems::new);
		pack.addProvider(TagGeneration.BBlocks::new);
		pack.addProvider(LangGeneration::new);
		pack.addProvider(LootTableGeneration::new);
	}
}
