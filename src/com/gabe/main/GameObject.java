package com.gabe.main;

import com.gabe.main.itemhandling.ItemStack;
import com.gabe.main.listeners.*;

import java.awt.*;

/**
 * Created by Gabriel on 2020-04-23.
 */
public abstract class GameObject {
    
    public static final int tickingTileDistance = 10;
    public int x, y, xDelta, yDelta;
    public ID id;
    public double speedSideWays, speedUpAndDown, gravityConstant = 0.6;
    public Handler handler;
    public Image sprite;
    public boolean isFalling = false;
    public boolean isStuckToSlope = false, justJumped = false;
    public Direction isFacing = Direction.RIGHT;
    private Direction gravityDirection = Direction.DOWN;
    public int totalHeight, totalWidth;
    public ItemStack itemInHand;
    public Image currentAnimation;
    
    public GameObject(int x, int y, ID id, Handler handler) {
        this.x = x;
        this.y = y;
        this.id = id;
        this.handler = handler;
        totalHeight = getTotalHeight(getBoundingBox());
        totalWidth = getTotalWidth(getBoundingBox());
        handler.addObject(this);
    }
  
    //Abstract Methods
    public abstract void tick();
    public abstract void render(Graphics g);
    public abstract BoundingBoxPivot getBoundingBoxPivot();
    
