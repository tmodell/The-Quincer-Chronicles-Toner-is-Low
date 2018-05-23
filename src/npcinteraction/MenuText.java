/*
 * This code was written by Alec Ewers, whether he's proud of it
 * or not. It is distributed freely so long as you include this
 * header and give what you think is appropriate credit.
 * This code comes with no warranty, implied or otherwise.
 */

package npcinteraction;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

/**
 *
 * @author alec.ewers
 */
public class MenuText {

    public MenuText (String input) {
        
    }
    
    //This section is the reader, which imports the content of the file and
    //passes it as separate lines out.
    public static void reader (String target, int iteration) 
            throws IOException {
        ArrayList<String> parts = new ArrayList<>();
        if (iteration == 0) {
            String content = new String(Files.readAllBytes(Paths.get(target 
                    + ".txt")));
            for (String I: content.split("\n")) {
                parts.add(I);
            }
        }
         parts.toArray(new String[parts.size()]);
    }
    
    //This section includes the control booleans and the data values that must
    //be kept consistent, plus all the get and set methods.
    public static boolean getMenu() {
        return isMenu;
    }
    
    public static void setMenu(boolean newMenu) {
        isMenu = newMenu;
    }
    
    public static boolean getSpeaking() {
        return isSpeaking;
    }
    
    public static void setSpeaking (boolean newTalk) {
        isSpeaking = newTalk;
    }
    
    public static boolean getNext() {
        return isNext;
    }
    
    public static void setNext (boolean newFollow) {
        isNext = newFollow;
    }
    
    public static void setCursPos(int y) {
        if (cursPos + y != -1 && cursPos + y != maxPos) {
            cursPos += y;
        }
    }
    
    public static int getCursPos() {
        return cursPos;
    }
    
    public static void setMaxPos(int y) {
        maxPos = y;
    }
    
    public static int getMaxPos() {
        return maxPos;
    }
    
    public static String getOutputs(int index) {
        return outputs.get(index);
    }
    
    public static void addToOutputs(String data) {
        outputs.add(data);
    }
    
    public static void setSpeechPos(int y) {
        speechPos = y;
    }
    
    public static int getSpeechPos() {
        return speechPos;
    }
    
    public static void setIteration(int y) {
        iteration = y;
    }
    
    public static int getIteration() {
        return iteration;
    }
    
    public static void setTarget(String y) {
        target = y;
    }
    
    public static String getTarget() {
        return target;
    }
    
    public static void reset() {
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
    
    private static ArrayList<String> outputs = new ArrayList<>();
    private String continent;
    private static String target;
    private static boolean isMenu = false;
    private static boolean isNext = false;
    private static boolean isSpeaking = false;
    private static int cursPos = 0;
    private static int maxPos = 0;
    private static int speechPos = 0;
    private static int iteration = 0;

    
}
