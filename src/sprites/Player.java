package sprites;

import javax.swing.ImageIcon;
import world.World;

/**
 *
 * @author albert.wilcox
 */
public class Player extends Movable{
    static final String[] PLAYER_IMAGE_URLS = {"down facing url", "up facing url", "left facing url", "right facing url"};
    static final int DEFAULT_HEALTH = 200;
    static final int DEFAULT_DAMAGE = 10;
    
    World world;
    
    int maxHealth = DEFAULT_HEALTH;
    int health = DEFAULT_HEALTH;
    
    int damage = DEFAULT_DAMAGE;
    
    public Player(int x, int y, World world){
        super(PLAYER_IMAGE_URLS[0]);
        this.x = x;
        this.y = y;
        
        this.world = world;
    }

    public void recieveStrike(int damage){
        health -= damage;
        //TODO add code to handle player death
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
