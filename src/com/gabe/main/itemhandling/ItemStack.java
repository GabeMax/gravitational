package com.gabe.main.itemhandling;

import com.gabe.main.Direction;
import com.gabe.main.listeners.ItemPickupEvent;
import com.gabe.main.listeners.ListenerHandler;

/**
 * Created by Gabriel on 2020-05-26.
 */
public class ItemStack {
    
    private ItemType typeID;
    private int stackAmount;
    
    public ItemStack(ItemType typeID, int stackAmount) {
        this.typeID = typeID;
        this.stackAmount = stackAmount;
    }

    public int getStackAmount() {
        return stackAmount;
    }

    public void setStackAmount(int stackAmount) {
        this.stackAmount = stackAmount;
    }

    public void setTypeID(ItemType typeID) {
        this.typeID = typeID;
    }

    public ItemType getTypeID() {
        return typeID;
    }
}
