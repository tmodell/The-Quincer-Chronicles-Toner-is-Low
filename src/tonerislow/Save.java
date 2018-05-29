/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tonerislow;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InvalidClassException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import sprites.Player;

/**
 *
 * @author albert.wilcox
 */
public class Save implements Serializable {
    private int playerX, playerY, money, maxHealth, health, potions, damage, armorLevel, swordLevel;

    private boolean shaman1, shaman2, shaman3, shaman4;
    private String playerRoom;
        
    private static final String FILE_NAME = "save.ser";
    private static final String DEFAULT_ROOM = "village0";
    
    private static boolean DEBUG = false;
    
    private transient Player player;
    
    public Save() {
        File f = new File(FILE_NAME);
        try {
            if (f.createNewFile()) {
                // This code is executed if it successfully creates the new file (meaning it didn't exist)
                // This code saves this object to the file
                ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME));
                playerX = 9;
                playerY = 11;
                money = Player.DEFAULT_MONEY;
                maxHealth = Player.DEFAULT_HEALTH;
                health = maxHealth;
                damage = Player.DEFAULT_DAMAGE;
                shaman1 = !DEBUG;
                shaman2 = !DEBUG;
                shaman3 = !DEBUG;
                shaman4 = !DEBUG;
                playerRoom = DEFAULT_ROOM;// TODO update this
                potions = Player.DEFAULT_POTIONS;
                armorLevel = 1;
                swordLevel = 1;
                oos.writeObject(this);
                oos.close();
            } else {
                // This code is executed if it already exists
                try{
                    ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME));
                    Save temp = (Save) ois.readObject();
                    playerX = temp.getPlayerX();
                    playerY = temp.getPlayerY();
                    money = temp.getMoney();
                    maxHealth = temp.getMaxHealth();
                    health = temp.getHealth();
                    damage = temp.getDamage();
                    shaman1 = temp.isShamanAlive(1);
                    shaman2 = temp.isShamanAlive(2);
                    shaman3 = temp.isShamanAlive(3);
                    shaman4 = temp.isShamanAlive(4);
                    armorLevel = temp.getArmorLevel();
                    swordLevel = temp.getSwordLevel();
                    playerRoom = temp.getPlayerRoom();
                    potions = temp.getPotions();
                    ois.close();
                } catch(Exception e){
                    if (e instanceof InvalidClassException){
                        System.out.println("I found an old save.ser file and am replacing it.");
                        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME));
                        playerX = 9;
                        playerY = 11;
                        money = Player.DEFAULT_MONEY;
                        maxHealth = Player.DEFAULT_HEALTH;
                        health = maxHealth;
                        damage = Player.DEFAULT_DAMAGE;
                        shaman1 = !DEBUG;
                        shaman2 = !DEBUG;
                        shaman3 = !DEBUG;
                        shaman4 = !DEBUG;
                        armorLevel = 1;
                        swordLevel = 1;
                        playerRoom = DEFAULT_ROOM;// TODO update this
                        potions = Player.DEFAULT_POTIONS;
                        oos.writeObject(this);
                        oos.close();
                    }else{e.printStackTrace();}
                }
            }
        } catch(Exception e) {e.printStackTrace();}
    }
    
    public void save(){
        // Update all the variables
        playerX = player.getX();
        playerY = player.getY();
        money = player.getMoney();
        health = player.getHealth();
        potions = player.getPotionCount();
        maxHealth = player.getMaxHealth();
        playerRoom = player.getWorld().getMap();
        damage = player.getDamage();
        armorLevel = player.getArmorLevel();
        swordLevel = player.getSwordLevel();
        
        // Now save this object
        try{
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME));
            oos.writeObject(this);
            oos.close();
        } catch (IOException e){}
    }
    
    public void setArmorLevel(int armorLevel) {
        this.armorLevel = armorLevel;
    }

    public void setSwordLevel(int swordLevel) {
        this.swordLevel = swordLevel;
    }

    public int getArmorLevel() {
        return armorLevel;
    }

    public int getSwordLevel() {
        return swordLevel;
    }
    
    public void setPlayer(Player player){
        this.player = player;
    }
    
    public void setDamage(int damage){
        this.damage = damage;
    }
    
    public int getDamage(){
        return damage;
    }
    
    public int getPlayerX(){
        return playerX;
    }
    
    public void setPlayerX(int x){
        this.playerX = x;
    }
    
    public int getPlayerY(){
        return playerY;
    }
    
    public void setPlayerY(int y){
        this.playerY = y;
    }
    
    public boolean isShamanAlive(int n){
        switch(n){
            case -1:
                return true;
            case 0:
                return false;
            case 1:
                return shaman1;
            case 2:
                return shaman2;
            case 3:
                return shaman3;
            case 4:
                return shaman4;
        }
        return false;
    }
    
    public void setShamanAlive(int n, boolean b){
        switch(n){
            case 1:
                shaman1 = b;
                break;
            case 2:
                shaman2 = b;
                break;
            case 3:
                shaman3 = b;
                break;
            case 4:
                shaman4 = b;
                break;
        }
    }
    
    public int nextShaman(){
        if (shaman1) return 1;
        if (shaman2) return 2;
        if (shaman3) return 3;
        if (shaman4) return 4;
        return 5;
    }
    
    public boolean allShamansDead(){
        return !(shaman1 || shaman2 || shaman3 || shaman4);
    }
    
    public String getPlayerRoom(){
        return playerRoom;
    }
    
    public void setPlayerRoom(String s){
        this.playerRoom = s;
    }
    
    public int getMoney(){
        return money;
    }
    
    public void setMoney(int n){
        this.money = n;
    }
    
    public int getMaxHealth(){
        return maxHealth;
    }
    
    public void setMaxHealth(int n){
        this.maxHealth = n;
    }
    
    public int getHealth(){
        return health;
    }
    
    public void setHealth(int n){
        this.health = n;
    }
    
    public void setPotions(int n){
        this.potions = n;
    }
    
    public int getPotions(){
        return potions;
    }
}
