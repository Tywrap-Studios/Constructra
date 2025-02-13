package org.tywrapstudios.constructra.api.client.gui.widget;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.sound.PositionedSoundInstance;
import net.minecraft.client.sound.SoundManager;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.tywrapstudios.constructra.Constructra;

import static org.tywrapstudios.constructra.client.logic.PlayTime.playTime;

@Environment(EnvType.CLIENT)
public abstract class ActionButtonWidget extends ClickableWidget {
    protected final Identifier texture = Constructra.id("textures/gui/big_button/big_button_base_blink0.png");
    protected final Identifier texture_blink = Constructra.id("textures/gui/big_button/big_button_base_blink1.png");
    protected final Identifier texture_pressed = Constructra.id("textures/gui/big_button/big_button_base_pressed.png");
    protected Identifier current;
    protected MinecraftClient client;
    public static final int width = 44;
    public static final int height = 30;
    protected final Identifier icon;
    protected final Identifier icon_blink;
    protected Identifier current_icon;
    private double cachedTickAmount;

    public ActionButtonWidget(int x, int y, Identifier icon, Identifier icon_blink) {
        super(x, y, width, height, Text.empty());
        this.icon = icon;
        this.icon_blink = icon_blink;
        this.current_icon = this.icon;
        this.current = this.texture;
        this.client = MinecraftClient.getInstance();
    }

    @Override
    protected void renderWidget(DrawContext context, int mouseX, int mouseY, float delta) {
        if (playTime % 20 == 0 && cachedTickAmount != playTime) {
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
        int icon_y = current == texture_pressed ? getY()+2 : getY();
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

    @Override
    public void playDownSound(SoundManager soundManager) {

    }

    public abstract void runAction();
}
