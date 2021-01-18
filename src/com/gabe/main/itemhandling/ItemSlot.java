package com.gabe.main.itemhandling;

/**
 * Created by Gabriel on 2020-07-01.
 */
public class ItemSlot {
    
    public int x, y, width, height;
    private ItemStack itemStack;
    
    public ItemSlot(int x, int y, int width, int height, ItemStack itemStack) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.itemStack = itemStack;
    }
    
    public void setItemStack(ItemStack itemStack) {
        this.getItemStack().setTypeID(itemStack.getTypeID());
        this.getItemStack().setStackAmount(itemStack.getStackAmount());
    }
    
    public ItemStack getItemStack() {
        return this.itemStack;
    }
    
    public void removeItemStack() {
        this.getItemStack().setTypeID(ItemType.NONE);
        this.getItemStack().setStackAmount(0);
    }
}
