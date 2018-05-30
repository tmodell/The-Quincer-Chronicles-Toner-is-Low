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

    private static final String PREFIX = "src/npcinteraction/lib/";
    private static final String SUFFIX = ".txt";
    
    String[] options, actions;
    
    int cursorIndex;
    int maxCursorIndex;
        
    Player player;
    
    public Menu(String fileName, Player player, String name) throws IOException{
        options = new String[3];
        actions = new String[3];
        
        BufferedReader inputStream = null;
        try{
            inputStream = new BufferedReader(new FileReader(PREFIX + fileName + SUFFIX));
            String l;
            int count = 0;
            while ((l = inputStream.readLine()) != null){
                String[] split = l.split(",");
                options[count] = split[0];
                actions[count] = split[1];
                count++;
            }
        } catch(Exception e){e.printStackTrace();}//NOT lazy i promise
        finally{
            if (inputStream != null) inputStream.close();
        }
        
//        System.out.println("making thing");
//        for (String s: actions){
//            System.out.println(s);
//        }
        
        cursorIndex = 0;
        maxCursorIndex = options.length - 1;
                
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
    
    public int getCursorPos(){
        return cursorIndex;
    }
    
    public String[] getOptions(){
        return options;
    }
    
    /**
     * Called when the player hits enter
     * @return Whether the interaction is over
     */
    public boolean press(){
        String s = actions[cursorIndex];
        String[] split = s.split(";");
        int buy = 0, potion = 0;
        boolean quit = false, health = false, damage = false;
        for (String str: split){
            char c = str.charAt(0);
            switch(c){
                case 'b':
                    buy = Integer.parseInt(str.substring(1, str.length()));
                    break;
                case 'h':
                    health = true;
                    break;
                case 'p':
                    potion = Integer.parseInt(str.substring(1, str.length()));
                    break;
                case 'd':
                    damage = true;
                    break;
                case 'q':
                    quit = true;
                    break;
            }
        }
        if (health) buy *= player.getArmorLevel();
        else if (damage) buy *= player.getSwordLevel();
        if (player.getMoney() >= buy){
            player.setMoney(player.getMoney() - buy);
            if (health) player.upgradeArmor();
            player.givePotions(potion);
            if (damage) player.upgradeSword();
            tonerislow.TonerIsLow.getMainFrame().getSideBar().update();
        } //else quit = true;
        if (quit) return true;
        return false;
    }
}
