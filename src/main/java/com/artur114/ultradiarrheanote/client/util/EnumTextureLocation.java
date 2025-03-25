package com.artur114.ultradiarrheanote.client.util;

import com.artur114.ultradiarrheanote.main.MainUDN;
import net.minecraft.util.ResourceLocation;

public enum EnumTextureLocation {
    BLOCKS_PATH(MainUDN.MODID, "textures/blocks"),
    ITEMS_PATH(MainUDN.MODID, "textures/items"),
    GUI_PATH(MainUDN.MODID, "textures/gui"),
    GUI_BUTTON_PATH(MainUDN.MODID, "textures/gui/button"),
    GUI_CONTAINER_PATH(MainUDN.MODID, "textures/gui/container"),
    GUI_GIF_PATH(MainUDN.MODID, "textures/gui/gif"),
    PARTICLE_PATH(MainUDN.MODID, "textures/particle"),
    MISC_PATH(MainUDN.MODID, "textures/misc");

    private final String modId;
    private final String path;
    private final String fullPath;

    EnumTextureLocation(String modId, String path) {
        this.fullPath = modId + ":" + path;
        this.modId = modId;
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    public String getFullPath() {
        return fullPath;
    }

    public ResourceLocation getRL(String additionalPath) {
        if (!additionalPath.startsWith("/")) additionalPath = "/" + additionalPath;
        if (!additionalPath.endsWith(".png")) additionalPath = additionalPath + ".png";
        return new ResourceLocation(modId, path + additionalPath);
    }

    public String getPathNotTextures(String additionalPath) {
        if (!additionalPath.startsWith("/")) additionalPath = "/" + additionalPath;
        return fullPath.replaceAll("textures/", "") + additionalPath;
    }
}
