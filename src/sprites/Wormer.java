/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sprites;


import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import world.*;
/**
 *
 * @author albert.wilcox
 */
public class Wormer extends Movable{
    static final String[] WORMER_IMAGE_URLS = {"lib/sprites/images/wormerfront.png", "lib/sprites/images/wormerback.png", 
        "lib/sprites/images/wormerleft.png", "lib/sprites/images/wormerright.png"};
    static final int DEFAULT_HEALTH = 100;
    static final int DEFAULT_DAMAGE = 10;
    static final int DEFAULT_COOLDOWN = 1000;
    static final int RANGE = 5;
    
    BufferedImage[] images = new BufferedImage[4];
    
    World world;
    Player player;
    
    int health;
    int maxHealth;
    int damage;
    int cooldown;
    int rewardMin;
    int rewardMax;
    int range;
    
    private volatile boolean alive = true;
        
    /**
     * Creates a Wormer with default health
     * @param world The world
     */
    public Wormer(World world, int x, int y, int shaman){
        super(WORMER_IMAGE_URLS[0]);
        this.world = world;
        
        if (shaman == 1){maxHealth = DEFAULT_HEALTH;} else {maxHealth = 100 * shaman - 50;}
        health = maxHealth;
        damage = DEFAULT_DAMAGE * shaman;
        cooldown = DEFAULT_COOLDOWN;
        rewardMin = shaman==1 ? 3 : shaman==2 ? 15 : shaman==3 ? 20 : 40;
        rewardMax = shaman==1 ? 7 : shaman==2 ? 20 : shaman==3 ? 30 : 50;
        range = RANGE;
        
        this.player = world.getPlayer();
        
        try{
           images[Movable.ORIENTATION_UP] = ImageIO.read(new File(WORMER_IMAGE_URLS[ORIENTATION_UP]));
           images[Movable.ORIENTATION_LEFT] = ImageIO.read(new File(WORMER_IMAGE_URLS[ORIENTATION_LEFT]));
           images[Movable.ORIENTATION_RIGHT] = ImageIO.read(new File(WORMER_IMAGE_URLS[ORIENTATION_RIGHT]));
           images[Movable.ORIENTATION_DOWN] = ImageIO.read(new File(WORMER_IMAGE_URLS[ORIENTATION_DOWN]));
        } catch (IOException e){}
        
        this.x = x;
        this.y = y;
        //thread = new WormerThread(this);
        //thread.start();
    }
    
    /**
     * Creates a wormer with custom health
     * @param world Za warudo
     * @param health The wormer's heath
     * @param damage The wormer's damage
     */
//    public Wormer(World world, int health, int damage, int x, int y){
//        super(WORMER_IMAGE_URLS[0]);
//        
//        this.world = world;
//        this.maxHealth = health;
//        this.health = health;
//        this.damage = damage;
//        
//        this.player = world.getPlayer();
//        
//        //thread = new WormerThread(this);
//        //thread.start();
//        
//        try{
//           images[Movable.ORIENTATION_UP] = ImageIO.read(new File(WORMER_IMAGE_URLS[ORIENTATION_UP]));
//           images[Movable.ORIENTATION_LEFT] = ImageIO.read(new File(WORMER_IMAGE_URLS[ORIENTATION_LEFT]));
//           images[Movable.ORIENTATION_RIGHT] = ImageIO.read(new File(WORMER_IMAGE_URLS[ORIENTATION_RIGHT]));
//           images[Movable.ORIENTATION_DOWN] = ImageIO.read(new File(WORMER_IMAGE_URLS[ORIENTATION_DOWN]));
//        } catch (IOException e){}
//        
//        this.x = x;
//        this.y = y;
//    }
    
    public void kill(){
        alive = false;
        world.killWormer(this, x, y);
        
        Random r = new Random();
        int reward = r.nextInt(rewardMax-rewardMin) + rewardMin;
        player.giveMoney(reward);
    }

