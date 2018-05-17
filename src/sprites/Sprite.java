/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sprites;

import java.awt.Image;
import javax.swing.ImageIcon;

/**
 *
 * @author albert.wilcox
 */
public abstract class Sprite {
    int x;
    int y;
    //int realX;
    //int realY;
    Image image;
    
    static final int GRID_SIZE = 100; // TODO add grid size info
    
    public Sprite(String url){
        ImageIcon ii = new ImageIcon(url);
        image = ii.getImage();
    }
    
    public int getRealX(){
        int realX = x * GRID_SIZE;
        return realX;
    }
    
    public int getRealY(){
        int realY = y * GRID_SIZE;
        realY -= image.getHeight(null);
        return realY;
    }
    
    public int getX(){
        return x;
    }
    
    public int getY(){
        return y;
    }
    
    public Image getImage(){
        return image;
    }
}
