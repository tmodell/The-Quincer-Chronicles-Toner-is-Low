package world;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
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
    
    JLabel toggle, save, exit;
    
    Image background;
    
    public SideBar (MainFrame frame){
        super();
        
        this.frame = frame;
        setPreferredSize(new Dimension(256, 1080));
        setBackground(Color.BLUE);
        
        background = new ImageIcon(IMAGE_URL).getImage();
        
        player = frame.getWorld().getPlayer();
        
        setLayout(null);
        
        save = new JLabel(new ImageIcon("src/world/lib/buttons/save.png"));
        save.setFocusable(false);
        save.setCursor(new Cursor(Cursor.HAND_CURSOR));
        save.addMouseListener(new MouseListener(){
            ImageIcon active = new ImageIcon("src/world/lib/buttons/saveactive.png"), inactive = (ImageIcon) save.getIcon();
            
            @Override
            public void mouseClicked(MouseEvent e) {
                //TODO add functionality
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                save.setIcon(active);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                save.setIcon(inactive);
            }
            
            @Override
            public void mousePressed(MouseEvent e){}

            @Override
            public void mouseReleased(MouseEvent e) {}
            
        });
        
        exit = new JLabel(new ImageIcon("src/world/lib/buttons/exit.png"));
        exit.setFocusable(false);
        exit.setCursor(new Cursor(Cursor.HAND_CURSOR));
        exit.addMouseListener(new MouseListener(){
            ImageIcon active = new ImageIcon("src/world/lib/buttons/exitactive.png"), inactive = (ImageIcon) exit.getIcon();
            
            @Override
            public void mouseClicked(MouseEvent e) {
                //TODO add functionality
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                exit.setIcon(active);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                exit.setIcon(inactive);
            }
            
            @Override
            public void mousePressed(MouseEvent e){}

            @Override
            public void mouseReleased(MouseEvent e) {}
            
        });
        
        add(exit);
        add(save);
        
        save.setBounds(28, 820, 200, 70);
        exit.setBounds(28, 915, 200, 70);
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
