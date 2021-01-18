package com.gabe.main;

import java.awt.*;

/**
 * Created by Gabriel on 2020-04-26.
 */
public class HUD {
    
    public static int HEALTH = 100;
    
    public void tick() {
        HEALTH = Game.clamp(HEALTH, 0, 100);
    }
    
    public void render(Graphics g) {
        g.setColor(Color.gray);
        g.fillRect(15, 15, 200, 32);
        g.setColor(Color.red);
        g.fillRect(15, 15, HEALTH * 2, 32);
        g.setColor(Color.green);
        g.drawRect(15, 15, 200, 32);
    }
}
