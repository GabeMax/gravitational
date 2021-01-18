package com.gabe.main;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.HashMap;

/**
 * Created by Gabriel on 2020-04-23.
 */
public class KeyInput extends KeyAdapter {
    
    public static HashMap<Integer, Boolean> keys = new HashMap<>();
    
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        keys.put(key, true);
        if(key == KeyEvent.VK_F1) {
            System.exit(0);
        }
    }
    
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        keys.put(key, false);
    }
    
    public static boolean isKeyDown(int key) {
        Boolean down = keys.get(key);
        return down == null ? false : down;
    }
}
