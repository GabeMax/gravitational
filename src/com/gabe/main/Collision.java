package com.gabe.main;

import java.awt.*;

/**
 * Created by Gabriel on 2020-05-07.
 */
public class Collision {
    public static Point getTopLeft(Rectangle hitBox) {
        return new Point(hitBox.x, hitBox.y);
    }
    
    public static Point getTopRight(Rectangle hitBox) {
        return new Point(hitBox.x+hitBox.width, hitBox.y);
    }
    
    public static Point getBottomLeft(Rectangle hitBox) {
        return new Point(hitBox.x, hitBox.y+hitBox.height);
    }
    
    public static Point getBottomRight(Rectangle hitBox) {
        return new Point(hitBox.x+hitBox.width, hitBox.y+hitBox.height);
    }
    
    public static Point getTopLeft(GameObject object) {
        Rectangle hitBox = object.getBoundingBox()[0];
        switch (object.getGravityDirection()) {
            case DOWN: return getTopLeft(hitBox);
            case RIGHT: return getBottomLeft(hitBox);
            case LEFT: return getTopRight(hitBox);
            case UP: return getBottomRight(hitBox);
        }
        return null;
    }
    
    public static Point getTopRight(GameObject object) {
        Rectangle hitBox = object.getBoundingBox()[0];
        switch(object.getGravityDirection()) {
            case DOWN: return getTopRight(hitBox);
            case RIGHT: return getTopLeft(hitBox);
            case LEFT: return getBottomRight(hitBox);
            case UP: return getBottomLeft(hitBox);
        }
        return null;
    }
    
    public static Point getBottomLeft(GameObject object) {
        Rectangle hitBox = object.getBoundingBox()[0];
        switch(object.getGravityDirection()) {
            case DOWN: return getBottomLeft(hitBox);
            case RIGHT: return getBottomRight(hitBox);
            case LEFT: return getTopLeft(hitBox);
            case UP: return getTopRight(hitBox);
        }
        return null;
    }
    
    public static Point getBottomRight(GameObject object) {
        Rectangle hitBox = object.getBoundingBox()[0];
        switch(object.getGravityDirection()) {
            case DOWN: return getBottomRight(hitBox);
            case RIGHT: return getTopRight(hitBox);
            case LEFT: return getBottomLeft(hitBox);
            case UP: return getTopLeft(hitBox);
        }
        return null;
    }
    
    public static boolean pointIsCollidingWithRectangle(Rectangle hitBox, Point point) {
        if(Triangle.isBetweenX(point, getBottomLeft(hitBox).x, getBottomRight(hitBox).x) && Triangle.isBetweenY(point, getBottomLeft(hitBox).y, getTopLeft(hitBox).y)) {
            return true;
        }
        return false;
    }
    
    public static boolean topLeftIsColliding(GameObject object) {
        return Tile.getTileAtPoint(getTopLeft(object)).isCollidable;
    }
    
    public static boolean topRightIsColliding(GameObject object) {
        return Tile.getTileAtPoint(getTopRight(object)).isCollidable;
    }
    
    public static boolean bottomLeftIsColliding(GameObject object) {
        return Tile.getTileAtPoint(getBottomLeft(object)).isCollidable;
    }
    
    public static boolean bottomRightIsColliding(GameObject object) {
        return Tile.getTileAtPoint(getBottomRight(object)).isCollidable;
    }
    
    public static Point getSlopeCollidablePoint(GameObject object) {
        Rectangle hitBox = object.getBoundingBox()[0];
        switch(object.getGravityDirection()) {
            case DOWN: return new Point(((getBottomRight(hitBox).x - getBottomLeft(hitBox).x)/2) + getBottomLeft(hitBox).x, getBottomLeft(hitBox).y);
            case UP: return new Point(((getBottomRight(hitBox).x - getBottomLeft(hitBox).x)/2) + getBottomLeft(hitBox).x, getTopLeft(hitBox).y);
            case LEFT: return new Point(getTopLeft(hitBox).x, ((getBottomLeft(hitBox).y - getTopLeft(hitBox).y) / 2) + getTopLeft(hitBox).y);
            case RIGHT: return new Point(getTopRight(hitBox).x, ((getBottomLeft(hitBox).y - getTopLeft(hitBox).y) / 2) + getTopLeft(hitBox).y);
        }
        return new Point(((getBottomRight(hitBox).x - getBottomLeft(hitBox).x)/2) + getBottomLeft(hitBox).x, getBottomLeft(hitBox).y);
    }

