/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package world;

import javax.swing.JPanel;

/**
 *
 * @author alber
 */
public class World extends JPanel{
    
    /**
     * Determines whether a square is occupiable by the player
     * @param x the square's x position
     * @param y the square's y position
     * @return whether the square is occupiable
     */
    public boolean isOccupiable(int x, int y){
        // TODO add logic here
        return true;
    }
    
    /**
     * This method will one day be used to repaint the panel
     * when something is changed
     */
    public void update(){
        // TODO add code
    }
}
