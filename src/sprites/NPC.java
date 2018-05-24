package sprites;

import java.io.IOException;
import npcinteraction.*;

/**
 *
 * @author albert.wilcox
 */
public class NPC extends Sprite{
    static final String PREFIX = "src/npcinteraction/lib/";
    
    public String interactionURL;
    String name;
    
    /**
     * Creates an NPC
     * @param imageURL
     * @param name
     * @param interactionFileName
     * @param x
     * @param y 
     */
    public NPC(String imageURL, String interactionFileName, String name, int x, int y){
        super("src/sprites/lib/images/" + imageURL + ".png");
        interactionURL = PREFIX + interactionFileName + ".txt";
        
        this.name = name;
        this.x = x;
        this.y = y;
    }
    
    public AdvancableText getInteraction(){
        try{
            return new AdvancableText(interactionURL);
        }catch(Exception e){return null;}
    }
}
