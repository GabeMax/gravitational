package com.gabe.main.itemhandling;

import com.gabe.main.KeyInput;

import java.awt.*;
import java.awt.event.KeyEvent;

/**
 * Created by Gabriel on 2020-07-04.
 */
public class InventorySlotSelector {
    
    private ActiveInventoryBar activeBar;
    private ItemSlot currentSlot;
    
    public InventorySlotSelector(ActiveInventoryBar activeBar) {
        this.activeBar = activeBar;
    }
    
    public void tick() {
        handleKeyEvents();
    }
    
    public void render(Graphics g) {
        drawSelector(g);
    }
    
    public void handleKeyEvents() {
        for(int i = KeyEvent.VK_1; i <= KeyEvent.VK_9; i++) {
            if(KeyInput.isKeyDown(i)) {
                currentSlot = getItemSlot(i - KeyEvent.VK_1);
            }
        }
    }
    
    public void drawSelector(Graphics g) {
        if(currentSlot != null) {
            g.setColor(Color.ORANGE);
            g.drawRect(currentSlot.x - 10, ActiveInventoryBar.activeBarHeightSpacing - 10, currentSlot.width + 20, currentSlot.height + 20);
        } else {
            currentSlot = getItemSlot(0);
        }
    }
    
    private ItemSlot getItemSlot(int index) {
        if(activeBar.inventory.inventoryList[0][index] != null) {
            return activeBar.inventory.inventoryList[0][index];
        }
        return null;
    }
    
    public ItemSlot getItemSlot() {
        return currentSlot;
    }
    
}
