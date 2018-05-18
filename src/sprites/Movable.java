/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sprites;

import javax.swing.ImageIcon;

/**
 *
 * @author alber
 */
public abstract class Movable extends Sprite{
        
    public static final int ORIENTATION_DOWN = 0;
    public static final int ORIENTATION_UP = 1;
    public static final int ORIENTATION_LEFT = 2;
    public static final int ORIENTATION_RIGHT = 3;
    
    int orientation = 0;
    
    public Movable(String s){
        super(s);
    }
    
    public void left(){
        x -= 1;
        orientation = ORIENTATION_LEFT;
    }
    
    public void right(){
        x += 1;
        orientation = ORIENTATION_RIGHT;
    }
    
    public void down(){
        y += 1;
        orientation = ORIENTATION_DOWN;
    }
    
    public void up(){
        y -= 1;
        orientation = ORIENTATION_UP;
    }
}
