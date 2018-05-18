package sprites;

import javax.swing.ImageIcon;
import world.World;

/**
 *
 * @author albert.wilcox
 */
public class Player extends Sprite{
    static final String[] PLAYER_IMAGE_URLS = {"down facing url", "up facing url", "left facing url", "right facing url"};
    
    public static final int ORIENTATION_DOWN = 0;
    public static final int ORIENTATION_UP = 1;
    public static final int ORIENTATION_LEFT = 2;
    public static final int ORIENTATION_RIGHT = 3;
    
    int orientation = 0;
    
    World world;
    
    public Player(int x, int y, World world){
        super(PLAYER_IMAGE_URLS[0]);
        this.x = x;
        this.y = y;
        
        this.world = world;
    }

    public void left(){
        x -= 1;
        orientation = ORIENTATION_LEFT;
        ImageIcon ii = new ImageIcon(PLAYER_IMAGE_URLS[orientation]);
        image = ii.getImage();
    }
    
    public void right(){
        x += 1;
        orientation = ORIENTATION_RIGHT;
        ImageIcon ii = new ImageIcon(PLAYER_IMAGE_URLS[orientation]);
        image = ii.getImage();
    }
    
    public void down(){
        y += 1;
        orientation = ORIENTATION_DOWN;
        ImageIcon ii = new ImageIcon(PLAYER_IMAGE_URLS[orientation]);
        image = ii.getImage();
    }
    
    public void up(){
        y -= 1;
        orientation = ORIENTATION_UP;
        ImageIcon ii = new ImageIcon(PLAYER_IMAGE_URLS[orientation]);
        image = ii.getImage();
    }
    
}
