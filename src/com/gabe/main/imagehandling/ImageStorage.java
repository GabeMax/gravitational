package com.gabe.main.imagehandling;

import com.gabe.main.Game;
import com.gabe.main.Tile;
import com.gabe.main.itemhandling.Inventory;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

/**
 * Created by Gabriel on 2020-06-24.
 */
public class ImageStorage {

    //Image variables
    public static BufferedImage tileDisplay;
    public static BufferedImage tileSlope;
    public static BufferedImage simpleItem;
    public static BufferedImage numberSignZero;
    public static BufferedImage numberSignOne;
    public static BufferedImage numberSignTwo;
    public static BufferedImage numberSignThree;
    public static BufferedImage numberSignFour;
    public static BufferedImage numberSignFive;
    public static BufferedImage numberSignSix;
    public static BufferedImage numberSignSeven;
    public static BufferedImage numberSignEight;
    public static BufferedImage numberSignNine;
    public static BufferedImage grapeImage;
    
    //Sizing variables
    public static int itemWidthFactor = (int)(Inventory.inventoryTileWidth - Inventory.inventorySlotWidthSpacing * 2);
    public static int itemHeightFactor = (int)(Inventory.inventoryTileHeight - Inventory.inventorySlotHeightSpacing * 2);
    
    static {
        try {
            //Tile Images
            tileDisplay = ImageIO.read(new File(Tile.class.getResource("/com/gabe/main/images/tile_sprite.png").getFile()));
            tileSlope = ImageIO.read(new File(Tile.class.getResource("/com/gabe/main/images/tile_sprite_slope.png").getFile()));
            
            //Inventory Number Images
            numberSignZero = ImageIO.read(new File(ImageStorage.class.getResource("/com/gabe/main/images/Numbers/NumberSign_Zero.png").getFile()));
            numberSignZero = ImageRotator.toBufferedImage(numberSignZero.getScaledInstance(Inventory.inventoryTileWidth/4, Inventory.inventoryTileHeight/4, Image.SCALE_DEFAULT));
            numberSignOne = ImageIO.read(new File(ImageStorage.class.getResource("/com/gabe/main/images/Numbers/NumberSign_One.png").getFile()));
            numberSignOne = ImageRotator.toBufferedImage(numberSignOne.getScaledInstance(Inventory.inventoryTileWidth/4, Inventory.inventoryTileHeight/4, Image.SCALE_DEFAULT));
            numberSignTwo = ImageIO.read(new File(ImageStorage.class.getResource("/com/gabe/main/images/Numbers/NumberSign_Two.png").getFile()));
            numberSignTwo = ImageRotator.toBufferedImage(numberSignTwo.getScaledInstance(Inventory.inventoryTileWidth/4,Inventory.inventoryTileHeight/4,Image.SCALE_DEFAULT));
            numberSignThree = ImageIO.read(new File(ImageStorage.class.getResource("/com/gabe/main/images/Numbers/NumberSign_Three.png").getFile()));
            numberSignThree = ImageRotator.toBufferedImage(numberSignThree.getScaledInstance(Inventory.inventoryTileWidth/4,Inventory.inventoryTileHeight/4,Image.SCALE_DEFAULT));
            numberSignFour = ImageIO.read(new File(ImageStorage.class.getResource("/com/gabe/main/images/Numbers/NumberSign_Four.png").getFile()));
            numberSignFour = ImageRotator.toBufferedImage(numberSignFour.getScaledInstance(Inventory.inventoryTileWidth/4,Inventory.inventoryTileHeight/4,Image.SCALE_DEFAULT));
            numberSignFive = ImageIO.read(new File(ImageStorage.class.getResource("/com/gabe/main/images/Numbers/NumberSign_Five.png").getFile()));
            numberSignFive = ImageRotator.toBufferedImage(numberSignFive.getScaledInstance(Inventory.inventoryTileWidth/4,Inventory.inventoryTileHeight/4,Image.SCALE_DEFAULT));
            numberSignSix = ImageIO.read(new File(ImageStorage.class.getResource("/com/gabe/main/images/Numbers/NumberSign_Six.png").getFile()));
            numberSignSix = ImageRotator.toBufferedImage(numberSignSix.getScaledInstance(Inventory.inventoryTileWidth/4,Inventory.inventoryTileHeight/4,Image.SCALE_DEFAULT));
            numberSignSeven = ImageIO.read(new File(ImageStorage.class.getResource("/com/gabe/main/images/Numbers/NumberSign_Seven.png").getFile()));
            numberSignSeven = ImageRotator.toBufferedImage(numberSignSeven.getScaledInstance(Inventory.inventoryTileWidth/4,Inventory.inventoryTileHeight/4,Image.SCALE_DEFAULT));
            numberSignEight = ImageIO.read(new File(ImageStorage.class.getResource("/com/gabe/main/images/Numbers/NumberSign_Eight.png").getFile()));
            numberSignEight = ImageRotator.toBufferedImage(numberSignEight.getScaledInstance(Inventory.inventoryTileWidth/4,Inventory.inventoryTileHeight/4,Image.SCALE_DEFAULT));
            numberSignNine = ImageIO.read(new File(ImageStorage.class.getResource("/com/gabe/main/images/Numbers/NumberSign_Nine.png").getFile()));
            numberSignNine = ImageRotator.toBufferedImage(numberSignNine.getScaledInstance(Inventory.inventoryTileWidth/4,Inventory.inventoryTileHeight/4,Image.SCALE_DEFAULT));
            
            //Inventory Slot Images
            simpleItem = ImageIO.read(new File(Game.class.getResource("/com/gabe/main/images/bananas.png").getFile()));
            simpleItem = ImageRotator.toBufferedImage(simpleItem.getScaledInstance(itemWidthFactor, itemHeightFactor, Image.SCALE_SMOOTH));
            grapeImage = ImageIO.read(new File(ImageStorage.class.getResource("/com/gabe/main/images/grapes.png").getFile()));
            grapeImage = ImageRotator.toBufferedImage(grapeImage.getScaledInstance(itemWidthFactor, itemHeightFactor, Image.SCALE_SMOOTH));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
