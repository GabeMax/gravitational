package com.gabe.main;

import com.gabe.main.imagehandling.Animation;
import com.gabe.main.itemhandling.Inventory;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

/**
 * Created by Gabriel on 2020-04-23.
 */
public class Player extends LivingGameObject {

    public Player(int x, int y, ID id, Handler handler) {
        super(x, y, id, handler);
    }

    private int iFrames = 0, jumpTimer = 0;
    private double sideWaysAcceleration = 0.5;
    public static int hitBoxAmount = 1;
    public Inventory inventory = new Inventory();
    public boolean renderingInventory = false;
    private Point mousePos;

    public void tick() {
        checkKeyEvents();
        checkMouseEvents();
        speedSideWays = Game.clamp(speedSideWays, -5, 5);
        speedUpAndDown = Game.clamp(speedUpAndDown, -20, 20);
        Collision.applySingleHitboxCollision(this);
        if (isTouching(BasicEnemy.class)) {
            if (iFrames == 0) {
                iFrames = 200;
                HUD.HEALTH -= 20;
            }
        }
        if (iFrames != 0) {
            iFrames--;
        }
        currentAnimation = Animation.getPlayerAnimation(this);
    }

    public void render(Graphics g) {
        Camera.cameraX = x - Game.WIDTH / 2 + 20;
        Camera.cameraY = y - Game.HEIGHT / 2 + (this.totalWidth / 2);
        g.drawImage(currentAnimation, x - Camera.cameraX, y - Camera.cameraY, null);
        drawBoundingBoxes(g);
        drawSlopeCollidablePoint(g);
    }

    public BoundingBoxPivot getBoundingBoxPivot() {
        return id.getBoundingBoxPivot();
    }
    
    public void checkMouseEvents() {
        if(!Game.inventoryIsOpen) {
            if(MouseInput.isMouseButtonDown(MouseEvent.BUTTON3)) {
                interactWithItemInHand();
            }
        }
    }

    public void checkKeyEvents() {
        if (KeyInput.isKeyDown(KeyEvent.VK_A)) {
            speedSideWays -= sideWaysAcceleration;
            isFacing = Direction.LEFT;
        } else if (speedSideWays < 0) {
            speedSideWays += 0.5;
            if (speedSideWays > 0) {
                speedSideWays = 0;
            }
        }
        if (KeyInput.isKeyDown(KeyEvent.VK_D)) {
            speedSideWays += sideWaysAcceleration;
            isFacing = Direction.RIGHT;
        } else if (speedSideWays > 0) {
            speedSideWays -= 0.5;
            if (speedSideWays < 0) {
                speedSideWays = 0;
            }
        }
        if (KeyInput.isKeyDown(KeyEvent.VK_DOWN)) {
            setGravityDirection(Direction.DOWN);
        }
        if (KeyInput.isKeyDown(KeyEvent.VK_LEFT)) {
            setGravityDirection(Direction.LEFT);
        }
        if (KeyInput.isKeyDown(KeyEvent.VK_RIGHT)) {
            setGravityDirection(Direction.RIGHT);
        }
        if (KeyInput.isKeyDown(KeyEvent.VK_UP)) {
            setGravityDirection(Direction.UP);
        }
        if (KeyInput.isKeyDown(KeyEvent.VK_SPACE) && jumpTimer == 0 && !isFalling) {
            speedUpAndDown = -15;
            isFalling = true;
            isStuckToSlope = false;
            justJumped = true;
            jumpTimer = 20;
        } else {
            justJumped = false;
        }
        if(jumpTimer > 0) {
            jumpTimer--;
        }
        if(KeyInput.isKeyDown(KeyEvent.VK_P)) {
            
        }
    }

    public boolean hasKeysPressed() {
        if (KeyInput.isKeyDown(KeyEvent.VK_A) || KeyInput.isKeyDown(KeyEvent.VK_D) || KeyInput.isKeyDown(KeyEvent.VK_SPACE)) {
            return true;
        }
        return false;
    }
}
