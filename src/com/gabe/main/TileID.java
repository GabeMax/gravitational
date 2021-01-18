package com.gabe.main;

import com.gabe.main.imagehandling.ImageStorage;

import java.awt.image.BufferedImage;

/**
 * Created by Gabriel on 2020-05-12.
 */
public enum TileID {
    
    Air(false, false, null, null),
    CollidableAir(true, false, null, null),
    BasicGround(true, false, null, ImageStorage.tileDisplay),
    BasicSlope(false, true, new Slope(0, 31, 31, 31, 31, 0, true), ImageStorage.tileSlope),
    AdvancedSlope(false, true, new Slope(0, 31, 31, 31, 0, 0,true), null),
    RoofSlope(false, true, new Slope(0, 0, 31, 0, 31, 31, true), null),
    ShapelessSlope(false, true, new Slope(0, 10, 15, 31, 31, 0, true), null),
    AlternateRoofSlope(false, true, new Slope(0, 0, 31, 0, 0 ,31, true), null);
    
    private boolean isCollidable;
    private boolean isSlope;
    private Slope slope;
    private BufferedImage image;
    
    TileID(boolean isCollidable, boolean isSlope, Slope slope, BufferedImage image) {
        this.isCollidable = isCollidable;
        this.isSlope = isSlope;
        this.slope = slope;
        this.image = image;
    }
    
    public boolean isCollidable() {
        return isCollidable;
    }
    
    public boolean isSlope() {
        return isSlope;
    }
    
    public Slope getSlope() {
        return slope;
    }
    
    public BufferedImage getImage() {
        return image;
    }
}
