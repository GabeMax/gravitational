package com.gabe.main;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

/**
 * Created by Gabriel on 2020-05-07.
 */
public class Tile {
    public static final int tileWidth = 32;
    public static final int tileHeight = 32;
    public static final int mapWidth = 200;
    public static final int mapHeight = 200;
    public int x;
    public int y;
    public boolean isCollidable;
    public boolean isSlope;
    public Triangle slopeHitBox;
    public TileID type;
    public static Tile[][] tiles = new Tile[mapWidth][mapHeight];
    public static BufferedImage tileMapImage = updateTiles(true);
    private static Graphics mapGraphics = tileMapImage.getGraphics();
    
    public Tile(int tileX, int tileY, boolean isCollidable, boolean isSlope, Triangle slopeHitBox, TileID type) {
        this.x = tileX*tileWidth;
        this.y = tileY*tileHeight;
        this.isCollidable = isCollidable;
        this.isSlope = isSlope;
        this.slopeHitBox = slopeHitBox;
        this.type = type;
    }
    
    public static void readTilesFromFile() {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(Tile.class.getResourceAsStream("/com/gabe/main/maps/Tiles.txt")));
            String line;
            for (int i = 0; (line = br.readLine()) != null; i++) {
                for (int j = 0; j < line.length(); j++) {
                    TileID id = TileID.Air;
                    boolean isCollidable = false;
                    boolean isSlope = false;
                    Triangle slopeHitBox = null;
                    if(line.charAt(j) == '1') {
                        id = TileID.BasicGround;
                        isCollidable = true;
                    } else if(line.charAt(j) == '2') {
                        id = TileID.BasicSlope; //Fenceposting has led to the need of all points covering the right side and bottom of a tile need to be uplifted by one
                        slopeHitBox = Triangle.getTriangle(j * tileWidth, i * tileHeight, id.getSlope());
                        isSlope = true;
                    } else if(line.charAt(j) == '3') {
                        id = TileID.AdvancedSlope;
                        slopeHitBox = Triangle.getTriangle(j * tileWidth, i * tileHeight, id.getSlope());
                        isSlope = true;
                    } else if(line.charAt(j) == '4') {
                        id = TileID.CollidableAir;
                        isCollidable = true;
                    } else if(line.charAt(j) == '5') {
                        id = TileID.RoofSlope;
                        slopeHitBox = Triangle.getTriangle(j * tileWidth, i * tileHeight, id.getSlope());
                        isSlope = true;
                    } else if(line.charAt(j) == '6') {
                        id = TileID.ShapelessSlope;
                        slopeHitBox = Triangle.getTriangle(j * tileWidth, i * tileHeight, id.getSlope());
                        isSlope = true;
                    } else if(line.charAt(j) == '7') {
                        id = TileID.AlternateRoofSlope;
                        slopeHitBox = Triangle.getTriangle(j * tileWidth, i * tileHeight, id.getSlope());
                        isSlope = true;
                    }
                    tiles[i][j] = new Tile(j, i, isCollidable, isSlope, slopeHitBox, id);
                }
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void drawSlopes(Graphics2D g) {
        for (Tile[] tileArray: tiles) {
            for(Tile tile: tileArray) {
                if(tile.isSlope) {
                    g.setColor(Color.cyan);
                    g.drawLine(tile.slopeHitBox.basePoint1.x, tile.slopeHitBox.basePoint1.y, tile.slopeHitBox.basePoint2.x, tile.slopeHitBox.basePoint2.y);
                    g.drawLine(tile.slopeHitBox.basePoint1.x, tile.slopeHitBox.basePoint1.y, tile.slopeHitBox.outerPoint.x, tile.slopeHitBox.outerPoint.y);
                    g.drawLine(tile.slopeHitBox.basePoint2.x, tile.slopeHitBox.basePoint2.y, tile.slopeHitBox.outerPoint.x, tile.slopeHitBox.outerPoint.y);
                }
            }
        }
    }
    
    public static BufferedImage updateTiles(boolean fromFile) {
        if (fromFile) {
            readTilesFromFile();
        }
        BufferedImage image = new BufferedImage(tileWidth * mapWidth, tileHeight * mapHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D tileImage = image.createGraphics();
        for (Tile[] tileArray : tiles) {
            for (Tile tile : tileArray) {
                tileImage.drawImage(tile.type.getImage(), tile.x, tile.y, null);
            }
        }
        drawSlopes(tileImage);
        tileImage.dispose();
        return image;
    }
    
    public static Tile getTileAtPoint(Point p) {
        return tiles[(int)Math.floor(p.y/tileHeight)][(int)Math.floor(p.x/tileWidth)];
    }
    
    public static Point getTilePositionAtPoint(Point p) {
        return new Point((p.x/tileWidth) * tileWidth + (p.x % tileWidth), (p.y / tileHeight) * tileHeight + (p.y % tileHeight));
    }
    
    public static void updateTile(Tile tile, TileID newTileID) {
        tile.type = newTileID;
        tile.isCollidable = tile.type.isCollidable();
        tile.isSlope = tile.type.isSlope();
        tile.slopeHitBox = Triangle.getTriangle(tile.x, tile.y, newTileID.getSlope());
        updateTileImage(tile);
    }

    public static void updateTileImage(Tile tile) {
        if(tile.type.getImage() == null) {
            mapGraphics.setColor(Color.white);
            mapGraphics.fillRect(tile.x, tile.y, tileWidth, tileHeight);
        }
        mapGraphics.drawImage(tile.type.getImage(), tile.x, tile.y, null);
    }
    
    public static void render(Graphics g) {
        g.drawImage(tileMapImage, 0- Camera.cameraX, 0-Camera.cameraY, null);
    }
}
