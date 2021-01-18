package com.gabe.main.itemhandling;

import com.gabe.main.*;
import com.gabe.main.imagehandling.Animation;
import com.gabe.main.listeners.ItemPickupEvent;
import com.gabe.main.listeners.ListenerHandler;

import java.awt.*;

/**
 * Created by Gabriel on 2020-05-26.
 */
public class ItemEntity extends GameObject {

    private ItemStack itemStack;
    
    public ItemEntity(int x, int y, ID id, Handler handler, ItemStack itemStack) {
        super(x, y, id, handler);
        this.itemStack = itemStack;
    }

    public void tick() {
        GameObject object;
        if((object = getTouchingObject(Player.class)) != null) {
            if(object instanceof Player) {
                Player p = (Player)object;
                p.inventory.addItem(getItemStack());
            }
            object.setItemInHand(getItemStack());
            ListenerHandler.fireEvent(new ItemPickupEvent(object, this));
            handler.removeObject(this);
        }
        //Collision.applySingleHitboxCollision(this);
    }

    public void render(Graphics g) {
        g.drawImage(Animation.getRotatedImage(this, getItemStack().getTypeID().getImage()), x-Camera.cameraX, y-Camera.cameraY, null);
        drawBoundingBoxes(g);
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    public BoundingBoxPivot getBoundingBoxPivot() {
        return getId().getBoundingBoxPivot();
    }
}