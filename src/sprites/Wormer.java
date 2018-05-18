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
    
    MainFrame frame;
    Player player;
    
    private volatile boolean alive = true;
    
    WormerThread thread;
    
    public Wormer(MainFrame frame){
        super("WORMER_IMAGE_URL");
        this.frame = frame;
        
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
