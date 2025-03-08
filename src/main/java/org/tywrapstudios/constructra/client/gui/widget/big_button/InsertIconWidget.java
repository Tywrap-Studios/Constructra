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

package org.tywrapstudios.constructra.client.gui.widget.big_button;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.client.gui.screen.narration.NarrationPart;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.tywrapstudios.constructra.Constructra;
import org.tywrapstudios.constructra.api.client.gui.widget.ActionButtonWidget;
import org.tywrapstudios.constructra.network.payload.InsertRequestC2SPayload;

public abstract class InsertIconWidget extends ActionButtonWidget {
    private static final Identifier ICON = Constructra.id("textures/gui/icons/insert_icon0.png");
    private static final Identifier ICON_BLINK = Constructra.id("textures/gui/icons/insert_icon1.png");

    public InsertIconWidget(int x, int y) {
        super(x, y, ICON, ICON_BLINK);
    }

    @Override
    protected void appendClickableNarrations(NarrationMessageBuilder builder) {
        builder.put(NarrationPart.USAGE, Text.translatable("narration.constructra.insert_button"));
    }

    public static <T extends ScreenHandler> GrabIconWidget builder(int x, int y, HandledScreen<T> screen) {
        return new GrabIconWidget(x, y) {
            @Override
            public void runAction() {
                T handler = screen.getScreenHandler();

                ClientPlayNetworking.send(new InsertRequestC2SPayload(handler.syncId));
            }
        };
    }
}