    public static Tile getSlopeCollidableTile(GameObject object) {
        return Tile.getTileAtPoint(getSlopeCollidablePoint(object));
    }
    
    public static Point getRoofSlopeCollidablePoint(GameObject object) {
        Rectangle hitBox = object.getBoundingBox()[0];
        switch(object.getGravityDirection()) {
            case DOWN: return new Point(((getTopRight(hitBox).x - getTopLeft(hitBox).x) / 2) + getTopLeft(hitBox).x, getTopLeft(hitBox).y);
            case UP: return new Point(((getTopRight(hitBox).x - getTopLeft(hitBox).x) / 2) + getTopLeft(hitBox).x, getBottomLeft(hitBox).y);
            case LEFT: return new Point(getTopRight(hitBox).x, ((getBottomRight(hitBox).y - getTopRight(hitBox).y) / 2) + getBottomRight(hitBox).y);
            case RIGHT: return new Point(getTopLeft(hitBox).x, ((getBottomRight(hitBox).y - getTopRight(hitBox).y) / 2) + getBottomRight(hitBox).y);
        }
        return new Point(((getTopRight(hitBox).x - getTopLeft(hitBox).x) / 2) + getTopLeft(hitBox).x, getTopLeft(hitBox).y);
    }
    
    public static Tile getRoofSlopeCollidableTile(GameObject object) {
        return Tile.getTileAtPoint(getRoofSlopeCollidablePoint(object));
    }
    
    public static Point getWallLeftSlopeCollidablePoint(GameObject object) {
        Rectangle hitBox = object.getBoundingBox()[0];
        switch(object.getGravityDirection()) {
            case DOWN: return new Point(getTopLeft(hitBox).x, (((getBottomLeft(hitBox).y - getTopLeft(hitBox).y)/2)+getTopLeft(hitBox).y));
            case UP: return new Point(getTopRight(hitBox).x, ((getBottomRight(hitBox).y - getTopRight(hitBox).y)/2)+getBottomRight(hitBox).y);
            case LEFT: return new Point(((getTopRight(hitBox).x - getTopLeft(hitBox).x) / 2 )+ getTopLeft(hitBox).x, getTopLeft(hitBox).y);
            case RIGHT: return new Point(((getBottomLeft(hitBox).x - getBottomRight(hitBox).x) / 2) + getBottomLeft(hitBox).x, getBottomLeft(hitBox).y);
        }
        return new Point(getTopLeft(hitBox).x, (((getBottomLeft(hitBox).y - getTopLeft(hitBox).y)/2)+getTopLeft(hitBox).y));
    }
    
    public static Tile getWallLeftSlopeCollidableTile(GameObject object) {
        return Tile.getTileAtPoint(getWallLeftSlopeCollidablePoint(object));
    }
    
    public static Point getWallRightSlopeCollidablePoint(GameObject object) {
        Rectangle hitBox = object.getBoundingBox()[0];
        switch(object.getGravityDirection()) {
            case DOWN: return new Point(getTopRight(hitBox).x, ((getBottomRight(hitBox).y - getTopRight(hitBox).y) / 2) + getTopRight(hitBox).y);
            case UP: return new Point(getTopLeft(hitBox).x, ((getBottomLeft(hitBox).y - getTopLeft(hitBox).y) / 2) + getTopLeft(hitBox).y);
            case LEFT: return new Point(((getBottomRight(hitBox).x - getBottomLeft(hitBox).x) / 2) + getBottomLeft(hitBox).x, getBottomLeft(hitBox).y);
            case RIGHT: return new Point(((getTopRight(hitBox).x - getTopLeft(hitBox).x) / 2) + getTopLeft(hitBox).x, getTopLeft(hitBox).y);
        }
        return new Point(getTopRight(hitBox).x, ((getBottomRight(hitBox).y - getTopRight(hitBox).y) / 2) + getTopRight(hitBox).y);
    }
    
