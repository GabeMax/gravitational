package com.gabe.main.listeners;

/**
 * Created by Gabriel on 2020-07-06.
 */
public class Event {
    
    private boolean canceled = false;
    
    public boolean isCanceled() {
        return canceled;
    }
    
    public void setCanceled(boolean canceled) {
        this.canceled = canceled;
    }
}
