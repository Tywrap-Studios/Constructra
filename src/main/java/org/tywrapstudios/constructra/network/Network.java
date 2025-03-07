package org.tywrapstudios.constructra.network;

import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.World;
import org.tywrapstudios.constructra.Constructra;
import org.tywrapstudios.constructra.api.annotation.Unstable;
import org.tywrapstudios.constructra.api.resource.ResourceHarvestTracker;
import org.tywrapstudios.constructra.api.resource.ResourceManager;
import org.tywrapstudios.constructra.api.resource.ResourceNode;
import org.tywrapstudios.constructra.network.payload.*;
import org.tywrapstudios.constructra.util.Util;

public class Network {
    public static void initialize() {
        PayloadTypeRegistry.playC2S().register(NodeQueryC2SPayload.ID, NodeQueryC2SPayload.CODEC);
        PayloadTypeRegistry.playS2C().register(NodeQueryS2CPayload.ID, NodeQueryS2CPayload.CODEC);
        PayloadTypeRegistry.playC2S().register(HarvestStartEventC2SPayload.ID, HarvestStartEventC2SPayload.CODEC);
        PayloadTypeRegistry.playC2S().register(HarvestEndEventC2SPayload.ID, HarvestEndEventC2SPayload.CODEC);
        PayloadTypeRegistry.playC2S().register(PickupRequestC2SPayload.ID, PickupRequestC2SPayload.CODEC);
        PayloadTypeRegistry.playC2S().register(GrabRequestC2SPayload.ID, GrabRequestC2SPayload.CODEC);

        /*
         * Sends a node back to the client, so it can render a HUD visual.
         */
        ServerPlayNetworking.registerGlobalReceiver(NodeQueryC2SPayload.ID, (load, ctx) -> {
            ResourceNode<?> node = ResourceManager.Nodes.get(load.pos(), ctx.server().getOverworld());
            if (node != null) {
                ServerPlayNetworking.send(ctx.player(), new NodeQueryS2CPayload(node));
            }
        });

        /*
         * Starts tracking a player for harvesting a node.
         */
        ServerPlayNetworking.registerGlobalReceiver(HarvestStartEventC2SPayload.ID, (load, ctx) -> ctx.server()
                .execute(() -> ResourceHarvestTracker.startHarvesting(ctx.player(), load.pos())));

        /*
         * Stops tracking a player for harvesting a node.
         */
        ServerPlayNetworking.registerGlobalReceiver(HarvestEndEventC2SPayload.ID, (load, ctx) -> ctx.server()
                .execute(() -> ResourceHarvestTracker.stopHarvesting(ctx.player())));

        /*
         * Picks up a machine at a certain position in a certain world.
         */
        ServerPlayNetworking.registerGlobalReceiver(PickupRequestC2SPayload.ID, (load, ctx) -> {
            World world = ctx.server().getWorld(RegistryKey.of(RegistryKeys.WORLD, load.worldIdentifier()));
            if (world != null) {
                world.breakBlock(load.pos(), true);
            }
        });

        /*
         * Grabs all the items from the inventory of a HandledScreen and attempts to give them to a player.
         */
        ServerPlayNetworking.registerGlobalReceiver(GrabRequestC2SPayload.ID, (load, ctx) -> {
            ServerPlayerEntity player = ctx.player();
            ScreenHandler handler = player.currentScreenHandler;

            if (handler.syncId != load.syncId()) {
                Constructra.LOGGER.error("ScreenHandler Sync ID did not match up for Grab All request from: " + player.getName());
                return;
            }

            for (int i = 0; i < handler.slots.size(); i++) {
                Slot slot = handler.slots.get(i);
                if (!slot.hasStack()) continue;
                if (slot.inventory instanceof PlayerInventory) continue;

                handler.quickMove(player, i);
                slot.markDirty();
                handler.updateToClient();
            }
        });

        /*
         * Grabs all the items from the inventory of a player and attempts to give insert them into the slots of a HandledScreen.
         */
        ServerPlayNetworking.registerGlobalReceiver(InsertRequestC2SPayload.ID, (load, ctx) -> {
            @Unstable("May not work as intended")
            ServerPlayerEntity player = ctx.player();
            ScreenHandler handler = player.currentScreenHandler;

            if (handler.syncId != load.syncId()) {
                Constructra.LOGGER.error("ScreenHandler Sync ID did not match up for Grab All request from: " + player.getName());
                return;
            }

            PlayerInventory playerInventory = player.getInventory();
            for (int s = 0; s < handler.slots.size(); s++) {
                Slot slot = handler.slots.get(s);

                if (!slot.hasStack()) {
                    for (ItemStack stack : playerInventory.main) {
                        if (stack == ItemStack.EMPTY) continue;
                        slot.insertStack(stack);
                        playerInventory.updateItems();
                        handler.updateToClient();
                    }
                } else {
                    ItemStack type = slot.getStack();
                    for (ItemStack stack : playerInventory.main) {
                        if (stack.equals(type)) {
                            slot.insertStack(stack);
                            playerInventory.updateItems();
                            handler.updateToClient();
                            break;
                        }
                    }
                }
            }
        });

        Util.logInitialisation();
    }
}
