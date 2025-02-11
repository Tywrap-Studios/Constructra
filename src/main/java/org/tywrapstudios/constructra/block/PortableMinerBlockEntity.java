package org.tywrapstudios.constructra.block;

import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.SimpleInventory;
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
import net.minecraft.world.World;
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
        return new PortableMinerScreenHandler(syncId, playerInventory, this);
    }

    @Override
    public BlockPos getScreenOpeningData(ServerPlayerEntity player) {
        return this.getPos();
    }

    @Override
    public int getMaxCountPerStack() {
        return 64;
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
                    if (!canInsertItemIntoSlot(new SimpleInventory(entity.size()), itemStack.getItem())) {
                        state.with(PortableMinerBlock.ACTIVE, false);
                        return;
                    }
                    if (current.isEmpty()) {
                        if (!state.get(PortableMinerBlock.ACTIVE)) state.with(PortableMinerBlock.ACTIVE, true);
                        entity.setStack(0, itemStack);
                    }
                    else {
                        if (!state.get(PortableMinerBlock.ACTIVE)) state.with(PortableMinerBlock.ACTIVE, true);
                        entity.setStack(0, current.copyWithCount(current.getCount() + itemStack.getCount()));
                    }
                });
            }
        }
    }

    private static boolean canInsertItemIntoSlot(SimpleInventory inventory, Item output) {
        return inventory.getStack(0).getItem() == output || inventory.getStack(0).isEmpty();
    }
}
