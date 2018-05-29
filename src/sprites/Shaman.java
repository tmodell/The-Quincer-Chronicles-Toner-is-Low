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
    int teleported;
    
    public Shaman(World world, int x, int y, int shamanNum) {
        super(world, x, y, shamanNum);
        
        this.shamanNum = shamanNum;
        
        damage = shamanNum * 20;
        maxHealth = 100 + shamanNum * 200;
        health = maxHealth;
        reward = 100 * shamanNum;
        teleported = 0;
        
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
    
    public void teleport(int destX, int destY){
        int oldX = this.x;
        int oldY = this.y;
        this.x = destX;
        this.y = destY;
        int newX = getX();
        int newY = getY();
        world.moveWormer(this, oldX, oldY, newX, newY);
    }
    
    @Override
    public void refresh(){
        boolean attack = moveTowardsPlayer();
        if (attack) attack();
    }
    
    @Override
    public void receiveStrike(int damage){
            health -= damage;
            orientation = getOrientation();
            
            if (health <= 0){ 
                kill(); 
            } else if (health <= maxHealth/3 && teleported == 1){ 
                int x = 5, y = 3, playerX = player.getX(), playerY = player.getY(); 
                if (player.getX() <= 9) x = 20;
                teleport(x,y);
                teleported = 2;
                if (world.isOccupiable(playerX-1, playerY-1)) world.summonWormer(playerX-1, playerY-1);
                if (world.isOccupiable(playerX-1, playerY+1)) world.summonWormer(playerX-1, playerY+1);
                if (world.isOccupiable(playerX+1, playerY-1)) world.summonWormer(playerX+1, playerY-1);
                if (world.isOccupiable(playerX+1, playerY+1)) world.summonWormer(playerX+1, playerY+1);
            } else if (health <= 2*maxHealth/3 && teleported == 0){
                int x = 20, y = 8, playerX = player.getX(), playerY = player.getY();
                if (player.getX() >= 16) x = 5;
                teleport(x,y);
                teleported = 1;
                if (world.isOccupiable(playerX-1, playerY-1)) world.summonWormer(playerX-1, playerY-1);
                if (world.isOccupiable(playerX-1, playerY+1)) world.summonWormer(playerX-1, playerY+1);
                if (world.isOccupiable(playerX+1, playerY-1)) world.summonWormer(playerX+1, playerY-1);
                if (world.isOccupiable(playerX+1, playerY+1)) world.summonWormer(playerX+1, playerY+1);
            }
    }
}
