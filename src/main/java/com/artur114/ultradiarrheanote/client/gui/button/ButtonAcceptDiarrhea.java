package com.artur114.ultradiarrheanote.client.gui.button;

import com.artur114.ultradiarrheanote.client.util.EnumTextureLocation;
import com.artur114.ultradiarrheanote.client.util.RenderHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;

public class ButtonAcceptDiarrhea extends GuiButton {
    private static final ResourceLocation TEXTURE = EnumTextureLocation.GUI_BUTTON_PATH.getRL("button_accept_diarrhea");

    public ButtonAcceptDiarrhea(int buttonId, int x, int y) {
        super(buttonId, x, y, "");

        this.width = 11;
        this.height = 10;
    }

    @Override
    public void drawButton(Minecraft mc, int mouseX, int mouseY, float partialTicks) {
        if (this.visible) {
            mc.getTextureManager().bindTexture(TEXTURE);
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            this.hovered = mouseX >= this.x && mouseY >= this.y && mouseX < this.x + this.width && mouseY < this.y + this.height;

            RenderHandler.renderTextureAtlas(this.x, this.y, 0, hovered ? 10 : 0, 11, 20, this.width, this.height);
        }
    }

    public void drawHoveredText(GuiScreen gui, int mouseX, int mouseY) {
        this.hovered = mouseX >= this.x && mouseY >= this.y && mouseX < this.x + this.width && mouseY < this.y + this.height;

        if (hovered) {
            gui.drawHoveringText(I18n.format("button.accept.diarrhea.text"), mouseX, mouseY);
        }
    }
}
