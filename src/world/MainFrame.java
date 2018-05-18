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

/**
 * This is the main class and is the one that houses the world
 * @author alber
 */
public class MainFrame extends JFrame{
    
    World world;
    
    public MainFrame(){
        super();
        
        world = new World(this);
        //TODO create objects for toolbar, dialogue box and stat bar
        
        init();
    }
    
    public void handleKeyPush(KeyEvent ke){
        int key = ke.getKeyCode();
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
        
        //interaction
        if (key == KeyEvent.VK_SPACE || key == KeyEvent.VK_Z
            || key == KeyEvent.VK_X){
            //TODO handle interactions
        }
    }
    
    public void init(){
        //world = new World();
        world.setFocusable(true);
        world.addKeyListener(new Listener(this));
        getContentPane().add(world);
        
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setUndecorated(true);
        setResizable(false);
        //setFocusable(true);
        //getContentPane().setBackground(new Color(204, 255, 0));
        setVisible(true);
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
    /**
     * If you can't figure out this method's function
     * you probably should have done world religions
     */
    public void updateWorld(){
        world.update();
    }
    
    public World getWorld(){
        return world;
    }
}
