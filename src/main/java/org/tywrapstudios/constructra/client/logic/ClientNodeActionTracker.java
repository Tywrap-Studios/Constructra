package org.tywrapstudios.constructra.client.logic;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.tywrapstudios.constructra.Constructra;
import org.tywrapstudios.constructra.api.resource.v1.ResourceNode;
import org.tywrapstudios.constructra.network.payload.HarvestEndEventC2SPayload;
import org.tywrapstudios.constructra.network.payload.HarvestStartEventC2SPayload;
import org.tywrapstudios.constructra.network.payload.NodeQueryC2SPayload;
import org.tywrapstudios.constructra.network.payload.NodeQueryS2CPayload;

@Environment(EnvType.CLIENT)
public class ClientNodeActionTracker {
    public static boolean HARVESTING_NODE = false;
    public static boolean WATCHING_NODE = false;
    @Nullable
    public static ResourceNode<?> CURRENT_NODE = null;
    @NotNull
    public static ResourceNode<?> LAST_NODE = ResourceNode.createDefaulted();

    public static void initializeClient() {
        ClientTickEvents.END_CLIENT_TICK.register(tickedClient -> {
            HitResult hit = tickedClient.crosshairTarget;
            if (hit == null || tickedClient.world == null) return;
            if (hit.getType().equals(HitResult.Type.BLOCK)) {
                BlockPos pos = ((BlockHitResult)hit).getBlockPos();
                boolean currentIsLast = CURRENT_NODE == LAST_NODE;
                boolean lastNodeBlockMatches = tickedClient.world.getBlockState(pos).getBlock().equals(LAST_NODE.getResource().getHarvestBlock());
                if (!currentIsLast) {
                    ClientPlayNetworking.send(new NodeQueryC2SPayload(pos));
                } else if (!lastNodeBlockMatches) {
                    CURRENT_NODE = null;
                }

                if (CURRENT_NODE != null) {
                    LAST_NODE = CURRENT_NODE;
                    WATCHING_NODE = true;
                }
            } else {
                CURRENT_NODE = null;
            }
        });

        ClientPlayNetworking.registerGlobalReceiver(NodeQueryS2CPayload.ID, (load, ctx) -> CURRENT_NODE = load.node());

        ClientTickEvents.END_CLIENT_TICK.register(tickedClient -> {
            if (tickedClient.options.attackKey.isPressed() && WATCHING_NODE) {
                if (HARVESTING_NODE) return;
                if (CURRENT_NODE == null) return;
                HARVESTING_NODE = true;

                ClientPlayNetworking.send(new HarvestStartEventC2SPayload(CURRENT_NODE.getCentre()));
                Constructra.LOGGER.debug("Harvest Start Event: " + HARVESTING_NODE);
            } else {
                if (HARVESTING_NODE) {
                    HARVESTING_NODE = false;
                    ClientPlayNetworking.send(new HarvestEndEventC2SPayload(1));
                    Constructra.LOGGER.debug("Harvest End Event: " + HARVESTING_NODE);
                }
            }
        });
    }
}
