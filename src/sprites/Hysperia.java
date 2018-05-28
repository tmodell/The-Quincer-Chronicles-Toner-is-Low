/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sprites;


import java.io.IOException;
import npcinteraction.*;
import tonerislow.Save;
/**
 * Hysperia had to have her own class because the interaction folk wanted her
 * dialogue to change at various points in the story.
 * 
 * @author alber
 */
public class Hysperia extends NPC{
    
    private static final String NAME = "Princess Hysperia";
    
    public Hysperia(int x, int y) {
        super("hysperia", null, NAME, x, y);
    }
    
    @Override
    public AdvancableText getInteraction(){
        Save s = tonerislow.TonerIsLow.getSave();
        int count = 1;
        if (!s.isShamanAlive(1))count++;
        if (!s.isShamanAlive(2))count++;
        if (!s.isShamanAlive(3))count++;
        if (!s.isShamanAlive(4))count++;
        
        String url = "src/npcinteraction/lib/hysperia" + Integer.toString(count) + ".txt";
        
        AdvancableText at = null;
        try{
            at = new AdvancableText(url, NAME);
        } catch (IOException e) {e.printStackTrace();}
        
        return at;
    }
    
}
