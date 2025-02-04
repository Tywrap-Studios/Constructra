package org.tywrapstudios.constructra.api.resource.v1;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.PersistentState;
import org.tywrapstudios.constructra.Constructra;
import org.tywrapstudios.constructra.registry.CaRegistries;

import java.util.ArrayList;
import java.util.List;

public class ResourceNodesState extends PersistentState {
    private final List<ResourceNode<?>> nodes = new ArrayList<>();
    public static final Type<ResourceNodesState> TYPE = new Type<>(ResourceNodesState::new, (nbt, wrapper) -> createFromNbt(nbt), null);
    public static final String STORAGE_ID = "resource_nodes";

    public ResourceNodesState() {
        super();
    }

    @Override
    public NbtCompound writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registries) {
        Constructra.LOGGER.debug("Writing NBT for " + nodes.size() + " nodes");
        NbtList nodesList = new NbtList();
        for (ResourceNode<?> node : nodes) {
            NbtCompound nodeNbt = new NbtCompound();

            nodeNbt.putInt("x", node.getCentre().getX());
            nodeNbt.putInt("y", node.getCentre().getY());
            nodeNbt.putInt("z", node.getCentre().getZ());
            nodeNbt.putString("resource", node.getResource().identifier().toString());
            nodeNbt.putInt("purity", node.getPurity().getIndex());
            nodeNbt.putBoolean("obstructed", node.isObstructed());
            nodeNbt.putInt("total_harvests", node.getTotalHarvests());
            nodesList.add(nodeNbt);
        }
        nbt.put("nodes", nodesList);
        return nbt;
    }

    public static ResourceNodesState createFromNbt(NbtCompound nbt) {
        ResourceNodesState state = new ResourceNodesState();
        NbtList nodesList = nbt.getList("nodes", NbtElement.COMPOUND_TYPE);

        for (NbtElement element : nodesList) {
            NbtCompound nodeNbt = (NbtCompound) element;
            BlockPos pos = new BlockPos(
                    nodeNbt.getInt("x"),
                    nodeNbt.getInt("y"),
                    nodeNbt.getInt("z")
            );
            ResourcePurity purity = ResourcePurity.indexed(nodeNbt.getInt("purity"));
            Resource resource = CaRegistries.RESOURCE.get(Identifier.of(nodeNbt.getString("resource")));
            boolean obstructed = nodeNbt.getBoolean("obstructed");
            int totalHarvests = nodeNbt.getInt("total_harvests");

            ResourceNode<?> node = new ResourceNode<>(resource, purity, pos, obstructed, totalHarvests);
            state.nodes.add(node);
        }
        return state;
    }

    public List<ResourceNode<?>> getNodes() {
        return nodes;
    }
}
