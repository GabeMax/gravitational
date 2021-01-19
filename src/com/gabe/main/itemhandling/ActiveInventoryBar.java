package com.gabe.main.itemhandling;

import com.gabe.main.Game;
import com.gabe.main.imagehandling.ImageStorage;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by Gabriel on 2020-05-26.
 */
public class ActiveInventoryBar {

    public Inventory inventory;
    public InventorySlotSelector slotSelector = new InventorySlotSelector(this);
    public static int activeBarWidthSpacing = Inventory.inventoryWidthSpacing;
    public static int activeBarHeightSpacing = Game.HEIGHT - (int) (Inventory.inventoryTileHeight * 2.25);

    public ActiveInventoryBar(Inventory inventory) {
        this.inventory = inventory;
    }

    public void tick() {
        slotSelector.tick();
    }

    public void render(Graphics g) {
        renderActiveBar(g);
        slotSelector.render(g);
    }

    public void renderActiveBar(Graphics g) {
        g.setColor(new Color(0, 255, 255, 30));
        g.fillRect(Inventory.inventoryWidthSpacing, Game.HEIGHT - (int) (Inventory.inventoryTileHeight * 2.25), Inventory.inventoryTileWidth * Inventory.amountOfInventoryColumns, Inventory.inventoryTileHeight);
        g.setColor(Color.black);
        for (int i = 0; i < Inventory.amountOfInventoryColumns; i++) {
            g.drawRect(inventory.inventoryList[0][i].x, Game.HEIGHT - (int) (Inventory.inventoryTileHeight * 2.25), Inventory.inventoryTileWidth, Inventory.inventoryTileHeight);
            if (inventory.inventoryList[0][i].getItemStack().getTypeID() != ItemType.NONE) {
                if (inventory.inventoryList[0][i].getItemStack().getTypeID().getImage() != null) {
                    g.drawImage(inventory.inventoryList[0][i].getItemStack().getTypeID().getImage(), inventory.inventoryList[0][i].x + Inventory.inventorySlotWidthSpacing, activeBarHeightSpacing + Inventory.inventorySlotHeightSpacing, null);
                    drawNumericalValues(g, inventory.inventoryList[0][i], new Point(inventory.inventoryList[0][i].x, activeBarHeightSpacing));
                }
            }
        }
    }

    public void drawNumericalValues(Graphics g, ItemSlot itemSlot, Point p) {
        if (itemSlot.getItemStack().getStackAmount() != 0) {
            String s = Integer.toString(itemSlot.getItemStack().getStackAmount());
            for (int i = s.length() - 1; i >= 0; i--) {
                drawDigit(g, p, Integer.parseInt(String.valueOf(s.charAt(i))), s.length() - 1 - i);
            }
        }
    }

    public void drawDigit(Graphics g, Point p, int num, int pos) {
        BufferedImage finalNumber = switch (num) {
            case 0 -> ImageStorage.numberSignZero;
            case 1 -> ImageStorage.numberSignOne;
            case 2 -> ImageStorage.numberSignTwo;
            case 3 -> ImageStorage.numberSignThree;
            case 4 -> ImageStorage.numberSignFour;
            case 5 -> ImageStorage.numberSignFive;
            case 6 -> ImageStorage.numberSignSix;
            case 7 -> ImageStorage.numberSignSeven;
            case 8 -> ImageStorage.numberSignEight;
            case 9 -> ImageStorage.numberSignNine;
            default -> null;
        };
        g.drawImage(finalNumber, p.x + (Inventory.inventoryTileWidth * 2 / 3) - (pos * (Inventory.inventoryTileWidth / 4) * 3 / 4), p.y + (Inventory.inventoryTileHeight * 2 / 3), null);
    }

}