    public static Tile getWallRightSlopeCollidableTile(GameObject object) {
        return Tile.getTileAtPoint(getWallRightSlopeCollidablePoint(object));
    }
    
    public static void snapObjectToSlope(GameObject object) {
        Point offSetPoint = getSlopeCollidableTile(object).slopeHitBox.getVerticalOffset(object, getSlopeCollidablePoint(object), true);
        object.x -= offSetPoint.x;
        object.y -= offSetPoint.y;
        object.speedUpAndDown = 0;
        object.isFalling = false;
    }
    
    public static void snapToTop(GameObject object, Tile tile) {
        object.y = tile.y - object.totalHeight - 1;
    }
    
    public static void snapToBottom(GameObject object, Tile tile) {
        object.y = tile.y + Tile.tileHeight - (object.getBoundingBox()[0].y - object.y);
    }
    
    public static void snapToRight(GameObject object, Tile tile) {
        object.x = tile.x + Tile.tileWidth - (object.getBoundingBox()[0].x - object.x);
    }
    
    public static void snapToLeft(GameObject object, Tile tile) {
        object.x = tile.x - object.totalWidth - 1;
    }
    
    public static void snapObjectToTopOfTile(GameObject object, Tile tile) {
        switch(object.getGravityDirection()) {
            case DOWN:
                snapToTop(object, tile);
                break;
            case UP:
                snapToBottom(object, tile);
                break;
            case LEFT:
                snapToRight(object, tile);
                break;
            case RIGHT:
                snapToLeft(object, tile);
                break;
        }
        object.speedUpAndDown = 0;
        object.isFalling = false;
    }
    
    public static void snapObjectToBottomOfTile(GameObject object, Tile tile) {
        switch(object.getGravityDirection()) {
            case DOWN:
                snapToBottom(object, tile);
                break;
            case UP:
                snapToTop(object, tile);
                break;
            case LEFT:
                snapToLeft(object, tile);
                break;
            case RIGHT:
                snapToRight(object, tile);
                break;
        }
        object.speedUpAndDown = 0;
    }
    
    public static void snapObjectToLeftOfTile(GameObject object, Tile tile) {
        switch(object.getGravityDirection()) {
            case DOWN:
                snapToLeft(object, tile);
                break;
            case UP:
                snapToRight(object, tile);
                break;
            case LEFT:
                snapToTop(object, tile);
                break;
            case RIGHT:
                snapToBottom(object, tile);
                break;
        }
        object.speedSideWays = 0;
    }
    
    public static void snapObjectToRightOfTile(GameObject object, Tile tile) {
        switch(object.getGravityDirection()) {
            case DOWN:
                snapToRight(object, tile);
                break;
            case UP:
                snapToLeft(object, tile);
                break;
            case LEFT:
                snapToBottom(object, tile);
                break;
            case RIGHT:
                snapToTop(object, tile);
                break;
        }
        object.speedSideWays = 0;
    }
    
    public static void checkSideToSideCollision(GameObject object) {
        Tile tileBottomLeft = Tile.getTileAtPoint(getBottomLeft(object));
        if (tileBottomLeft.isCollidable) {
            snapObjectToRightOfTile(object, tileBottomLeft);
        }
        Tile tileBottomRight = Tile.getTileAtPoint(getBottomRight(object));
        if (tileBottomRight.isCollidable) {
            snapObjectToLeftOfTile(object, tileBottomRight);
        }
        Tile tileTopLeft = Tile.getTileAtPoint(getTopLeft(object));
        if (tileTopLeft.isCollidable) {
            snapObjectToRightOfTile(object, tileTopLeft);
        }
        Tile tileTopRight = Tile.getTileAtPoint(getTopRight(object));
        if (tileTopRight.isCollidable) {
            snapObjectToLeftOfTile(object, tileTopRight);
        }
    }

