/*
 * this demo is working.
 */
//testing
package tonerislow;
import java.awt.Color;
import javax.swing.JFrame;

/**
 *
 * @author IB CS 2 SL
 */
public class TonerIsLow {

    public static void main(String[] args) {
        JFrame main = new JFrame();
        main.setExtendedState(JFrame.MAXIMIZED_BOTH);
        main.setUndecorated(true);
        main.setResizable(false);
        main.getContentPane().setBackground(new Color(204, 255, 0));
        main.setVisible(true);
        main.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        
        while (true)
        {
            main.setAlwaysOnTop(true);
            main.requestFocus();
        }
    }
}