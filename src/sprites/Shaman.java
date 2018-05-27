/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sprites;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import static sprites.Wormer.WORMER_IMAGE_URLS;
import world.World;

/**
 *
 * @author alber
 */
public class Shaman extends Wormer{
    static final String[] SHAMEN_IMAGE_URLS = {"src/sprites/lib/images/shamenfront.png", "src/sprites/lib/images/shamenfront.png", 
        "src/sprites/lib/images/shamenfront.png", "src/sprites/lib/images/shamenfront.png"};
    
    int shamenNum;
    
    public Shaman(World world, int health, int damage, int x, int y, int shamenNum) {
        super(world, health, damage, x, y);
        
        this.shamenNum = shamenNum;
        
        try{
           images[Movable.ORIENTATION_UP] = ImageIO.read(new File(SHAMEN_IMAGE_URLS[ORIENTATION_UP]));
           images[Movable.ORIENTATION_LEFT] = ImageIO.read(new File(SHAMEN_IMAGE_URLS[ORIENTATION_LEFT]));
           images[Movable.ORIENTATION_RIGHT] = ImageIO.read(new File(SHAMEN_IMAGE_URLS[ORIENTATION_RIGHT]));
           images[Movable.ORIENTATION_DOWN] = ImageIO.read(new File(SHAMEN_IMAGE_URLS[ORIENTATION_DOWN]));
        } catch (IOException e){}
    }
    
    @Override
    public void kill(){
        super.kill();
        world.getFrame().getSave().setShamanAlive(shamenNum, false);
    }
    
}
