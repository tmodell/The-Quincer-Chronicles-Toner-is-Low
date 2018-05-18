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
public class Wormer extends Sprite{
    static final String WORMER_IMAGE_URL = "insert image url here";
    
    MainFrame frame;
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
        
        @Override
        public void run(){
            while(alive){
                
            }
        }
    }
}
