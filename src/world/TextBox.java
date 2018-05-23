/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package world;

import java.awt.event.KeyEvent;
import javax.swing.JPanel;
import javax.swing.JComponent;
import java.awt.*;
import javax.swing.ImageIcon;
import java.io.IOException; 

import npcinteraction.*;

/**
 *
 * @author albert.wilcox
 */
public class TextBox extends JPanel{
    MainFrame frame;
    
    AdvancableText currentText = null;
    
    Image background;
    
    public TextBox(MainFrame frame){
        super();
        this.frame = frame;
        setPreferredSize(new Dimension(1664 , 184));
        
        background = new ImageIcon("src/world/lib/textbox.png").getImage();
        //setBackground(Color.RED);
        
        TextComponent textMenu = new TextComponent();
        this.frame.add(textMenu);
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
                    } else{
                        displayText(s);
                    }
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
    }
    
    public boolean active(){
        return currentText != null;
    }
    
    public void displayText(String text){
        //TODO add code to display text
    }
    
    @Override
    public void paintComponent(Graphics g){
        g.drawImage(background, 0, 0, null);
    }
}

class TextComponent extends JComponent {
    
    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        
        Image rob = Toolkit.getDefaultToolkit().getImage("textBox.png");
        Image bob = Toolkit.getDefaultToolkit().getImage("cursor.png");

        g2.drawImage(rob, 0, 480-150, this);
        
        String[] hold = new String[3];
        
        try {
            hold = MenuText.reader("menutest", MenuText.getIteration());
        } catch (IOException e) {
            
        }
        
        MenuText.setIteration(MenuText.getIteration());
        
        int dialogPos = 0;
        
        if (MenuText.getNext() == true) {
            MenuText.setSpeechPos(MenuText.getSpeechPos() + 1 <= 
                    hold.length - 1 ? 
                    MenuText.getSpeechPos() + 1 : 
                    MenuText.getSpeechPos());
        }
        
        String I = hold[MenuText.getSpeechPos()];
        if (I.contains("/'/")) {
            int L = 0;
            MenuText.setMaxPos(Character.getNumericValue(
                    I.charAt(0)));
            for (String J : I.split("/'/")) {
                for (String K : J.split("_")) {
                    if (L % 2 != 1) {
                        if (L == 0) {
                            g2.drawString(K.substring(1), 50, 360 
                                    + dialogPos * 25);
                            dialogPos++;
                            MenuText.setMenu(true);
                            L++;
                        } else {
                            g2.drawString(K, 50, 360 + dialogPos * 25);
                            dialogPos++;
                            MenuText.setMenu(true);
                            L++;
                        }
                    } else {
                        MenuText.addToOutputs(K);
                        L++;
                    }
                }
            }
        } else {
            g2.drawString(I, 35, 360);
            MenuText.setSpeaking(true);
        }
        MenuText.setNext(false);
        
        if (MenuText.getMenu() == true) {
            g2.drawImage(bob, 35, 350 + MenuText.getCursPos() * 25, 
                    this);
        }
    }
}