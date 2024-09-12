package com.strajag.brick_breaker.tools;

public class Timer {

    private volatile long delay;
    private volatile TimerListener timerListener;
    private volatile boolean isRunning = false;
    private volatile boolean isAlive = false;
    private final Timer timer = this;

    public Timer(int delay, TimerListener timerListener) {
        this.delay = delay;
        this.timerListener = timerListener;
    }

    public synchronized void start() {
        if (isRunning) { return; }
        if (delay < 0) { return; }
        if (timerListener == null) { return; }
        isAlive = true;

        new Thread(() -> {
            isRunning = true;
            long startTime;
            long waitTime;
            while (isAlive) {
                startTime = System.currentTimeMillis();
                timerListener.timerPerformed(timer);
                waitTime = System.currentTimeMillis() - startTime;
                waitTime = delay - waitTime;
                try {
                    if (waitTime > 0) {
                        Thread.sleep(waitTime);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //while (System.currentTimeMillis() - startTime < delay) Thread.onSpinWait();
            }
            isRunning = false;
        }).start();

        while (!isRunning) Thread.onSpinWait();
    }

    public synchronized void stop() {
        if (!isRunning) { return; }
        isAlive = false;

        while (isRunning) Thread.onSpinWait();
    }

    public synchronized void setDelay(int delay) {
        if (delay < 0) { return; }
        this.delay = delay;
    }

    public synchronized void setTimerListener(TimerListener timerListener) {
        if (timerListener == null) { return; }
        this.timerListener = timerListener;
    }

    public long getDelay() {
        return delay;
    }

    public TimerListener getTimerListener() {
        return timerListener;
    }

    public boolean isRunning() {
        return isRunning;
    }
}
