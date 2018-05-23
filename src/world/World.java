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
    
    private static final String PREFIX = "src/world/lib/maps/";
    private static final String SUFFIX = ".csv";
    
    //The sprites arraylist is used for rendering. It contains all the items in the subsequent ALs as well
    ArrayList<Sprite> sprites;
    
    Player player;    
    char[][] squares;
    Path[][] paths;
    NPC[][] NPCs;
    Wormer[][] wormers; //TODO some code that keeps track of which wormers are in which squares. This will be inconvenient but i think faster
    
    MainFrame frame;
    Image tile;
    
    volatile boolean repainting = true;
    
    HashMap<Character, String> symbolMap;
        
    public World(MainFrame frame){        
        //setSize(new Dimension(WIDTH * GRID_SIZE, HEIGHT * GRID_SIZE));
        
        sprites = new ArrayList <Sprite>();
        
        squares = new char[WIDTH][HEIGHT];
        paths = new Path[WIDTH][HEIGHT];
        NPCs = new NPC[WIDTH][HEIGHT];
        wormers = new Wormer[WIDTH][HEIGHT];
        
        this.frame = frame;
        
        symbolMap = new HashMap <Character, String>();
        populateSymbolMap();
        
        player = new Player(0, 0, this);
        
        // the following loop is for testing purposes only
//        for(int i = 0; i < 500; i++){
//            add(new JLabel("Press any key to close "));
//        }
    }
    
    public void loadMap(String name, int playerX, int playerY) throws IOException{
        repainting = false;

        sprites.clear();
        sprites.add(player);
        
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
                if(l.substring(0, 1).equals("#")) continue;
                s = s + l + "\n";
            }
        } catch(Exception e){e.printStackTrace();}//NOT lazy i promise
        finally{
            if (inputStream != null) inputStream.close();
        }

        String[] lines = s.split("\n");
        
        //System.out.println(1);
        tile = new ImageIcon("src/sprites/lib/tiles/" + lines[0] + ".png").getImage();
        
        //System.out.println("The loop is about to start...");
        /*
         * This loop fills in all the variables and loads the world
         */
        for (int y = 1; y < 15; y++){
            //System.out.println(lines[y]);
            String[] commaSplit = lines[y].split(",");
            for (int x = 0; x < WIDTH; x++){
                //System.out.println("\ny: " + y + " \nx: " + x);
                char c;
                //System.out.println(1);
                if (commaSplit[x].equals("")) c = ' ';
                else c = commaSplit[x].charAt(0);
               // System.out.println(2);
                // What to do if I notice an escape character
                if (c == '%'){
                    //System.out.println(3);
                    //System.out.println("Something went wrong.");
                    String[] escapeSplit = commaSplit[x].split(";");
                    char ch = escapeSplit[0].charAt(1);
                    switch (ch){
                        // Paths
                        case 'P':
                            c = 'P';
                            char symbol = '\0';
                            if (!escapeSplit[1].equals(""))sprites.add(new Stationary(symbolMap.get(escapeSplit[1].charAt(0)), x, y - 1));
                            int destX = Integer.parseInt(escapeSplit[2]);
                            int destY = Integer.parseInt(escapeSplit[3]);
                            String destName = escapeSplit[4];
                            Path path = new Path(destX, destY, destName);
                            paths[x][y - 1] = path;
                            break;
                        // NPCs
                        case 'N':
                            c = 'N';
                            char symbool = escapeSplit[1].charAt(0);
                            String interactionFileName = escapeSplit[2];
                            String nam = escapeSplit[3];
                            NPC npc = new NPC(symbolMap.get(symbool), interactionFileName, nam, x, y - 1);
                            NPCs[x][y - 1] = npc;
                            sprites.add(npc);
                            break;
                        // Hysperia
                        case 'H':
                            c = 'N';
                            Hysperia hysperia = new Hysperia(x, y - 1);
                            NPCs[x][y - 1] = hysperia;
                            sprites.add(hysperia);
                            break;
                    }
                }
                else if (c != ' '){
                    //System.out.println(c);
                    //System.out.println(symbolMap.get(c));
                    sprites.add(new Stationary(symbolMap.get(c), x , y - 1));
                    //System.out.println(5);
                }
                squares[x][y - 1] = c;
            }//System.out.println(count++);
        } //System.out.println("Loop complete.");
        repainting = true;
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
    
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        //System.out.println("Painting");
        for (int x = 0; x < WIDTH; x++){
            System.out.println(x);
            for (int y = 0; y < HEIGHT; y++){
                g.drawImage(tile, x * GRID_SIZE, y * GRID_SIZE, null);
            }
        }
        
        // Sample loop for drawing map; leaving it commented out for now
        for (Sprite x: sprites){
            //System.out.println(x.getRealY());
            g.drawImage(x.getImage(), x.getRealX(), x.getRealY(), null);
        }
        
        try{
            Thread.sleep(33);
        } catch (Exception e){}
        repaint();
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
                } else{
                    // maybe add code for what to do if a square is unoccupiable
                }
                break;
            case KeyEvent.VK_D:
                hypotheticalX = x + 1;
                // check whether the square is occupiable
                if (isOccupiable(hypotheticalX, y)){
                    player.right();
                } else{
                    // maybe add code for what to do if a square is unoccupiable
                }
                break;
            case KeyEvent.VK_W:
                hypotheticalY = y - 1;
                // check whether the square is occupiable
                if (isOccupiable(x, hypotheticalY)){
                    player.up();
                } else{
                    // maybe add code for what to do if a square is unoccupiable
                }
                break;
            case KeyEvent.VK_S:
                hypotheticalY = y + 1;
                // check whether the square is occupiable
                if (isOccupiable(x, hypotheticalY)){
                    player.down();
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
