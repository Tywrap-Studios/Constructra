package org.tywrapstudios.constructra.network.payload;

import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import org.tywrapstudios.constructra.network.NetworkConstants;

public record PickupRequestC2SPayload(BlockPos pos, Identifier worldIdentifier) implements CustomPayload {
    public static final Id<PickupRequestC2SPayload> ID = new Id<>(NetworkConstants.PORTABLE_MINER_PICKUP_REQUEST);
    public static final PacketCodec<PacketByteBuf, PickupRequestC2SPayload> CODEC =
            PacketCodec.tuple(BlockPos.PACKET_CODEC, PickupRequestC2SPayload::pos, Identifier.PACKET_CODEC,
                    PickupRequestC2SPayload::worldIdentifier,  PickupRequestC2SPayload::new);

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
}
