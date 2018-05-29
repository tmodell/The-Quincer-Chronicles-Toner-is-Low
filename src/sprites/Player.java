package sprites;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;
import sound.SoundFXController;
import tonerislow.Save;
import world.World;

/**
 *
 * @author albert.wilcox
 */
public class Player extends Movable{
    static final String[] PLAYER_IMAGE_URLS = {"src/sprites/lib/images/quincerfront.png", "src/sprites/lib/images/quincerback.png", 
        "src/sprites/lib/images/quincerleft.png", "src/sprites/lib/images/quincerright.png"};
    public static final int DEFAULT_HEALTH = 200;
    public static final int DEFAULT_DAMAGE = 25;
    public static final int DEFAULT_COOLDOWN = 900;
    public static final int DEFAULT_MONEY = 100;
    public static final int DEFAULT_POTIONS = 20;
    
    public static final int POTION_RESTORATION = 50;
    
    public static final int SWORD_UPGRADE = 10;
    public static final int ARMOR_UPGRADE = 30;
    
    World world;
    
    int maxHealth = DEFAULT_HEALTH;
    int health = DEFAULT_HEALTH;
    int damage = DEFAULT_DAMAGE;
    int cooldown = DEFAULT_COOLDOWN;
    int money = DEFAULT_MONEY;
    int armorLevel = 1;
    int swordLevel = 1;
    
    double time;
    double deltatime;
    
    int potionCount;
    
    Save save;
    
    public Player(int x, int y, World world){
        super(PLAYER_IMAGE_URLS[0]);
        this.x = x;
        this.y = y;
        
        this.world = world;
        
        save = tonerislow.TonerIsLow.getSave();
        
        save.setPlayer(this);
        
        money = save.getMoney();
        health = save.getHealth();
        maxHealth = save.getMaxHealth();
        potionCount = save.getPotions();
        damage = save.getDamage();
        armorLevel = save.getArmorLevel();
        swordLevel = save.getSwordLevel();
    }
    
    
    public int getArmorLevel() {
        return armorLevel;
    }

    public int getSwordLevel() {
        return swordLevel;
    }
    
    public void restoreHealth(){
        health = maxHealth;
    }
    
    public void increaseHealth(int value){
        health += value;
        if (health > maxHealth) health = maxHealth;
    }
    
    public void upgradeArmor(){
        maxHealth += ARMOR_UPGRADE;
        armorLevel++;
    }
    
    public void upgradeSword(){
        damage += SWORD_UPGRADE;
        swordLevel++;
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
        if (health == maxHealth || potionCount < 1) return;
        potionCount--;
        health += POTION_RESTORATION;
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
        try { //Plays attack sound
            SoundFXController.swordFX();
        } catch (UnsupportedAudioFileException | LineUnavailableException | IOException | InterruptedException ex) {
            Logger.getLogger(World.class.getName()).log(Level.SEVERE, null, ex);
        }
        
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
    
    public void giveMoney(int amount){
        money+= amount;
        world.getFrame().getSideBar().update();
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
