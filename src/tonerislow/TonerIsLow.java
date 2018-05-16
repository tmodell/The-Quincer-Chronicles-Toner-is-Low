/*
 * this demo is working.
 */

package tonerislow;
import java.awt.Color;
import javax.swing.JFrame;

/**
 *
 * @author IB CS 2 SL
 */
public class TonerIsLow {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        JFrame main = new JFrame();
        main.setExtendedState(JFrame.MAXIMIZED_BOTH);
        main.setUndecorated(true);
        main.setResizable(false);
        main.getContentPane().setBackground(Color.BLACK);
        main.setVisible(true);
        main.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        
        
        while (true)
        {
            main.requestFocus();
        }
    }
}
