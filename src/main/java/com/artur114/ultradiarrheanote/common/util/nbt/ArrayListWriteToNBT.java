package com.artur114.ultradiarrheanote.common.util.nbt;

import net.minecraft.nbt.NBTTagCompound;

import javax.annotation.Nullable;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class ArrayListWriteToNBT<T extends IIsNeedWriteToNBT> extends ArrayList<T> implements IWriteToNBT, IReadFromNBT {
    private NBTTagCompound currentTag = new NBTTagCompound();
    private boolean isChanged = true;
    private Class<T> objClass = null;

    public ArrayListWriteToNBT(Collection<? extends T> c) {
        super(c);
    }

    public ArrayListWriteToNBT(int initialCapacity) {
        super(initialCapacity);
    }

    public ArrayListWriteToNBT() {
        super();
    }

    public ArrayListWriteToNBT(Class<T> objClass) {
        this.objClass = objClass;
    }

    @Override
    public boolean add(T t) {
        isChanged = true;
        return super.add(t);
    }

    @Override
    public void add(int index, T element) {
        isChanged = true;
        super.add(index, element);
    }

    public void addAll(T[] values) {
        isChanged = true;
        Collections.addAll(this, values);
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        isChanged = true;
        return super.addAll(c);
    }

    @Override
    public T remove(int index) {
        isChanged = true;
        return super.remove(index);
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        isChanged = true;
        return super.removeAll(c);
    }

    @Override
    public T set(int index, T element) {
        isChanged = true;
        return super.set(index, element);
    }

    private boolean isAnyNeedWriteToNBT() {
        return this.stream().anyMatch(IIsNeedWriteToNBT::isNeedWriteToNBT);
    }

    /**
     @param notUse Will not be used, there should be null.
     @return new NBTTagCompound, do not change it!
     **/
    @Override
    public NBTTagCompound writeToNBT(@Nullable NBTTagCompound notUse) {
        if (isChanged || this.isAnyNeedWriteToNBT()) {
            NBTTagCompound nbt = new NBTTagCompound();
            for (int i = 0; i != this.size(); i++) {
                nbt.setTag(String.valueOf(i), this.get(i).writeToNBT(new NBTTagCompound()));
            }
            currentTag = nbt;
            isChanged = false;
        }
        return currentTag;
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        if (objClass == null) throw new NullPointerException();
        for (int i = 0; nbt.hasKey(String.valueOf(i)); i++) {
            try {
                NBTTagCompound compound = nbt.getCompoundTag(String.valueOf(i));
                T obj = objClass.newInstance();
                obj.readFromNBT(compound);
                this.add(obj);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        isChanged = true;
    }
}
