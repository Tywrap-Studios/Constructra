package org.tywrapstudios.constructra.api.resource.v1;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.block.Block;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.tywrapstudios.constructra.Constructra;
import org.tywrapstudios.constructra.network.payload.HarvestEndEventC2SPayload;
import org.tywrapstudios.constructra.network.payload.HarvestStartEventC2SPayload;
import org.tywrapstudios.constructra.network.payload.NodeQueryC2SPayload;
import org.tywrapstudios.constructra.network.payload.NodeQueryS2CPayload;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * Class Related to everything that needs to be managed in terms of Resources.
 */
public class ResourceManager {
    /**
     * Inner class dedicated to specifically the network of {@link ResourceNode}{@code s}.
     */
    public static class Nodes {
        /**
         * Get or create a {@link ResourceNodesState}, aka fetch all the nodes from the .dat file in {@code run/saves/WORLD/data}
         * @param world the ServerWorld to fetch the State from.
         * @return said {@link ResourceNodesState}.
         */
        public static ResourceNodesState getOrCreateState(ServerWorld world) {
            return world.getPersistentStateManager().getOrCreate(ResourceNodesState.TYPE, ResourceNodesState.STORAGE_ID);
        }

        /**
         * Adds a node to the network.
         * @see #addNode(Resource, BlockPos, boolean, World)
         * @see #addNode(Resource, BlockPos, World)
         * @param node the Node to add.
         * @param world the world to add it to, preferably a {@link ServerWorld}.
         */
        public static void addNode(ResourceNode<?> node, World world) {
            if (world instanceof ServerWorld serverWorld) {
                ResourceNodesState state = getOrCreateState(serverWorld);
                node.createOriginBlock(world);
                state.getNodes().add(node);
                state.markDirty();
            }
        }

        public static <T extends Resource> void addNode(T resource, BlockPos pos, World world) {
            ResourceNode<T> node = new ResourceNode<>(resource, pos);
            addNode(node, world);
        }

        public static <T extends Resource> void addNode(T resource, BlockPos pos, boolean obstructed, World world) {
            ResourceNode<T> node = new ResourceNode<>(resource, pos, obstructed);
            addNode(node, world);
        }

        /**
         * Checks if there is a Node at a position, and if so, returns it.
         * @param pos the position to check for.
         * @param world the {@link ServerWorld} to check in.
         * @return if present, the {@link ResourceNode}.
         */
        @Nullable
        public static ResourceNode<?> getAtPos(BlockPos pos, ServerWorld world) {
            ResourceNodesState state = getOrCreateState(world);
            for (ResourceNode<?> node : state.getNodes()) {
                if (node.getCentre().equals(pos)) {
                    return node;
                }
            }
            return null;
        }

        public static ResourceNode<?> getAtPos(int x, int y, int z, ServerWorld world) {
            return getAtPos(new BlockPos(x, y, z), world);
        }

        /**
         * Checks whether a {@link BlockPos} is at a Node.
         * @param pos the position to check for.
         * @param world the {@link ServerWorld} to check in.
         * @return whether there is a Node at the position. (boolean)
         */
        public static boolean isInNode(BlockPos pos, ServerWorld world) {
            ResourceNodesState state = getOrCreateState(world);
            for (ResourceNode<?> node : state.getNodes()) {
                if (node.getCentre().isWithinDistance(pos, 1)) {
                    return true;
                }
            }
            return false;
        }

        /**
         * (Mass) Remove Nodes from the network.
         * @param centre the centre of where we should start searching
         * @param range the range of the search, in blocks.
         * @param destroyBlock whether we should also destroy the block inside the Node.
         * @param world the {@link ServerWorld} to check in.
         * @param runWhenFound this allows you to run a certain method every time a Node is found and removed. Can be null in order to not do anything.
         * @return a list of all the Nodes purged.
         */
        public static List<ResourceNode<?>> purge(BlockPos centre, int range, boolean destroyBlock, ServerWorld world, @Nullable Consumer<ResourceNode<?>> runWhenFound) {
            List<ResourceNode<?>> REMOVAL = new ArrayList<>();

            ResourceNodesState state = getOrCreateState(world);
            List<ResourceNode<?>> purgedNodes = new ArrayList<>();
            Constructra.LOGGER.info("Attempting purge at: " + centre + " with range " + range);
            for (ResourceNode<?> node : state.getNodes()) {
                boolean isWithinDistance = node.getCentre().isWithinDistance(centre, range);
                Constructra.LOGGER.debug("Checking for purge at " + node.getCentre());
                Constructra.LOGGER.debug("isWithinDistance: " + isWithinDistance + " for range " + range);
                if (isWithinDistance) {
                    REMOVAL.add(node);
                    purgedNodes.add(node);
                    if (destroyBlock) world.breakBlock(node.getCentre(), false);
                    if (runWhenFound != null) runWhenFound.accept(node);
                    Constructra.LOGGER.warn("Marked ResourceNode for removal at " + node.getCentre());
                }
            }
            state.markDirty();
            state.getNodes().removeAll(REMOVAL);
            return purgedNodes;
        }

        public static List<ResourceNode<?>> purge(BlockPos centre, int range, boolean destroyBlock, ServerWorld world) {
            return purge(centre, range, destroyBlock, world, null);
        }

