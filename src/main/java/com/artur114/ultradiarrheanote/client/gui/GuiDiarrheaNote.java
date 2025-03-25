package com.artur114.ultradiarrheanote.client.gui;

import com.artur114.ultradiarrheanote.client.util.EnumTextureLocation;
import com.artur114.ultradiarrheanote.client.util.RenderHandler;
import com.artur114.ultradiarrheanote.common.init.InitItems;
import com.artur114.ultradiarrheanote.common.item.ItemUltraDiarrheaNote;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatAllowedCharacters;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import java.io.IOException;

public class GuiDiarrheaNote extends GuiScreen {
    private static final ResourceLocation BACKGROUND = EnumTextureLocation.GUI_PATH.getRL("diarrhea_note_gui_background");

    public String[] lines = new String[20];
    public final EnumHand bookHand;
    public int currentLine = 0;
    public int xSize = 292;
    public int ySize = 180;


    public GuiDiarrheaNote(EnumHand bookHand) {
        this.bookHand = bookHand;
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawBackground();

        super.drawScreen(mouseX, mouseY, partialTicks);

        this.drawLines();
    }

    @Override
    public void onGuiClosed() {
        ItemStack stack = mc.player.getHeldItem(bookHand);

        if (stack.getItem() instanceof ItemUltraDiarrheaNote) {
            ((ItemUltraDiarrheaNote) stack.getItem()).closeBook(stack);
        }
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        switch (keyCode)
        {
            case 28:
            case 156:
                currentLine++;
                return;
            default:
            if (ChatAllowedCharacters.isAllowedCharacter(typedChar)) {
                this.lineInsertIntoCurrent(Character.toString(typedChar));
            }
        }

        super.keyTyped(typedChar, keyCode);
    }

    private void drawBackground() {
        mc.getTextureManager().bindTexture(BACKGROUND);

        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);

        int x = (this.width - xSize) / 2;
        int y = (this.height - ySize) / 2;

        RenderHandler.renderPrimitive(x, x + xSize, y, y + ySize, 0, 1, 0, 1);
    }

    private void drawLines() {
        for (int i = 0; i != lines.length; i++) {
            if (lines[i] == null && i != currentLine) {
                continue;
            }

            int x;
            int y;

            if (i < 10) {
                x = 17;
                y = 22 + 12 * i;
            } else {
                x = 162;
                y = 22 + 12 * (i - 10);
            }

            fontRenderer.drawString(i == currentLine ? (lines[i] == null ? "" : lines[i]) + "_" : lines[i], (this.width - xSize) / 2 + x, (this.height - ySize) / 2 + y, 0);
        }
    }

    private void lineInsertIntoCurrent(String string) {
        StringBuilder res = new StringBuilder((lines[currentLine] == null ? "" : lines[currentLine]) + string);

        while (fontRenderer.getStringWidth(res.toString()) > 90) {
            res.deleteCharAt(res.length() - 1);
        }

        lines[currentLine] = res.toString();
    }
}
