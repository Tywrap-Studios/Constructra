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
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.sound.PositionedSoundInstance;
import net.minecraft.client.sound.SoundInstance;
import net.minecraft.client.sound.SoundManager;
import net.minecraft.sound.SoundEvent;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import static org.tywrapstudios.constructra.client.logic.PlayTime.playTime;

@Environment(EnvType.CLIENT)
public abstract class PressableButtonWidget extends ClickableWidget {
    protected final Identifier texture;
    protected final Identifier texture_blink;
    protected final Identifier texture_pressed;
    protected Identifier current;
    protected MinecraftClient client;
    protected final int pressOffset;
    protected final Identifier icon;
    protected final Identifier icon_blink;
    protected Identifier current_icon;
    protected final int blinkTickInterval;
    private double cachedTickAmount;

    public PressableButtonWidget(int x, int y, int width, int height, int pressOffset, Identifier texture, Identifier texture_blink, Identifier texture_pressed, Identifier icon, Identifier icon_blink, int blinkTickInterval) {
        super(x, y, width, height, Text.empty());
        this.pressOffset = pressOffset;
        this.icon = icon;
        this.icon_blink = icon_blink;
        this.current_icon = this.icon;
        this.texture = texture;
        this.texture_blink = texture_blink;
        this.texture_pressed = texture_pressed;
        this.current = this.texture;
        this.client = MinecraftClient.getInstance();
        this.blinkTickInterval = blinkTickInterval;
    }

    @Override
    protected void renderWidget(DrawContext context, int mouseX, int mouseY, float delta) {
        if (playTime % this.blinkTickInterval == 0 && cachedTickAmount != playTime) {
            cachedTickAmount = playTime;
            if (current == texture) {
                current = texture_blink;
                current_icon = icon_blink;
            }
            else if (current == texture_blink) {
                current = texture;
                current_icon = icon;
            } else if (current == texture_pressed) {
                current_icon = icon_blink;
            }
        }
        int icon_y = current == texture_pressed ? getY()+pressOffset : getY();
        context.drawTexture(RenderLayer::getGuiTextured, current, getX(), getY(), 0, 0, width, height, width, height);
        context.drawTexture(RenderLayer::getGuiTextured, current_icon, getX(), icon_y, 0, 0, width, height, width, height);
    }

    @Override
    public void onClick(double mouseX, double mouseY) {
        current = texture_pressed;
        this.runAction();
        super.onClick(mouseX, mouseY);
    }

    @Override
    public void onRelease(double mouseX, double mouseY) {
        current = texture;
        super.onRelease(mouseX, mouseY);
    }

    public static SoundInstance clickSound(SoundEvent sound) {
        return clickSound(sound, 1.0F);
    }

    public static SoundInstance clickSound(SoundEvent sound, float pitch) {
        return PositionedSoundInstance.master(sound, pitch);
    }

    @Override
    public abstract void playDownSound(SoundManager soundManager);

    public abstract void runAction();
}
