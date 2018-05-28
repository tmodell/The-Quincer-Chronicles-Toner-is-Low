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
    static final String[] SHAMEN_IMAGE_URLS = {"src/sprites/lib/images/shamanfront.png", "src/sprites/lib/images/shamanback.png", 
        "src/sprites/lib/images/shamanleft.png", "src/sprites/lib/images/shamanright.png"};
    
    int shamanNum;
    
    public Shaman(World world, int x, int y, int shamanNum) {
        super(world, x, y, shamanNum);
        
        this.shamanNum = shamanNum;
        
        damage = shamanNum * 30;
        maxHealth = shamanNum * 500;
        health = maxHealth;
        reward = 100 * shamanNum;
        
        try{
           images[Movable.ORIENTATION_UP] = ImageIO.read(new File(SHAMEN_IMAGE_URLS[ORIENTATION_UP]));
           images[Movable.ORIENTATION_LEFT] = ImageIO.read(new File(SHAMEN_IMAGE_URLS[ORIENTATION_LEFT]));
           images[Movable.ORIENTATION_RIGHT] = ImageIO.read(new File(SHAMEN_IMAGE_URLS[ORIENTATION_RIGHT]));
           images[Movable.ORIENTATION_DOWN] = ImageIO.read(new File(SHAMEN_IMAGE_URLS[ORIENTATION_DOWN]));
        } catch (IOException e){e.printStackTrace();}
        
        image = images[0];
    }
    
    @Override
    public void kill(){
        super.kill();
        world.getFrame().getSave().setShamanAlive(shamanNum, false);
    }
    
}
