package com.gabe.main.imagehandling;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

/**
 * Created by Gabriel on 2020-06-19.
 */
public class ImageRotator {
    
    public static BufferedImage rotateImageClockwise(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();
        BufferedImage rotatedImage = new BufferedImage(height, width, image.getType());
        for(int i = 0; i < width; i++) {
            for(int j = 0; j < height; j++) {
                rotatedImage.setRGB(height-1-j, i, image.getRGB(i, j));
            }
        }
        return rotatedImage;
    }
    
    public static BufferedImage rotateImageCounterClockwise(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();
        BufferedImage rotatedImage = new BufferedImage(height, width, image.getType());
        for(int i = 0; i < width; i++) {
            for(int j = 0; j < height; j++) {
                rotatedImage.setRGB(j, width-1-i, image.getRGB(i, j));
            }
        }
        return rotatedImage;
    }

    public static BufferedImage flipImage(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();
        BufferedImage rotatedImage = new BufferedImage(width, height, image.getType());
        for(int i = 0; i < width; i++) {
            for(int j = 0; j < height; j++) {
                rotatedImage.setRGB(width-1-i, height-1-j, image.getRGB(i, j));
            }
        }
        return rotatedImage;
    }

    public static BufferedImage rotateImageWithPrecision(BufferedImage image, int degree) {
        int width = image.getWidth();
        int height = image.getHeight();
        double radians = Math.toRadians(degree);
        double sin = Math.abs(Math.sin(radians));
        double cos = Math.abs(Math.cos(radians));
        int adjustedWidth = (int)Math.floor(width * cos + height * sin) + 2;
        int adjustedHeight = (int)Math.floor(height * cos + width * sin) + 2;
        BufferedImage rotatedImage = new BufferedImage(adjustedWidth, adjustedHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = rotatedImage.createGraphics();
        AffineTransform at = new AffineTransform();
        at.translate((adjustedWidth - width) / 2, (adjustedHeight - height) / 2);
        int x = width / 2;
        int y = height / 2;
        at.rotate(radians, x, y);
        g2d.setTransform(at);
        g2d.drawImage(image, 0, 0, null);
        g2d.dispose();
        return rotatedImage;
    }
    
    public static BufferedImage toBufferedImage(Image image) {
        if(image instanceof BufferedImage) {
            return (BufferedImage)image;
        }
        BufferedImage convertedImage = new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = convertedImage.createGraphics();
        g2d.drawImage(image, 0, 0, null);
        g2d.dispose();
        return convertedImage;
    }
}
