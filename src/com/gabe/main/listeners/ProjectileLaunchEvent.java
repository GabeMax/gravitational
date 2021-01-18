package com.gabe.main.listeners;

import com.gabe.main.GameObject;
import com.gabe.main.Projectile;

/**
 * Created by Gabriel on 2020-07-28.
 */
public class ProjectileLaunchEvent extends Event {
    
    public GameObject gameObject;
    public Projectile projectile;
    
    public ProjectileLaunchEvent(GameObject gameObject, Projectile projectile) {
        this.gameObject = gameObject;
        this.projectile = projectile;
    }

    public GameObject getGameObject() {
        return gameObject;
    }

    public Projectile getProjectile() {
        return projectile;
    }
}
