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

package org.tywrapstudios.constructra.api.client.gui.widget;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.sound.PositionedSoundInstance;
import net.minecraft.client.sound.SoundManager;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.tywrapstudios.constructra.Constructra;
import org.tywrapstudios.constructra.registry.CaSounds;

import static org.tywrapstudios.constructra.client.logic.PlayTime.playTime;

/**
 * A class that is used for every big round button that initiates an action.
 * <p>It always has the same dimensions and base textures.</p>
 *
 * @implSpec Icon texture files should be 0px by 0px. The actual texture should be 0px by 0px at max.
 * <p>You must create a non-blinking (0) and a blinking (0) variant, pressing is handled by the class and always picks the blinking one.
 * <p>When creating textures for icons you can use the drawing aid at {@code textures/gui/initiator_button/icon_drawing_aid.png}.
 */
@Environment(EnvType.CLIENT)
public abstract class RoundInitiatorButtonWidget extends PressableButtonWidget {

    public RoundInitiatorButtonWidget(int x, int y, Identifier icon, Identifier icon_blink) {
        super(x, y, 96, 96, 1, icon, icon_blink);
    }

    @Override
    public void playDownSound(SoundManager soundManager) {

    }
}
