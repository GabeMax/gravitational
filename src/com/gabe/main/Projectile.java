package com.gabe.main;

import com.gabe.main.listeners.*;

import java.awt.*;

/**
 * Created by Gabriel on 2020-07-04.
 */
public class Projectile extends GameObject {
    
    public ProjectileID projectileID;
    public int despawnTime;
    
    public Projectile(int x, int y, ID id, Handler handler, ProjectileID projectileID) {
        super(x, y, id, handler);
        this.projectileID = projectileID;
        despawnTime = projectileID.getTimeUntilDespawn();
        
    }
    
    public void tick() {
        Collision.applySingleHitboxCollision(this);
        if(speedSideWays != 0) {
            speedSideWays*=0.95;
        }
        if(speedUpAndDown != 0) {
            speedUpAndDown*=0.95;
        } 
        despawnTime--;
        if(despawnTime == 0) {
            handler.removeObject(this);
        }
    }
    
    public void render(Graphics g) {
        g.drawImage(projectileID.getImage(), x-Camera.cameraX, y-Camera.cameraY, null);
    }

    public static void launchProjectile(GameObject gameObject, Projectile projectile, Point fromPoint, Point toPoint, int speed) {
       projectile.setXAndYVelocity(getXComponent(speed, getAngle(fromPoint, toPoint)), getYComponent(speed, getAngle(fromPoint, toPoint)));
       ListenerHandler.fireEvent(new ProjectileLaunchEvent(gameObject, projectile));
    }
    
    public void setXAndYVelocity(double speedSideWays, double speedUpAndDown) {
        this.speedSideWays = speedSideWays;
        this.speedUpAndDown = speedUpAndDown;
    }
    
    public static double getAngle(Point p1, Point p2) {
        System.out.println(Math.atan2(p2.y - p1.y, p2.x - p1.x));
        return Math.atan2(p2.y - p1.y, p2.x - p1.x);
    }
    
    public static double getXComponent(int magnitude, double angle) {
        return (magnitude)*Math.cos(angle);
    }
    
    public static double getYComponent(int magnitude, double angle) {
        return (magnitude)*Math.sin(angle);
    }
    
    public BoundingBoxPivot getBoundingBoxPivot() {
        return id.getBoundingBoxPivot();
    }
    
}
