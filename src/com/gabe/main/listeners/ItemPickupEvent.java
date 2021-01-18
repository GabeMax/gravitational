package com.gabe.main.listeners;

import com.gabe.main.GameObject;
import com.gabe.main.itemhandling.ItemEntity;

/**
 * Created by Gabriel on 2020-07-06.
 */
public class ItemPickupEvent extends Event {
    
    private ItemEntity itemEntity;
    private GameObject gameObject;
    
    public ItemPickupEvent(GameObject gameObject, ItemEntity itemEntity) {
        this.itemEntity = itemEntity;
        this.gameObject = gameObject;
    }

    public ItemEntity getItemEntity() {
        return itemEntity;
    }

    public GameObject getGameObject() {
        return gameObject;
    }
}
