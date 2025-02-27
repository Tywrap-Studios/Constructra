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

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import org.tywrapstudios.constructra.Constructra;

import java.util.HashMap;
import java.util.Map;

/**
 * Used to track which {@link PlayerEntity} is mining at which {@link BlockPos}.
 */
public class ResourceHarvestTracker {
    private static final Map<PlayerEntity, BlockPos> ACTIVELY_HARVESTING = new HashMap<>();
    private static int TIME_OF_LAST_HARVEST = 0;

    /**
     * Mark a player as Harvesting.
     * @param player the player.
     * @param pos the {@link BlockPos} at which the node is.
     */
    public static void startHarvesting(PlayerEntity player, BlockPos pos) {
        ACTIVELY_HARVESTING.put(player, pos);
    }

    /**
     * Unmark a player as Harvesting.
     * @param player the player.
     */
    public static void stopHarvesting(PlayerEntity player) {
        ACTIVELY_HARVESTING.remove(player);
    }

    public static void tick(MinecraftServer server) {
        for (Map.Entry<PlayerEntity, BlockPos> entry : ACTIVELY_HARVESTING.entrySet()) {
            PlayerEntity player = entry.getKey();
            BlockPos pos = entry.getValue();

            ResourceNode<?> node = ResourceManager.Nodes.get(pos, (ServerWorld)player.getWorld());
            if (node != null) {
                if (node.tryHarvest((ServerWorld) player.getWorld(), new ResourceNode.HarvestSource<>(player))) {
                    int currentTime = server.getTicks();
                    int ticks = currentTime - TIME_OF_LAST_HARVEST;
                    TIME_OF_LAST_HARVEST = currentTime;
                    Constructra.LOGGER.debug("Harvesting took " + ticks + " ticks (" + (float) ticks/20 + " seconds)");
                }
            }
        }
    }
}
