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
    
    World world;
    TextBox box;
    SideBar bar;
    
    Save save;
    
    static final boolean DEVELOPER_DEBUG = true;
    
    public MainFrame(Save save){
        super();
        
        this.save  = save;
        
        mainPanel = new JPanel();
        world = new World(this);
        box = new TextBox(this);
        bar = new SideBar(this);
        
        init();
    }
    
    public void init(){
        try {
            MusicController.trackOne();
        } catch (UnsupportedAudioFileException | LineUnavailableException | IOException | InterruptedException ex) {
            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        world.setFocusable(true);
        world.addKeyListener(new Listener(this));

        box.setFocusable(false);
        bar.setFocusable(false);
                
        mainPanel.setLayout(null);
        
        mainPanel.add(world);
        mainPanel.add(box);
        mainPanel.add(bar);
        
        world.setBounds(256,0,1664,896);
        box.setBounds(256,896,1664,184);
        bar.setBounds(0,0,256,1080);
        
        getContentPane().add(mainPanel);
        
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
            world.loadMap("test", 11, 11);
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
    
    public void handleKeyPush(KeyEvent ke){
        int key = ke.getKeyCode();
        
        // Checks whether the user is currently in an NPC interaction
        if (!box.active()){
            //Movement
            if (key == KeyEvent.VK_W || key == KeyEvent.VK_A
                    || key == KeyEvent.VK_S || key == KeyEvent.VK_D){
                world.handleMovementKey(key);
            }

            //Combat
            if (key == KeyEvent.VK_LEFT || key == KeyEvent.VK_RIGHT 
                    || key == KeyEvent.VK_UP || key == KeyEvent.VK_DOWN){
                //TODO handling combat
            }

            // Beginning an interaction
            if (key == KeyEvent.VK_SPACE){
                world.playerInteraction();
            }
        // what to do if the user is in an npc interaction.
        }else{
            //interaction
            box.handleInteractionKey(key);
//            if (MenuText.getMenu() == true && (key == KeyEvent.VK_UP
//                    || key == KeyEvent.VK_DOWN || key == KeyEvent.VK_ENTER ||
//                    key == KeyEvent.VK_X)) {
//                if (MenuText.getSpeaking() == true) {
//                    if (key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_ENTER) {
//                        MenuText.setNext(true);
//                        MenuText.setSpeaking(false);
//                        box.repaint();
//                    }
//                } else if (MenuText.getSpeaking() == false) {
//                    if (key == KeyEvent.VK_UP) {
//                        MenuText.setCursPos(-1);
//                        box.repaint();
//                    } else if (key == KeyEvent.VK_DOWN) {
//                        MenuText.setCursPos(1);
//                        box.repaint();
//                    } else if (key == KeyEvent.VK_X) {
//                        MenuText.setMenu(false);
//                        box.repaint();
//                    } else if (MenuText.getMenu() == true && 
//                            key == KeyEvent.VK_ENTER) {
//                        MenuText.setMenu(false);
//                        System.out.println(MenuText.getOutputs(
//                                MenuText.getCursPos()));
//                        MenuText.setNext(true);
//                        box.repaint();
//                    }
//                }
//            } else if (MenuText.getMenu() == false && (key == KeyEvent.VK_SPACE
//                    || key == KeyEvent.VK_Z || key == KeyEvent.VK_X ||
//                    key == KeyEvent.VK_C)){
//                //TODO handle interactions
//            }
        }
        
    }
    
    public World getWorld(){
        return world;
    }
    
    public Save getSave(){
        return save;
    }
}