    //Bounding Box Methods
    public Rectangle[] getBoundingBox() {
        Rectangle[] hitBox = new Rectangle[1];
        hitBox[0] = getRotatedBoundingBox();
        return hitBox;
    }
    public Rectangle getRotatedBoundingBox() {
        BoundingBoxPivot b = getBoundingBoxPivot();
        return switch (getGravityDirection()) {
            case DOWN -> new Rectangle(x + b.xOffset, y + b.yOffset, b.width, b.height);
            case RIGHT -> new Rectangle(x + b.xOffset - b.xOffset, y + b.yOffset + b.xOffset, b.height, b.width);
            case LEFT -> new Rectangle(x + b.xOffset - 2 * b.width + 25, y + b.yOffset + b.xOffset, b.height, b.width);
            case UP -> new Rectangle(x + b.xOffset, y + b.yOffset, b.width, b.height);
        };
    }
    public boolean isTouching(Class c) {
        for(GameObject entity: handler.object) {
            if(entity.getClass() == c) {
                for(Rectangle hitbox1: this.getBoundingBox()) {
                    for(Rectangle hitbox2: entity.getBoundingBox()) {
                        if(hitbox1.intersects(hitbox2)) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
    public GameObject getTouchingObject(Class c) {
        for(GameObject entity: handler.object) {
            if(entity.getClass() == c) {
                for(Rectangle hitBox1: this.getBoundingBox()) {
                    for(Rectangle hitBox2: entity.getBoundingBox()) {
                        if(hitBox1.intersects(hitBox2)) {
                            return entity;
                        }
                    }
                }
            }
        }
        return null;
    }
    public GameObject getTouchingObject() {
        for(GameObject entity: handler.object) {
            for(Rectangle hitBox1: this.getBoundingBox()) {
                for(Rectangle hitBox2: entity.getBoundingBox()) {
                    if(hitBox1.intersects(hitBox2)) {
                        return entity;
                    }
                }
            }
        }
        return null;
    }
    public boolean isTouchingLeft(Class c) {
        for(GameObject entity: handler.object) {
            if(entity.getClass() == c) {
                if(this.isTouchingLeft(entity)) {
                    return true;
                }
            }
        }
        return false;
    }
    public boolean isTouchingRight(Class c) {
        for(GameObject entity: handler.object) {
            if(entity.getClass() == c) {
                if(this.isTouchingRight(entity)) {
                    return true;
                }
            }
        }
        return false;
    }
    private boolean isTouchingLeft(GameObject object) {
        Point upperRight = Collision.getTopRight(this.getBoundingBox()[0]);
        Point lowerRight = Collision.getBottomRight(this.getBoundingBox()[0]);
        return Collision.pointIsCollidingWithRectangle(object.getBoundingBox()[0], upperRight) || Collision.pointIsCollidingWithRectangle(object.getBoundingBox()[0], lowerRight);
    }
    private boolean isTouchingRight(GameObject object) {
        Point upperLeft = Collision.getTopLeft(this.getBoundingBox()[0]);
        Point lowerLeft = Collision.getBottomLeft(this.getBoundingBox()[0]);
        return Collision.pointIsCollidingWithRectangle(object.getBoundingBox()[0], upperLeft) || Collision.pointIsCollidingWithRectangle(object.getBoundingBox()[0], lowerLeft);
    }
    public int getTotalWidth(Rectangle[] hitBoxes) {
        int maxWidth = Integer.MIN_VALUE;
        for(Rectangle hitbox: hitBoxes) {
            int specificWidth = (hitbox.x - this.x) + hitbox.width;
            if(specificWidth > maxWidth) {
                maxWidth = specificWidth;
            }
        }
        return maxWidth;
    }
    public int getTotalHeight(Rectangle[] hitBoxes) {
        int maxHeight = Integer.MIN_VALUE;
        for(Rectangle hitbox: hitBoxes) {
            int specificHeight = (hitbox.y - this.y) + hitbox.height;
            if(specificHeight > maxHeight) {
                maxHeight = specificHeight;
            }
        }
        return maxHeight;
    }
    
    //Drawing Methods
    public void drawSlopeCollidablePoint(Graphics g) {
        g.setColor(Color.RED);
        g.drawLine(Collision.getSlopeCollidablePoint(this).x - Camera.cameraX, Collision.getSlopeCollidablePoint(this).y - Camera.cameraY, Collision.getSlopeCollidablePoint(this).x - Camera.cameraX, Collision.getSlopeCollidablePoint(this).y - Camera.cameraY);
    }
    public void drawBoundingBoxes(Graphics g) {
        g.setColor(Color.green);
        for(Rectangle hitbox: this.getBoundingBox()) {
            g.drawRect(hitbox.x - Camera.cameraX, hitbox.y - Camera.cameraY, hitbox.width, hitbox.height);
        }
    }
    
    //Location Methods
    public Point getLocation() {
        return new Point(x, y);
    }
    public Point getPositionOnScreen() {
        return new Point(x - Camera.cameraX, y - Camera.cameraY);
    }
    private Point getObjectMidPointAnchor() {
        return new Point((getBoundingBoxPivot().width/2) + x, (getBoundingBoxPivot().height/2) + y);
    }
    public Point getMidPointAnchorOnScreen() {
        return new Point(getObjectMidPointAnchor().x - Camera.cameraX, getObjectMidPointAnchor().y - Camera.cameraY);
    }
    public double getDistanceTo(GameObject gameObject) {
        return Math.sqrt(Math.pow(gameObject.getLocation().x-getLocation().x, 2) + Math.pow(gameObject.getLocation().y-getLocation().y, 2));
    }
    public boolean isWithinPlayerDistance() {
        for(GameObject gameObject: handler.object) {
            if(gameObject instanceof Player) {
                if(getDistanceTo(gameObject) <= tickingTileDistance * Tile.tileWidth) {
                    return true;
                }
            }
        }
        return false;
    }
    
    //Movement Methods
    public void moveObjectSideToSide() {
        switch (getGravityDirection()) {
            case DOWN -> x += speedSideWays;
            case UP -> x -= speedSideWays;
            case LEFT -> y += speedSideWays;
            case RIGHT -> y -= speedSideWays;
        }
    }
    public void moveObjectUpAndDown() {
        switch (getGravityDirection()) {
            case DOWN -> y += speedUpAndDown;
            case UP -> y -= speedUpAndDown;
            case LEFT -> x -= speedUpAndDown;
            case RIGHT -> x += speedUpAndDown;
        }
    }
    public void setGravityDirection(Direction d) {
        gravityDirection = d;
        ListenerHandler.fireEvent(new GravitySwitchEvent(this, new Point(x, y), d));
    }
    public int convertNumberUponDirection(int num) {
        return switch (getGravityDirection()) {
            case DOWN, UP -> num;
            case LEFT, RIGHT -> -num;
        };
    }
    public int getSpeedSideWays(int speedSideWays) {
        return convertNumberUponDirection(speedSideWays);
    }
    public int getSpeedUpAndDown(int speedUpAndDown) {
        return convertNumberUponDirection(speedUpAndDown);
    }
    
    //Item Methods
    public void setItemInHand(ItemStack itemStack) {
        itemInHand = new ItemStack(itemStack.getTypeID(), itemStack.getStackAmount());
    }
    public ItemStack getItemInHand() {
        if(this instanceof Player) {
            Player player = (Player)this;
            return player.inventory.getItemInHand();
        }
        return itemInHand;
    }
    public void interactWithItemInHand() {
        ListenerHandler.fireEvent(new ItemInteractEvent(this, getItemInHand()));
    }
    
    //Tile methods
    public Tile getTileOffsetPoint(Point p, int xOffset, int yOffset) {
        return switch (getGravityDirection()) {
            case DOWN -> Tile.tiles[((int) Math.floor(p.y / Tile.tileHeight)) + yOffset][((int) Math.floor(p.x / Tile.tileWidth)) + xOffset];
            case RIGHT -> Tile.tiles[((int) Math.floor(p.y / Tile.tileHeight)) + xOffset][((int) Math.floor(p.x / Tile.tileWidth)) + yOffset];
            case LEFT -> Tile.tiles[((int) Math.floor(p.y / Tile.tileHeight)) - xOffset][((int) Math.floor(p.x / Tile.tileWidth)) - yOffset];
            case UP -> Tile.tiles[((int) Math.floor(p.y / Tile.tileHeight)) - yOffset][((int) Math.floor(p.x / Tile.tileWidth)) - xOffset];
        };
    }
    
    //Getter methods
    public Handler getHandler() {
        return handler;
    }
    public ID getId() {
        return id;
    }

    public Direction getGravityDirection() {
        return gravityDirection;
    }
}
