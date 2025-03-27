package com.artur114.ultradiarrheanote.common.events.managers;

import com.artur114.ultradiarrheanote.common.util.data.WorldData;
import com.artur114.ultradiarrheanote.common.util.nbt.IReadFromNBT;
import com.artur114.ultradiarrheanote.common.util.nbt.IWriteToNBT;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.event.FMLServerStoppingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.ArrayList;
import java.util.Iterator;

public class TimerTasksManager {
    private final ArrayList<ITask> TASKS_BUFFER = new ArrayList<>();
    private final ArrayList<ITask> TASKS = new ArrayList<>();
    private boolean isIterating = false;

    public void tickEventServerTickEvent(TickEvent.ServerTickEvent e) {
        if (e.phase == TickEvent.Phase.END || TASKS.isEmpty()) {
            return;
        }

        Iterator<ITask> iterator = TASKS.iterator();

        isIterating = true;
        while (iterator.hasNext()) {
            ITask task = iterator.next();
            task.tick();

            if (task.isFinish()) {
                iterator.remove();
            }
        }
        isIterating = false;

        if (!TASKS_BUFFER.isEmpty()) {
            TASKS.addAll(TASKS_BUFFER);
            TASKS_BUFFER.clear();
        }
    }

    public void fMLServerStoppingEvent(FMLServerStoppingEvent e) {
        TASKS.clear();
    }

    public void fMLServerStartingEvent(FMLServerStartingEvent e) {
        try {
            this.load(WorldData.get().data.getTagList("tasks", 10));
        } catch (Exception ex) {
            new RuntimeException(ex).printStackTrace(System.err);
        }
    }


    public void worldEventSave(WorldEvent.Save e) {
        if (!e.getWorld().isRemote && !TASKS.isEmpty()) {
            NBTTagList list = new NBTTagList();
            for (ITask task : TASKS) {
                if (task.canWriteToNBT()) {
                    list.appendTag(((IWriteToNBT) task).writeToNBT(new NBTTagCompound()));
                }
            }
            WorldData.get().data.setTag("tasks", list);
            WorldData.get().markDirty();
        }
    }

    @SuppressWarnings("unchecked")
    private void load(NBTTagList list) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        for (int i = 0; i != list.tagCount(); i++) {
            NBTTagCompound nbt = list.getCompoundTagAt(i);
            Class<ITask> clas = (Class<ITask>) Class.forName(nbt.getString("className"));
            ITask task = clas.newInstance();
            ((IReadFromNBT) task).readFromNBT(nbt);
            TASKS.add(task);
        }
    }

    public void addTask(ITask task) {
        if (isIterating) {
            TASKS_BUFFER.add(task);
        } else {
            TASKS.add(task);
        }
    }

    public void addTask(int time, Runnable finish) {
        this.addTask(new RunnableTask(finish, time));
    }

    private static class RunnableTask implements ITask {
        private boolean isFinish = false;
        private final Runnable finish;
        private final int maxTime;
        private int time;

        protected RunnableTask(Runnable finish, int time) {
            this.finish = finish;
            this.maxTime = time;
        }

        @Override
        public void tick() {
            if (time >= maxTime) {
                finish.run();
                isFinish = true;
            }

            if (!isFinish) {
                time++;
            }
        }

        @Override
        public boolean isFinish() {
            return isFinish;
        }
    }

    public interface ITask {
        boolean isFinish();
        void tick();

        default boolean canWriteToNBT() {return false;}
    }

    public interface ITaskCanSave extends ITask, IReadFromNBT, IWriteToNBT {
        @Override
        default boolean canWriteToNBT() {return true;}
    }
}
