/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package world;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

import sprites.*;

/**
 *
 * @author alber
 */
public class World extends JPanel{
    
    //todo add support for this
    ArrayList<Sprite> sprites;
    
    public World(){
        setPreferredSize(new Dimension(500, 500));
        
        sprites = new ArrayList<Sprite>();
        
        // the following loop is for testing purposes only
        for(int i = 0; i < 500; i++){
            add(new JLabel("Press any key to close "));
        }
    }
    
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
    
    public void dispose(){
        dispose();
    }
    
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        // Sample loop for drawing map; leaving it commented out for now
//        for (Sprite x: sprites){
//            g.drawImage(x.getImage(), x.getRealX(), x.getRealY(), null);
//        }
    }
}
