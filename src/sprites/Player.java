package sprites;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 *
 * @author albert.wilcox
 */
public class Player extends Sprite implements KeyListener{
    static final String PLAYER_IMAGE_URL = "TODO add url";
    
    //TODO add reference to world
    
    public Player(int x, int y){
        super(PLAYER_IMAGE_URL);
        this.x = x;
        this.y = y;
    }

    @Override
    public void keyTyped(KeyEvent ke) {}

    @Override
    public void keyPressed(KeyEvent ke) {
        int key = ke.getKeyCode();
        switch (key){
            case KeyEvent.VK_LEFT:
                x -= 1;
                break;
            case KeyEvent.VK_RIGHT:
                //code
                break;
            case KeyEvent.VK_UP:
                //code
                break;
            case KeyEvent.VK_DOWN:
                //code
                break;
            
               
        }
    }

    @Override
    public void keyReleased(KeyEvent ke) {
        
    }
}
