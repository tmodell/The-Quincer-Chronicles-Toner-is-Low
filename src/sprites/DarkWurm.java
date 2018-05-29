package sprites;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import static sprites.Movable.ORIENTATION_DOWN;
import static sprites.Movable.ORIENTATION_LEFT;
import static sprites.Movable.ORIENTATION_RIGHT;
import static sprites.Movable.ORIENTATION_UP;
import world.World;

/**
 *
 * @author alber
 */
public class DarkWurm extends Wormer{
    
    static final int DAMAGE = 100;
    static final int HEALTH = 3000;
    static final int REWARD = 10000;
    
    static final String[] WURM_IMAGE_URLS = {"src/sprites/lib/images/wurmfront.png", "src/sprites/lib/images/wurmback.png", 
        "src/sprites/lib/images/wurmleft.png", "src/sprites/lib/images/wurmright.png"};
        
    public DarkWurm(World world, int x, int y) {
        super(world, x, y, 0);
                
        damage = DAMAGE;
        maxHealth = HEALTH;
        health = maxHealth;
        reward = REWARD;
        
        try{
           images[Movable.ORIENTATION_UP] = ImageIO.read(new File(WURM_IMAGE_URLS[ORIENTATION_UP]));
           images[Movable.ORIENTATION_LEFT] = ImageIO.read(new File(WURM_IMAGE_URLS[ORIENTATION_LEFT]));
           images[Movable.ORIENTATION_RIGHT] = ImageIO.read(new File(WURM_IMAGE_URLS[ORIENTATION_RIGHT]));
           images[Movable.ORIENTATION_DOWN] = ImageIO.read(new File(WURM_IMAGE_URLS[ORIENTATION_DOWN]));
        } catch (IOException e){e.printStackTrace();}
        
        image = images[0];
    }
    
    @Override
    public void kill(){
        super.kill();
        world.getFrame().getSave().setShamanAlive(4, false);
    }
    
    @Override
    public int getRealX(){
        if (orientation == ORIENTATION_UP || orientation == ORIENTATION_DOWN)return super.getRealX() - 8;
        else return super.getRealX();
    }
    
    @Override
    public Image getImage(){
        BufferedImage image = images[orientation];
        Graphics2D  g = (Graphics2D) image.getGraphics();
        
        int aliveLength = (int)(((float)health/(float)maxHealth) * 80);
        
        g.setColor(Color.GREEN);
        g.fillRect(2, 1, aliveLength, 6);
        g.setColor(Color.RED);
        g.fillRect(2 + aliveLength, 1, 80 - aliveLength, 6);
        
        return images[orientation];
    }
    
}
