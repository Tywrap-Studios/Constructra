package org.tywrapstudios.constructra.registry;

import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import org.tywrapstudios.constructra.block.PortableMinerBlockEntity;
import org.tywrapstudios.constructra.util.Util;

import static org.tywrapstudios.constructra.Constructra.id;

public class CaBlockEntities {
    public static final BlockEntityType<PortableMinerBlockEntity> PORTABLE_MINER_ENTITY;

    static {
        PORTABLE_MINER_ENTITY = create("portable_miner_entity", PortableMinerBlockEntity::new, CaBlocks.PORTABLE_MINER);
    }

    private static <T extends BlockEntity> BlockEntityType<T> create(String id, FabricBlockEntityTypeBuilder.Factory<T> factory, Block... blocks) {
        return Registry.register(Registries.BLOCK_ENTITY_TYPE, id(id), FabricBlockEntityTypeBuilder.create(factory, blocks).build());
    }

    public static void register() {
        Util.logInitialisation();
    }
}
