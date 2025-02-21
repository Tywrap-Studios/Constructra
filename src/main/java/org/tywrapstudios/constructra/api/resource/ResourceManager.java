package org.tywrapstudios.constructra.api.resource;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.minecraft.block.Block;
import net.minecraft.item.ItemConvertible;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.tywrapstudios.constructra.Constructra;
import org.tywrapstudios.constructra.registry.CaRegistries;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * Class Related to everything that needs to be managed in terms of Resources.
 */
public class ResourceManager {
    /**
     * Inner class dedicated to the world's network of {@link ResourceNode}{@code s}.
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
         * @see #add(Resource, BlockPos, boolean, World)
         * @see #add(Resource, BlockPos, World)
         * @param node the Node to add.
         * @param world the world to add it to, preferably a {@link ServerWorld}.
         */
        public static void add(ResourceNode<?> node, World world) {
            if (world instanceof ServerWorld serverWorld) {
                ResourceNodesState state = getOrCreateState(serverWorld);
                node.createOriginBlock(world);
                state.getNodes().add(node);
                state.markDirty();
            }
        }

        public static <T extends Resource> void add(T resource, BlockPos pos, World world) {
            ResourceNode<T> node = new ResourceNode<>(resource, pos);
            add(node, world);
        }

        public static <T extends Resource> void add(T resource, BlockPos pos, boolean obstructed, World world) {
            ResourceNode<T> node = new ResourceNode<>(resource, pos, obstructed);
            add(node, world);
        }

        /**
         * Checks if there is a Node at a position, and if so, returns it.
         * @param pos the position to check for.
         * @param world the {@link ServerWorld} to check in.
         * @return if present, the {@link ResourceNode}.
         */
        @Nullable
        public static ResourceNode<?> get(BlockPos pos, ServerWorld world) {
            ResourceNodesState state = getOrCreateState(world);
            for (ResourceNode<?> node : state.getNodes()) {
                if (node.getCentre().equals(pos)) {
                    return node;
                }
            }
            return null;
        }

        public static ResourceNode<?> get(int x, int y, int z, ServerWorld world) {
            return get(new BlockPos(x, y, z), world);
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
         * @param foundNode this allows you to run a certain method every time a Node is found and removed. Can be null in order to not do anything.
         * @return a list of all the Nodes purged.
         */
        public static List<ResourceNode<?>> purge(BlockPos centre, int range, boolean destroyBlock, ServerWorld world, @Nullable Consumer<ResourceNode<?>> foundNode) {
            List<ResourceNode<?>> removal = new ArrayList<>();

            ResourceNodesState state = getOrCreateState(world);
            Constructra.LOGGER.info("Attempting purge at: " + centre + " with range " + range);
            for (ResourceNode<?> node : state.getNodes()) {
                boolean isWithinDistance = node.getCentre().isWithinDistance(centre, range);
                Constructra.LOGGER.debug("  Checking for purge at " + node.getCentre());
                Constructra.LOGGER.debug("  isWithinDistance: " + isWithinDistance + " for range " + range);
                if (isWithinDistance) {
                    removal.add(node);
                    if (destroyBlock) world.breakBlock(node.getCentre(), false);
                    if (foundNode != null) foundNode.accept(node);
                    Constructra.LOGGER.warn("   Marked ResourceNode for removal at " + node.getCentre());
                }
            }
            state.markDirty();
            state.getNodes().removeAll(removal);
            return removal;
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
            if (!block.equals(node.getResource().getHarvestBlock())) {
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
        }
    }

    /**
     * Internal class dedicated for Resource Registry related handling.
     */
    public static class Registries {
        public static RegistryKey<Resource> key(Identifier id) {
            return RegistryKey.of(CaRegistries.Keys.RESOURCE, id);
        }

        public static Resource get(Identifier id) {
            return CaRegistries.RESOURCE.get(id);
        }

        public static Resource register(ItemConvertible retrievableItem, ResourceRarity rarity, Block harvestBlock, Identifier identifier) {
            return register(new ImplementedResource(retrievableItem, rarity, harvestBlock, identifier));
        }

        public static Resource register(Resource resource) {
            return Registry.register(CaRegistries.RESOURCE, resource.getIdentifier(), resource);
        }
    }
}
