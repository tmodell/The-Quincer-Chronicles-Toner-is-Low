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

    }
    
    public void handleInteractionKey (int key){
        if (currentText == null && mt == null){
            return;
        }
        
        if (currentText.getMenuText() != null) {
            //the issue, according to the debugger, is that menutext objects 
            //aren't being instantiated to the textbox when called.
            mt = currentText.getMenuText();
            s = null;
            if (mt.getMenu() == true && (key == KeyEvent.VK_UP
                    || key == KeyEvent.VK_DOWN || key == KeyEvent.VK_ENTER ||
                    key == KeyEvent.VK_X)) {
                if (mt.getSpeaking() == true) {
                    if (key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_ENTER) {
                        mt.setNext(true);
                        mt.setSpeaking(false);
                        repaint();
                    }
                } else if (mt.getSpeaking() == false) {
                    if (key == KeyEvent.VK_UP) {
                        mt.setCursPos(-1);
                        repaint();
                    } else if (key == KeyEvent.VK_DOWN) {
                        mt.setCursPos(1);
                        repaint();
                    } else if (key == KeyEvent.VK_X) {
                        mt.setMenu(false);
                        repaint();
                    } else if (mt.getMenu() == true && 
                            key == KeyEvent.VK_ENTER) {
                        mt.setMenu(false);
                        System.out.println(mt.getOutputs(
                                mt.getCursPos()));
                        mt.setNext(true);
                        repaint();
                    }
                }
            }
        } else {
            int optionCount = currentText.getOptionCount();
            switch (key){
                case KeyEvent.VK_SPACE:
                    if (optionCount < 2){
                        s = currentText.nextLine();
                        if (s == null) {
                            currentText = null;
                        }
                        repaint();
                    }
                    break;
                case KeyEvent.VK_Z:
                    if (optionCount == 2){
                        s = currentText.nextLine(1);
                        repaint();
                    }
                    break;
                case KeyEvent.VK_X:
                    if (optionCount == 2){
                        s = currentText.nextLine(2);
                        repaint();
                    }
                    break;
            }
        }
    }
    
    public void setMenuText(MenuText mt){
        this.mt = mt;
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
    
    public void setText(String text) {
        this.s = text;
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
        } else if (currentText != null && mt != null && 
                mt.getMenu() == true) {
            System.out.println("I actually tried!");
            
            int vertPos = (int) Math.round(Toolkit.getDefaultToolkit()
                    .getScreenSize().getHeight());
            
            Image bob = Toolkit.getDefaultToolkit().getImage(
                    "./src/npcinteraction.lib/cursor.png");
        
            int dialogPos = 0;

            if (mt.getNext() == true) {
                mt.setSpeechPos(mt.getSpeechPos() + 1 <= 
                        mt.getParts().length - 1 ? 
                        mt.getSpeechPos() + 1 : 
                        mt.getSpeechPos());
            }

            String I = mt.getPartsOfParts(mt.getSpeechPos());
            if (I.contains("/'/")) {
                int L = 0;
                mt.setMaxPos(Character.getNumericValue(
                        I.charAt(0)));
                for (String J : I.split("/'/")) {
                    for (String K : J.split("_")) {
                        if (L % 2 != 1) {
                            if (L == 0) {
                                g.drawString(K.substring(1), 50, vertPos - 160 
                                        + dialogPos * 25);
                                dialogPos++;
                                mt.setMenu(true);
                                L++;
                            } else {
                                g.drawString(K, 50, vertPos - 160 
                                        + dialogPos * 25);
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
                g.drawString(I, 35, vertPos - 160);
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

