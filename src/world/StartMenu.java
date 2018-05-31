/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package world;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.*;

/**
 *  
 * @author albert.wilcox
 */
public class StartMenu extends JPanel{
    JLabel start, credits, exit;
    
    JFrame frame;
    
    Image image = null;
    
    //World world;
    MainFrame mainFrame;
    public StartMenu(MainFrame frame){
        this.frame = frame;
        
        this.image = new ImageIcon("lib/world/start.png").getImage();
        
        start = new JLabel(new ImageIcon("lib/world/buttons/start.png"));
        credits = new JLabel(new ImageIcon("lib/world/buttons/credits.png"));
        exit = new JLabel(new ImageIcon("lib/world/buttons/exit.png"));
        
        start.setFont(new Font(MainFrame.FONT_NAME, Font.PLAIN, 72));
        credits.setFont(new Font(MainFrame.FONT_NAME, Font.PLAIN, 72));
        exit.setFont(new Font(MainFrame.FONT_NAME, Font.PLAIN, 72));
        
        start.setCursor(new Cursor(Cursor.HAND_CURSOR));
        start.addMouseListener(new MouseListener(){
            Icon inactive = start.getIcon(), active = new ImageIcon("lib/world/buttons/startselected.png");
            
            @Override
            public void mouseClicked(MouseEvent e) {
                frame.displayGame();
            }

            @Override
            public void mousePressed(MouseEvent e) {}

            @Override
            public void mouseReleased(MouseEvent e) {}

            @Override
            public void mouseEntered(MouseEvent e) {
                start.setIcon(active);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                start.setIcon(inactive);
            }
            
        });
        
        credits.setCursor(new Cursor(Cursor.HAND_CURSOR));
        credits.addMouseListener(new MouseListener(){
            Icon inactive = credits.getIcon(), active = new ImageIcon("lib/world/buttons/creditsselected.png");
            
            @Override
            public void mouseClicked(MouseEvent e) {
                frame.dispose();
                System.exit(0);
            }

            @Override
            public void mousePressed(MouseEvent e) {}

            @Override
            public void mouseReleased(MouseEvent e) {}

            @Override
            public void mouseEntered(MouseEvent e) {
                credits.setIcon(active);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                credits.setIcon(inactive);
            }
            
        });
        
        exit.setCursor(new Cursor(Cursor.HAND_CURSOR));
        exit.addMouseListener(new MouseListener(){
            Icon inactive = exit.getIcon(), active = new ImageIcon("lib/world/buttons/exitselected.png");
            
            @Override
            public void mouseClicked(MouseEvent e) {
                frame.dispose();
                System.exit(0);
            }

            @Override
            public void mousePressed(MouseEvent e) {}

            @Override
            public void mouseReleased(MouseEvent e) {}

            @Override
            public void mouseEntered(MouseEvent e) {
                exit.setIcon(active);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                exit.setIcon(inactive);
            }
            
        });
        
        setLayout(null);
        
        add(start);
        add(credits);
        add(exit);
                
        start.setBounds(1370, 500, 420, 56);
        credits.setBounds(1513, 600, 277, 56);
        exit.setBounds(1604, 700, 186, 56);
        
        repaint();
    }
    
    
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        
        g.drawImage(image, 0, 0, null);
    }
}
