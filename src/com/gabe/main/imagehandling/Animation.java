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
            playerIdleRight = new ImageIcon(new File(Player.class.getResource("/com/gabe/main/images/animations/Standin.gif").getFile()).toURI().toURL()).getImage();
            playerIdleLeft = new ImageIcon(new File(Player.class.getResource("/com/gabe/main/images/animations/StandinLeft.gif").getFile()).toURI().toURL()).getImage();
            playerWalkingRight = new ImageIcon(new File(Player.class.getResource("/com/gabe/main/images/animations/Walkin.gif").getFile()).toURI().toURL()).getImage();
            playerWalkingLeft = new ImageIcon(new File(Player.class.getResource("/com/gabe/main/images/animations/WalkinLeft.gif").getFile()).toURI().toURL()).getImage();
            playerJumpRight = new ImageIcon(new File(Player.class.getResource("/com/gabe/main/images/animations/FlailingJump.gif").getFile()).toURI().toURL()).getImage();
            playerJumpLeft = new ImageIcon(new File(Player.class.getResource("/com/gabe/main/images/animations/FlailingJumpLeft.gif").getFile()).toURI().toURL()).getImage();
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
        switch(object.getGravityDirection()) {
            case DOWN: return rotatedImage;
            case UP: return ImageRotator.flipImage(rotatedImage);
            case LEFT: return ImageRotator.rotateImageClockwise(rotatedImage);
            case RIGHT: return ImageRotator.rotateImageCounterClockwise(rotatedImage);
        }
        return rotatedImage;
    }
}
