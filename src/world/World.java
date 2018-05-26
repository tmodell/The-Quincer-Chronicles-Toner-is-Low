/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package world;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import sprites.*;
import npcinteraction.*;
import sound.SoundFXController;
import tonerislow.TonerIsLow;

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
    String map;
    
    int shamenNum;
    boolean needsSort = false;
    int wormerRefreshCount = 0;
    
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
        
        Timer t = new Timer(20, new AbstractAction(){
            
            int count = 0;
            
            @Override
            public void actionPerformed(ActionEvent e) {
                repaint();
                if (count++ > 30){
                    count = 0;
                    refreshWormers();
                }
            }
            
        });
        t.start();
        
        // the following loop is for testing purposes only
//        for(int i = 0; i < 500; i++){
//            add(new JLabel("Press any key to close "));
//        }
    }
    
    public void loadMap(String name, int playerX, int playerY) throws IOException{
        System.out.println(imageFileExists("statue"));
        
        repainting = false;

        sprites.clear();
        sprites.add(player);
        
        paths = new Path[WIDTH][HEIGHT];
        NPCs = new NPC[WIDTH][HEIGHT];
        wormers = new Wormer[WIDTH][HEIGHT];

        player.setPosition(playerX, playerY);
        
        String url = PREFIX + name + SUFFIX;
        map = name;
        
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
        
        // Find out whether the map is infested by wormers
        String[] line1Split = lines[0].split(",");
        shamenNum = Integer.parseInt(line1Split[0]);
        boolean infested = TonerIsLow.getSave().isShamenAlive(shamenNum);
        
        // Figure out which tile to paint on
        String tileName = line1Split[1];
        if (tileFileExists(tileName + "snow") && infested) {
           // System.out.println(tileName + "snow exists");
            tileName = tileName + "snow";
        }
        
        //System.out.println(tileName);
        ImageIcon ii = new ImageIcon("src/sprites/lib/tiles/" + tileName + ".png");
        tile = ii.getImage();
        
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
                            c = ' ';
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
                            //System.out.println("Drawing NPC");
                            // Does not spawn an NPC if the map is infested
                            if (infested){
                                sprites.add(new Stationary("statue", x , y - 1));
                                c = 'O';
                                break;
                            }
                            c = 'N';
                            char symbool = escapeSplit[1].charAt(0);
                            System.out.println(symbool);
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
                        case 'W':
                            // TODO code to parse and add a wormer shamen
                            break;
                        case 'D':
                            //TODO code to spawn a dark worm
                            break;
                    }
                }
                else if (c != ' '){
                    // This basically makes snowy textures snowy if needed
                    String imageName = symbolMap.get(c);
                    if (infested && imageFileExists(imageName + "snow")){
                       // System.out.println(imageName + "snow exists");
                        imageName = imageName + "snow";
                    } //System.out.println(imageName);
                    sprites.add(new Stationary(imageName, x , y - 1));
                }
                squares[x][y - 1] = c;
            }
        } 
        if (infested) spawnWormers();
        
        // Hardcode wormer
        Wormer w = new Wormer(this, 100, 10, 20, 5);
        sprites.add(w);
        wormers[20][5] = w;
        
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
        symbolMap.put('H', "stonehouse");
        symbolMap.put('.', null);
        symbolMap.put('A', "alchemist");
        symbolMap.put('C', "cart");
        symbolMap.put('Y', "hysperia");
        symbolMap.put('L', "villageleader");
        symbolMap.put('V', "villager");
        symbolMap.put('B', "blacksmith");
        symbolMap.put('W', "lledo");
        symbolMap.put('O', "statue");
        symbolMap.put('w', "wormercorpse");
        symbolMap.put('h', "thatchedhut");
        symbolMap.put('*', "black");
        symbolMap.put('f', "fire");
        symbolMap.put('b', "stonebasilica");
    }
    
    /**
     * This method is called when the player attacks
     * @param x
     * @param y
     * @param damage
     */
    public void PlayerAttack(int x, int y, int damage){
        Wormer w = wormers[x][y];
        if (w== null) System.out.println("Kys");
        else w.receiveStrike(damage);
    }
    
    /**
     * This method is called when a wormer attacks
     * @param x 
     * @param y
     * @param damage 
     */
    public void WormerAttack(int x, int y, int damage){
        player.receiveStrike(damage);
    }
    
    public void killWormer(Wormer wormer, int x, int y){
        sprites.remove(wormer);
        sprites.add(new Stationary(symbolMap.get('w'), x, y));
        wormers[x][y] = null;
    }
    
    // TODO complete method to spawn wormers
    public void spawnWormers(){
        int emptyCount = 0;
        System.out.println("Spawning wormers.");
        // counts how many empty squares are in the map
        for (char[] c: squares){
            for (char d: c){
                if (d == ' ') emptyCount ++;
            }
        }
        
        int wormerCount = emptyCount / 50;
        
        Random r = new Random();
        int count = 0;
        while (count < wormerCount){
            count++;
            int x = r.nextInt(WIDTH);
            int y = r.nextInt(HEIGHT);
            if (isOccupiable(x, y)){
                Wormer w = new Wormer(this, 100, 10, x, y);
                sprites.add(w);
                wormers[x][y] = w;
            } else count--;
        }
    }
    
    public void refreshWormers(){
        // Iterate through each wormer
        for (Wormer[] list: wormers){
            for (Wormer wormer: list){
                if (wormer != null) wormer.refresh();
            }
        }
    }
    
    public void moveWormer(Wormer wormer, int oldX, int oldY, int newX, int newY){
        wormers[oldX][oldY] = null;
        wormers[newX][newY] = wormer;
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
    
    public boolean wormerAtPos(int x, int y){
        return (wormers[x][y] != null);
    }
    
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        
        //if (wormerRefreshCount++ > 10){
            //wormerRefreshCount = 0;
            //refreshWormers();
        //}
        
        long time = System.currentTimeMillis();
        //System.out.println("Painting");
        for (int x = 0; x < WIDTH; x++){
            //System.out.println(x);
            for (int y = 0; y < HEIGHT; y++){
                g.drawImage(tile, x * GRID_SIZE, y * GRID_SIZE, null);
            }
        }
        
        if (needsSort) sortSprites();
        for (Sprite x: sprites){
            //System.out.println(x.getRealY());
            g.drawImage(x.getImage(), x.getRealX() + x.getOffsetX(), x.getRealY() + x.getOffsetY(), null);
        }
        
//        int delay = (int)(System.currentTimeMillis() - time) + 20;
//        try{
//            Thread.sleep(delay);
//        } catch (Exception e){}
//        repaint();
    }
    
    private void sortSprites(){
        for (int i = 1; i < sprites.size(); i++){
            for (int j = i; j > 0 && sprites.get(j - 1).getY() > sprites.get(j).getY(); j--){
                Sprite temp = sprites.get(j);
                sprites.set(j, sprites.get(j - 1));
                sprites.set(j - 1, temp);
            }
        }
    }
    
    private boolean imageFileExists(String s){
        s = "src/sprites/lib/images/" + s + ".png";
        File tmpDir = new File(s);
        return tmpDir.exists();
    }
    
    private boolean tileFileExists(String s){
        s = "src/sprites/lib/tiles/" + s + ".png";
        File tmpDir = new File(s);
        return tmpDir.exists();
    }
    
    /**
     * This method will be called when the player presses an arrow key.
     * Its job is to check whether the player can move where he/she wants
     * to and if they can the sprite is notified and display updated.
     * @param key 
     */
    public void handleMovementKey(int key) {
        int hypotheticalX, hypotheticalY, x = player.getX(), y = player.getY();
        needsSort = true;
        switch (key){
            case KeyEvent.VK_LEFT:
                hypotheticalX = x - 1;
                // check whether the square is occupiable
                if (isOccupiable(hypotheticalX, y)){
                    player.left();
                    x = hypotheticalX;
                } else {
                   player.setOrientation(Movable.ORIENTATION_LEFT);
                }
                break;
            case KeyEvent.VK_RIGHT:
                hypotheticalX = x + 1;
                // check whether the square is occupiable
                if (isOccupiable(hypotheticalX, y)){
                    player.right();
                    x = hypotheticalX;
                } else {
                   player.setOrientation(Movable.ORIENTATION_RIGHT);
                }
                break;
            case KeyEvent.VK_UP:
                hypotheticalY = y - 1;
                // check whether the square is occupiable
                if (isOccupiable(x, hypotheticalY)){
                    player.up();
                    y = hypotheticalY;
                } else {
                   player.setOrientation(Movable.ORIENTATION_UP);
                }
                break;
            case KeyEvent.VK_DOWN:
                hypotheticalY = y + 1;
                // check whether the square is occupiable
                if (isOccupiable(x, hypotheticalY)){
                    player.down();
                    y = hypotheticalY;
                } else {
                   player.setOrientation(Movable.ORIENTATION_DOWN);
                }
                break;
        }
        Path p;
        if ((p = paths[x][y]) != null){
            try{
                loadMap(p.destName, p.destX, p.destY);
            } catch (Exception e){}
        }
    }
    
    public void handleCombatKey(int key){
        int hypotheticalX, hypotheticalY, x = player.getX(), y = player.getY();
        needsSort = true;
        try { //Plays attack sound
            SoundFXController.swordFX();
        } catch (UnsupportedAudioFileException | LineUnavailableException | IOException | InterruptedException ex) {
            Logger.getLogger(World.class.getName()).log(Level.SEVERE, null, ex);
        }
        switch (key){
            case KeyEvent.VK_A:
                player.setOrientation(player.ORIENTATION_LEFT);
                hypotheticalX = x - 1;
                if (wormerAtPos(hypotheticalX, y)){
                    player.attack();
                } else if (wormerAtPos(x+1, y)) {
                    //some way to block attacks
                } else {
                   // maybe add code for what to do if a square is unoccupiable 
                }
                break;
            case KeyEvent.VK_D:
                player.setOrientation(player.ORIENTATION_RIGHT);
                hypotheticalX = x + 1;
                if (wormerAtPos(hypotheticalX, y)){
                    player.attack();
                } else if (wormerAtPos(x-1, y)) {
                    //some way to block attacks
                } else {
                   // maybe add code for what to do if a square is unoccupiable 
                }
                break;
            case KeyEvent.VK_W:
                player.setOrientation(player.ORIENTATION_UP);
                hypotheticalY = y - 1;
                if (wormerAtPos(x, hypotheticalY)){
                    player.attack();
                } else if (wormerAtPos(x, y+1)) {
                    //some way to block attacks
                } else {
                   // maybe add code for what to do if a square is unoccupiable 
                }
                break;
            case KeyEvent.VK_S:
                player.setOrientation(player.ORIENTATION_DOWN);
                hypotheticalY = y + 1;
                if (wormerAtPos(x, hypotheticalY)){
                    player.attack();
                } else if (wormerAtPos(x, y-1)) {
                    //some way to block attacks
                } else {
                   // maybe add code for what to do if a square is unoccupiable 
                }
                break;
        }
    }
    
    public Player getPlayer(){
        return player;
    }
    
    public MainFrame getFrame(){
        return frame;
    }
    
    public String getMap(){
        return map;
    }
    
    private class Path{
        //private static final String PREFIX = "src/world/lib/maps/";
        
        int destX, destY;
        String destName;
        
        public Path(int destX, int destY, String locName){
            this.destX = destX;
            this.destY = destY;
            this.destName = locName;
        }        
        
        public String getDestName(){
            return destName;
        }
    }
}
