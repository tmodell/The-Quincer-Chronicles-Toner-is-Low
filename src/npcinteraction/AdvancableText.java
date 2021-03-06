/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package npcinteraction;

import java.io.*;
import java.util.Scanner;

import sprites.Player;
import world.SideBar;
import world.TextBox;

/**
 *
 * @author albert.wilcox
 */
public class AdvancableText {
    
    // Don't even try to figure out how this works lol
    TreeNode topNode, currentNode;
    Player player;
    String name;
    
    Menu menu;
    
    TextBox box;
    SideBar bar;
    
    //Test purposes only
//    public static void main(String[] args) throws IOException{
//        // This chunk populates a string with all the text from test.txt
//        //System.out.println("Hey.");
//       
//        BufferedReader inputStream = null;
//        String s = "";
//        try{
//            inputStream = new BufferedReader(new FileReader("src/npcinteraction/test.txt"));
//            String l;
//            while ((l = inputStream.readLine()) != null){
//                s = s + l + "\n";
//            }
//        } 
//        finally{
//            if (inputStream != null) inputStream.close();
//        }
//        
//        // This makes the tree.
//        AdvancableText text = new AdvancableText(s);
//        Scanner scan = new Scanner(System.in);
//        
//        System.out.println(s);
//        
//        String line;
//        int n = 1;
//        while ((line = text.nextLine(n)) != null){
//            System.out.println(line);
//            String input = scan.nextLine();
//            n = Integer.parseInt(input);
//        }
//    }
    
    public AdvancableText(String URL, String name) throws IOException{
        BufferedReader inputStream = null;
        String s = "";
        try{
            inputStream = new BufferedReader(new FileReader(URL));
            String l;
            while ((l = inputStream.readLine()) != null){
                s = s + l + "\n";
            }
        } catch(Exception e){}//NOT lazy i promise
        finally{
            if (inputStream != null) inputStream.close();
        }
        
        player = tonerislow.TonerIsLow.getMainFrame().getWorld().getPlayer();
        box = tonerislow.TonerIsLow.getMainFrame().getTextBox();
        bar = tonerislow.TonerIsLow.getMainFrame().getSideBar();
        
        this.name = name;
        
        topNode = new TreeNode(s, ">");
        currentNode = new TreeNode(topNode);
        
        menu = null;
    }
    
    public String getName(){
        return name;
    }
    
    public Menu getMenu(){
        return menu;
    }
    
    public String nextLine(int n){
        currentNode = currentNode.getChild(n);
        if (currentNode == null) {
            return null;
        } else {
            String s = currentNode.getMessage();
            s = checkHealthRestore(s);
            s = checkSave(s);
            s = checkMenu(s);
            return s;
        }
    }
    
    public String nextLine(){
        String s = nextLine(1);
        return s;
    }
    
    private String checkHealthRestore(String s){
        if (s.contains("~r")){
            s = s.replace("~r", "Health Restored.");
            player.restoreHealth();
            bar.update();
        }
        return s;
    }
    
    private String checkMenu(String s){
        if (s.contains ("~m")){
            s = s.replace("~m", "");
            try{ menu = new Menu(s, player, name);}catch(Exception e){}
            s = null;
        }
        
        return s;
    }
    
    private String checkSave(String s){
        if (s.contains("~s")){
            s = s.replace("~s", "Progress Saved.");
            tonerislow.TonerIsLow.getSave().save();
        }
        return s;
    }
    
    /**
     * This will be used to make a tree of values to hold an interaction.
     * For this constructor to interpret the file correctly the file must
     * be formatted exactly. The first line has no characters before it,
     * while all after it have a dash for each layer of conversation.
     * 
     * Here's an example:
     * 
     * Hello, I offer armor.
     * >Would you like some armor?
     * ->Ok, here is some armor.
     * ->Are you sure?
     * -->Suit yourself.
     * -->Ok, here is some armor.
     * 
     * In this interaction an NPC greets the player, then offers him armor.
     * If the player accepts the NPC says "Ok, here is some armor." If he
     * rejects it the NPC asks if he is sure, then responds accordingly.
     */
    private class TreeNode{
        
        TreeNode child1, child2;
        
        String message;
        
        /**
         * Recursive constructor that will hopefully read a file into a tree
         * @param data The entire string to read into a tree
         * @param key The key for splitting. If you're calling this constructor just use ">"
         */
        public TreeNode(String data, String key){
            String[] split = data.split("\n" + key);
            String key2 = "-" + key;
            message = split[0];
            switch (split.length){
                case 1:
                    child1 = null;
                    child2 = null;
                    break;
                case 2:
                    child2 = null;
                    child1 = new TreeNode(split[1], key2);
                    break;
                case 3:
                    child1 = new TreeNode(split[1], key2);
                    child2 = new TreeNode(split[2], key2);
                    break;
                
            }
        }
        
        public TreeNode(TreeNode node){
            child1 = node;
            child2 = null;
        }
        
        public TreeNode getChild(int num){
            switch (num){
                case 1:
                    return child1;
                case 2:
                    return child2;
                default:
                    return null;
            }
        }
        
        public String getMessage(){
            return message;
        }
    }
    
    public int getOptionCount(){
            int count = 0;
            if (currentNode.getChild(1) != null) count += 1;
            if (currentNode.getChild(2) != null) count += 1;
            return count;
        }
    
    private MenuText mt;
    private boolean isMenu = false;
    
    public void setMenuText(MenuText menu) {
        mt = menu;
    }
    
    public MenuText getMenuText() {
        return mt;
    }
    
    public void setIsMenu(boolean set) {
        isMenu = set;
    }
    
    public boolean getIsMenu() {
        return isMenu;
    }
}
