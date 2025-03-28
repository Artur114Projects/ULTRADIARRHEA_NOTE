package com.artur114.ultradiarrheanote.client.gui;

import com.artur114.ultradiarrheanote.client.gui.button.ButtonAcceptDiarrhea;
import com.artur114.ultradiarrheanote.client.gui.button.ButtonChangePage;
import com.artur114.ultradiarrheanote.client.util.EnumTextureLocation;
import com.artur114.ultradiarrheanote.client.util.RenderHandler;
import com.artur114.ultradiarrheanote.common.item.ItemUltraDiarrheaNote;
import com.artur114.ultradiarrheanote.common.network.ServerPacketSyncDiarrheaNote;
import com.artur114.ultradiarrheanote.common.util.nbt.IIsNeedWriteToNBT;
import com.artur114.ultradiarrheanote.common.util.nbt.IWriteToNBT;
import com.artur114.ultradiarrheanote.main.MainUDN;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.util.ChatAllowedCharacters;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;

import java.io.IOException;
import java.util.Arrays;

public class GuiDiarrheaNote extends GuiScreen implements IWriteToNBT {
    private static final ResourceLocation PRINTING_LINE_CONTOUR = EnumTextureLocation.GUI_PATH.getRL("printing_line_contour");
    private static final ResourceLocation BACKGROUND = EnumTextureLocation.GUI_PATH.getRL("diarrhea_note_gui_background");
    private static final int PAGES_COUNT = 10;

    protected ButtonAcceptDiarrhea buttonAcceptDiarrhea;
    protected GuiButton buttonNextPage;
    protected GuiButton buttonPrevPage;
    public final EnumHand bookHand;
    private int currentPageId = 0;
    private Page currentPage;
    public int xSize = 280;
    public int ySize = 180;
    private Page[] pages;


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

        this.currentPage.draw();
        this.drawMick();

