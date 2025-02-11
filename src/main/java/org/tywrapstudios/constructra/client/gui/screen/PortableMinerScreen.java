package org.tywrapstudios.constructra.client.gui.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.Slot;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.tywrapstudios.constructra.Constructra;
import org.tywrapstudios.constructra.client.gui.widget.big_button.GrabIconWidget;
import org.tywrapstudios.constructra.client.gui.widget.big_button.PickupIconWidget;
import org.tywrapstudios.constructra.network.payload.PortableMinerPickupRequestC2SPayload;
import org.tywrapstudios.constructra.screen.PortableMinerScreenHandler;

@Environment(EnvType.CLIENT)
public class PortableMinerScreen extends HandledScreen<PortableMinerScreenHandler> {
    private static final Identifier TEXTURE = Constructra.id("textures/gui/portable_miner_gui.png");

    public PortableMinerScreen(PortableMinerScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
    }

    @Override
    protected void drawBackground(DrawContext context, float delta, int mouseX, int mouseY) {
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        int x = (width - backgroundWidth) / 2;
        int y = (height - backgroundHeight) / 2;
        context.drawTexture(RenderLayer::getGuiTextured, TEXTURE, x, y, 0, 0, backgroundWidth, backgroundHeight, 256, 256);
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        renderBackground(context, mouseX, mouseY, delta);
        super.render(context, mouseX, mouseY, delta);
        drawMouseoverTooltip(context, mouseX, mouseY);
    }

    @Override
    protected void init() {
        super.init();
        playerInventoryTitleY = 10000;
        titleX = (backgroundWidth - textRenderer.getWidth(title)) / 2;
        int x = (width - backgroundWidth) / 2;
        int y = (height - backgroundHeight) / 2 + 28;

        PickupIconWidget widget = new PickupIconWidget(x + 112, y) {
            @Override
            public void runAction() {
                if (client != null && client.world != null) ClientPlayNetworking.send(new PortableMinerPickupRequestC2SPayload(handler.data, client.world.getRegistryKey().getValue()));
                close();
            }
        };

        GrabIconWidget widget1 = new GrabIconWidget(x + 20, y) {
            @Override
            public void runAction() {
                if (client.world == null || client.player == null) { close(); return; }
//                PortableMinerBlockEntity entity = (PortableMinerBlockEntity) client.world.getBlockEntity(handler.data);
//                if (entity != null) {
//                    ItemStack itemStack1 = entity.removeStack(1);
//                    client.player.giveItemStack(itemStack1);
//                    Constructra.LOGGER.debug("HIT: " + entity);
//                } else Constructra.LOGGER.debug("BRUSH: " + handler.data);
//                handler.quickMove(client.player, 0);
                Slot slot = handler.getSlot(0);
                ItemStack stack = slot.takeStack(slot.getMaxItemCount());
                slot.markDirty();
                Constructra.LOGGER.debug("Slot: " + slot + " Stack: " + stack);
                        //client.player.giveItemStack(stack);
                Constructra.LOGGER.debug("Slot: " + slot + " Stack: " + stack);
            }
        };
        addDrawableChild(widget);
        addDrawableChild(widget1);
    }
}
