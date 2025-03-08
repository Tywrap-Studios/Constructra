package org.tywrapstudios.constructra.network.payload;

import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.math.BlockPos;
import org.tywrapstudios.constructra.network.NetworkConstants;

public record HarvestStartEventC2SPayload(BlockPos pos) implements CustomPayload {
    public static final CustomPayload.Id<HarvestStartEventC2SPayload> ID = new CustomPayload.Id<>(NetworkConstants.HARVEST_START_EVENT);
    public static final PacketCodec<PacketByteBuf, HarvestStartEventC2SPayload> CODEC = PacketCodec.tuple(BlockPos.PACKET_CODEC, HarvestStartEventC2SPayload::pos, HarvestStartEventC2SPayload::new);

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
}
