/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package world;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

import sprites.*;
import npcinteraction.*;

/**
 *
 * @author alber
 */
public class World extends JPanel{
    
    public static final int GRID_SIZE = 64;
    public static final int WIDTH = 26;
    public static final int HEIGHT = 14;
    
    private static final String PREFIX = "src/world/lib/";
    private static final String SUFFIX = ".txt";
    
    // These all have some use somewhere
    //The sprites arraylist is used for rendering. It contains all the items in the subsequent ALs as well
    ArrayList<Sprite> sprites;
    // The wormers array list is used for collision detection and combat
    ArrayList<Wormer> wormers;
    //The npcs array list is used to initiate interactions
    ArrayList<NPC> NPCs;
    //the paths AL is used for paths between maps
    private ArrayList<Path> paths;
    
    Player player;    
    char[][] squares;
    
    MainFrame frame;
    Image tile;
    
    HashMap<Character, String> symbolMap;
        
    public World(MainFrame frame){
        setSize(new Dimension(WIDTH * GRID_SIZE, HEIGHT * GRID_SIZE));
        
        sprites = new ArrayList <Sprite>();
        wormers = new ArrayList<Wormer>();
        paths = new ArrayList<Path>();
        NPCs = new ArrayList<NPC>();
        
        this.frame = frame;
        symbolMap = new HashMap <Character, String>();
                
        // the following loop is for testing purposes only
        for(int i = 0; i < 500; i++){
            add(new JLabel("Press any key to close "));
        }
    }
    
    public void loadMap(String name, int playerX, int playerY) throws IOException{
        sprites.clear();
        wormers.clear();
        paths.clear();
        NPCs.clear();
        
        player.setPosition(playerX, playerY);
        
        String url = PREFIX + name + SUFFIX;
        
        // File IO is fun right?
        BufferedReader inputStream = null;
        String s = "";
        try{
            inputStream = new BufferedReader(new FileReader(url));
            String l;
            while ((l = inputStream.readLine()) != null){
                if(s.substring(0, 1).equals("#")) continue;
                s = s + l + "\n";
            }
        } //catch(Exception e){}//NOT lazy i promise
        finally{
            if (inputStream != null) inputStream.close();
        }
        
        String[] lines = s.split("\n");
        
        int pathCount = 0;
        tile = new ImageIcon("src/sprites/lib/tiles/" + lines[0] + ".png").getImage();
        
        /*
         * This loop populates the 2D char array and sprites arraylist
         */
        for (int i = 1; i < 15; i++){
            for (int j = 0; j < 26; j++){
                char c = lines[i].charAt(j);
                if (c != ' '){
                    sprites.add(new Stationary(symbolMap.get(c)));
                }
                if (c == 'P' || c == 'D'){
                    pathCount++;
                    paths.add(new Path(i, j, null));
                }
                squares[i][j] = c;
            }
        }
        
        // This code handles paths
        for (int i = 15; i < 15 + pathCount; i++){
            paths.get(i - 15).setLocName(lines[i]);
        }
        
        // This code handles NPCs
        for (int i = 15 + pathCount; i < lines.length; i++){
            String[] split = lines[i].split(",");
            String imageURL = "src/sprites/lib/characters/" + split[0] + ".png";
            String interactionName = split[1];
            String nam = split[2];
            int x = Integer.parseInt(split[3]);
            int y = Integer.parseInt(split[4]);
            NPC npc = new NPC(imageURL, interactionName, nam, x, y);
            squares[x][y] = 'N';
            
            sprites.add(npc);
            NPCs.add(npc);
        }
    }
    
    /**
     * The way this works is that each letter in the text box will
     * correspond to a type of stationary sprite. This map will be
     * used to match them up. Use the .put(Key, sprite name) method
     * to add new things to the map. If you have questions you know
     * where to find me.
     */
    private void populateSymbolMap(){
        symbolMap.put('T', "tree");
        symbolMap.put('H', "house");
        symbolMap.put('X', null);
        //add more if you please
        
        /* These two are special, and represent pathways that, when stepped
         * on, will send the player elsewhere. If you feel the need to add one
         * here let me know so I can hard code support for it.
         */
        symbolMap.put('D', "doorway");
        symbolMap.put('P', "path");
    }
    
