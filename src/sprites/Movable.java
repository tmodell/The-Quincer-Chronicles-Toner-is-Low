/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sprites;

import javax.swing.ImageIcon;
import static sprites.Sprite.GRID_SIZE;

/**
 *
 * @author alber
 */
public abstract class Movable extends Sprite{
        
    public static final int ORIENTATION_DOWN = 0;
    public static final int ORIENTATION_UP = 1;
    public static final int ORIENTATION_LEFT = 2;
    public static final int ORIENTATION_RIGHT = 3;
    
    private boolean alreadyMoving= false;
    
    int orientation = 0;
    
    public Movable(String s){
        super(s);
    }
    
    public int getOrientation(){
        return orientation;
    }
    
    public void setOrientation(int orientation){
        this.orientation = orientation;
    }
    
    public int getXInFront(){
        switch(orientation){
            case ORIENTATION_LEFT:
                return x - 1;
            case ORIENTATION_RIGHT:
                return x + 1;
            default:
                return x;
        }
    }
    
    public int getYInFront(){
        switch(orientation){
            case ORIENTATION_UP:
                return y - 1;
            case ORIENTATION_DOWN:
                return y + 1;
            default:
                return y;
        }
    }
    
    // TODO add code to notify the world about movements
    public void left(){
        if (!alreadyMoving){
            x -= 1;
            alreadyMoving = true;
            orientation = ORIENTATION_LEFT;
            new Thread(){
                @Override
                public void run(){
                    getOffsetX = 64;
                    while(getOffsetX > 0){
                        getOffsetX -= GRID_SIZE/10;
                        try{
                            Thread.sleep(10);
                        } catch(Exception e){}
                    }
                    getOffsetX = 0;
                    alreadyMoving = false;
                }
            }.start();
        }
    }
    
    public void right(){
        if (!alreadyMoving){
            x += 1;
            alreadyMoving = true;
            orientation = ORIENTATION_RIGHT;
            new Thread(){
                @Override
                public void run(){
                    getOffsetX = -64;
                    while(getOffsetX < 0){
                        getOffsetX += GRID_SIZE/10;
                        try{
                            Thread.sleep(10);
                        } catch(Exception e){}
                    }
                    getOffsetX = 0;
                    alreadyMoving = false;
                }
            }.start();
        }
    }
    
    public void down(){
        if (!alreadyMoving){
            y += 1;
            alreadyMoving = true;
            orientation = ORIENTATION_DOWN;
            new Thread(){
                @Override
                public void run(){
                    getOffsetY = -64;
                    while(getOffsetY < 0){
                        getOffsetY += GRID_SIZE/10;
                        try{
                            Thread.sleep(10);
                        } catch(Exception e){}
                    }
                    getOffsetY = 0;
                    alreadyMoving = false;
                }
            }.start();
        }
    }
    
    public void up(){
        if (!alreadyMoving){
            y -= 1;
            alreadyMoving = true;
            orientation = ORIENTATION_UP;
            new Thread(){
                @Override
                public void run(){
                    getOffsetY = 64;
                    while(getOffsetY > 0){
                        getOffsetY -= GRID_SIZE/10;
                        try{
                            Thread.sleep(10);
                        } catch(Exception e){}
                    }
                    getOffsetY = 0;
                    alreadyMoving = false;
                }
            }.start();
        }
    }
}
