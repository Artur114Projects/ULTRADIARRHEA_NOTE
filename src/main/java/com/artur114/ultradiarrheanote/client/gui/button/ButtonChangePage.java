package com.artur114.ultradiarrheanote.client.gui.button;

import com.artur114.ultradiarrheanote.client.util.EnumTextureLocation;
import com.artur114.ultradiarrheanote.client.util.RenderHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

public class ButtonChangePage extends GuiButton {
    protected final Type type;
    public ButtonChangePage(int buttonId, Type type, int x, int y) {
        super(buttonId, x, y, "");
        this.type = type;

        this.width = 18;
        this.height = 10;
    }

    @Override
    public void drawButton(Minecraft mc, int mouseX, int mouseY, float partialTicks) {
        if (this.enabled) {
            mc.getTextureManager().bindTexture(type.getTexture());
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            this.hovered = mouseX >= this.x && mouseY >= this.y && mouseX < this.x + this.width && mouseY < this.y + this.height;

            RenderHandler.renderTextureAtlas(this.x, this.y, 0, hovered ? 10 : 0, 18, 20, this.width, this.height);
        }
    }



    public enum Type {
        NEXT(0),
        PREV(1);

        private final ResourceLocation texture;

        Type(int id) {
            this.texture = EnumTextureLocation.GUI_BUTTON_PATH.getRL("button_change_page_" + id);
        }

        public ResourceLocation getTexture() {
            return texture;
        }
    }
}