    public static void checkUpAndDownCollision(GameObject object) {
        boolean bottomLeftOrRight = false;
        Tile tileBottomLeft = Tile.getTileAtPoint(getBottomLeft(object));
        if (tileBottomLeft.isCollidable) {
            snapObjectToTopOfTile(object, tileBottomLeft);
            bottomLeftOrRight = true;
        }
        Tile tileBottomRight = Tile.getTileAtPoint(getBottomRight(object));
        if (tileBottomRight.isCollidable) {
            snapObjectToTopOfTile(object, tileBottomRight);
            bottomLeftOrRight = true;
        }
        groundCheck(object, bottomLeftOrRight);
        Tile tileTopLeft = Tile.getTileAtPoint(getTopLeft(object));
        if (tileTopLeft.isCollidable) {
            snapObjectToBottomOfTile(object, tileTopLeft);
        }
        Tile tileTopRight = Tile.getTileAtPoint(getTopRight(object));
        if (tileTopRight.isCollidable) {
            snapObjectToBottomOfTile(object, tileTopRight);
        }
    }
    
    public static void groundCheck(GameObject object, boolean bottomLeftOrRight) {
        if (!bottomLeftOrRight) {
            Point p1 = getBottomLeft(object);
            Point p2 = getBottomRight(object);
            switch(object.getGravityDirection()) {
                case DOWN:
                    p1.y++;
                    p2.y++;
                    break;
                case UP:
                    p1.y--;
                    p2.y--;
                    break;
                case LEFT:
                    p1.x--;
                    p2.x--;
                    break;
                case RIGHT:
                    p1.x++;
                    p2.x++;
                    break;
            }
            Tile tileLeft = Tile.getTileAtPoint(p1);
            Tile tileRight = Tile.getTileAtPoint(p2);
            if (tileLeft.isCollidable || tileRight.isCollidable) {
                object.isFalling = false;
            } else {
                object.isFalling = true;
            }
        }
    }

    public static void handleSlopeCollision(GameObject object) {
        boolean skipSnap = false;
        if(!getSlopeCollidableTile(object).isSlope) {
            if (object.getTileOffsetPoint(getSlopeCollidablePoint(object), 0, -1).isSlope && object.speedUpAndDown == 0) {
                snapObjectToTopOfTile(object, getSlopeCollidableTile(object));
            } else if (object.getTileOffsetPoint(getSlopeCollidablePoint(object), 0, 1).isSlope) {
                upAndDownTranslate(object);
                object.isFalling = true;
            } else if ((object.getTileOffsetPoint(getSlopeCollidablePoint(object), 1, 0).isSlope || object.getTileOffsetPoint(getSlopeCollidablePoint(object), -1, 0).isSlope) && getSlopeCollidableTile(object).isCollidable && object.speedUpAndDown == 0) {
                snapObjectToTopOfTile(object, Tile.getTileAtPoint(getSlopeCollidablePoint(object)));
                skipSnap = true;
            } else if (object.getTileOffsetPoint(getSlopeCollidablePoint(object), 0, 1).isCollidable && object.speedUpAndDown == 0) {
                snapObjectToTopOfTile(object, object.getTileOffsetPoint(getSlopeCollidablePoint(object), 0, 1));
                skipSnap = true;
            } else if(object.getTileOffsetPoint(getSlopeCollidablePoint(object), 0, -1).isSlope && object.speedUpAndDown > 0) {
                snapObjectToTopOfTile(object, getSlopeCollidableTile(object));
                object.isStuckToSlope = true;
            } else if(Tile.getTileAtPoint(getSlopeCollidablePoint(object)).isCollidable) {
                snapObjectToTopOfTile(object, getSlopeCollidableTile(object));
            }
        }
        if(getSlopeCollidableTile(object).isSlope && !skipSnap) {
            if(getSlopeCollidableTile(object).slopeHitBox.pointIsInsideTriangle(getSlopeCollidablePoint(object))) {
                object.isStuckToSlope = true;
            }
            if(object.isStuckToSlope) {
                snapObjectToSlope(object);
            }
        }
        checkSlopeWallCollision(object);
        if(topLeftIsColliding(object) || topRightIsColliding(object)) {
            snapObjectToBottomOfTile(object, Tile.getTileAtPoint(getTopLeft(object)));
            if(getSlopeCollidableTile(object).isSlope) {
                Point offSetPoint = getSlopeCollidableTile(object).slopeHitBox.getHorizontalOffset(object, getSlopeCollidablePoint(object));
                object.x += offSetPoint.x;
                object.y += offSetPoint.y;
            }
        }
    }
    
