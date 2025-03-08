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

package org.tywrapstudios.constructra.block;

import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.tywrapstudios.constructra.api.inventory.ImplementedInventory;
import org.tywrapstudios.constructra.api.resource.ResourceManager;
import org.tywrapstudios.constructra.api.resource.ResourceNode;
import org.tywrapstudios.constructra.registry.CaBlockEntities;
import org.tywrapstudios.constructra.screen.PortableMinerScreenHandler;

public class PortableMinerBlockEntity extends BlockEntity implements ExtendedScreenHandlerFactory<BlockPos>, ImplementedInventory {
    private final DefaultedList<ItemStack> contents = DefaultedList.ofSize(1, ItemStack.EMPTY);

    public PortableMinerBlockEntity(BlockPos pos, BlockState state) {
        super(CaBlockEntities.PORTABLE_MINER_ENTITY, pos, state);
    }

    @Override
    public DefaultedList<ItemStack> getItems() {
        return this.contents;
    }

    @Override
    public Text getDisplayName() {
        return Text.translatable("container.constructra.portable_miner").formatted(Formatting.GRAY);
    }

    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        return new PortableMinerScreenHandler(syncId, playerInventory, this, this.getPos());
    }

    @Override
    public BlockPos getScreenOpeningData(ServerPlayerEntity player) {
        return this.getPos();
    }

    @Override
    protected void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registries) {
        super.writeNbt(nbt, registries);
        Inventories.writeNbt(nbt, getItems(), registries);
    }

    @Override
    protected void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registries) {
        Inventories.readNbt(nbt, getItems(), registries);
        super.readNbt(nbt, registries);
    }

    public void tick(World world, BlockPos blockPos, BlockState state, PortableMinerBlockEntity entity) {
        BlockPos underneath = blockPos.down();
        if (world instanceof ServerWorld serverWorld) {
            ResourceNode<?> node = ResourceManager.Nodes.get(underneath, serverWorld);
            if (node != null) {
                node.tryHarvest(serverWorld, itemStack -> {
                    ItemStack current = entity.getStack(0);
                    if (!canInsertItemIntoSlot(this, itemStack.getItem())) {
                        state.with(PortableMinerBlock.ACTIVE, false);
                        return;
                    }
                    if (current.isEmpty()) {
                        state.with(PortableMinerBlock.ACTIVE, true);
                        entity.setStack(0, itemStack);
                    }
                    else {
                        state.with(PortableMinerBlock.ACTIVE, true);
                        entity.setStack(0, current.copyWithCount(current.getCount() + itemStack.getCount()));
                    }
                }, new ResourceNode.HarvestSource<>(this));
            }
        }
    }

    private static boolean canInsertItemIntoSlot(Inventory inventory, Item output) {
        return inventory.getStack(0).getItem() == output || inventory.getStack(0).isEmpty();
    }

    @Override
    public boolean canExtract(int slot, ItemStack stack, Direction side) {
        return false;
    }

    @Override
    public boolean canInsert(int slot, ItemStack stack, @Nullable Direction side) {
        return false;
    }

    @Override
    public int getMaxCountPerStack() {
        return 64;
    }
}
