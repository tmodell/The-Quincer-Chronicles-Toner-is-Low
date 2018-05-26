package world;

import java.awt.*;
import javax.swing.*;
import sprites.Player;
/**
 * This will have a bunch of side stuff in it heh
 * @author alber
 */
public class SideBar extends JPanel{
    private static final String IMAGE_URL = "src/world/lib/sidebar.png";
    
    MainFrame frame;
    
    Player player;
    
    Image background;
    
    public SideBar (MainFrame frame){
        super();
        
        this.frame = frame;
        setPreferredSize(new Dimension(256, 1080));
        setBackground(Color.BLUE);
        
        background = new ImageIcon(IMAGE_URL).getImage();
        
        player = frame.getWorld().getPlayer();
    }
    
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        
        g.drawImage(background, 0, 0, null);
        
        float healthPercent = (float)player.getHealth()/(float)player.getMaxHealth();
        
        if (healthPercent > .5) g.setColor(Color.GREEN);
        else if (healthPercent > 0.25) g.setColor(Color.YELLOW);
        else g.setColor(Color.RED);
        
        int healthBarLength = (int)(healthPercent * 198);
        g.fillRect(25, 393, healthBarLength, 48);
        
        String potions = Integer.toString(player.getPotionCount());
        g.setColor(Color.WHITE);
        g.setFont(new Font("Old English Text MT", Font.BOLD, 50));
        g.drawString(potions, 120, 598);
    }
    
    public void update(){
        repaint();
    }
}
