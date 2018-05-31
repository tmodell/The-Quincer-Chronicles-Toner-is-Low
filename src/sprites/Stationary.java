/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sprites;

/**
 *
 * @author albert.wilcox
 */
public class Stationary extends Sprite{
    private static final String PREFIX = "lib/sprites/images/";
    private static final String SUFFIX = ".png";
    
    boolean passive;
    
    public Stationary(String name, int x, int y){
        super(PREFIX + name + SUFFIX);
        this.x = x;
        this.y = y;
        passive = false;
    }
    
    public void setPassive (boolean passive){
        this.passive = passive;
    }
    
    @Override
    public boolean getPassive(){
        return passive;
    }
}
