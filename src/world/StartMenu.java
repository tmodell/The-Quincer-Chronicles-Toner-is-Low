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
                new Credits();
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
                
        start.setBounds(1370, 550, 420, 56);
        credits.setBounds(1513, 650, 277, 56);
        exit.setBounds(1604, 750, 186, 56);
        
        repaint();
    }
    
    
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        
        g.drawImage(image, 0, 0, null);
    }
    
    private class Credits extends JFrame{
        public Credits(){
            super("Credits");
            
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            
            Font f = new Font("Times New Roman", Font.BOLD, 20);
            
            JLabel tech = new JLabel("Technical Director - Alec Ewers");
            JLabel integration = new JLabel("Integration - Albert Wilcox");
            JLabel npc = new JLabel("NPC Interactions - Earl Ranario and John Geronimo");
            JLabel combat = new JLabel("Combat - Sam Vasquez and Dzuy Nguyen");
            JLabel world = new JLabel("World - Trever Rhodes and Jacob Burghgraef");
            JLabel sound = new JLabel("Sound - Jeevan Bhullar and Rajul Bains");
            
            JLabel art = new JLabel("Art Director - Cameron Scott");
            JLabel artTeam = new JLabel("Art Team - Matthew Hargitay and Weston Monroe");
            
            tech.setFont(f);
            integration.setFont(f);
            npc.setFont(f);
            combat.setFont(f);
            world.setFont(f);
            sound.setFont(f);
            art.setFont(f);
            artTeam.setFont(f);
            
            JPanel panel = new JPanel();
            panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
            
            panel.add(tech);
            panel.add(integration);
            panel.add(npc);
            panel.add(combat);
            panel.add(world);
            panel.add(sound);
            panel.add(Box.createVerticalStrut(20));
            panel.add(art);
            panel.add(artTeam);
            
            getContentPane().add(panel);
            setResizable(false);
            
            pack();
            setVisible(true);
        }
    }
}
