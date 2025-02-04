package org.tywrapstudios.constructra.network.payload;

import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import org.tywrapstudios.constructra.network.NetworkConstants;

public record HarvestEndEventC2SPayload(int i) implements CustomPayload {
    public static final Id<HarvestEndEventC2SPayload> ID = new Id<>(NetworkConstants.HARVEST_END_EVENT);
    public static final PacketCodec<PacketByteBuf, HarvestEndEventC2SPayload> CODEC = PacketCodec.tuple(PacketCodecs.INTEGER, HarvestEndEventC2SPayload::i, HarvestEndEventC2SPayload::new);

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
}
