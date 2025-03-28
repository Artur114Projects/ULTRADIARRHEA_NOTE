package com.artur114.ultradiarrheanote.common.util.nbt;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public interface IWriteToNBT {
    NBTTagCompound writeToNBT(NBTTagCompound nbt);

    static NBTTagList getNBTListAsCollection(Collection<? extends IWriteToNBT> collection) {
        NBTTagList list = new NBTTagList();

        for (IWriteToNBT write : collection) {
            list.appendTag(write.writeToNBT(new NBTTagCompound()));
        }

        return list;
    }

    static <T> List<T> initObjectsAsNBTList(NBTTagList list, Class<T> objClass) throws NullPointerException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        if (list == null || objClass == null) {
            throw new NullPointerException();
        }

        List<T> ret = new ArrayList<>(list.tagCount());

        for (int i = 0; i != list.tagCount(); i++) {
            NBTTagCompound tag = list.getCompoundTagAt(i);
            Constructor<T> constructor = objClass.getDeclaredConstructor(NBTTagCompound.class);
            constructor.setAccessible(true);
            ret.add(constructor.newInstance(tag));
        }

        return ret;
    }

    @SuppressWarnings("unchecked")
    static Object initObjFromString(NBTTagCompound data) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
        return initObj(((Class<IReadFromNBT>) Class.forName(data.getString("className"))), data);
    }


    static <T extends IReadFromNBT> T initObj(Class<T> objClass, NBTTagCompound data) throws InstantiationException, IllegalAccessException {
        T readable = objClass.newInstance();
        readable.readFromNBT(data);
        return readable;
    }
}
