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
    static final int DEFAULT_DAMAGE = 25;
    static final int DEFAULT_COOLDOWN = 1250;
    
    World world;
    
    int maxHealth = DEFAULT_HEALTH;
    int health = DEFAULT_HEALTH;
    int damage = DEFAULT_DAMAGE;
    int speed = DEFAULT_COOLDOWN;
    
    long time;
    long deltatime;
    
    public Player(int x, int y, World world){
        super(PLAYER_IMAGE_URLS[0]);
        this.x = x;
        this.y = y;
        
        this.world = world;
    }
    
    public void restoreHealth(){
        health = maxHealth;
    }
    
    public void increaseHealth(int value){
        health += value;
        if (health > maxHealth) health = maxHealth;
    }
    
    public void setMaxHealth(int newHealth){
        this.maxHealth = newHealth;
    }
    
    public void setDamage(int damage){
        this.damage = damage;
    }
    
    public void recieveStrike(int damage){
        health -= damage;
        if (health <= 0) kill();
    }
    
    public void kill(){
        //TODO add code to handle player death
    }
    
    public void setSpeed(int time){
        this.speed = speed;
    }
    
    public void setPosition(int x, int y){
        this.x = x;
        this.y = y;
    }
    
    public void attack(){
        int attackdmg = damage;
        
        deltatime = System.currentTimeMillis() - time;
        time = System.currentTimeMillis();
        
        //TODO add formula for damage reduction
        world.PlayerAttack(x, y, attackdmg);
        
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