    /**
     * Move towards the player and return whether the wormer should attack.
     * @return Whether or not the wormer needs to attack
     */
    public boolean moveTowardsPlayer(){
        // Find distance between player and wormer
        int dx = x - player.getX();
        //System.out.println(dx);
        int dy = y - player.getY();
        //System.out.println(dy);
        int distance = (int)(Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2)));
        //System.out.println();
        //System.out.println();
        if (distance > range) return false;
        if (distance == 1 && (dx == 0 || dy == 0)) return true;

        //TODO this code has no way of handling obstacles
        if (Math.abs(dx) == Math.abs(dy)){
            if (dx > 0 && world.isOccupiable(x - 1, y)) left();
            else if (dx < 0 && world.isOccupiable(x + 1, y)) right();
            else if (dy > 0 && world.isOccupiable(x, y - 1)) up();
            else if (dy < 0 && world.isOccupiable(x, y + 1)) down();
        } else if (Math.abs(dx) > Math.abs(dy)){
            if (dx > 0) {
                if(world.isOccupiable(x - 1, y)) left();
                else if (world.isOccupiable(x, y + 1)) down();
                else if (world.isOccupiable(x, y - 1)) up();
            }
            else {
                if (world.isOccupiable(x + 1, y)) right();
                else if (world.isOccupiable(x, y + 1)) down();
                else if (world.isOccupiable(x, y - 1)) up();
            }
        } else{
            if (dy > 0) {
                if (world.isOccupiable(x, y - 1)) up();
                else if (world.isOccupiable(x - 1, y)) left();
                else if (world.isOccupiable(x + 1, y)) right();
            }
            else {
                if (world.isOccupiable(x, y + 1)) down();
                else if (world.isOccupiable(x - 1, y)) left();
                else if (world.isOccupiable(x + 1, y)) right();
            }
        }

        return false;
    }

    public void setHealth(int health){
        this.health = health;
    }
    
    /**
     * This method will attack the user
     */
    public void attack(){
        int destX = getXInFront(), destY = getYInFront();
        world.WormerAttack(destX, destY, damage);
    }
    
    public void refresh(){
        //System.out.println("Refreshing");
        boolean attack = moveTowardsPlayer();
        if (attack) attack();
        
    }
    
    public void receiveStrike(int damage){
            health -= damage;
            
            orientation = getOrientation();
            if (health <= 0) kill();
    }
    
    @Override
    public Image getImage(){
        BufferedImage image = images[orientation];
        Graphics2D  g = (Graphics2D) image.getGraphics();
        
        int aliveLength = (int)(((float)health/(float)maxHealth) * 60);
        
        g.setColor(Color.GREEN);
        g.fillRect(2, 1, aliveLength, 6);
        g.setColor(Color.RED);
        g.fillRect(2 + aliveLength, 1, 60 - aliveLength, 6);
        
        return images[orientation];
    }
    
    @Override
    public void left(){
        super.left();
        int x = getX(), oldX = x + 1;
        int y = getY();
        world.moveWormer(this, oldX, y, x, y);
        //ImageIcon ii = new ImageIcon(WORMER_IMAGE_URLS[orientation]);
        //image = ii.getImage();
    }
    
    @Override
    public void right(){
        super.right();
        int x = getX(), oldX = x - 1;
        int y = getY();
        world.moveWormer(this, oldX, y, x, y);
        //ImageIcon ii = new ImageIcon(WORMER_IMAGE_URLS[orientation]);
        //image = ii.getImage();
    }
    
    @Override
    public void up(){
        super.up();
        int x = getX();
        int y = getY(), oldY = y + 1;
        world.moveWormer(this, x, oldY, x, y);
        //ImageIcon ii = new ImageIcon(WORMER_IMAGE_URLS[orientation]);
        //image = ii.getImage();
    }
    
    @Override
    public void down(){
        super.down();
        int x = getX();
        int y = getY(), oldY = y - 1;
        world.moveWormer(this, x, oldY, x, y);
        //ImageIcon ii = new ImageIcon(WORMER_IMAGE_URLS[orientation]);
        //image = ii.getImage();
    }
}
