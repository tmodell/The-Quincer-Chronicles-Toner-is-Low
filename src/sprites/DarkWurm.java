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
import java.util.Random;

/**
 *
 * @author alber
 */
public class DarkWurm extends Wormer{
    
    static final int DAMAGE = 80;
    static final int HEALTH = 2500;
    static final int REWARD = 10000;
    static final int RANGE = 15;
    
    static final String[] WURM_IMAGE_URLS = {"lib/sprites/images/wurmfront.png", "lib/sprites/images/wurmback.png", 
        "lib/sprites/images/wurmleft.png", "lib/sprites/images/wurmright.png"};
        
    int reward;
    Random r;
    Random rx;
    Random ry;
    
    public DarkWurm(World world, int x, int y) {
        super(world, x, y, 0);
                
        damage = DAMAGE;
        maxHealth = HEALTH;
        health = maxHealth;
        reward = REWARD;
        range = RANGE;
        r = new Random();
        rx = new Random();
        ry = new Random();
        
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
        world.killWormer(this, x, y);
        player.giveMoney(reward);
        world.getFrame().getSave().setShamanAlive(4, false);
    }
    
    @Override
    public int getRealX(){
        if (orientation == ORIENTATION_UP || orientation == ORIENTATION_DOWN) return super.getRealX() - 8;
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
    
    @Override
    public void receiveStrike(int damage){
        health -= damage;
        orientation = getOrientation();
        int tp = r.nextInt(10);

        if (health <= 0){ 
            kill(); 
        } else if ((health <= (maxHealth/3 + 20) && health >= (maxHealth/3 - 20)) || (health <= (2*maxHealth/3 + 20) && health >= (2*maxHealth/3 - 20)) || tp == 5){ 
            int randX = rx.nextInt(24)+1, randY = ry.nextInt(11)+2, playerX = player.getX(), playerY = player.getY(); 
            while (!world.isOccupiable(randX, randY)){
                randX = rx.nextInt(24)+1;
                randY = ry.nextInt(11)+2;
            } 
            teleport(randX, randY);
            if (world.isOccupiable(playerX-1, playerY-1)) world.summonWormer(playerX-1, playerY-1);
            if (world.isOccupiable(playerX-1, playerY+1)) world.summonWormer(playerX-1, playerY+1);
            if (world.isOccupiable(playerX+1, playerY-1)) world.summonWormer(playerX+1, playerY-1);
            if (world.isOccupiable(playerX+1, playerY+1)) world.summonWormer(playerX+1, playerY+1);
            if (world.isOccupiable(playerX-2, playerY)) world.summonWormer(playerX-2, playerY);
            if (world.isOccupiable(playerX+2, playerY)) world.summonWormer(playerX+2, playerY);
            if (world.isOccupiable(playerX, playerY-2)) world.summonWormer(playerX, playerY-2);
            if (world.isOccupiable(playerX, playerY+2)) world.summonWormer(playerX, playerY+2);
        } 
    }
    
    public void teleport(int destX, int destY){
        int oldX = this.x;
        int oldY = this.y;
        this.x = destX;
        this.y = destY;
        int newX = getX();
        int newY = getY();
        world.moveWormer(this, oldX, oldY, newX, newY);
    }    
}
