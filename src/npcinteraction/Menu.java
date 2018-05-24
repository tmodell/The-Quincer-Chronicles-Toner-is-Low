/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package npcinteraction;

import java.awt.Image;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import javax.swing.ImageIcon;
import sprites.*;

/**
 *
 * @author alber
 */
public class Menu {
    private static final String CURSOR_IMAGE_URL = "url";
    
    String[] options;
    
    int cursorIndex;
    int maxCursorIndex;
    
    Image cursor;
    
    Player player;
    
    public Menu(String url, Player player) throws IOException{
        BufferedReader inputStream = null;
        String s = "";
        try{
            inputStream = new BufferedReader(new FileReader(url));
            String l;
            while ((l = inputStream.readLine()) != null){
                s = s + l + "\n";
            }
        } catch(Exception e){}//NOT lazy i promise
        finally{
            if (inputStream != null) inputStream.close();
        }
        
        options = s.split("/n");
        cursorIndex = 0;
        maxCursorIndex = options.length - 1;
        
        cursor = new ImageIcon(CURSOR_IMAGE_URL).getImage();
        
        this.player = player;
    }
    
    public void left(){
        cursorIndex -= 1;
        if (cursorIndex < 0) cursorIndex = 0;
    }
    
    public void right(){
        cursorIndex += 1;
        if (cursorIndex > maxCursorIndex) cursorIndex = maxCursorIndex;
    }
    
    /**
     * Called when the player hits enter
     * @return Whether the interaction is over
     */
    public boolean press(){
        String s = options[cursorIndex];
        String[] split = s.split(",");
        int buy = 0, health = 0, potion = 0, damage = 0;
        boolean quit = false;
        for (String str: split){
            char c = str.charAt(0);
            switch(c){
                case 'b':
                    buy = Integer.parseInt(str.substring(1, str.length()));
                    break;
                case 'h':
                    health = Integer.parseInt(str.substring(1, str.length()));
                    break;
                case 'p':
                    potion = Integer.parseInt(str.substring(1, str.length()));
                    break;
                case 'd':
                    damage = Integer.parseInt(str.substring(1, str.length()));
                    break;
                case 'q':
                    quit = true;
                    break;
            }
        }
        if (player.getMoney() >= buy){
            player.setMoney(player.getMoney() - buy);
            if (player.getMaxHealth() < health)player.setMaxHealth(health);
            player.givePotion(potion);
            if (player.getDamage() < health)player.setDamage(damage);
        } else quit = true;
        if (quit) return true;
        return false;
    }
}
