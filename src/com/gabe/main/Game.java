package com.gabe.main;

import com.gabe.main.itemhandling.ItemEntity;
import com.gabe.main.itemhandling.ItemStack;
import com.gabe.main.itemhandling.ItemType;
import com.gabe.main.listeners.ListenerRegisters;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;

/**
 * Created by Gabriel on 2020-04-23.
 */
public class Game extends Canvas implements Runnable {
    
    public static final int WIDTH = 900, HEIGHT = WIDTH / 12 * 9;
    private Thread thread;
    private boolean runnning = false;
    private Handler handler;
    private HUD hud;
    public static boolean stopFrame = false, inventoryIsOpen = false;
    public static boolean tickOnce = false, renderOnce = false;
    public MouseInput mouseInput = new MouseInput();
    public Player player;
    
    public Game() {
        handler = new Handler();
        this.addKeyListener(new KeyInput());
        this.addMouseListener(mouseInput);
        this.addMouseMotionListener(mouseInput);
        new Window(WIDTH, HEIGHT, "Gravitational", this);
        //ImageStorage.readImages();
        hud = new HUD();
        player = new Player(WIDTH/2, HEIGHT/2, ID.Player, handler);
        new BasicEnemy(WIDTH/2+100, HEIGHT/2 - 100, ID.BasicEnemy, handler);
        new ItemEntity(WIDTH/2+100, HEIGHT/2 - 50, ID.itemEntity, handler, new ItemStack(ItemType.FRUIT, 100));
        new ItemEntity(WIDTH/2+500, HEIGHT/2 - 50, ID.itemEntity, handler, new ItemStack(ItemType.FRUIT, 7));
        new ItemEntity(WIDTH/2+200, HEIGHT/2 - 50, ID.itemEntity, handler, new ItemStack(ItemType.STAR_FURY, 1));
        new ItemEntity(1000, 100, ID.itemEntity, handler, new ItemStack(ItemType.POLAR_STAR, 1));
        ListenerRegisters.registerAllListeners();
    }
    
    public synchronized void start() {
        thread = new Thread(this);
        thread.start();
        runnning = true;
    }

    public synchronized void stop() {
        try {
            thread.join();
            runnning = false;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void run() {
        this.requestFocus();
        long lastTime = System.nanoTime();
        double amountOfTicks = 60.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        long timer = System.currentTimeMillis();
        int frames = 0;
        while(runnning) {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            if(KeyInput.isKeyDown(KeyEvent.VK_P)) {
                stopFrame = true;
            }
            while(stopFrame) { //For incrementing the game one tick and render at a time
                now = System.nanoTime();
                lastTime = now;
                if(KeyInput.isKeyDown(KeyEvent.VK_U)) {
                    stopFrame = false;
                }
                if(KeyInput.isKeyDown(KeyEvent.VK_T) && !tickOnce) {
                    tick();
                    render();
                    tickOnce = true;
                } else if(!KeyInput.isKeyDown(KeyEvent.VK_T) && tickOnce) {
                    tickOnce = false;
                }
                if(KeyInput.isKeyDown(KeyEvent.VK_R) && !renderOnce) {
                    render();
                    renderOnce = true;
                } else if(!KeyInput.isKeyDown(KeyEvent.VK_R) && renderOnce) {
                    renderOnce = false;
                }
            }
            if(KeyInput.isKeyDown(KeyEvent.VK_E)) { //Inventory keyevent handling
                inventoryIsOpen = true;
            }
            if(KeyInput.isKeyDown(KeyEvent.VK_ESCAPE) && inventoryIsOpen) {
                inventoryIsOpen = false;
            }
            while(delta >= 1) {
                tick();
                delta--;
            }
            if(runnning) {
                render();
                frames++;
            }
            if(System.currentTimeMillis() - timer > 1000) {
                timer += 1000;
                //System.out.println("Fps: " + frames);
                frames = 0;
            }
        }
        stop();
    }
    
    private void tick() {
        handler.tick();
        hud.tick();
        GameRunnable.tick();
        if(inventoryIsOpen) {
            player.inventory.tick();
        } else {
            player.inventory.activeBar.tick();
        }
    }
    
    private void render() {
        BufferStrategy bs = this.getBufferStrategy();
        if(bs == null) {
            this.createBufferStrategy(3);
            return;
        }
        Graphics g = bs.getDrawGraphics();
        g.setColor(Color.white);
        g.fillRect(0, 0, WIDTH, HEIGHT);
        Tile.render(g);
        handler.render(g);
        hud.render(g);
        if(inventoryIsOpen) {
            player.inventory.render(g);
        } else {
            player.inventory.activeBar.render(g);
        }
        g.dispose();
        bs.show();
    }
    
    
    
    public static int clamp(int val, int min, int max) {
        if(val <= min) {
            return min;
        } else if(val >= max) {
            return max;
        }
        return val;
    }
    
    public static double clamp(double val, double min, double max) {
        if(val <= min) {
            return min;
        } else if(val >= max) {
            return max;
        }
        return val;
    }
    
    public static void main(String args[]) throws Exception {
        new Game();
    }
    
}
