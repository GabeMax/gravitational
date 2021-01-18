package com.gabe.main;

import com.gabe.main.listeners.ItemInteractEvent;
import com.gabe.main.listeners.ListenerHandler;

/**
 * Created by Gabriel on 2020-07-11.
 */
public abstract class LivingGameObject extends GameObject {
    
    public LivingGameObject(int x, int y, ID id, Handler handler) {
        super(x, y, id, handler);
        
    }
}
