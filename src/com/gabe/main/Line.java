package com.gabe.main;

/**
 * Created by Gabriel on 2020-06-28.
 */
public class Line {
    
    private double startX, startY, endX, endY;
    
    public Line(double startX, double startY, double endX, double endY) {
        this.startX = startX;
        this.startY = startY;
        this.endX = endX;
        this.endY = endY;
    }
    
    public double getStartX() {
        return startX;
    }
    
    public double getStartY() {
        return startY;
    }
    
    public double getEndX() {
        return endX;
    }
    
    public double getEndY() {
        return endY;
    }
}
