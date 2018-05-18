package sprites;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import world.World;

/**
 *
 * @author albert.wilcox
 */
public class Player extends Sprite implements KeyListener{
    static final String PLAYER_IMAGE_URL = "TODO add url";
    
    World world;
    
    public Player(int x, int y, World world){
        super(PLAYER_IMAGE_URL);
        this.x = x;
        this.y = y;
        
        this.world = world;
    }

    @Override
    public void keyTyped(KeyEvent ke) {}
    
    @Override
    public void keyReleased(KeyEvent ke) {}

    /**
     * This method handles user input for motion.
     * 
     * NOTE: this should be where input for other actions is handled as well IMO
     * @param ke Don't worry about this parameter
     */
    @Override
    public void keyPressed(KeyEvent ke) {
        int key = ke.getKeyCode();
        int hypotheticalX, hypotheticalY;
        switch (key){
            case KeyEvent.VK_LEFT:
                hypotheticalX = x - 1;
                // check whether the square is occupiable
                if (world.isOccupiable(hypotheticalX, y)){
                    x = hypotheticalX;
                    world.update();
                } else{
                    // maybe add code for what to do if a square is unoccupiable
                }
                break;
            case KeyEvent.VK_RIGHT:
                hypotheticalX = x + 1;
                // check whether the square is occupiable
                if (world.isOccupiable(hypotheticalX, y)){
                    x = hypotheticalX;
                    world.update();
                } else{
                    // maybe add code for what to do if a square is unoccupiable
                }
                break;
            case KeyEvent.VK_UP:
                hypotheticalY = y - 1;
                // check whether the square is occupiable
                if (world.isOccupiable(x, hypotheticalY)){
                    y = hypotheticalY;
                    world.update();
                } else{
                    // maybe add code for what to do if a square is unoccupiable
                }
                break;
            case KeyEvent.VK_DOWN:
                hypotheticalY = y + 1;
                // check whether the square is occupiable
                if (world.isOccupiable(x, hypotheticalY)){
                    y = hypotheticalY;
                    world.update();
                } else{
                    // maybe add code for what to do if a square is unoccupiable
                }
                break;
            
               
        }
    }
}
