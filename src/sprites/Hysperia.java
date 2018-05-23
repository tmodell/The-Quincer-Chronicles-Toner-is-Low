/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sprites;


import npcinteraction.*;
/**
 * Hysperia had to have her own class because the interaction folk wanted her
 * dialogue to change at various points in the story.
 * 
 * @author alber
 */
public class Hysperia extends NPC{
    
    private static final String IMAGE_URL = "src/sprites/lib/characters/hysperia.png";
    private static final String NAME = "Princess Hysperia";
    
    public Hysperia(int x, int y) {
        super(IMAGE_URL, null, NAME, x, y);
    }
    
    @Override
    public AdvancableText getInteraction(){
        //TODO code to return Hysperia's interaction.
        return null;
    }
    
}
