/*
 * MIT License
 *
 * Copyright (c) 2025 Tywrap Studios;
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package org.tywrapstudios.constructra.api.resource;

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
            nodeNbt.putString("resource", node.getResource().getIdentifier().toString());
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
