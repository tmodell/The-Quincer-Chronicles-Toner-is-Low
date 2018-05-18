package sprites;

import javax.swing.ImageIcon;
import world.World;

/**
 *
 * @author albert.wilcox
 */
public class Player extends Movable{
    static final String[] PLAYER_IMAGE_URLS = {"down facing url", "up facing url", "left facing url", "right facing url"};
    
    World world;
    
    public Player(int x, int y, World world){
        super(PLAYER_IMAGE_URLS[0]);
        this.x = x;
        this.y = y;
        
        this.world = world;
    }

    @Override
    public void left(){
        super.left();
        ImageIcon ii = new ImageIcon(PLAYER_IMAGE_URLS[orientation]);
        image = ii.getImage();
    }
    
    @Override
    public void right(){
        super.right();
        ImageIcon ii = new ImageIcon(PLAYER_IMAGE_URLS[orientation]);
        image = ii.getImage();
    }
    
    @Override
    public void down(){
        super.down();
        ImageIcon ii = new ImageIcon(PLAYER_IMAGE_URLS[orientation]);
        image = ii.getImage();
    }
    
    @Override
    public void up(){
        super.up();
        ImageIcon ii = new ImageIcon(PLAYER_IMAGE_URLS[orientation]);
        image = ii.getImage();
    }
    
}
