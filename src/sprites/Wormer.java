/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sprites;


import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import world.*;
/**
 *
 * @author albert.wilcox
 */
public class Wormer extends Movable{
    static final String[] WORMER_IMAGE_URLS = {"src/sprites/lib/images/wormerfront.png", "src/sprites/lib/images/wormerfront.png", 
        "src/sprites/lib/images/wormerfront.png", "src/sprites/lib/images/wormerfront.png", "src/sprites/lib/images/wormerfront90.png", "src/sprites/lib/images/wormerfront90.png", 
        "src/sprites/lib/images/wormerfront90.png", "src/sprites/lib/images/wormerfront90.png", "src/sprites/lib/images/wormerfront75.png", "src/sprites/lib/images/wormerfront75.png", 
        "src/sprites/lib/images/wormerfront75.png", "src/sprites/lib/images/wormerfront75.png", "src/sprites/lib/images/wormerfront50.png", "src/sprites/lib/images/wormerfront50.png", 
        "src/sprites/lib/images/wormerfront50.png", "src/sprites/lib/images/wormerfront50.png", "src/sprites/lib/images/wormerfront25.png", "src/sprites/lib/images/wormerfront25.png", 
        "src/sprites/lib/images/wormerfront25.png", "src/sprites/lib/images/wormerfront25.png", "src/sprites/lib/images/wormerfront10.png", "src/sprites/lib/images/wormerfront10.png", 
        "src/sprites/lib/images/wormerfront10.png", "src/sprites/lib/images/wormerfront10.png"};
    static final int DEFAULT_HEALTH = 100;
    static final int DEFAULT_DAMAGE = 10;
    static final int DEFAULT_COOLDOWN = 1000;
    
    BufferedImage[] images = new BufferedImage[4];
    
    World world;
    Player player;
    
    int health;
    int maxhealth;
    int damage;
    int cooldown;
    
    private volatile boolean alive = true;
    
    //WormerThread thread;
    
    /**
     * Creates a Wormer with default health
     * @param world The world
     */
    public Wormer(World world, int x, int y){
        super(WORMER_IMAGE_URLS[0]);
        this.world = world;
        
        health = DEFAULT_HEALTH;
        damage = DEFAULT_DAMAGE;
        cooldown = DEFAULT_COOLDOWN;
        
        this.player = world.getPlayer();
        
        try{
           images[Movable.ORIENTATION_UP] = ImageIO.read(new File(WORMER_IMAGE_URLS[ORIENTATION_UP]));
           images[Movable.ORIENTATION_LEFT] = ImageIO.read(new File(WORMER_IMAGE_URLS[ORIENTATION_LEFT]));
           images[Movable.ORIENTATION_RIGHT] = ImageIO.read(new File(WORMER_IMAGE_URLS[ORIENTATION_RIGHT]));
           images[Movable.ORIENTATION_DOWN] = ImageIO.read(new File(WORMER_IMAGE_URLS[ORIENTATION_DOWN]));
        } catch (IOException e){}
        
        
        //thread = new WormerThread(this);
        //thread.start();
    }
    
    /**
     * Creates a wormer with custom health
     * @param world Za warudo
     * @param health The wormer's heath
     * @param damage The wormer's damage
     */
    public Wormer(World world, int health, int damage, int x, int y){
        super(WORMER_IMAGE_URLS[0]);
        
        this.world = world;
        this.maxhealth = health;
        this.health = health;
        this.damage = damage;
        
        this.player = world.getPlayer();
        
        //thread = new WormerThread(this);
        //thread.start();
        
        try{
           images[Movable.ORIENTATION_UP] = ImageIO.read(new File(WORMER_IMAGE_URLS[ORIENTATION_UP]));
           images[Movable.ORIENTATION_LEFT] = ImageIO.read(new File(WORMER_IMAGE_URLS[ORIENTATION_LEFT]));
           images[Movable.ORIENTATION_RIGHT] = ImageIO.read(new File(WORMER_IMAGE_URLS[ORIENTATION_RIGHT]));
           images[Movable.ORIENTATION_DOWN] = ImageIO.read(new File(WORMER_IMAGE_URLS[ORIENTATION_DOWN]));
        } catch (IOException e){}
        
        this.x = x;
        this.y = y;
    }
    
    public void kill(){
        alive = false;
        world.killWormer(this, x, y);
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
        if (distance > 5) return false;
        if (distance == 1 && (dx == 0 || dy == 0)) return true;

        //TODO this code has no way of handling obstacles
        if (Math.abs(dx) > Math.abs(dy)){
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
            if (health <= 0) { kill(); }
            else if (health <= maxhealth / 10){ ImageIcon ii = new ImageIcon(WORMER_IMAGE_URLS[20+orientation]); image = ii.getImage(); }
            else if (health <= maxhealth / 4){ ImageIcon ii = new ImageIcon(WORMER_IMAGE_URLS[16+orientation]); image = ii.getImage(); }
            else if (health <= maxhealth / 2){ ImageIcon ii = new ImageIcon(WORMER_IMAGE_URLS[12+orientation]); image = ii.getImage(); }
            else if (health <= 3 * maxhealth / 4){ ImageIcon ii = new ImageIcon(WORMER_IMAGE_URLS[8+orientation]); image = ii.getImage(); }
            else{ ImageIcon ii = new ImageIcon(WORMER_IMAGE_URLS[4+orientation]); image = ii.getImage(); }
//            String s;
//            s = health <= 0 ? "dead" : Integer.toString(health);
//            System.out.println(s);
    }
    
    @Override
    public Image getImage(){
        return images[orientation];
    }
    
    @Override
    public void left(){
        super.left();
        ImageIcon ii = new ImageIcon(WORMER_IMAGE_URLS[orientation]);
        image = ii.getImage();
        int x = getX(), oldX = x + 1;
        int y = getY();
        world.moveWormer(this, oldX, y, x, y);
    }
    
    @Override
    public void right(){
        super.right();
        ImageIcon ii = new ImageIcon(WORMER_IMAGE_URLS[orientation]);
        image = ii.getImage();
        int x = getX(), oldX = x - 1;
        int y = getY();
        world.moveWormer(this, oldX, y, x, y);
    }
    
    @Override
    public void up(){
        super.up();
        ImageIcon ii = new ImageIcon(WORMER_IMAGE_URLS[orientation]);
        image = ii.getImage();
        int x = getX();
        int y = getY(), oldY = y + 1;
        world.moveWormer(this, x, oldY, x, y);
    }
    
    @Override
    public void down(){
        super.down();
        ImageIcon ii = new ImageIcon(WORMER_IMAGE_URLS[orientation]);
        image = ii.getImage();
        int x = getX();
        int y = getY(), oldY = y - 1;
        world.moveWormer(this, x, oldY, x, y);
    }
}
