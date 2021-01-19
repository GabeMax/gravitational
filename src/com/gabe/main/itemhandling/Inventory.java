package com.gabe.main.itemhandling;

import com.gabe.main.Game;
import com.gabe.main.imagehandling.ImageStorage;
import com.gabe.main.MouseInput;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

/**
 * Created by Gabriel on 2020-05-26.
 */
public class Inventory {
    
    public static final int inventoryTileWidth = 64;
    public static final int inventoryTileHeight = 64;
    public static final int amountOfInventoryColumns = 9;
    public static final int amountOfInventoryRows = 4;
    public ItemSlot[][] inventoryList = new ItemSlot[amountOfInventoryRows][amountOfInventoryColumns];
    public static final int inventoryWidthSpacing = (Game.WIDTH - (inventoryTileWidth*amountOfInventoryColumns))/2;
    public static final int inventoryHeightSpacing = (Game.HEIGHT - (inventoryTileHeight*amountOfInventoryRows))/6;
    public static final int inventorySlotWidthSpacing = inventoryTileWidth/8;
    public static final int inventorySlotHeightSpacing = inventoryTileHeight/8;
    public ActiveInventoryBar activeBar = new ActiveInventoryBar(this);
    private ItemSlot mouseSlot = new ItemSlot(0, 0, inventoryTileWidth, inventoryTileHeight, new ItemStack(ItemType.NONE, 0));
    private boolean itemIsStuckToMouse = false, mouseRelease = false, rightMouseRelease = false;
    public static int guiScaleFactor = 1;
    
    public Inventory() {
        for(int i = 0; i < amountOfInventoryRows; i++) {
            for(int j = 0; j < amountOfInventoryColumns; j++) {
                inventoryList[i][j] = new ItemSlot(inventoryWidthSpacing + (j*inventoryTileWidth), inventoryHeightSpacing + ((amountOfInventoryRows-1-i) * inventoryTileHeight), inventoryTileWidth, inventoryTileHeight, new ItemStack(ItemType.NONE, 0));
            }
        }
    }
    
    public void tick() {
        handleClickEvents();
    }
    
    public void render(Graphics g) {
        renderInventory(g);
    }
    
    public void renderInventory(Graphics g) {
        g.setColor(new Color(0, 255, 255, 30));
        g.fillRect(inventoryWidthSpacing, inventoryHeightSpacing, inventoryTileWidth * amountOfInventoryColumns, inventoryTileHeight * amountOfInventoryRows);
        g.setColor(Color.black);
        for(int i = amountOfInventoryRows - 1; i >= 0; i--) {
            for(int j = 0; j < amountOfInventoryColumns; j++) {
                g.drawRect(inventoryWidthSpacing + (j*inventoryTileWidth), inventoryHeightSpacing + (i * inventoryTileHeight), inventoryTileWidth, inventoryTileHeight);
                if(inventoryList[i][j].getItemStack().getTypeID()!= ItemType.NONE) {
                    if(inventoryList[i][j].getItemStack().getTypeID().getImage() != null) {
                        g.drawImage(inventoryList[i][j].getItemStack().getTypeID().getImage(), inventoryList[i][j].x + inventorySlotWidthSpacing, inventoryList[i][j].y + inventorySlotHeightSpacing, null);
                        drawNumericalValues(g, inventoryList[i][j]);
                    }
                }
            }
        }
        if(itemIsStuckToMouse) {
            g.drawImage(mouseSlot.getItemStack().getTypeID().getImage(), mouseSlot.x + inventorySlotWidthSpacing, mouseSlot.y + inventorySlotHeightSpacing, null);
            drawNumericalValues(g, mouseSlot);
        }
    }
    
    public void drawNumericalValues(Graphics g, ItemSlot itemSlot) {
        if(itemSlot.getItemStack().getStackAmount() != 0) {
            String s = Integer.toString(itemSlot.getItemStack().getStackAmount());
            for (int i = s.length() - 1; i >= 0; i--) {
                drawDigit(g, itemSlot, Integer.parseInt(String.valueOf(s.charAt(i))), s.length() - 1 - i);
            }
        }
    }

