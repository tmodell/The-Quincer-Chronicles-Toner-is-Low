/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package world;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import javax.swing.JPanel;
import javax.swing.ImageIcon;

import npcinteraction.*;

/**
 *
 * @author albert.wilcox
 */
public class TextBox extends JPanel{
    MainFrame frame;
    
    AdvancableText currentText = null;
    Menu menu = null;
    MenuText mt;
    
    Image background;
    Image cursor;
    
    String s;
    String[] ss;
    String name;
        
    public TextBox(MainFrame frame){
        super();
        this.frame = frame;
        setPreferredSize(new Dimension(1664 , 184));
        
        cursor = new ImageIcon("src/npcinteraction/lib/cursor.png").getImage();
        
        background = new ImageIcon("src/world/lib/textbox.png").getImage();
        //setBackground(Color.RED);

    }
    
    public void handleInteractionKey (int key){
        if (currentText == null && menu == null){
            return;
        }
        int optionCount = 0;
        if (currentText != null)  optionCount = currentText.getOptionCount();
        switch (key){
        case KeyEvent.VK_SPACE:
            if (optionCount < 2){
                s = currentText.nextLine();
            }
            
            if (s == null) {
                menu = currentText.getMenu();
                if (menu != null){
                    ss = menu.getOptions();
                }
                currentText = null;
            }
            break;
        case KeyEvent.VK_Z:
            if (optionCount > 2){
                s = currentText.nextLine(1);
            } else{
                s = currentText.nextLine();
            }
            
            if (s == null) {
                System.out.println("NULL B");
                menu = currentText.getMenu();
                if (menu != null){
                    System.out.println("menu isn't null");
                    ss = menu.getOptions();
                    for (String s: ss){
                        System.out.println(s);
                    }
                }
                currentText = null;
            }
            break;
        case KeyEvent.VK_X:
            if (optionCount == 2){
                s = currentText.nextLine(2);
            } else {
                s = currentText.nextLine(1);
            }
            
            if (s == null) {
                menu = currentText.getMenu();
                if (menu != null){
                    ss = menu.getOptions();
                }
                currentText = null;
            }
            break;
        case KeyEvent.VK_LEFT:
            if (menu == null) break;
            menu.left();
            break;
        case KeyEvent.VK_RIGHT:
            if (menu == null) break;
            menu.right();
            break;
        case KeyEvent.VK_ENTER:
            if (menu == null) break;
            boolean quit = menu.press();
            if (quit){
                menu = null;
                ss = null;
            }
            break;
        }
        
        
        repaint();
    }
   
    public void startInteraction(AdvancableText text){
        currentText = text;
        displayText(currentText.nextLine());
        name  = currentText.getName();
        System.out.println("Name: " + name);
    }
    
    public boolean active(){
        return currentText != null || menu != null;
    }
    
    public void displayText(String text){
        this.s = text;
        repaint();
    }
    
    public void setText(String text) {
        this.s = text;
    }
    
    @Override
    public void paintComponent(Graphics g){
        g.drawImage(background, 0, 0, null);
        
        if (currentText == null && menu == null){
            return;
        }
        
        g.setColor(Color.WHITE);
        g.setFont(new Font("", Font.BOLD, 30));
        
        /*
        Alec, I'm implementing my menu because i figured we needed NPC interactions as we start to set up the story
        If you'd like to work on yours I've copied this class as "TextBoxCopy"
        */
        
        if (s != null){
            g.drawString(name, 30, 50);
            g.setFont(new Font("", Font.PLAIN, 30));
            g.drawString(s, 100, 100);
            if (currentText.getOptionCount() > 1) {
                g.drawString("z: Yes    x: No", 100, 150);
            }
        }
        if (ss != null){
            //System.out.println("kill");
            g.drawString(name, 30, 50);
            g.setFont(new Font("", Font.PLAIN, 30));
            int x = 100;
            for (String str: ss){
                //System.out.println("leng " + ss.length);
                g.drawString(str, x, 100);
                x += 500;
            }
            int cursorX = (menu.getCursorPos() * 500) + 70;
            g.drawImage(cursor, cursorX, 85, null);
            g.drawString("Enter: select", 100, 150);
        }
    }
}

