/*
 * this demo is working.
 */
//testing
package tonerislow;
import java.awt.Color;
import javax.swing.JFrame;
import world.MainFrame;

/**
 *
 * @author IB CS 2 SL
 */
public class TonerIsLow {

    static MainFrame mainFrame;
    static Save save;
    
    public static void main(String[] args) {
        save = new Save();
        mainFrame = new MainFrame(save);
    }
    
    public static MainFrame getMainFrame(){
        return mainFrame;
    }
    
    public static Save getSave(){
        return save;
    }
}