    public void drawDigit(Graphics g, ItemSlot itemSlot, int num, int pos) {
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
        g.drawImage(finalNumber, itemSlot.x + (inventoryTileWidth * 2/3) - (pos * (inventoryTileWidth/4) * 3/4), itemSlot.y + (inventoryTileHeight * 2/3), null);
    }
    
    public void handleClickEvents() {
        ItemSlot itemSlot;
        if(MouseInput.isMouseButtonDown(MouseEvent.BUTTON1) && !itemIsStuckToMouse && !mouseRelease) {
            itemSlot = getItemSlot(MouseInput.mousePos);
            if(itemSlot != null) {
                if(itemSlot.getItemStack().getTypeID() != ItemType.NONE) {
                    mouseSlot.setItemStack(itemSlot.getItemStack());
                    itemSlot.removeItemStack();
                    itemIsStuckToMouse = true;
                    mouseRelease = true;
                }
            }
        }
        if(MouseInput.isMouseButtonDown(MouseEvent.BUTTON1) && itemIsStuckToMouse && !mouseRelease) {
            itemSlot = getItemSlot(MouseInput.mousePos);
            if(itemSlot != null) {
                if(itemSlot.getItemStack().getTypeID() == ItemType.NONE) {
                    itemSlot.setItemStack(mouseSlot.getItemStack());
                    mouseSlot.removeItemStack();
                    itemIsStuckToMouse = false;
                } else if(itemSlot.getItemStack().getTypeID() != mouseSlot.getItemStack().getTypeID()){
                    ItemStack is = new ItemStack(itemSlot.getItemStack().getTypeID(), itemSlot.getItemStack().getStackAmount());
                    itemSlot.setItemStack(mouseSlot.getItemStack());
                    mouseSlot.setItemStack(is);
                } else if(itemSlot.getItemStack().getTypeID() == mouseSlot.getItemStack().getTypeID()){
                    if(itemSlot.getItemStack().getStackAmount() + mouseSlot.getItemStack().getStackAmount() <= itemSlot.getItemStack().getTypeID().getMaxStackSize()) {
                        itemSlot.getItemStack().setStackAmount(itemSlot.getItemStack().getStackAmount() + mouseSlot.getItemStack().getStackAmount());
                        mouseSlot.removeItemStack();
                        itemIsStuckToMouse = false;
                    } else {
                        mouseSlot.getItemStack().setStackAmount(mouseSlot.getItemStack().getStackAmount() - (itemSlot.getItemStack().getTypeID().getMaxStackSize() - itemSlot.getItemStack().getStackAmount()));
                        itemSlot.getItemStack().setStackAmount(itemSlot.getItemStack().getTypeID().getMaxStackSize());
                    }
                }
                mouseRelease = true;
            }
        }
        if(MouseInput.isMouseButtonDown(MouseEvent.BUTTON3) && !itemIsStuckToMouse && !rightMouseRelease) {
            itemSlot = getItemSlot(MouseInput.mousePos);
            if(itemSlot != null) {
                if(itemSlot.getItemStack().getTypeID() != ItemType.NONE) {
                    if (itemSlot.getItemStack().getStackAmount() == 1) {
                        mouseSlot.setItemStack(itemSlot.getItemStack());
                        removeItem(itemSlot, 1);
                    } else {
                        mouseSlot.getItemStack().setTypeID(itemSlot.getItemStack().getTypeID());
                        mouseSlot.getItemStack().setStackAmount((int)Math.ceil((double)itemSlot.getItemStack().getStackAmount() / 2));
                        removeItem(itemSlot, (int)Math.ceil((double)itemSlot.getItemStack().getStackAmount() / 2));
                    }
                    itemIsStuckToMouse = true;
                    rightMouseRelease = true;
                }
            }
        }
        if(MouseInput.isMouseButtonDown(MouseEvent.BUTTON3) && itemIsStuckToMouse && !rightMouseRelease) {
            itemSlot = getItemSlot(MouseInput.mousePos);
            if(itemSlot != null) {
                if(itemSlot.getItemStack().getTypeID() == ItemType.NONE) {
                    itemSlot.getItemStack().setTypeID(mouseSlot.getItemStack().getTypeID());
                    itemSlot.getItemStack().setStackAmount(itemSlot.getItemStack().getStackAmount() + 1);
                    removeItem(mouseSlot, 1);
                } else if(itemSlot.getItemStack().getTypeID() != mouseSlot.getItemStack().getTypeID()) {
                    ItemStack is = new ItemStack(itemSlot.getItemStack().getTypeID(), itemSlot.getItemStack().getStackAmount());
                    itemSlot.setItemStack(mouseSlot.getItemStack());
                    mouseSlot.setItemStack(is);
                } else if(itemSlot.getItemStack().getTypeID() == mouseSlot.getItemStack().getTypeID()) {
                    if(itemSlot.getItemStack().getStackAmount() < itemSlot.getItemStack().getTypeID().getMaxStackSize()) {
                        removeItem(mouseSlot, 1);
                        itemSlot.getItemStack().setStackAmount(itemSlot.getItemStack().getStackAmount() + 1);
                    }
                }
                if(mouseSlot.getItemStack().getTypeID() == ItemType.NONE) {
                    itemIsStuckToMouse = false;
                }
                rightMouseRelease = true;
            }
        }
        if(!MouseInput.isMouseButtonDown(MouseEvent.BUTTON3) && rightMouseRelease) {
            rightMouseRelease = false;
        }
        if(!MouseInput.isMouseButtonDown(MouseEvent.BUTTON1) && mouseRelease) {
            mouseRelease = false;
        }
        if(itemIsStuckToMouse) {
            mouseSlot.x = MouseInput.mousePos.x - inventoryTileWidth/2;
            mouseSlot.y = MouseInput.mousePos.y - inventoryTileHeight/2;
        }
    }
    
