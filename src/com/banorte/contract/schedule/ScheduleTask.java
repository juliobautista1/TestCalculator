package com.banorte.contract.schedule;

import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author MRIOS
 */
public class ScheduleTask implements Runnable {

    private Runnable runnable;
    private long lastExec;
    private boolean isRunning;
    private String taskName;
    private ScheduledFuture scheduledFuture;

    public ScheduleTask(String name, Runnable task) {
        taskName = name;
        runnable = task;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setScheduledFuture(ScheduledFuture f) {
        scheduledFuture = f;
    }

    public ScheduledFuture getScheduledFuture() {
		return scheduledFuture;
	}

	public long getDelay() {
        return scheduledFuture != null ? scheduledFuture.getDelay(TimeUnit.SECONDS) : 0;
    }

    public long getNextExec() {
        if (scheduledFuture != null) {
            long actual = System.currentTimeMillis();
            long delay = scheduledFuture.getDelay(TimeUnit.MILLISECONDS);
            return actual + delay;
        }
        return 0;
    }

    public void run() {
        synchronized (this) {
            lastExec = System.currentTimeMillis();
            isRunning = true;
            runnable.run();
            isRunning = false;
        }
    }

    public boolean isRunning() {
        return isRunning;
    }

    public long getLastExec() {
        return lastExec;
    }
}