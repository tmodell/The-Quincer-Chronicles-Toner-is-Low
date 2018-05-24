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
    
    String s;
    String name;
    
    public TextBox(MainFrame frame){
        super();
        this.frame = frame;
        setPreferredSize(new Dimension(1664 , 184));
        
        background = new ImageIcon("src/world/lib/textbox.png").getImage();
        //setBackground(Color.RED);
        
        //TextComponent textMenu = new TextComponent();
        //this.frame.add(textMenu);
    }
    
    public void handleInteractionKey (int key){
        if (currentText == null){
            return;
        }
        
        int optionCount = currentText.getOptionCount();
        switch (key){
            case KeyEvent.VK_SPACE:
                if (optionCount < 2){
                    String s = currentText.nextLine();
                    if (s == null) {
                        currentText = null;
                    }
                    displayText(s);
                }
                break;
            case KeyEvent.VK_Z:
                if (optionCount == 2){
                    String s = currentText.nextLine(1);
                    displayText(s);
                }
                break;
            case KeyEvent.VK_X:
                if (optionCount == 2){
                    String s = currentText.nextLine(2);
                    displayText(s);
                }
                break;
        }
    }
    
    public void startInteraction(AdvancableText text){
        currentText = text;
        displayText(currentText.nextLine());
        name  = currentText.getName();
        System.out.println("Name: " + name);
    }
    
    public boolean active(){
        return currentText != null;
    }
    
    public void displayText(String text){
        this.s = text;
        repaint();
    }
    
    @Override
    public void paintComponent(Graphics g){
        g.setColor(Color.WHITE);
        g.setFont(new Font("", Font.BOLD, 30));
        
        g.drawImage(background, 0, 0, null);
        if (s != null){
            g.drawString(name, 30, 50);
            g.setFont(new Font("", Font.PLAIN, 30));
            g.drawString(s, 100, 100);
            if (currentText.getOptionCount() > 1) {
                g.drawString("z: Yes    x: No", 100, 150);
            }
        } else if (currentText.getMenuText() != null && 
                currentText.getMenuText().getMenu() == true) {
            mt = currentText.getMenuText();
            
            Image bob = Toolkit.getDefaultToolkit().getImage(
                    "./src/npcinteraction.lib/cursor.png");
            
            String[] hold = new String[3];
            
            mt.setIteration(mt.getIteration());
        
            int dialogPos = 0;

            if (mt.getNext() == true) {
                mt.setSpeechPos(mt.getSpeechPos() + 1 <= 
                        hold.length - 1 ? 
                        mt.getSpeechPos() + 1 : 
                        mt.getSpeechPos());
            }

            String I = hold[mt.getSpeechPos()];
            if (I.contains("/'/")) {
                int L = 0;
                mt.setMaxPos(Character.getNumericValue(
                        I.charAt(0)));
                for (String J : I.split("/'/")) {
                    for (String K : J.split("_")) {
                        if (L % 2 != 1) {
                            if (L == 0) {
                                g.drawString(K.substring(1), 50, 360 
                                        + dialogPos * 25);
                                dialogPos++;
                                mt.setMenu(true);
                                L++;
                            } else {
                                g.drawString(K, 50, 360 + dialogPos * 25);
                                dialogPos++;
                                mt.setMenu(true);
                                L++;
                            }
                        } else {
                            mt.addToOutputs(K);
                            L++;
                        }
                    }
                }
            } else {
                g.drawString(I, 35, 360);
                mt.setSpeaking(true);
            }
            mt.setNext(false);

            if (mt.getMenu() == true) {
                g.drawImage(bob, 35, 350 + mt.getCursPos() * 25, 
                        this);
            }
        }
    }
}

