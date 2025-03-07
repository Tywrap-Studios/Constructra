package org.tywrapstudios.constructra.network.payload;

import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import org.tywrapstudios.constructra.network.NetworkConstants;

public record InsertRequestC2SPayload(int syncId) implements CustomPayload {
    public static final Id<InsertRequestC2SPayload> ID = new Id<>(NetworkConstants.GRAB_ALL_REQUEST);
    public static final PacketCodec<RegistryByteBuf, InsertRequestC2SPayload> CODEC = PacketCodec.tuple(
            PacketCodecs.SYNC_ID, InsertRequestC2SPayload::syncId, InsertRequestC2SPayload::new);

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
}
