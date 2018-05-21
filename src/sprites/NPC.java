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
    
    /**
     * Creates an NPC
     * @param imageURL the NPC's image's URL
     * @param name the NPC's name for dialogue finding purposes
     */
    public NPC(String imageURL, String name){
        super(imageURL);
        interactionURL = PREFIX + name + ".txt";
    }
    
    public AdvancableText getInteraction() throws IOException{
        return new AdvancableText(interactionURL);
    }
}
