package com.gabe.main;

import java.util.ArrayList;

/**
 * Created by Gabriel on 2020-06-18.
 */
public abstract class GameRunnable {
    
    private static ArrayList<GameRunnable> activeRunnables = new ArrayList<>();
    private static ArrayList<GameRunnable> removeQueuedRunnables = new ArrayList<>();
    private int delay;
    
    public void runTaskLater(int delay) {
        this.delay = delay;
        activeRunnables.add(this);
    }
    
    public static void tick() {
        for(GameRunnable runnable: activeRunnables) {
            runnable.delay--;
            if(runnable.delay == 0) {
                runnable.run();
                removeQueuedRunnables.add(runnable);
            }
        }
        activeRunnables.removeAll(removeQueuedRunnables);
        removeQueuedRunnables.clear();
    }
    
    public abstract void run();
}
