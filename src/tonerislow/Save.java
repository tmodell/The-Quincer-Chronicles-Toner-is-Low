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
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import sprites.Player;

/**
 *
 * @author albert.wilcox
 */
public class Save implements Serializable {
    private int playerX, playerY, money, maxHealth, health, potions;
    private boolean shaman1, shaman2, shaman3, shaman4;
    private String playerRoom;
        
    private static final String FILE_NAME = "save.ser";
    private static final String DEFAULT_ROOM = "test";
    
    private transient Player player;
    
    public Save() {
        File f = new File(FILE_NAME);
        try {
            if (f.createNewFile()) {
                    // This code is executed if it successfully creates the new file (meaning it didn't exist)
                    // This code saves this object to the file
                    ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME));
                    playerX = 11;
                    playerY = 11;
                    money = Player.DEFAULT_MONEY;
                    maxHealth = Player.DEFAULT_HEALTH;
                    health = maxHealth;
                    shaman1 = true;
                    shaman2 = true;
                    shaman3 = true;
                    playerRoom = DEFAULT_ROOM;// TODO update this
                    potions = 100;
                    oos.writeObject(this);
                    oos.close();
            } else {
                    // This code is executed if it already exists
                    ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME));
                    Save temp = (Save) ois.readObject();
                    playerX = temp.getPlayerX();
                    playerY = temp.getPlayerY();
                    money = temp.getMoney();
                    maxHealth = temp.getMaxHealth();
                    health = temp.getHealth();
                    shaman1 = temp.isShamenAlive(1);
                    shaman2 = temp.isShamenAlive(2);
                    shaman3 = temp.isShamenAlive(3);
                    playerRoom = temp.getPlayerRoom();
                    potions = temp.getPotions();
                    ois.close();
            }
        } catch(Exception e) {}
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
        
        // Now save this object
        try{
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME));
            oos.writeObject(this);
            oos.close();
        } catch (IOException e){}
    }
    
    public void setPlayer(Player player){
        this.player = player;
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
    
    public boolean isShamenAlive(int n){
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
    
    public void setShamenAlive(int n, boolean b){
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
