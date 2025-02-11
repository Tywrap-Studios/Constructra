package org.tywrapstudios.constructra.network.payload;

import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import org.tywrapstudios.constructra.api.resource.ResourceNode;
import org.tywrapstudios.constructra.network.NetworkConstants;

public record NodeQueryS2CPayload(ResourceNode<?> node) implements CustomPayload {
    public static final Id<NodeQueryS2CPayload> ID = new Id<>(NetworkConstants.NODE_QUERY_REQUEST_S2C);
    public static final PacketCodec<RegistryByteBuf, NodeQueryS2CPayload> CODEC = PacketCodec.tuple(ResourceNode.PACKET_CODEC, NodeQueryS2CPayload::node, NodeQueryS2CPayload::new);

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
}
