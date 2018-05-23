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
    private static final String SUFFIX = ".csv";
    
    // These all have some use somewhere
    //The sprites arraylist is used for rendering. It contains all the items in the subsequent ALs as well
    ArrayList<Sprite> sprites;
    // The wormers array list is used for collision detection and combat
    //The npcs array list is used to initiate interactions
    //the paths AL is used for paths between maps
    
    Player player;    
    char[][] squares;
    Path[][] paths;
    NPC[][] NPCs;
    Wormer[][] wormers; //TODO some code that keeps track of which wormers are in which squares. This will be inconvenient but i think faster
    
    MainFrame frame;
    Image tile;
    
    HashMap<Character, String> symbolMap;
        
    public World(MainFrame frame){
        setSize(new Dimension(WIDTH * GRID_SIZE, HEIGHT * GRID_SIZE));
        
        sprites = new ArrayList <Sprite>();
        
        squares = new char[WIDTH][HEIGHT];
        paths = new Path[WIDTH][HEIGHT];
        NPCs = new NPC[WIDTH][HEIGHT];
        wormers = new Wormer[WIDTH][HEIGHT];
        
        this.frame = frame;
        symbolMap = new HashMap <Character, String>();
                
        // the following loop is for testing purposes only
        for(int i = 0; i < 500; i++){
            add(new JLabel("Press any key to close "));
        }
    }
    
    public void loadMap(String name, int playerX, int playerY) throws IOException{
        sprites.clear();
        
        paths = new Path[WIDTH][HEIGHT];
        NPCs = new NPC[WIDTH][HEIGHT];
        wormers = new Wormer[WIDTH][HEIGHT];
        
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
        
        tile = new ImageIcon("src/sprites/lib/tiles/" + lines[0] + ".png").getImage();
        
        /*
         * This loop fills in all the variables and loads the world
         */
        for (int y = 1; y < 15; y++){
            String[] split = lines[y].split(",");
            for (int x = 0; x < WIDTH; x++){
                char c;
                if (split[x].equals("")) c = ' ';
                else c = split[x].charAt(0);
                // What to do if I notice an escape character
                if (c == '%'){
                    String[] split2 = split[x].split(",");
                    char ch = split2[0].charAt(1);
                    switch (ch){
                        // Paths
                        case 'P':
                            c = 'P';
                            char symbol = '\0';
                            if (!split2[1].equals(""))sprites.add(new Stationary(symbolMap.get(split2[1].charAt(0)), x, y));
                            int destX = Integer.parseInt(split2[2]);
                            int destY = Integer.parseInt(split2[3]);
                            String destName = split2[4];
                            Path path = new Path(destX, destY, destName);
                            paths[x][y] = path;
                            break;
                        // NPCs
                        case 'N':
                            c = 'N';
                            char symbool = split2[1].charAt(0);
                            String interactionFileName = split2[2];
                            String nam = split2[3];
                            NPC npc = new NPC(symbolMap.get(symbool), interactionFileName, nam, x, y);
                            NPCs[x][y] = npc;
                            sprites.add(npc);
                            break;
                        // Hysperia
                        case 'H':
                            c = 'N';
                            Hysperia hysperia = new Hysperia(x, y);
                            NPCs[x][y] = hysperia;
                            sprites.add(hysperia);
                            break;
                    }
                }
                else if (c != ' '){
                    sprites.add(new Stationary(symbolMap.get(c), x , y));
                }
                squares[x][y] = c;
            }
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
        symbolMap.put('.', null);
        symbolMap.put('A', "alchemist");
//        symbolMap.put('D', "doorway");
//        symbolMap.put('P', "path");
        symbolMap.put('C', "alchemistcart");
        symbolMap.put('Y', "hysperia");
        symbolMap.put('L', "villageleader");
        symbolMap.put('V', "villager");
        symbolMap.put('B', "blacksmith");
        symbolMap.put('W', "wiseman");
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
        
        if (squares[x][y] == 'N'){
            AdvancableText at = NPCs[x][y].getInteraction();
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
        return !(squares[x][y] != ' ' || wormers[x][y] != null);
    }
    
    /**
     * This method will one day be used to repaint the panel
     * when something is changed
     */
    public void update(){
    }
    
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        
        for (int i = 0; i < WIDTH; i++){
            for (int j = 0; j < HEIGHT; j++){
                g.drawImage(tile, i * GRID_SIZE, j * GRID_SIZE, null);
            }
        }
        
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
        //private static final String PREFIX = "src/world/lib/maps/";
        
        int destX, destY;
        String destURL;
        
        public Path(int destX, int destY, String locName){
            this.destX = destX;
            this.destY = destY;
            this.destURL = PREFIX + locName + SUFFIX;
        }        
        
        public String getDestURL(){
            return destURL;
        }
    }
}
