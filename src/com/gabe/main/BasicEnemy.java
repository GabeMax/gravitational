package com.gabe.main;

import java.awt.*;

/**
 * Created by Gabriel on 2020-04-23.
 */
public class BasicEnemy extends LivingGameObject {
    
    public BasicEnemy(int x, int y, ID id, Handler handler) {
        super(x, y, id, handler);
        speedSideWays = 0;
        speedUpAndDown = 0;
    }
    
    public void tick() {
        /*x += speedSideWays;
        y += speedUpAndDown; */
        speedSideWays = Game.clamp(speedSideWays, -5, 5);
        speedUpAndDown = Game.clamp(speedUpAndDown, -20, 20);
        //Collision.applySingleHitboxCollision(this);
        if(isWithinPlayerDistance()) { //Must be within certain tile distance of player to tick
            
        }
    }
    
    public void render(Graphics g) {
        g.setColor(Color.red);
        g.fillRect(x-Camera.cameraX, y-Camera.cameraY, 16, 48);
        drawBoundingBoxes(g);
    }
    
    public BoundingBoxPivot getBoundingBoxPivot() {
        return id.getBoundingBoxPivot();
    }
}
