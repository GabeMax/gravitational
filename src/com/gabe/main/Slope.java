package com.gabe.main;

/**
 * Created by Gabriel on 2020-06-28.
 */
public class Slope {
    
    public int xOffset1, yOffset1, xOffset2, yOffset2, xOffset3, yOffset3;
    public boolean isClimbable;
    
    public Slope(int xOffset1, int yOffset1, int xOffset2, int yOffset2, int xOffset3, int yOffset3, boolean isClimbable) {
        this.xOffset1 = xOffset1;
        this.yOffset1 = yOffset1;
        this.xOffset2 = xOffset2;
        this.yOffset2 = yOffset2;
        this.xOffset3 = xOffset3;
        this.yOffset3 = yOffset3;
        this.isClimbable = isClimbable;
    }
}
