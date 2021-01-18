package com.gabe.main;

/**
 * Created by Gabriel on 2020-04-23.
 */
public enum ID {
    Player(new BoundingBoxPivot(6, 0, 16, 61)),
    BasicEnemy(new BoundingBoxPivot(0, 0, 16, 48)),
    itemEntity(new BoundingBoxPivot(16, 16, 32, 32)),
    Projectile(new BoundingBoxPivot(0, 0, 32, 32))
    ;

    private BoundingBoxPivot boundingBoxPivot;
    
    ID(BoundingBoxPivot boundingBoxPivot) {
        this.boundingBoxPivot = boundingBoxPivot;
    }

    public BoundingBoxPivot getBoundingBoxPivot() {
        return boundingBoxPivot;
    }
}
