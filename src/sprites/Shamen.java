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
    
    public Shamen(World world, int health, int damage, int speed, int shamenNum) {
        super(world, health, damage, speed);
        
        this.shamenNum = shamenNum;
        
        ImageIcon ii = new ImageIcon(IMAGE_URL);
        image = ii.getImage();
    }
    
}
