package com.gabe.main;

import java.awt.*;
import java.util.LinkedList;

/**
 * Created by Gabriel on 2020-04-23.
 */
public class Handler {
    LinkedList<GameObject> object = new LinkedList<>();
    
    public void tick() {
        //System.out.println(object.size());
        for(int i = 0; i < object.size(); i++) {
            GameObject entity = object.get(i);
            entity.tick();
            
        }
    }
    
    public void render(Graphics g) {
        for(int i = 0; i < object.size(); i++) {
            GameObject entity = object.get(i);
            entity.render(g);
        }
    }
    
    public void addObject(GameObject object) {
        this.object.add(object);
    }
    
    public void removeObject(GameObject object) {
        this.object.remove(object);
    }
}
