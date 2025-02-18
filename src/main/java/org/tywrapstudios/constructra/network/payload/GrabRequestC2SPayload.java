package org.tywrapstudios.constructra.network.payload;

import net.minecraft.item.ItemStack;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import org.tywrapstudios.constructra.network.NetworkConstants;

public record GrabRequestC2SPayload(ItemStack stack) implements CustomPayload {
    public static final Id<GrabRequestC2SPayload> ID = new Id<>(NetworkConstants.GRAB_ALL_REQUEST);
    public static final PacketCodec<RegistryByteBuf, GrabRequestC2SPayload> CODEC = PacketCodec.tuple(
                    ItemStack.PACKET_CODEC, GrabRequestC2SPayload::stack, GrabRequestC2SPayload::new);

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
}
