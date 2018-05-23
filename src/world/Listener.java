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

    MainFrame frame;
    
    public Listener (MainFrame frame){
        this.frame = frame;
    }
    
    // ignore these methods
    @Override
    public void keyTyped(KeyEvent ke) {}
    @Override
    public void keyReleased(KeyEvent ke) {}

    /**
     * This method handles user input.
     * 
     * NOTE: this should be where input for other actions is handled as well IMO
     * @param ke Don't worry about this parameter
     */
    @Override
    public void keyPressed(KeyEvent ke) {
        // frame.dispose is for testing. once we have something more than press any key to quit we'll replace it
        frame.dispose();
    }
}
