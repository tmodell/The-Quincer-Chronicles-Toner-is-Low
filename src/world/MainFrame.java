/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package world;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import npcinteraction.MenuText;
import sound.MusicController;
import tonerislow.Save;

/**
 * This is the main class and is the one that houses the world
 * @author alber
 */
public class MainFrame extends JFrame{
    
    JPanel mainPanel;
    StartMenu start;
    
    World world;
    TextBox box;
    SideBar bar;
    
    Save save;
    
    static final String FONT_NAME = "Old English Text MT";
    
    static final boolean DEVELOPER_DEBUG = false;
    
    public MainFrame(Save save){
        super();
        
        this.save  = save;
        
        start = new StartMenu(this);
        
        mainPanel = new JPanel();
        world = new World(this);
        box = new TextBox(this);
        bar = new SideBar(this);
        
        init();
    }
    
    public void init(){
        try {
            MusicController.changeMusic(1);
        } catch (UnsupportedAudioFileException | LineUnavailableException | IOException | InterruptedException ex) {
            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
        
//        try {
//            // Set System L&F
//            UIManager.setLookAndFeel(
//            UIManager.getSystemLookAndFeelClassName());
//	} catch (Exception e){} 
        
        
        
        setFocusable(true);
        addKeyListener(new Listener(this));

        box.setFocusable(false);
        bar.setFocusable(false);
        world.setFocusable(false);
                
        mainPanel.setLayout(null);
        
        mainPanel.add(world);
        mainPanel.add(box);
        mainPanel.add(bar);
        
        world.setBounds(256,0,1664,896);
        box.setBounds(256,896,1664,184);
        bar.setBounds(0,0,256,1080);
        
        getContentPane().add(start);
        
//        getContentPane().add(world, BorderLayout.CENTER);
//        getContentPane().add(box, BorderLayout.SOUTH);
//        getContentPane().add(bar, BorderLayout.WEST);
        
//        GroupLayout g = new GroupLayout(mainPanel);
//        g.setHorizontalGroup(g.createSequentialGroup()
//            .addComponent(bar)
//            .addGroup(g.createParallelGroup()
//                .addComponent(world)
//                .addComponent(box)));
//        g.setVerticalGroup(g.createParallelGroup()
//            .addComponent(bar)
//            .addGroup(g.createSequentialGroup()
//                .addComponent(world)
//                .addComponent(box)));
//        mainPanel.setLayout(g);
//        getContentPane().add(mainPanel);
        
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        try{
            world.loadMap(save.getPlayerRoom(), save.getPlayerX(), save.getPlayerY());
        } catch (Exception e){}
        
        if (DEVELOPER_DEBUG){
            setUndecorated(false);
            setResizable(true);
        } else {
            setUndecorated(true);
            setResizable(false);
        }
        //setFocusable(true);
        //getContentPane().setBackground(new Color(204, 255, 0));
        setVisible(true);
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
    public void displayGame(){
        try { //Stop current music(1), stop loop, and play track Three (village)
            MusicController.stopAudio();
            MusicController.changeMusic(2);
        } catch (UnsupportedAudioFileException | LineUnavailableException | IOException | InterruptedException | NullPointerException ex) {
            //ex.printStackTrace();
            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        getContentPane().removeAll();
        getContentPane().add(mainPanel);
        
        world.setFocusable(true);
        
        mainPanel.setVisible(false);
        mainPanel.setVisible(true);
    }
    
    public void handleKeyPush(KeyEvent ke){
        int key = ke.getKeyCode();
        
        if (world.playerDead()){
            world.handlePlayerRevive();
            return;
        }
        
        // Checks whether the user is currently in an NPC interaction
        if (!box.active()){
            //Movement
            if (key == KeyEvent.VK_UP || key == KeyEvent.VK_LEFT
                    || key == KeyEvent.VK_DOWN || key == KeyEvent.VK_RIGHT){
                world.handleMovementKey(key);
            }

            //Combat
            if (key == KeyEvent.VK_W || key == KeyEvent.VK_A 
                    || key == KeyEvent.VK_S || key == KeyEvent.VK_D){
                world.handleCombatKey(key);
            }

            // Beginning an interaction
            if (key == KeyEvent.VK_SPACE){
                world.playerInteraction();
                world.handleCombatKey(key);
            }
            
            if (key == KeyEvent.VK_Q){
                world.getPlayer().usePotion();
            }
        // what to do if the user is in an npc interaction.
        }else{
            //interaction
            box.handleInteractionKey(key);
        }
        
    }
    
    public World getWorld(){
        return world;
    }
    
    public TextBox getTextBox(){
        return box;
    }
    
    public SideBar getSideBar(){
        return bar;
    }
    
    public Save getSave(){
        return save;
    }
}
