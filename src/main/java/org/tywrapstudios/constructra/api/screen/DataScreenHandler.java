package org.tywrapstudios.constructra.api.screen;

import net.minecraft.screen.ScreenHandler;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType;

/**
 * A Type of ScreenHandler that can be used for ensuring there is additional synced data.
 * Supposed to be used with an {@link ExtendedScreenHandlerType} where the data of the handler is the same as here.
 * @param <D> the type of the data to store, is stored by the constructor.
 */
public abstract class DataScreenHandler<D> extends ScreenHandler {
    private final D data;

    protected DataScreenHandler(ExtendedScreenHandlerType<? extends DataScreenHandler<D>, D> type, int syncId, D data) {
        super(type, syncId);
        this.data = data;
    }

    /**
     * Returns the currently stored data, obviously given it exists.
     * @return the data
     */
    public D getData() {
        return this.data;
    }
}
