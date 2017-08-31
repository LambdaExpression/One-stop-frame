package org.tcat.frame.component;

import java.util.UUID;

/**
 * Created by Lin on 2017/8/31.
 */
public abstract class TimerRunnable implements Runnable {

    private final String taskId;
    private boolean one_time = false;
    private DynamicTimer dynamicTimer;

    public TimerRunnable() {
        taskId = UUID.randomUUID().toString();
    }

    public final TimerRunnable setOneTime(DynamicTimer dynamicTimer) {
        this.dynamicTimer = dynamicTimer;
        one_time = true;
        return this;
    }

    @Override
    public final void run() {
        customize();
        if (one_time) {
            dynamicTimer.cancel(taskId);
        }
    }

    public abstract void customize();

    public final String getTaskId() {
        return taskId;
    }

}
