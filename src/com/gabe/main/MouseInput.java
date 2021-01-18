package com.gabe.main;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;

/**
 * Created by Gabriel on 2020-07-01.
 */
public class MouseInput extends MouseAdapter {
    
    public static Point mousePos;
    
    public static HashMap<Integer, Boolean> mouseButtons = new HashMap<>();
    
    public void mousePressed(MouseEvent e) {
        mouseButtons.put(e.getButton(), true);
        mousePos = e.getPoint();
    }
    
    public void mouseReleased(MouseEvent e) {
        mouseButtons.put(e.getButton(), false);
    }
    
    public void mouseEntered(MouseEvent e) {}
    
    public void mouseExited(MouseEvent e) {}
    
    public void mouseClicked(MouseEvent e) {}
    
    public void mouseMoved(MouseEvent e) {
        mousePos = e.getPoint();
    }
    
    public void mouseDragged(MouseEvent e) {
        mousePos = e.getPoint();
    }
    
    public static boolean isMouseButtonDown(int button) {
        Boolean down = mouseButtons.get(button);
        return down == null ? false : down;
    }
}
