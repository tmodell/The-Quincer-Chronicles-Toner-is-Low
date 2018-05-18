package sprites;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import world.World;

/**
 *
 * @author albert.wilcox
 */
public class Player extends Sprite{
    static final String PLAYER_IMAGE_URL = "TODO add url";
    
    World world;
    
    public Player(int x, int y, World world){
        super(PLAYER_IMAGE_URL);
        this.x = x;
        this.y = y;
        
        this.world = world;
    }

    public void left(){
        x -= 1;
    }
    
    public void right(){
        x += 1;
    }
    
    public void down(){
        y += 1;
    }
    
    public void up(){
        y -= 1;
    }
    
}
