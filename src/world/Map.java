
package world;

import java.awt.Image;
import java.util.ArrayList;
import java.util.Iterator;

import sprites.Sprite; 

public class Map {
    
    private Image[][] tiles;
    private ArrayList sprites;
    private Sprite player;
    private char[][] slist;  
    
    /**
        Creates a new TileMap with the specified width and
        height (in number of tiles) of the map.
    */
    public Map(int absX, int absY) {
        tiles = new Image[absX][absY];
        sprites = new ArrayList();
    }
        /**
        Gets the width of this TileMap (number of tiles across).
    */
    public int getWidth() {
        return tiles.length;
    }

    /**
        Gets the height of this TileMap (number of tiles down).
    */
    public int getHeight() {
        return tiles[0].length;
    }

    /**
        Gets the tile at the specified location. Returns null if
        no tile is at the location or if the location is out of
        bounds.
    */
    public Image getTile(int x, int y) {
        if (x < 0 || x >= getWidth() ||
            y < 0 || y >= getHeight())
        {
            return null;
        }
        else {
            return tiles[x][y];
        }
    }
    
    /**
        Sets the tile at the specified location.
    */
    public void setTile(int x, int y, Image tile) {
        tiles[x][y] = tile;
    }
    
    /**
        Gets the player Sprite.
    */
    public Sprite getPlayer() {
        return player;
    }

    /**
        Sets the player Sprite.
    */
    public void setPlayer(Sprite player) {
        this.player = player;
    }

    /**
        Adds a Sprite object to this map.
    */
    public void addSprite(Sprite sprite) {
        sprites.add(sprite);
    }

    /**
        Removes a Sprite object from this map.
    */
    public void removeSprite(Sprite sprite) {
        sprites.remove(sprite);
    }

    /**
        Gets an Iterator of all the Sprites in this map,
        excluding the player Sprite.
    */
    public Iterator getSprites() {
        return sprites.iterator();
    }
//26 by 14 for world map
    //each tile 64 by 64 pixels
}
