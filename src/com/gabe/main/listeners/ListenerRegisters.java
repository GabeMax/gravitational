package com.gabe.main.listeners;

import com.gabe.main.Direction;
import com.gabe.main.GameObject;
import com.gabe.main.itemhandling.ItemType;

/**
 * Created by Gabriel on 2020-07-28.
 */
public class ListenerRegisters {
    
    public static void registerAllListeners() {
        ListenerHandler.registerListener(GravitySwitchEvent.class, e -> {
            GravitySwitchEvent event = (GravitySwitchEvent)e;
            GameObject gameObject = event.getGameObject();
            gameObject.totalWidth = gameObject.getTotalWidth(gameObject.getBoundingBox());
            gameObject.totalHeight = gameObject.getTotalHeight(gameObject.getBoundingBox());
        });
        ListenerHandler.registerListener(ItemInteractEvent.class, e -> {
            ItemInteractEvent event = (ItemInteractEvent)e;
            if(event.getItemStack().getTypeID().getItemMethod() != null) {
                event.getItemStack().getTypeID().getItemMethod().run(event.getGameObject());
            }
        });
        ListenerHandler.registerListener(ProjectileLaunchEvent.class, e -> {
            ProjectileLaunchEvent event = (ProjectileLaunchEvent)e;
            event.getProjectile().setGravityDirection(event.getGameObject().getGravityDirection());
        });
        ListenerHandler.registerListener(ItemPickupEvent.class, e -> {
            ItemPickupEvent event = (ItemPickupEvent)e;
            if(event.getItemEntity().getItemStack().getTypeID() == ItemType.STAR_FURY) {
                event.getGameObject().setGravityDirection(Direction.RIGHT);
            }
        });
    }
}