    /**
     * This method is called when the player attacks
     * @param x
     * @param y
     * @param damage
     */
    public void PlayerAttack(int x, int y, int damage){
        //TODO add logic for player attacks
        
    }
    
    /**
     * This method is called when a wormer attacks
     * @param x 
     * @param y
     * @param damage 
     */
    public void WormerAttack(int x, int y, int damage){
        // TODO add logic for wormer attacks
    }
    
    public void playerInteraction(){
        int x = player.getX(), y = player.getY(), orientation = player.getOrientation();
        switch (orientation){
            case Movable.ORIENTATION_UP:
                y--;
                break;
            case Movable.ORIENTATION_DOWN:
                y++;
                break;
            case Movable.ORIENTATION_LEFT:
                x--;
                break;
            case Movable.ORIENTATION_RIGHT:
                x++;
                break;
        }
        
        AdvancableText at = null;
        if (squares[x][y] == 'N'){
            for (NPC npc: NPCs){
                if (npc.getX() == x && npc.getY() == y){
                    try{
                        at = npc.getInteraction();
                    }catch(Exception e){}
                }
            }
            if (at == null) System.out.println("Something went horribly wrong.");
            
            frame.box.startInteraction(at);
        }
    }
    
    /**
     * Determines whether a square is occupiable by the player
     * @param x the square's x position
     * @param y the square's y position
     * @return whether the square is occupiable
     */
    public boolean isOccupiable(int x, int y){
        if (squares[x][y] != ' ') return false;
        for (Wormer w: wormers){
            if (w.getX() == x && w.getY() == y) return false;
        }
        return true;
    }
    
    /**
     * This method will one day be used to repaint the panel
     * when something is changed
     */
    public void update(){
        // TODO add code
    }
    
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        // Sample loop for drawing map; leaving it commented out for now
        for (Sprite x: sprites){
            g.drawImage(x.getImage(), x.getRealX(), x.getRealY(), null);
        }
    }
    
    /**
     * This method will be called when the player presses an arrow key.
     * Its job is to check whether the player can move where he/she wants
     * to and if they can the sprite is notified and display updated.
     * @param key 
     */
    public void handleMovementKey(int key) {
        int hypotheticalX, hypotheticalY, x = player.getX(), y = player.getY();
        switch (key){
            case KeyEvent.VK_A:
                hypotheticalX = x - 1;
                // check whether the square is occupiable
                if (isOccupiable(hypotheticalX, y)){
                    player.left();
                    update();
                } else{
                    // maybe add code for what to do if a square is unoccupiable
                }
                break;
            case KeyEvent.VK_D:
                hypotheticalX = x + 1;
                // check whether the square is occupiable
                if (isOccupiable(hypotheticalX, y)){
                    player.right();
                    update();
                } else{
                    // maybe add code for what to do if a square is unoccupiable
                }
                break;
            case KeyEvent.VK_W:
                hypotheticalY = y - 1;
                // check whether the square is occupiable
                if (isOccupiable(x, hypotheticalY)){
                    player.up();
                    update();
                } else{
                    // maybe add code for what to do if a square is unoccupiable
                }
                break;
            case KeyEvent.VK_S:
                hypotheticalY = y + 1;
                // check whether the square is occupiable
                if (isOccupiable(x, hypotheticalY)){
                    player.down();
                    update();
                } else{
                    // maybe add code for what to do if a square is unoccupiable
                }
                break;
        }
    }
    
    public Player getPlayer(){
        return player;
    }
    
    private class Path{
        int destX, destY;
        String locName;
        public Path(int destX, int destY, String locName){
            this.destX = destX;
            this.destY = destY;
            this.locName = locName;
        }
        
        public void setLocName(String locName){
            this.locName = locName;
        }
        
        public String getLocName(){
            return locName;
        }
    }
}
