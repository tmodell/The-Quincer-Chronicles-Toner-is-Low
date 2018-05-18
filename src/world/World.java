/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package world;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import sprites.*;

/**
 *
 * @author alber
 */
public class World extends JPanel{
    
    //todo add support for this
    ArrayList<Sprite> sprites;
    Player player;
    MainFrame frame;
    
    public World(MainFrame frame){
        setPreferredSize(new Dimension(500, 500));
        
        sprites = new ArrayList<Sprite>();
        this.frame = frame;
        
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
    
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        // Sample loop for drawing map; leaving it commented out for now
//        for (Sprite x: sprites){
//            g.drawImage(x.getImage(), x.getRealX(), x.getRealY(), null);
//        }
    }
    
    /**
     * This method will be called when the player presses an arrow key.
     * Its job is to check whether the player can move where he/she wants
     * to and if they can the sprite is notified and display updated.
     * @param key 
     */
    public void handleMovementKey(int key) {
        int hypotheticalX, hypotheticalY, x = player.getX(), y = player.getY();
        switch (key){
            case KeyEvent.VK_LEFT:
                hypotheticalX = x - 1;
                // check whether the square is occupiable
                if (isOccupiable(hypotheticalX, y)){
                    player.left();
                    update();
                } else{
                    // maybe add code for what to do if a square is unoccupiable
                }
                break;
            case KeyEvent.VK_RIGHT:
                hypotheticalX = x + 1;
                // check whether the square is occupiable
                if (isOccupiable(hypotheticalX, y)){
                    player.right();
                    update();
                } else{
                    // maybe add code for what to do if a square is unoccupiable
                }
                break;
            case KeyEvent.VK_UP:
                hypotheticalY = y - 1;
                // check whether the square is occupiable
                if (isOccupiable(x, hypotheticalY)){
                    player.up();
                    update();
                } else{
                    // maybe add code for what to do if a square is unoccupiable
                }
                break;
            case KeyEvent.VK_DOWN:
                hypotheticalY = y + 1;
                // check whether the square is occupiable
                if (isOccupiable(x, hypotheticalY)){
                    player.down();
                    update();
                } else{
                    // maybe add code for what to do if a square is unoccupiable
                }
                break;
        }
    }
    
    public Player getPlayer(){
        return player;
    }
}