        this.buttonAcceptDiarrhea.drawHoveredText(this, mouseX, mouseY);
    }

    @Override
    public void updateScreen() {
        super.updateScreen();

        this.currentPage.update();
    }

    @Override
    public void onGuiClosed() {
        ItemStack stack = mc.player.getHeldItem(bookHand);

        if (stack.getItem() instanceof ItemUltraDiarrheaNote) {
            ((ItemUltraDiarrheaNote) stack.getItem()).closeBook(stack);
            this.currentPage.acceptLine();
            NBTTagCompound data = this.writeToNBT(new NBTTagCompound());
            stack.getOrCreateSubCompound(MainUDN.MODID).setTag("data", data);
            NBTTagCompound nbt = ServerPacketSyncDiarrheaNote.getBaseNBT(bookHand);
            nbt.setBoolean("openState", false);
            nbt.setTag("syncData", data);
            MainUDN.NETWORK.sendToServer(new ServerPacketSyncDiarrheaNote(nbt));
        }
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        if (GuiScreen.isKeyComboCtrlV(keyCode)) {
            this.currentPage.addToCurrentLine(GuiScreen.getClipboardString());
            return;
        }

        switch (keyCode) {
            case 28:
            case 156:
                this.currentPage.acceptLine();
                return;
            default:
            if (ChatAllowedCharacters.isAllowedCharacter(typedChar)) {
                this.currentPage.addToCurrentLine(Character.toString(typedChar));
            }
        }

        super.keyTyped(typedChar, keyCode);
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        switch (button.id) {
            case 0:
                currentPage.acceptLine();
                break;
            case 1:
                this.nextPage();
                break;
            case 2:
                this.prevPage();
                break;
        }
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
        NBTTagList list = new NBTTagList();
        for (int i = 0; i != pages.length; i++) {
            list.appendTag(pages[i].writeToNBT(new NBTTagCompound()));
        }
        nbt.setInteger("currentPage", currentPageId);
        nbt.setTag("pages", list);
        return nbt;
    }

    @Override
    public void initGui() {
        if (!this.playerHasBook()) {
            mc.displayGuiScreen(null);
            return;
        }
        NBTTagCompound nbt = mc.player.getHeldItem(bookHand).getOrCreateSubCompound(MainUDN.MODID).getCompoundTag("data");
        if (!nbt.hasKey("pagesCount")) nbt.setInteger("pagesCount", PAGES_COUNT);

        this.pages = new Page[nbt.getInteger("pagesCount")];
        NBTTagList pagesList = nbt.getTagList("pages", 10);

        this.buttonAcceptDiarrhea = new ButtonAcceptDiarrhea(0, 0, 0);
        this.buttonNextPage = new ButtonChangePage(1, ButtonChangePage.Type.NEXT, (this.width - xSize) / 2 + 237, (this.height - ySize) / 2 + 155);
        this.buttonPrevPage = new ButtonChangePage(2, ButtonChangePage.Type.PREV, (this.width - xSize) / 2 + 25, (this.height - ySize) / 2 + 155);
        for (int i = 0; i != pages.length; i++) {
            Page page = new Page(this, buttonAcceptDiarrhea);
            if (!pagesList.hasNoTags()) page.readFromNBT(pagesList.getCompoundTagAt(i));
            pages[i] = page;
        }
        this.currentPageId = nbt.getInteger("currentPage");
        this.currentPage = pages[this.currentPageId];
        this.currentPage.onPageEnable();
        this.updateButtonsState();

        this.addButton(buttonAcceptDiarrhea);
        this.addButton(buttonNextPage);
        this.addButton(buttonPrevPage);
    }

    private void nextPage() {
        if (!this.hasNextPage()) return;

        this.currentPageId++;
        this.currentPage.onPageDisable();
        this.currentPage = pages[currentPageId];
        this.currentPage.onPageEnable();
        this.updateButtonsState();
    }

    private boolean hasNextPage() {
        return currentPageId < (pages.length - 1);
    }

    private void prevPage() {
        if (!this.hasPrevPage()) return;

        this.currentPageId--;
        this.currentPage.onPageDisable();
        this.currentPage = pages[currentPageId];
        this.currentPage.onPageEnable();
        this.updateButtonsState();
    }

    private boolean hasPrevPage() {
        return currentPageId > 0;
    }

    private void updateButtonsState() {
        this.buttonNextPage.enabled = this.hasNextPage();
        this.buttonPrevPage.enabled = this.hasPrevPage();
    }

    private int getLocalX() {
        return (this.width - xSize) / 2;
    }

    private int getLocalY() {
        return (this.height - ySize) / 2;
    }

    private boolean playerHasBook() {
        return mc.player.getHeldItem(bookHand).getItem() instanceof ItemUltraDiarrheaNote;
    }

    private void onLineAccept(String lineText) {
        if (!this.playerHasBook()) {
            mc.displayGuiScreen(null);
            return;
        }

        NBTTagCompound nbt = ServerPacketSyncDiarrheaNote.getBaseNBT(bookHand);
        nbt.setString("kill", lineText);
        MainUDN.NETWORK.sendToServer(new ServerPacketSyncDiarrheaNote(nbt));
    }

    private void onPageFull() {
        this.nextPage();
    }

    private void drawBackground() {
        mc.getTextureManager().bindTexture(BACKGROUND);

        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);

        int x = (this.width - xSize) / 2;
        int y = (this.height - ySize) / 2;

        RenderHandler.renderPrimitive(x, x + xSize, y, y + ySize, 0, 1, 0, 1);
    }

    private void drawMick() {
        int page = (currentPageId + 1) * 2;
        String text0 = page - 1 + "/" + pages.length * 2;
        String text1 = page + "/" + pages.length * 2;

        int x0 = (131 - fontRenderer.getStringWidth(text0)) + this.getLocalX();
        int x1 = 150 + this.getLocalX();

        int y = 162 + this.getLocalY();

        fontRenderer.drawString(text0, x0, y, 0);
        fontRenderer.drawString(text1, x1, y, 0);
    }


    private static class Page implements IIsNeedWriteToNBT {
        protected final Minecraft mc = Minecraft.getMinecraft();
        protected final FontRenderer fontRenderer = mc.fontRenderer;
        public final String[] lines = new String[20];
        protected final GuiButton acceptButton;
        protected final GuiDiarrheaNote gui;
        protected int lastPrinting = 0;
        protected int tickCounter = 0;
        public int currentLine = 0;

        private Page(GuiDiarrheaNote gui, GuiButton acceptButton) {
            this.acceptButton = acceptButton;
            this.gui = gui;

            Arrays.fill(lines, "");
        }

        public void addToCurrentLine(String s) {
            if (this.isFull()) {
                return;
            }

            StringBuilder res = new StringBuilder((lines[currentLine] == null ? "" : lines[currentLine]) + s);

            while (fontRenderer.getStringWidth(res.toString()) > 92) {
                res.deleteCharAt(res.length() - 1);
            }

            this.lines[this.currentLine] = res.toString();
            this.lastPrinting = 0;
        }

        public void acceptLine() {
            if (this.isFull()) return;
            if (this.lines[currentLine].isEmpty()) return;

            this.gui.onLineAccept(lines[currentLine]);
            this.currentLine++;
            if (this.isFull()) gui.onPageFull();
            this.updateButtonPos();
        }

        public boolean isFull() {
            return !(currentLine < 20);
        }

        private void onPageEnable() {
            this.updateButtonPos();
        }

        private void onPageDisable() {
            this.acceptLine();
        }

        private void update() {
            this.tickCounter++;
            this.lastPrinting++;
        }

        private void draw() {
            this.drawLines();
        }

        private void drawLines() {
            for (int i = 0; i != lines.length; i++) {
                if (lines[i].isEmpty() && i != currentLine) {
                    continue;
                }

                int x = this.getDrawPosFromId(i, 'x', false);
                int y = this.getDrawPosFromId(i, 'y', false);

                if (i == currentLine) {
                    fontRenderer.drawString(lines[i] + this.getPrintingPrefix(), x, y, 0);
                    mc.getTextureManager().bindTexture(PRINTING_LINE_CONTOUR);
                    RenderHandler.renderPrimitive(x - 3, (x - 3) + 118, y - 3, (y - 3) + 14, 0, 1, 0, 1);
                } else {
                    fontRenderer.drawString(lines[i], x, y, 0);
                }
            }
        }

        private int getDrawPosFromId(int index, char direction, boolean local) {
            if (direction == 'x') {
                if (index < 10) {
                    return 18 + (local ? 0 : (gui.width - gui.xSize) / 2);
                } else {
                    return 152 + (local ? 0 : (gui.width - gui.xSize) / 2);
                }
            } else if (direction == 'y') {
                if (index < 10) {
                    return 22 + 12 * index + (local ? 0 : (gui.height - gui.ySize) / 2);
                } else {
                    return 22 + 12 * (index - 10) + (local ? 0 : (gui.height - gui.ySize) / 2);
                }
            } else {
                return 0;
            }
        }

        private String getPrintingPrefix() {
            if (lastPrinting < 10) {
                return "_";
            }

            return this.tickCounter / 6 % 2 == 0 ? "_" : "";
        }

        private void updateButtonPos() {
            if (this.isFull()) {
                acceptButton.x = Integer.MAX_VALUE;
                return;
            }

            acceptButton.x = this.getDrawPosFromId(currentLine, 'x', false) + 101;
            acceptButton.y = this.getDrawPosFromId(currentLine, 'y', false) - 1;
        }

        @Override
        public boolean isNeedWriteToNBT() {
            return true;
        }

        @Override
        public void readFromNBT(NBTTagCompound nbt) {
            if (!nbt.hasKey("lines")) return;

            NBTTagList list = nbt.getTagList("lines", 8);

            if (list.hasNoTags()) return;

            for (int i = 0; i != list.tagCount(); i++) {
                lines[i] = list.getStringTagAt(i);
            }

            this.currentLine = list.tagCount();
        }

        @Override
        public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
            NBTTagList list = new NBTTagList();
            for (int i = 0; i != currentLine; i++) {
                list.appendTag(new NBTTagString(lines[i]));
            }
            nbt.setTag("lines", list);
            return nbt;
        }
    }
}
