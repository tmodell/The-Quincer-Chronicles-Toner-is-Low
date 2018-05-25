/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package world;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

/**
 *  
 * @author albert.wilcox
 */
public class StartMenu extends JPanel{
    JButton start, exit, settings;
    JLabel header;
    
    //World world;
    MainFrame mainFrame;
    public StartMenu(MainFrame frame){
        setPreferredSize(new Dimension(1920, 1080));
        start = new JButton("Start Game");
        exit = new JButton("Exit Game");
        settings  = new JButton("Settings");
        header = new JLabel("Quincer Chronicles: Toner is Low");
        header.setFont(new Font("", Font.BOLD, 30));
        
        this.mainFrame = frame;
        
        start.addActionListener(new ActionListener(){
           
            @Override
            public void actionPerformed(ActionEvent e){
                mainFrame.displayGame();
            }
        });
        exit.addActionListener(new ActionListener(){
           
            @Override
            public void actionPerformed(ActionEvent e){
                System.exit(0);
            }
        });
        settings.addActionListener(new ActionListener(){
           
            @Override
            public void actionPerformed(ActionEvent e){
                try{
                    Thread.sleep(5000);
                }catch (InterruptedException ie){}
                System.exit(0);
            }
        });
        
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        
        add(header);
        add(start);
        add(settings);
        add(exit);
    }
}
