/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package world;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 *
 * @author alber
 */
public class Listener implements KeyListener{

    World world;
    
    public Listener (World world){
        this.world = world;
    }
    
    // ignore these methods
    @Override
    public void keyTyped(KeyEvent ke) {}
    @Override
    public void keyReleased(KeyEvent ke) {}

    /**
     * This method handles user input for motion.
     * 
     * NOTE: this should be where input for other actions is handled as well IMO
     * @param ke Don't worry about this parameter
     */
    @Override
    public void keyPressed(KeyEvent ke) {
        world.frame.dispose();
        
        int key = ke.getKeyCode();
        if (key == KeyEvent.VK_LEFT || key == KeyEvent.VK_RIGHT 
                || key == KeyEvent.VK_UP || key == KeyEvent.VK_DOWN){
            world.handleMovementKey(key);
        }
    }
}
