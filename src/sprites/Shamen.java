/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sprites;

import javax.swing.ImageIcon;
import world.World;

/**
 *
 * @author alber
 */
public class Shamen extends Wormer{
    private static final String IMAGE_URL = "src/sprites/lib/images/shamen.png";
    
    int shamenNum;
    
    public Shamen(World world, int health, int damage, int x, int y, int shamenNum) {
        super(world, health, damage, x, y);
        
        this.shamenNum = shamenNum;
        
        ImageIcon ii = new ImageIcon(IMAGE_URL);
        image = ii.getImage();
    }
    
    @Override
    public void kill(){
        super.kill();
        world.getFrame().getSave().setShamenAlive(shamenNum, false);
    }
    
}
