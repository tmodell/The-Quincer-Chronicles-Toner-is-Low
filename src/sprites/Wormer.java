/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sprites;


import world.*;
/**
 *
 * @author albert.wilcox
 */
public class Wormer extends Movable{
    static final String WORMER_IMAGE_URL = "insert image url here";
    static final int MAX_HEALTH = 100;
    
    MainFrame frame;
    Player player;
    
    int health;
    
    private volatile boolean alive = true;
    
    WormerThread thread;
    
    /**
     * Creates a Wormer with default health
     * @param frame The main frame
     */
    public Wormer(MainFrame frame){
        super("WORMER_IMAGE_URL");
        this.frame = frame;
        
        health = MAX_HEALTH;
        
        thread = new WormerThread(this);
        thread.start();
    }
    
    /**
     * Creates a wormer with custom health
     * @param frame The main frame
     * @param health The wormer's heath
     */
    public Wormer(MainFrame frame, int health){
        super("WORMER_IMAGE_URL");
        this.frame = frame;
        
        this.health = health;
        
        thread = new WormerThread(this);
        thread.start();
    }
    
    public void kill(){
        alive = false;
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
            
            frame.updateWorld();
            return false;
        }
        
        /**
         * Call this method when the Wormer has been attacked
         * @param damage How much damage to do to the wormer (poor guy)
         */
        public void strike(int damage){
            health -= damage;
            if (health < 0) kill();
        }
        
        /**
         * This method will attack the user
         */
        public void attack(){
            // todo attack logic
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
                    Thread.sleep(1000);
                } catch (InterruptedException e) {/*lazy exception handling*/}
            }
        }
    }
}
