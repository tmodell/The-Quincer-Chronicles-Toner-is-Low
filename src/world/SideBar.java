package world;

import java.awt.*;
import javax.swing.*;
/**
 * This will have a bunch of side stuff in it heh
 * @author alber
 */
public class SideBar extends JPanel{
    private static final String IMAGE_URL = "src/world/lib/sidebar.png";
    
    MainFrame frame;
    
    Image background;
    
    public SideBar (MainFrame frame){
        super();
        
        this.frame = frame;
        setPreferredSize(new Dimension(256, 1080));
        setBackground(Color.BLUE);
        
        background = new ImageIcon(IMAGE_URL).getImage();
        //repaint();
    }
    
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        
        g.drawImage(background, 0, 0, null);
    }
}
