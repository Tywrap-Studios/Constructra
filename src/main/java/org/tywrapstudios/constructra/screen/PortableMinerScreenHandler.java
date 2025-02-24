package org.tywrapstudios.constructra.screen;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.Slot;
import net.minecraft.util.math.BlockPos;
import org.tywrapstudios.constructra.api.screen.DataScreenHandler;
import org.tywrapstudios.constructra.registry.CaScreenHandlers;

public class PortableMinerScreenHandler extends DataScreenHandler<BlockPos> {
    private final Inventory inventory;

    public PortableMinerScreenHandler(int syncId, PlayerInventory playerInventory, BlockPos data) {
        this(syncId, playerInventory, new SimpleInventory(1), data);
    }

    public PortableMinerScreenHandler(int syncId, PlayerInventory playerInventory, Inventory inventory, BlockPos data) {
        super(CaScreenHandlers.PORTABLE_MINER_HANDLER, syncId, data);
        checkSize(inventory, 1);
        this.inventory = inventory;
        inventory.onOpen(playerInventory.player);

        this.addSlot(new Slot(inventory, 0, 80, 35));

        this.addPlayerSlots(playerInventory, 8, 84);
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return this.inventory.canPlayerUse(player);
    }

    @Override
    public ItemStack quickMove(PlayerEntity player, int invSlot) {
        ItemStack newStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(invSlot);
        if (slot != null && slot.hasStack()) {
            ItemStack originalStack = slot.getStack();
            newStack = originalStack.copy();
            if (invSlot < this.inventory.size()) {
                if (!this.insertItem(originalStack, this.inventory.size(), this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.insertItem(originalStack, 0, this.inventory.size(), false)) {
                return ItemStack.EMPTY;
            }

            if (originalStack.isEmpty()) {
                slot.setStack(ItemStack.EMPTY);
            } else {
                slot.markDirty();
            }
        }

        return newStack;
    }
}
