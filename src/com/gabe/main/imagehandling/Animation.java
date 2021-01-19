package com.gabe.main.imagehandling;

import com.gabe.main.Direction;
import com.gabe.main.GameObject;
import com.gabe.main.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

/**
 * Created by Gabriel on 2020-05-14.
 */
public class Animation {
    
    public static Image playerIdleRight;
    public static Image playerIdleLeft;
    public static Image playerWalkingRight;
    public static Image playerWalkingLeft;
    public static Image playerJumpRight;
    public static Image playerJumpLeft;
    
    static {
        try {
            playerIdleRight = new ImageIcon(Player.class.getResourceAsStream("/com/gabe/main/images/animations/Standin.gif").readAllBytes()).getImage();
            playerIdleLeft = new ImageIcon(Player.class.getResourceAsStream("/com/gabe/main/images/animations/StandinLeft.gif").readAllBytes()).getImage();
            playerWalkingRight = new ImageIcon(Player.class.getResourceAsStream("/com/gabe/main/images/animations/Walkin.gif").readAllBytes()).getImage();
            playerWalkingLeft = new ImageIcon(Player.class.getResourceAsStream("/com/gabe/main/images/animations/WalkinLeft.gif").readAllBytes()).getImage();
            playerJumpRight = new ImageIcon(Player.class.getResourceAsStream("/com/gabe/main/images/animations/FlailingJump.gif").readAllBytes()).getImage();
            playerJumpLeft = new ImageIcon(Player.class.getResourceAsStream("/com/gabe/main/images/animations/FlailingJumpLeft.gif").readAllBytes()).getImage();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static Image getPlayerAnimation(Player p) {
        Image getAnimation = null;
        if(((p.getGravityDirection() == Direction.DOWN && p.yDelta > 0) || (p.getGravityDirection() == Direction.UP && p.yDelta < 0) || (p.getGravityDirection() == Direction.LEFT && p.xDelta < 0) || (p.getGravityDirection() == Direction.RIGHT && p.xDelta > 0)) && !p.isStuckToSlope) {
            getAnimation = p.isFacing == Direction.RIGHT ? getRotatedImage(p, playerJumpRight) : getRotatedImage(p, playerJumpLeft);
        } else if(p.speedSideWays == 0 && !p.hasKeysPressed()) {
            getAnimation = p.isFacing == Direction.RIGHT ? getRotatedImage(p, playerIdleRight) : getRotatedImage(p, playerIdleLeft);
        } else if(p.hasKeysPressed() || p.speedSideWays != 0) {
            getAnimation = p.isFacing == Direction.RIGHT ? getRotatedImage(p, playerWalkingRight) : getRotatedImage(p, playerWalkingLeft);
        }
        return getAnimation;
    }
    
    public static Image getRotatedImage(GameObject object, Image image) {
        BufferedImage rotatedImage = ImageRotator.toBufferedImage(image);
        return switch (object.getGravityDirection()) {
            case DOWN -> rotatedImage;
            case UP -> ImageRotator.flipImage(rotatedImage);
            case LEFT -> ImageRotator.rotateImageClockwise(rotatedImage);
            case RIGHT -> ImageRotator.rotateImageCounterClockwise(rotatedImage);
        };
    }
}
