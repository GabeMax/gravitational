package com.gabe.main;

import com.gabe.main.imagehandling.ImageStorage;

import java.awt.image.BufferedImage;

/**
 * Created by Gabriel on 2020-07-04.
 */
public enum ProjectileID {
    
    ARROW(ImageStorage.tileDisplay, 120, new BoundingBoxPivot(0, 0, 32, 32))
    ;
    
    private BufferedImage image;
    private int timeUntilDespawn;
    private BoundingBoxPivot boundingBoxPivot;
    
    ProjectileID(BufferedImage image, int timeUntilDespawn, BoundingBoxPivot boundingBoxPivot) {
        this.image = image;
        this.timeUntilDespawn = timeUntilDespawn;
        this.boundingBoxPivot = boundingBoxPivot;
    }
    
    public BufferedImage getImage() {
        return image;
    }
    public int getTimeUntilDespawn() {return timeUntilDespawn;}
    public BoundingBoxPivot getBoundingBoxPivot() {
        return boundingBoxPivot;
    }
}
