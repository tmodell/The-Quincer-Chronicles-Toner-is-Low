/*
 * This code was written by Alec Ewers, whether he's proud of it
 * or not. It is distributed freely so long as you include this
 * header and give what you think is appropriate credit.
 * This code comes with no warranty, implied or otherwise.
 */

package npcinteraction;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import javax.swing.JComponent;

/**
 *
 * @author alec.ewers
 */
public class MenuText {

    public MenuText (String input) {
        
    }
    
    //This section is the reader, which imports the content of the file and
    //passes it as separate lines out.
    public void reader (String target, int iteration) 
            throws IOException {
        ArrayList<String> parts = new ArrayList<>();
        if (iteration == 0) {
            String content = new String(Files.readAllBytes(Paths.get(target 
                    + ".txt")));
            for (String I: content.split("\n")) {
                parts.add(I);
            }
        }
         continent = parts.toArray(new String[parts.size()]);
    }
    
    //This section includes the control booleans and the data values that must
    //be kept consistent, plus all the get and set methods.
    public boolean getMenu() {
        return isMenu;
    }
    
    public void setMenu(boolean newMenu) {
        isMenu = newMenu;
    }
    
    public boolean getSpeaking() {
        return isSpeaking;
    }
    
    public void setSpeaking (boolean newTalk) {
        isSpeaking = newTalk;
    }
    
    public boolean getNext() {
        return isNext;
    }
    
    public void setNext (boolean newFollow) {
        isNext = newFollow;
    }
    
    public void setCursPos(int y) {
        if (cursPos + y != -1 && cursPos + y != maxPos) {
            cursPos += y;
        }
    }
    
    public int getCursPos() {
        return cursPos;
    }
    
    public void setMaxPos(int y) {
        maxPos = y;
    }
    
    public int getMaxPos() {
        return maxPos;
    }
    
    public String getOutputs(int index) {
        return outputs.get(index);
    }
    
    public void addToOutputs(String data) {
        outputs.add(data);
    }
    
    public void setSpeechPos(int y) {
        speechPos = y;
    }
    
    public int getSpeechPos() {
        return speechPos;
    }
    
    public void setIteration(int y) {
        iteration = y;
    }
    
    public int getIteration() {
        return iteration;
    }
    
    public void setTarget(String y) {
        target = y;
    }
    
    public String getTarget() {
        return target;
    }
    
    public void reset() {
        outputs.clear();
        target = "";
        isMenu = false;
        isNext = false;
        isSpeaking = false;
        cursPos = 0;
        maxPos = 0;
        speechPos = 0;
        iteration = 0;
    }
    
    private ArrayList<String> outputs = new ArrayList<>();
    private String [] continent;
    private String target;
    private boolean isMenu = false;
    private boolean isNext = false;
    private boolean isSpeaking = false;
    private int cursPos = 0;
    private int maxPos = 0;
    private int speechPos = 0;
    private int iteration = 0;
    
}

class TextComponent extends JComponent {
    
    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        //MenuText mt = //todo create currentmenu instance somewhere.
        
        Image rob = Toolkit.getDefaultToolkit().getImage("textBox.png");
        Image bob = Toolkit.getDefaultToolkit().getImage("cursor.png");

        g2.drawImage(rob, 0, 480-150, this);
        
        String[] hold = new String[3];
        
//        try {
//            //hold = MenuText.reader("menutest", MenuText.getIteration());
//        } catch (IOException e) {
//            
//        }
        
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