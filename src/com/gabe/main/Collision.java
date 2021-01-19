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
        return switch (object.getGravityDirection()) {
            case DOWN -> getTopLeft(hitBox);
            case RIGHT -> getBottomLeft(hitBox);
            case LEFT -> getTopRight(hitBox);
            case UP -> getBottomRight(hitBox);
        };
    }
    
    public static Point getTopRight(GameObject object) {
        Rectangle hitBox = object.getBoundingBox()[0];
        return switch (object.getGravityDirection()) {
            case DOWN -> getTopRight(hitBox);
            case RIGHT -> getTopLeft(hitBox);
            case LEFT -> getBottomRight(hitBox);
            case UP -> getBottomLeft(hitBox);
        };
    }
    
    public static Point getBottomLeft(GameObject object) {
        Rectangle hitBox = object.getBoundingBox()[0];
        return switch (object.getGravityDirection()) {
            case DOWN -> getBottomLeft(hitBox);
            case RIGHT -> getBottomRight(hitBox);
            case LEFT -> getTopLeft(hitBox);
            case UP -> getTopRight(hitBox);
        };
    }
    
    public static Point getBottomRight(GameObject object) {
        Rectangle hitBox = object.getBoundingBox()[0];
        return switch (object.getGravityDirection()) {
            case DOWN -> getBottomRight(hitBox);
            case RIGHT -> getTopRight(hitBox);
            case LEFT -> getBottomLeft(hitBox);
            case UP -> getTopLeft(hitBox);
        };
    }
    
    public static boolean pointIsCollidingWithRectangle(Rectangle hitBox, Point point) {
        return Triangle.isBetweenX(point, getBottomLeft(hitBox).x, getBottomRight(hitBox).x) && Triangle.isBetweenY(point, getBottomLeft(hitBox).y, getTopLeft(hitBox).y);
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
        return switch (object.getGravityDirection()) {
            case DOWN -> new Point(((getBottomRight(hitBox).x - getBottomLeft(hitBox).x) / 2) + getBottomLeft(hitBox).x, getBottomLeft(hitBox).y);
            case UP -> new Point(((getBottomRight(hitBox).x - getBottomLeft(hitBox).x) / 2) + getBottomLeft(hitBox).x, getTopLeft(hitBox).y);
            case LEFT -> new Point(getTopLeft(hitBox).x, ((getBottomLeft(hitBox).y - getTopLeft(hitBox).y) / 2) + getTopLeft(hitBox).y);
            case RIGHT -> new Point(getTopRight(hitBox).x, ((getBottomLeft(hitBox).y - getTopLeft(hitBox).y) / 2) + getTopLeft(hitBox).y);
        };
    }

    public static Tile getSlopeCollidableTile(GameObject object) {
        return Tile.getTileAtPoint(getSlopeCollidablePoint(object));
    }
    
    public static Point getRoofSlopeCollidablePoint(GameObject object) {
        Rectangle hitBox = object.getBoundingBox()[0];
        return switch (object.getGravityDirection()) {
            case DOWN -> new Point(((getTopRight(hitBox).x - getTopLeft(hitBox).x) / 2) + getTopLeft(hitBox).x, getTopLeft(hitBox).y);
            case UP -> new Point(((getTopRight(hitBox).x - getTopLeft(hitBox).x) / 2) + getTopLeft(hitBox).x, getBottomLeft(hitBox).y);
            case LEFT -> new Point(getTopRight(hitBox).x, ((getBottomRight(hitBox).y - getTopRight(hitBox).y) / 2) + getBottomRight(hitBox).y);
            case RIGHT -> new Point(getTopLeft(hitBox).x, ((getBottomRight(hitBox).y - getTopRight(hitBox).y) / 2) + getBottomRight(hitBox).y);
        };
    }
    
    public static Tile getRoofSlopeCollidableTile(GameObject object) {
        return Tile.getTileAtPoint(getRoofSlopeCollidablePoint(object));
    }
    
    public static Point getWallLeftSlopeCollidablePoint(GameObject object) {
        Rectangle hitBox = object.getBoundingBox()[0];
        return switch (object.getGravityDirection()) {
            case DOWN -> new Point(getTopLeft(hitBox).x, (((getBottomLeft(hitBox).y - getTopLeft(hitBox).y) / 2) + getTopLeft(hitBox).y));
            case UP -> new Point(getTopRight(hitBox).x, ((getBottomRight(hitBox).y - getTopRight(hitBox).y) / 2) + getBottomRight(hitBox).y);
            case LEFT -> new Point(((getTopRight(hitBox).x - getTopLeft(hitBox).x) / 2) + getTopLeft(hitBox).x, getTopLeft(hitBox).y);
            case RIGHT -> new Point(((getBottomLeft(hitBox).x - getBottomRight(hitBox).x) / 2) + getBottomLeft(hitBox).x, getBottomLeft(hitBox).y);
        };
    }
    
    public static Tile getWallLeftSlopeCollidableTile(GameObject object) {
        return Tile.getTileAtPoint(getWallLeftSlopeCollidablePoint(object));
    }
    
    public static Point getWallRightSlopeCollidablePoint(GameObject object) {
        Rectangle hitBox = object.getBoundingBox()[0];
        return switch (object.getGravityDirection()) {
            case DOWN -> new Point(getTopRight(hitBox).x, ((getBottomRight(hitBox).y - getTopRight(hitBox).y) / 2) + getTopRight(hitBox).y);
            case UP -> new Point(getTopLeft(hitBox).x, ((getBottomLeft(hitBox).y - getTopLeft(hitBox).y) / 2) + getTopLeft(hitBox).y);
            case LEFT -> new Point(((getBottomRight(hitBox).x - getBottomLeft(hitBox).x) / 2) + getBottomLeft(hitBox).x, getBottomLeft(hitBox).y);
            case RIGHT -> new Point(((getTopRight(hitBox).x - getTopLeft(hitBox).x) / 2) + getTopLeft(hitBox).x, getTopLeft(hitBox).y);
        };
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
        switch (object.getGravityDirection()) {
            case DOWN -> snapToTop(object, tile);
            case UP -> snapToBottom(object, tile);
            case LEFT -> snapToRight(object, tile);
            case RIGHT -> snapToLeft(object, tile);
        }
        object.speedUpAndDown = 0;
        object.isFalling = false;
    }
    
    public static void snapObjectToBottomOfTile(GameObject object, Tile tile) {
        switch (object.getGravityDirection()) {
            case DOWN -> snapToBottom(object, tile);
            case UP -> snapToTop(object, tile);
            case LEFT -> snapToLeft(object, tile);
            case RIGHT -> snapToRight(object, tile);
        }
        object.speedUpAndDown = 0;
    }
    
    public static void snapObjectToLeftOfTile(GameObject object, Tile tile) {
        switch (object.getGravityDirection()) {
            case DOWN -> snapToLeft(object, tile);
            case UP -> snapToRight(object, tile);
            case LEFT -> snapToTop(object, tile);
            case RIGHT -> snapToBottom(object, tile);
        }
        object.speedSideWays = 0;
    }
    
    public static void snapObjectToRightOfTile(GameObject object, Tile tile) {
        switch (object.getGravityDirection()) {
            case DOWN -> snapToRight(object, tile);
            case UP -> snapToLeft(object, tile);
            case LEFT -> snapToBottom(object, tile);
            case RIGHT -> snapToTop(object, tile);
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
            switch (object.getGravityDirection()) {
                case DOWN -> {
                    p1.y++;
                    p2.y++;
                }
                case UP -> {
                    p1.y--;
                    p2.y--;
                }
                case LEFT -> {
                    p1.x--;
                    p2.x--;
                }
                case RIGHT -> {
                    p1.x++;
                    p2.x++;
                }
            }
            Tile tileLeft = Tile.getTileAtPoint(p1);
            Tile tileRight = Tile.getTileAtPoint(p2);
            object.isFalling = !tileLeft.isCollidable && !tileRight.isCollidable;
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
        switch (object.getGravityDirection()) {
            case DOWN -> object.y += Math.abs(object.speedSideWays) + 1;
            case UP -> object.y -= Math.abs(object.speedSideWays) + 1;
            case LEFT -> object.x -= Math.abs(object.speedSideWays) + 1;
            case RIGHT -> object.x += Math.abs(object.speedSideWays) + 1;
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