        /**
         * Remove ALL The Nodes from the network.
         * @param reason the reason why you're doing this.
         * @param world the {@link ServerWorld} to check in.
         */
        public static void flush(String reason, ServerWorld world) {
            ResourceNodesState state = getOrCreateState(world);
            for (ResourceNode<?> node : state.getNodes()) {
                world.breakBlock(node.getCentre(), false);
                state.getNodes().remove(node);
            }
            if (!state.getNodes().isEmpty()) {
                state.getNodes().clear();
                Constructra.LOGGER.error("The NodesState still had Nodes inside it even though we flushed through all of them, this should not happen.");
            }
            Constructra.LOGGER.warn("Flushed all resource nodes: " + reason);
        }

        public static void flush(ServerWorld world) {
            flush("Unknown reason", world);
        }

        private static void tick(ServerWorld serverWorld) {
            ResourceNodesState state = getOrCreateState(serverWorld);
            List<ResourceNode<?>> REMOVAL = new ArrayList<>();

            for (ResourceNode<?> node : state.getNodes()) {
                BlockPos pos = node.getCentre();
                verifyNode(node, serverWorld, pos, REMOVAL);

                if (Constructra.config().resources.visualize_centres) {
                    serverWorld.spawnParticles(ParticleTypes.SCRAPE, pos.getX(), pos.getY(), pos.getZ(), 10, 0, 0, 0, 0);
                    serverWorld.spawnParticles(ParticleTypes.SCRAPE, pos.getX()+1, pos.getY(), pos.getZ(), 10, 0, 0, 0, 0);
                    serverWorld.spawnParticles(ParticleTypes.SCRAPE, pos.getX()+1, pos.getY(), pos.getZ()+1, 10, 0, 0, 0, 0);
                    serverWorld.spawnParticles(ParticleTypes.SCRAPE, pos.getX(), pos.getY(), pos.getZ()+1, 10, 0, 0, 0, 0);
                    serverWorld.spawnParticles(ParticleTypes.SCRAPE, pos.getX(), pos.getY()+1, pos.getZ(), 10, 0, 0, 0, 0);
                    serverWorld.spawnParticles(ParticleTypes.SCRAPE, pos.getX()+1, pos.getY()+1, pos.getZ(), 10, 0, 0, 0, 0);
                    serverWorld.spawnParticles(ParticleTypes.SCRAPE, pos.getX()+1, pos.getY()+1, pos.getZ()+1, 10, 0, 0, 0, 0);
                    serverWorld.spawnParticles(ParticleTypes.SCRAPE, pos.getX(), pos.getY()+1, pos.getZ()+1, 10, 0, 0, 0, 0);
                }
            }

            state.getNodes().removeAll(REMOVAL);
        }

        private static void verifyNode(ResourceNode<?> node, ServerWorld world, BlockPos pos, List<ResourceNode<?>> removal) {
            if (world.getRegistryKey() != World.OVERWORLD) {
                Constructra.LOGGER.debug("ServerWorld for verifyNode isn't of RegistryKey Overworld. Skipping.");
                //Constructra.LOGGER.error("Found ResourceNode that isn't in Overworld, marked for removal. This should not happen, please report this.");
                return;
            }
            Block block = world.getBlockState(pos).getBlock();
            if (!block.equals(node.getResource().harvestBlock())) {
                removal.add(node);
                Constructra.LOGGER.warn("Marked ResourceNode for removal due to block mismatch at " + pos);
            }
        }

        public static void initializeServer() {
            ServerTickEvents.END_WORLD_TICK.register(ResourceManager.Nodes::tick);
            ServerTickEvents.START_SERVER_TICK.register(ResourceHarvestTracker::tick);
            PlayerBlockBreakEvents.BEFORE.register((world, playerEntity, blockPos, blockState, blockEntity) -> {
                if (world instanceof ServerWorld serverWorld) {
                    if (playerEntity.isCreative()) {
                        return true;
                    }
                    return !isInNode(blockPos, serverWorld);
                }
                return true;
            });

            PayloadTypeRegistry.playC2S().register(NodeQueryC2SPayload.ID, NodeQueryC2SPayload.CODEC);
            PayloadTypeRegistry.playS2C().register(NodeQueryS2CPayload.ID, NodeQueryS2CPayload.CODEC);
            PayloadTypeRegistry.playC2S().register(HarvestStartEventC2SPayload.ID, HarvestStartEventC2SPayload.CODEC);
            PayloadTypeRegistry.playC2S().register(HarvestEndEventC2SPayload.ID, HarvestEndEventC2SPayload.CODEC);

            ServerPlayNetworking.registerGlobalReceiver(NodeQueryC2SPayload.ID, (load, ctx) -> {
                ResourceNode<?> node = ResourceManager.Nodes.getAtPos(load.pos(), ctx.server().getOverworld());
                if (node != null) {
                    ServerPlayNetworking.send(ctx.player(), new NodeQueryS2CPayload(node));
                }
            });

            ServerPlayNetworking.registerGlobalReceiver(HarvestStartEventC2SPayload.ID, (load, ctx) -> {
                ctx.server().execute(() -> ResourceHarvestTracker.startHarvesting(ctx.player(), load.pos()));
            });

            ServerPlayNetworking.registerGlobalReceiver(HarvestEndEventC2SPayload.ID, (load, ctx) -> ctx.server()
                    .execute(() -> ResourceHarvestTracker.stopHarvesting(ctx.player())));
        }
    }
}
