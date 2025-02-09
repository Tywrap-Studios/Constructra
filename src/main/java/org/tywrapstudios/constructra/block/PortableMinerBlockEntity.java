package org.tywrapstudios.constructra.block;

import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.tywrapstudios.constructra.api.inventory.v1.ImplementedInventory;
import org.tywrapstudios.constructra.api.resource.v1.ResourceManager;
import org.tywrapstudios.constructra.api.resource.v1.ResourceNode;
import org.tywrapstudios.constructra.registry.CaBlockEntities;

public class PortableMinerBlockEntity extends BlockEntity implements ExtendedScreenHandlerFactory<ServerPlayerEntity>, ImplementedInventory {
    private final DefaultedList<ItemStack> contents = DefaultedList.ofSize(1, ItemStack.EMPTY);

    public PortableMinerBlockEntity(BlockPos pos, BlockState state) {
        super(CaBlockEntities.PORTABLE_MINER_ENTITY, pos, state);
    }

    @Override
    public ServerPlayerEntity getScreenOpeningData(ServerPlayerEntity serverPlayerEntity) {
        return serverPlayerEntity;
    }

    @Override
    public Text getDisplayName() {
        return Text.translatable("container.constructra.portable_miner");
    }

    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        return null;
    }

    @Override
    public DefaultedList<ItemStack> getItems() {
        return contents;
    }

    public void tick(World world, BlockPos blockPos, BlockState state, PortableMinerBlockEntity entity) {
        BlockPos underneath = blockPos.down();
        if (world instanceof ServerWorld serverWorld) {
            ResourceNode<?> node = ResourceManager.Nodes.getAtPos(underneath, serverWorld);
            if (node != null) {
                node.tryHarvest(serverWorld, itemStack -> {
                    ItemStack current = this.getStack(0);
                    if (current.getItem() != itemStack.getItem()) return;
                    if (current.isEmpty()) this.setStack(0, itemStack);
                    else this.setStack(0, current.copyWithCount(current.getCount() + itemStack.getCount()));
                });
            }
        }
    }
}
