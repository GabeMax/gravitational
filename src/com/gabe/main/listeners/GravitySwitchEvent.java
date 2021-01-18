package com.gabe.main.listeners;

import com.gabe.main.Direction;
import com.gabe.main.GameObject;

import java.awt.*;

/**
 * Created by Gabriel on 2020-07-05.
 */
public class GravitySwitchEvent extends Event {
    
    
    private GameObject gameObject;
    private Point location;
    private Direction direction;
    
    public GravitySwitchEvent(GameObject gameObject, Point location, Direction direction) {
        this.location = location;
        this.direction = direction;
        this.gameObject = gameObject;
    }
    
    public Point getLocation() {
        return location;
    }
    
    public Direction getDirection() {
        return direction;
    }

    public GameObject getGameObject() {
        return gameObject;
    }
}
