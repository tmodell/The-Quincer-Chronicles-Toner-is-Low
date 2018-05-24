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

    /*
    * ALEC LOOK HERE
    menu text will be initialized in a method in an advanceable text object
    just set it up so tht the textbox can recieve a menutext object and display it
    */
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
    
    public MenuText (String input) {
        try {
            reader(input, 0);
        } catch (IOException e) {
            
        }
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
    
}