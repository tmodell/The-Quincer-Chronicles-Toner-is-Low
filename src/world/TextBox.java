/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package world;

import java.awt.event.KeyEvent;
import javax.swing.JPanel;

import npcinteraction.*;

/**
 *
 * @author albert.wilcox
 */
public class TextBox extends JPanel{
    MainFrame frame;
    
    AdvancableText currentText = null;
    
    public TextBox(MainFrame frame){
        super();
        this.frame = frame;
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
    
    public boolean active(){
        return currentText != null;
    }
    
    public void displayText(String text){
        //TODO add code to display text
    }
}
