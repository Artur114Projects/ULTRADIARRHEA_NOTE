package com.artur114.ultradiarrheanote.common.util.data;

import com.artur114.ultradiarrheanote.main.MainUDN;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.storage.MapStorage;
import net.minecraft.world.storage.WorldSavedData;
import net.minecraftforge.fml.common.FMLCommonHandler;

import java.util.Objects;


public class WorldData extends WorldSavedData {

    public NBTTagCompound data = new NBTTagCompound();
    private final String dataKey = "data";
    public static final String DATA_NAME = MainUDN.MODID + "_data";

    public WorldData() {
        super(DATA_NAME);
    }

    public WorldData(String s) {
        super(s);
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        data = nbt.getCompoundTag(dataKey);
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        compound.setTag(dataKey, data);
        return compound;
    }

    public static WorldData get() {
        MapStorage storage = FMLCommonHandler.instance().getMinecraftServerInstance().getWorld(0).getMapStorage();
        WorldData instance = (WorldData) Objects.requireNonNull(storage).getOrLoadData(WorldData.class, DATA_NAME);

        if (instance == null) {
            instance = new WorldData();
            storage.setData(DATA_NAME, instance);
        }
        return instance;
    }
}
