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
    
    private static boolean alreadyMoving= false;
    
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
            alreadyMoving = true;
            orientation = ORIENTATION_LEFT;
            new Thread(){
                @Override
                public void run(){
                    for (int i=0; i<10; i++){
                        if (getOffsetX < -(GRID_SIZE-(GRID_SIZE/4))){
                            x -= 1;
                            getOffsetX = 0;
                        } else {
                            getOffsetX -= (GRID_SIZE/10);
                        }
                        try {
                            Thread.sleep(10);
                        } catch (InterruptedException e){  
                            e.printStackTrace();
                        }
                    }
                    if (((GRID_SIZE/4)-getOffsetX) < 0){
                    getOffsetX = 0; 
                    }
                    alreadyMoving = false;
                }
            }.start();
        }
    }
    
    public void right(){
        if (!alreadyMoving){
            alreadyMoving = true;
            orientation = ORIENTATION_RIGHT;
            new Thread(){
                @Override
                public void run(){
                    for (int i=0; i<10; i++){
                        if (getOffsetX > (GRID_SIZE-(GRID_SIZE/4))){
                            x += 1;
                            getOffsetX = 0;
                        } else {
                            getOffsetX += (GRID_SIZE/10);
                        }
                        try {
                            Thread.sleep(10);
                        } catch (InterruptedException e){  
                            e.printStackTrace();
                        }
                    }
                    if (((GRID_SIZE/4)-getOffsetX) < 0){
                    getOffsetX = 0; 
                    }
                    alreadyMoving = false;
                }
            }.start();
        }
    }
    
    public void down(){
        if (!alreadyMoving){
            alreadyMoving = true;
            orientation = ORIENTATION_DOWN;
            new Thread(){
                @Override
                public void run(){
                    for (int i=0; i<10; i++){
                        if (getOffsetY > (GRID_SIZE-(GRID_SIZE/4))){
                            y += 1;
                            getOffsetY = 0;
                        } else {
                            getOffsetY += (GRID_SIZE/10);
                        }
                        try {
                            Thread.sleep(10);
                        } catch (InterruptedException e){  
                            e.printStackTrace();
                        }
                    }
                    if (((GRID_SIZE/4)-getOffsetY) < 0){
                    getOffsetY = 0; 
                    }
                    alreadyMoving = false;
                }
            }.start();
        }
    }
    
    public void up(){
        if (!alreadyMoving){
            alreadyMoving = true;
            orientation = ORIENTATION_UP;
            new Thread(){
                @Override
                public void run(){
                    for (int i=0; i<10; i++){
                        if (getOffsetY < -(GRID_SIZE-(GRID_SIZE/4))){
                            y -= 1;
                            getOffsetY = 0;
                        } else {
                            getOffsetY -= (GRID_SIZE/10);
                        }
                        try {
                            Thread.sleep(10);
                        } catch (InterruptedException e){  
                            e.printStackTrace();
                        }
                    }
                    if (((GRID_SIZE/4)-getOffsetY) < 0){
                        getOffsetY = 0; 
                    }
                    alreadyMoving = false;
                }
            }.start();
        }
    }
}
