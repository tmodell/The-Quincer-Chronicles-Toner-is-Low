/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sprites;


import javax.swing.ImageIcon;
import world.*;
/**
 *
 * @author albert.wilcox
 */
public class Wormer extends Movable{
    static final String[] WORMER_IMAGE_URLS = {"src/sprites/lib/images/wormerfront.png", "src/sprites/lib/images/wormerfront.png", 
        "src/sprites/lib/images/wormerfront.png", "src/sprites/lib/images/wormerfront.png"};
    static final int DEFAULT_HEALTH = 100;
    static final int DEFAULT_DAMAGE = 10;
    static final int DEFAULT_COOLDOWN = 1000;
    
    World world;
    Player player;
    
    int health;
    int damage;
    int cooldown;
    
    private volatile boolean alive = true;
    
    WormerThread thread;
    
    /**
     * Creates a Wormer with default health
     * @param world The world
     */
    public Wormer(World world){
        super(WORMER_IMAGE_URLS[0]);
        this.world = world;
        
        health = DEFAULT_HEALTH;
        damage = DEFAULT_DAMAGE;
        cooldown = DEFAULT_COOLDOWN;
        
        thread = new WormerThread(this);
        thread.start();
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
        this.health = health;
        this.damage = damage;
        
        thread = new WormerThread(this);
        thread.start();
        
        this.x = x;
        this.y = y;
    }
    
    public void kill(){
        alive = false;
        world.killWormer(this, x, y);
    }

    public void receiveStrike(int damage){
            health -= damage;
//            String s;
            if (health < 0) kill();
//            s = health < 0 ? "dead" : Integer.toString(health);
//            System.out.println(s);
    }
    
    private class WormerThread extends Thread{
        Wormer wormer;
        
        WormerThread(Wormer wormer){
            this.wormer = wormer;
        }
        
        /**
         * Move towards the player and return whether the wormer should attack.
         * @return Whether or not the wormer needs to attack
         */
        public boolean moveTowardsPlayer(){
            // Find distance between player and wormer
            int dx = x - player.getX();
            int dy = y - player.getY();
            int distance = (int)(Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2)));
            
            if (distance > 5) return false;
            if (distance == 1) return true;
            
            //TODO this code has no way of handling obstacles
            if (dx > dy){
                if (dx > 0) left();
                else right();
            } else{
                if (dy > 0) up();
                else down();
            }
            
            return false;
        }
        
        /**
         * Call this method when the Wormer has been attacked
         * @param damage How much damage to do to the wormer (poor guy)
         */
        
        
        /**
         * This method will attack the user
         */
        public void attack(){
            int destX = x, destY = y;
            switch (orientation){
                case 0:
                    destY++;
                    break;
                case 1:
                    destY--;
                    break;
                case 2:
                    destX--;
                    break;
                case 3:
                    destX++;
                    break;
            }
            world.WormerAttack(x, y, damage);
        }
        
        // TODO this wormer ai needs alot of work
        @Override
        public void run(){
            while(alive){
                boolean attack = moveTowardsPlayer();
                if (attack) {
                    attack();
                }
                try{
                    Thread.sleep(cooldown);
                } catch (InterruptedException e) {/*lazy exception handling*/}
            }
        }
    }
    
    @Override
    public void left(){
        super.left();
        ImageIcon ii = new ImageIcon(WORMER_IMAGE_URLS[orientation]);
        image = ii.getImage();
    }
    
    @Override
    public void right(){
        super.right();
        ImageIcon ii = new ImageIcon(WORMER_IMAGE_URLS[orientation]);
        image = ii.getImage();
    }
    
    @Override
    public void up(){
        super.up();
        ImageIcon ii = new ImageIcon(WORMER_IMAGE_URLS[orientation]);
        image = ii.getImage();
    }
    
    @Override
    public void down(){
        super.down();
        ImageIcon ii = new ImageIcon(WORMER_IMAGE_URLS[orientation]);
        image = ii.getImage();
    }
}
