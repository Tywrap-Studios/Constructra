package org.tywrapstudios.constructra.registry;

import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.resource.featuretoggle.FeatureSet;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.math.BlockPos;
import org.tywrapstudios.constructra.Constructra;
import org.tywrapstudios.constructra.screen.PortableMinerScreenHandler;
import org.tywrapstudios.constructra.util.Util;

public class CaScreenHandlers {
    public static final ExtendedScreenHandlerType<PortableMinerScreenHandler, BlockPos> PORTABLE_MINER_HANDLER;

    static {
        PORTABLE_MINER_HANDLER = createExtended("portable_miner_gui", PortableMinerScreenHandler::new, BlockPos.PACKET_CODEC);
    }

    private static <T extends ScreenHandler> ScreenHandlerType<T> create(String id, ScreenHandlerType.Factory<T> factory, FeatureSet requiredFeatures) {
        return Registry.register(Registries.SCREEN_HANDLER, Constructra.id(id), new ScreenHandlerType<>(factory, requiredFeatures));
    }

    private static <T extends ScreenHandler, D> ExtendedScreenHandlerType<T, D> createExtended(String id, ExtendedScreenHandlerType.ExtendedFactory<T, D> factory, PacketCodec<? super RegistryByteBuf, D> codec) {
        return Registry.register(Registries.SCREEN_HANDLER, Constructra.id(id), new ExtendedScreenHandlerType<>(factory, codec));
    }

    public static void register() {
        Util.logInitialisation();
    }
}
