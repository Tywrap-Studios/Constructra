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
