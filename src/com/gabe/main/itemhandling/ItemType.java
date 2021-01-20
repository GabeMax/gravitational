package com.gabe.main.itemhandling;

import com.gabe.main.imagehandling.ImageStorage;
import com.gabe.main.itemhandling.itemmethods.PistolMethod;

import java.awt.image.BufferedImage;

/**
 * Created by Gabriel on 2020-05-26.
 */
public enum ItemType {
    NONE(null, null, 0, null, null),
    FRUIT("Polar Star", "Weapon", 64, ImageStorage.simpleItem, null),
    STAR_FURY("Starfury", "Sword", 5, ImageStorage.grapeImage, null),
    POLAR_STAR("Polar star", "Basic gun", 1, ImageStorage.grapeImage, new PistolMethod())
    ;
    
    private String name;
    private String description;
    private int maxStackSize;
    private BufferedImage image;
    private AbstractItemMethod itemMethod;

    ItemType(String name, String descprtion, int maxStackSize, BufferedImage image, AbstractItemMethod itemMethod) {
        this.name = name;
        this.description = descprtion;
        this.maxStackSize = maxStackSize;
        this.image = image;
        this.itemMethod = itemMethod;
    }

    public String getName() {
        return name;
    }
    public String getDescription() {
        return description;
    }
    public int getMaxStackSize() {
        return maxStackSize;
    }
    public BufferedImage getImage() {
        return image;
    }
    public AbstractItemMethod getItemMethod() {
        return itemMethod;
    }
}
