package org.tywrapstudios.constructra.network;

import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.World;
import org.tywrapstudios.constructra.api.resource.ResourceHarvestTracker;
import org.tywrapstudios.constructra.api.resource.ResourceManager;
import org.tywrapstudios.constructra.api.resource.ResourceNode;
import org.tywrapstudios.constructra.network.payload.*;

public class Network {
    public static void initialize() {
        PayloadTypeRegistry.playC2S().register(NodeQueryC2SPayload.ID, NodeQueryC2SPayload.CODEC);
        PayloadTypeRegistry.playS2C().register(NodeQueryS2CPayload.ID, NodeQueryS2CPayload.CODEC);
        PayloadTypeRegistry.playC2S().register(HarvestStartEventC2SPayload.ID, HarvestStartEventC2SPayload.CODEC);
        PayloadTypeRegistry.playC2S().register(HarvestEndEventC2SPayload.ID, HarvestEndEventC2SPayload.CODEC);
        PayloadTypeRegistry.playC2S().register(PickupRequestC2SPayload.ID, PickupRequestC2SPayload.CODEC);
        PayloadTypeRegistry.playC2S().register(GrabRequestC2SPayload.ID, GrabRequestC2SPayload.CODEC);

        ServerPlayNetworking.registerGlobalReceiver(NodeQueryC2SPayload.ID, (load, ctx) -> {
            ResourceNode<?> node = ResourceManager.Nodes.get(load.pos(), ctx.server().getOverworld());
            if (node != null) {
                ServerPlayNetworking.send(ctx.player(), new NodeQueryS2CPayload(node));
            }
        });

        ServerPlayNetworking.registerGlobalReceiver(HarvestStartEventC2SPayload.ID, (load, ctx) -> ctx.server()
                .execute(() -> ResourceHarvestTracker.startHarvesting(ctx.player(), load.pos())));

        ServerPlayNetworking.registerGlobalReceiver(HarvestEndEventC2SPayload.ID, (load, ctx) -> ctx.server()
                .execute(() -> ResourceHarvestTracker.stopHarvesting(ctx.player())));

        ServerPlayNetworking.registerGlobalReceiver(PickupRequestC2SPayload.ID, (load, ctx) -> {
            World world = ctx.server().getWorld(RegistryKey.of(RegistryKeys.WORLD, load.worldIdentifier()));
            if (world != null) {
                world.breakBlock(load.pos(), true);
            }
        });

        ServerPlayNetworking.registerGlobalReceiver(GrabRequestC2SPayload.ID, (load, ctx) -> {
            ServerPlayerEntity player = ctx.player();

            player.giveOrDropStack(load.stack());
        });
    }
}
