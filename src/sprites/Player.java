package sprites;

import javax.swing.ImageIcon;
import world.World;

/**
 *
 * @author albert.wilcox
 */
public class Player extends Movable{
    static final String[] PLAYER_IMAGE_URLS = {"src/sprites/lib/images/quincerfront.png", "src/sprites/lib/images/quincerfront.png", 
        "src/sprites/lib/images/quincerfront.png", "src/sprites/lib/images/quincerfront.png"};
    public static final int DEFAULT_HEALTH = 200;
    public static final int DEFAULT_DAMAGE = 25;
    public static final int DEFAULT_COOLDOWN = 1250;
    public static final int DEFAULT_MONEY = 100;
    
    World world;
    
    int maxHealth = DEFAULT_HEALTH;
    int health = DEFAULT_HEALTH;
    int damage = DEFAULT_DAMAGE;
    int cooldown = DEFAULT_COOLDOWN;
    int money = DEFAULT_MONEY;
    
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
    
    public int getDamage(){
        return damage;
    }
    
    public void receiveStrike(int damage){
        health -= damage;
        if (health <= 0) kill();
    }
    
    public void givePotion(int n){
        //TODO code to give the player n potions
    }
    
    public void kill(){
        //TODO add code to handle player death
    }
    
    public void setCooldown(int cooldown){
        this.cooldown = cooldown;
    }
    
    public void setPosition(int x, int y){
        this.x = x;
        this.y = y;
    }
    
    public void attack(){
        int attackdmg = damage;
        
        deltatime = System.currentTimeMillis() - time;
        time = System.currentTimeMillis();
        
        //Needlessly complicated but it works.
        //Relationship for damage and time between key presses.
        if(deltatime <= 250){attackdmg = 1;}
        else if(deltatime > 250 && deltatime < cooldown){attackdmg = (int)((double)damage*Math.pow((double)damage/2,(deltatime-cooldown)/(cooldown-250)));}
        
        world.PlayerAttack(x, y, attackdmg);
        
    }
    
    public int getMoney(){
        return money;
    }
    
    public void setMoney(int money){
        this.money = money;
    }
    
    public int getMaxHealth(){
        return maxHealth;
    }
    
    public int getHealth(){
        return health;
    }
    
    public World getWorld(){
        return world;
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