    public ItemSlot getItemSlot(Point p) {
        for(int i = 0; i < amountOfInventoryRows; i++) {
            for(int j = 0; j < amountOfInventoryColumns; j++) {
                if(pointIsInsideSlot(p, inventoryList[i][j])) {
                    return inventoryList[i][j];
                }
            }
        }
        return null;
    }
    
    public boolean pointIsInsideSlot(Point point, ItemSlot itemSlot) {
        if(point.x > itemSlot.x && point.x < (itemSlot.x + inventoryTileWidth)) {
            return point.y > itemSlot.y && point.y < (itemSlot.y + inventoryTileHeight);
        }
        return false;
    }
    
    public void addItem(ItemStack item) {
        ItemSlot itemSlot;
        int maxSize;
        ItemType itemType;
        for(int i = 0; i < amountOfInventoryRows; i++) {
            for(int j = 0; j < amountOfInventoryColumns; j++) {
                itemSlot = inventoryList[i][j];
                maxSize = itemSlot.getItemStack().getTypeID().getMaxStackSize();
                itemType = itemSlot.getItemStack().getTypeID();
                if(itemType == ItemType.NONE) {
                    if(item.getStackAmount() <= item.getTypeID().getMaxStackSize()) {
                        itemSlot.setItemStack(item);
                        return;
                    } else {
                        itemSlot.setItemStack(new ItemStack(item.getTypeID(), item.getTypeID().getMaxStackSize()));
                        item.setStackAmount(item.getStackAmount() - item.getTypeID().getMaxStackSize());
                    }
                }
                if(itemType == item.getTypeID()) {
                    if(item.getStackAmount() + itemSlot.getItemStack().getStackAmount() <= maxSize) {
                        itemSlot.getItemStack().setStackAmount(itemSlot.getItemStack().getStackAmount() + item.getStackAmount());
                        return;
                    } else {
                        item.setStackAmount(item.getStackAmount() - (item.getTypeID().getMaxStackSize() - itemSlot.getItemStack().getStackAmount()));
                        itemSlot.getItemStack().setStackAmount(maxSize);
                    }
                }
            } 
        }
    }
    
    public void removeItem(ItemSlot itemSlot, int amount) {
        if(itemSlot.getItemStack().getTypeID() != ItemType.NONE) {
            if(itemSlot.getItemStack().getStackAmount() <= amount) {
                itemSlot.setItemStack(new ItemStack(ItemType.NONE, 0));
            } else {
                itemSlot.getItemStack().setStackAmount(itemSlot.getItemStack().getStackAmount() - amount);
            }
        }
    }
    
    public ItemStack getItemInHand() {
        return activeBar.slotSelector.getItemSlot().getItemStack();
    }
}
