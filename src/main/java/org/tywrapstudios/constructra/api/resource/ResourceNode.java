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

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import org.tywrapstudios.constructra.Constructra;
import org.tywrapstudios.constructra.block.PortableMinerBlockEntity;
import org.tywrapstudios.constructra.registry.CaRegistries;
import org.tywrapstudios.constructra.registry.Resources;

import java.util.Random;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class ResourceNode<T extends Resource> {
    public static final PacketCodec<ByteBuf, ResourceNode<?>> PACKET_CODEC = new PacketCodec<>() {
        @Override
        public ResourceNode<?> decode(ByteBuf buf) {
            PacketByteBuf packetBuf = new PacketByteBuf(buf);
            Resource resource = CaRegistries.RESOURCE.get(Identifier.PACKET_CODEC.decode(buf));
            ResourcePurity purity = ResourcePurity.PACKET_CODEC.decode(buf);
            BlockPos pos = packetBuf.readBlockPos();
            boolean obstructed = packetBuf.readBoolean();
            int totalHarvests = packetBuf.readInt();

            return new ResourceNode<>(resource, purity, pos, obstructed, totalHarvests);
        }

        @Override
        public void encode(ByteBuf buf, ResourceNode<?> value) {
            PacketByteBuf packetBuf = new PacketByteBuf(buf);
            Identifier.PACKET_CODEC.encode(buf, value.getResource().getIdentifier());
            ResourcePurity.PACKET_CODEC.encode(buf, value.getPurity());
            packetBuf.writeBlockPos(value.getCentre());
            packetBuf.writeBoolean(value.isObstructed());
            packetBuf.writeInt(value.getTotalHarvests());
        }
    };

    private final T resource;
    private final ResourcePurity purity;
    private final BlockPos centre;
    private boolean obstructed;
    private int totalHarvests;
    private long lastHarvestTime;
    public static final Supplier<Integer> HARVESTS_TO_DE_OBSTRUCT = () -> Constructra.config().resources.consecutive_harvests_for_destruction;

    /**
     * Construct a new ResourceNode.
     * @see ResourceNode#ResourceNode(Resource, ResourcePurity, BlockPos, boolean)
     * @apiNote It will not be in the world unless added using {@link ResourceManager.Nodes#add(ResourceNode, World)}!
     * @param resource the {@link Resource} that will be held by the Node.
     * @param purity the {@link ResourcePurity} of the Node, which modifies the length of harvest. (or not if specified in the Config)
     * @param centre the position that represents the actual position of the Node, the "centre".
     * @param obstructed whether the Node needs is in an obstructed state.
     * @param totalHarvests the amount of total harvests this node has had, this is used for Networking and other Codec shenanigans.
     */
    protected ResourceNode(T resource, ResourcePurity purity, BlockPos centre, boolean obstructed, int totalHarvests) {
        this.resource = resource;
        this.purity = purity;
        this.centre = centre;
        this.obstructed = obstructed;
        this.totalHarvests = totalHarvests;
        this.lastHarvestTime = 0;
    }

    public ResourceNode(T resource, ResourcePurity purity, BlockPos centre, boolean obstructed) {
        this(resource, purity, centre, obstructed, 0);
    }

    public ResourceNode(T resource, BlockPos centre, boolean obstructed) {
        this(resource, ResourcePurity.random(), centre, obstructed);
    }

    public ResourceNode(T resource, BlockPos centre) {
        this(resource, centre, new Random().nextBoolean());
    }

    public T getResource() {
        assert this.resource != null : "Resource of Node is null: " + this;
        return this.resource;
    }

    /**
     * @return the Purity of the Node, following the Config Rules.
     */
    public ResourcePurity getPurity() {
        String conf = Constructra.config().resources.default_resource_purity;
        if (conf.equals("RANDOM")) {
            return this.purity;
        } else {
            return ResourcePurity.fromString(conf);
        }
    }

    /**
     * @return the "centre" of the Node, aka the position.
     */
    public BlockPos getCentre() {
        return this.centre;
    }

    /**
     * @return whether the Node is obstructed, following the Config Rules.
     */
    public boolean isObstructed() {
        return this.obstructed && Constructra.config().resources.do_obstructions;
    }

    /**
     * Makes the Node not obstructed.
     */
    public void deObstruct() {
        setObstructed(false);
    }

    /**
     * Sets the Node being obstructed to whatever you please.
     * @param obstructed whether to obstruct the Node.
     */
    public void setObstructed(boolean obstructed) {
        this.obstructed = obstructed;
    }

    /**
     * @return the last time at which the Node was harvested.
     */
    public long getLastHarvestTime() {
        return lastHarvestTime;
    }

    /**
     * @return the total amount of times the Node has been harvested, ever.
     */
    public int getTotalHarvests() {
        return totalHarvests;
    }

    /**
     * Puts a {@link net.minecraft.block.Block} at the centre of the Node that is the {@code harvestBlock} of the Resource.
     * @param world the world to put the Block in.
     * @return whether the placing was successful.
     */
    protected boolean createOriginBlock(World world) {
        try {
            Constructra.LOGGER.debug("Creating origin block for: " + this);
            world.setBlockState(centre, resource.getHarvestBlock().getDefaultState());
            return true;
        } catch (Exception e) {
            if (this.getResource() != null) {
                Constructra.LOGGER.error("Failed to place Origin for Resource: " + resource.getIdentifier());
            }
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Tries to harvest the Node, and do all handling for it accordingly.
     * @param world the {@link ServerWorld} handle the harvesting in.
     * @param harvestResult a Consumer that gives you the ItemStack of the harvest to do what you want with it.
     * @param harvestSource a HarvestSource which is used to verify whether the harvest should be valid.
     * @see #tryHarvest(ServerWorld, HarvestSource)
     * @return whether the harvest was successful
     */
    public boolean tryHarvest(ServerWorld world, Consumer<ItemStack> harvestResult, HarvestSource<?> harvestSource) {
        long currentTime = world.getTime();
        long harvestCooldown = (long)(20 * purity.getMiningTimeMultiplier()); // Convert purity to ticks

        if (currentTime - lastHarvestTime < harvestCooldown) {
            return false;
        }

        if (harvestSource.source() instanceof PortableMinerBlockEntity && isObstructed()) return false;

        ItemStack harvestStack = new ItemStack(resource.getRetrievableItem());
        harvestResult.accept(harvestStack);
        totalHarvests++;

        if (isObstructed()) {
            if (totalHarvests >= HARVESTS_TO_DE_OBSTRUCT.get()) {
                world.playSound(null,
                        centre.getX(),
                        centre.getY(),
                        centre.getZ(),
                        SoundEvents.ENTITY_ITEM_BREAK,
                        SoundCategory.BLOCKS,
                        2.0f, 1.7f);
                deObstruct();
            }
        }

        lastHarvestTime = currentTime;
        return true;
    }

    public <S extends LivingEntity> boolean tryHarvest(ServerWorld world, HarvestSource<S> harvestSource) {
        return tryHarvest(world, itemStack -> {
            ItemEntity itemEntity = new ItemEntity(world,
                    centre.getX() + 0.5,
                    centre.getY() + 1.0,
                    centre.getZ() + 0.5,
                    itemStack);
            world.spawnEntity(itemEntity);
        }, harvestSource);
    }

    /**
     * A record that is used by {@linkplain #tryHarvest(ServerWorld, Consumer, HarvestSource)} to validate if the source is even allowed to harvest in the first place.
     * @param source the source itself
     * @param <S> the type of the source
     */
    public record HarvestSource<S>(@NotNull S source) {
        public S getSource() {
            return source();
        }
    }

    /**
     * @return a ResourceNode that can be used as a "default" if you prefer not to {@code null} fields.
     */
    public static ResourceNode<?> createDefaulted() {
        return new ResourceNode<>(Resources.IRON, ResourcePurity.NONE, BlockPos.ORIGIN, false);
    }

    /**
     * @return a String merely containing the Node's Resource Identifier.
     * @see #toString()
     */
    public String toSimpleString() {
        return "ResourceNode{" +
                "resource=" + resource.getIdentifier() +
                "}";
    }

    @Override
    public String toString() {
        return "ResourceNode{" +
                "resource=" + resource +
                ", purity=" + purity +
                ", centre=" + centre +
                ", obstructed=" + obstructed +
                '}';
    }
}
