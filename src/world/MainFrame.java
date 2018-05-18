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
public class MainFrame extends JFrame implements KeyListener{
    
    World world;
    
    public MainFrame(){
        super();
        
        world = new World();
        //TODO create objects for toolbar, dialogue box and stat bar
        
        init();
    }

    public void init(){
        //world = new World();
        world.setFocusable(true);
        world.addKeyListener(this);
        getContentPane().add(world);
        
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setUndecorated(true);
        setResizable(false);
        //setFocusable(true);
        //getContentPane().setBackground(new Color(204, 255, 0));
        setVisible(true);
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        System.out.println("Hey");
        dispose();
    }

    @Override
    public void keyReleased(KeyEvent e) {}
}
