package com.gabe.main.listeners;

import com.gabe.main.GameObject;
import com.gabe.main.itemhandling.ItemStack;

/**
 * Created by Gabriel on 2020-07-10.
 */
public class ItemInteractEvent extends Event {
    
    private GameObject gameObject;
    private ItemStack itemStack;
    
    public ItemInteractEvent(GameObject gameObject, ItemStack itemStack) {
        this.gameObject = gameObject;
        this.itemStack = itemStack;
    }

    public GameObject getGameObject() {
        return gameObject;
    }

    public ItemStack getItemStack() {
        return itemStack;
    }
}
