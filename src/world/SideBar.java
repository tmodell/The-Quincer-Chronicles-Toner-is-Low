package world;

import java.awt.*;
import javax.swing.*;
/**
 * This will have a bunch of side stuff in it heh
 * @author alber
 */
public class SideBar extends JPanel{
    MainFrame frame;
    
    public SideBar (MainFrame frame){
        this.frame = frame;
        setPreferredSize(new Dimension(256, 1080));
        setBackground(Color.BLUE);
    }
}