    public static void upAndDownTranslate(GameObject object) {
        switch(object.getGravityDirection()) {
            case DOWN:
                object.y += Math.abs(object.speedSideWays) + 1;
                break;
            case UP:
                object.y -= Math.abs(object.speedSideWays) + 1;
                break;
            case LEFT:
                object.x -= Math.abs(object.speedSideWays) + 1;
                break;
            case RIGHT:
                object.x += Math.abs(object.speedSideWays) + 1;
                break;
        }
    }
    
    public static void checkSlopedRoofCollisionSideToSide(GameObject object) {
        if(object.speedSideWays != 0) {
            if(getRoofSlopeCollidableTile(object).isSlope) {
                if(getRoofSlopeCollidableTile(object).slopeHitBox.pointIsInsideTriangle(getRoofSlopeCollidablePoint(object))) {
                    Point offSetPoint = getRoofSlopeCollidableTile(object).slopeHitBox.getHorizontalOffset(object, getRoofSlopeCollidablePoint(object));
                    object.x += offSetPoint.x;
                    object.y += offSetPoint.y;
                    object.speedSideWays = 0;
                    object.isFalling = true;
                }
            }
        }
    }
    
    public static void checkSlopedRoofCollisionUpAndDown(GameObject object) {
        if(object.speedUpAndDown != 0) {
            if(getRoofSlopeCollidableTile(object).isSlope) {
                if(getRoofSlopeCollidableTile(object).slopeHitBox.pointIsInsideTriangle(getRoofSlopeCollidablePoint(object))) {
                    Point offSetPoint = getRoofSlopeCollidableTile(object).slopeHitBox.getVerticalOffset(object, getRoofSlopeCollidablePoint(object), false);
                    object.x -= offSetPoint.x;
                    object.y -= offSetPoint.y;
                    object.speedUpAndDown = 0;
                    object.isFalling = true;
                }
            }
        }
    }
    
    public static void checkSlopeWallCollision(GameObject object) {
        if(getWallLeftSlopeCollidableTile(object).isCollidable) {
            snapObjectToRightOfTile(object, getWallLeftSlopeCollidableTile(object));
        }
        if(getWallRightSlopeCollidableTile(object).isCollidable) {
            snapObjectToLeftOfTile(object, getWallRightSlopeCollidableTile(object));
        }
    }

    public static void applySingleHitboxCollision(GameObject object) {
        int prevY = object.y;
        int prevX = object.x;
        if(getSlopeCollidableTile(object).isSlope || object.getTileOffsetPoint(getSlopeCollidablePoint(object), 0, 1).isSlope) {
            object.moveObjectSideToSide();
            checkSlopedRoofCollisionSideToSide(object);
            object.moveObjectUpAndDown();
            checkSlopedRoofCollisionUpAndDown(object);
            handleSlopeCollision(object);
        } else {
            object.moveObjectSideToSide();
            checkSideToSideCollision(object);
            checkSlopedRoofCollisionSideToSide(object);
            object.moveObjectUpAndDown();
            checkUpAndDownCollision(object);
            checkSlopedRoofCollisionUpAndDown(object);
            object.isStuckToSlope = false;
            if(getSlopeCollidableTile(object).isSlope) {
                handleSlopeCollision(object);
            }
        }
        if(object.isFalling) {
            object.speedUpAndDown += object.gravityConstant;
        }
        object.x = Game.clamp(object.x, 0, Tile.mapWidth*Tile.tileWidth);
        object.y = Game.clamp(object.y, 0, Tile.mapHeight*Tile.tileHeight);
        object.yDelta = object.y - prevY;
        object.xDelta = object.x - prevX;
    }
}
