/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sprites;

import java.awt.Image;
import javax.swing.ImageIcon;

/**
 * Abstract class encompassing each sprite
 * @author albert.wilcox
 */
public abstract class Sprite {
    int x;
    int y;
    int getOffsetX = 0;
    int getOffsetY = 0;
    
    //int realX;
    //int realY;
    Image image = null;
    
    static final int GRID_SIZE = 64;
    
    public Sprite(String url){
        if (url.contains(".png") && url.contains(".gif")){
            url = url.replace(".png", "");
        }
        ImageIcon ii = new ImageIcon(url);
        image = ii.getImage();
    }
    
    /**
     * Finds the real X position to be used when drawing
     * @return real X position
     */
    public int getRealX(){
        int realX = x * GRID_SIZE;
        return realX;
    }
    
    /**
     * Find the real Y position to be used when drawing
     * @return the real Y position
     */
    public int getRealY(){
        int realY = y * GRID_SIZE;
        realY += GRID_SIZE;
        realY -= image.getHeight(null);
        return realY;
    }
    
    public int getX(){
        return x;
    }
    
    public int getY(){
        return y;
    }
    
    public int getOffsetX(){
        return getOffsetX;
    }

    public int getOffsetY(){
        return getOffsetY;
    }
    
    public Image getImage(){
        //if (image == null) System.out.println("This is bad");
        return image;
    }
    
    public boolean getPassive(){
        return false;
    }
}
