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
    public static final int DEFAULT_COOLDOWN = 900;
    public static final int DEFAULT_MONEY = 100;
    
    World world;
    
    int maxHealth = DEFAULT_HEALTH;
    int health = DEFAULT_HEALTH;
    int damage = DEFAULT_DAMAGE;
    int cooldown = DEFAULT_COOLDOWN;
    int money = DEFAULT_MONEY;
    
    double time;
    double deltatime;
    
    int potionCount = 100;
    
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
        
        world.getFrame().getSideBar().update();
//        String s;
//        System.out.println(health);
//        s = health < 0 ? "dead" : Integer.toString(health);
//        System.out.println(s);
    }

    public void usePotion(){
        if (health == maxHealth) return;
        potionCount--;
        health += 50;
        if (health > maxHealth) health = maxHealth;
        world.getFrame().getSideBar().update();
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
        if(deltatime <= 250){attackdmg = 2;}
        else if(deltatime < cooldown){attackdmg = (int)((double)damage * Math.pow(damage/3,(deltatime-cooldown)/(cooldown-250)));}
        System.out.println(deltatime);
        System.out.println(attackdmg);
        int destX = getXInFront();
        int destY = getYInFront();
        
        world.PlayerAttack(destX, destY, attackdmg);
        
    }
    
    @Override
    public void setOrientation(int orientation){
        super.setOrientation(orientation);
        ImageIcon ii = new ImageIcon(PLAYER_IMAGE_URLS[orientation]);
        image = ii.getImage();
    }
    
    public int getPotionCount(){
        return potionCount;
    }
    
    public void givePotions(int num){
        potionCount += num;
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
