package com.gabe.main.itemhandling.itemmethods;

import com.gabe.main.*;
import com.gabe.main.itemhandling.AbstractItemMethod;

import java.awt.*;

/**
 * Created by Gabriel on 2020-07-10.
 */
public class PistolMethod implements AbstractItemMethod {
    
    public void run(GameObject gameObject) {
        if(gameObject instanceof Player) {
            Projectile.launchProjectile(gameObject, new Projectile(gameObject.x, gameObject.y, ID.Projectile, gameObject.getHandler(), ProjectileID.ARROW), gameObject.getMidPointAnchorOnScreen(), MouseInput.mousePos, 20);
        } else {
            Projectile.launchProjectile(gameObject, new Projectile(gameObject.x, gameObject.y, ID.Projectile, gameObject.getHandler(), ProjectileID.ARROW), gameObject.getMidPointAnchorOnScreen(), new Point(gameObject.isFacing == Direction.LEFT ? gameObject.x - 20 : gameObject.x + 20, gameObject.y), 20); //Fix for directionality
        }
    }
}
