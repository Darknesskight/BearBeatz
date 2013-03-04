package org.tunnelsnakes.entities;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.tiled.TiledMap;
import org.tunnelsnakes.Game;
import org.tunnelsnakes.attributes.Renderable;

/**
 * A MapLayer is an individual layer of a GameMap. This is used to individually render the Layer
 * so other Entities can be above and below certain Layers
 * 
 * @author Tucker Lein
 *
 */
public class MapLayer implements Renderable {
	//renderPriority of the MapLayer
    private int renderPriority;
    
    //TiledMap the MapLayer belongs to
    private TiledMap map;
    
    //which layer of the TiledMap this refers to
    private int layer;
    
    /**
     * Constructs a new MapLayer with the given map, layer, and renderPriority
     * 
     * @param map TiledMap this layer belongs to
     * @param layer layer number
     * @param renderPriority renderPriority of the layer
     */
    public MapLayer(TiledMap map, int layer, int renderPriority) {
        this.map = map;
        this.layer = layer;
        this.renderPriority = renderPriority;
    }
    
    /**
     * inits the MapLayer
     * 
     * @param gc GameContainer context
     * @param game StateBasedGame context
     */
    public void init(GameContainer gc, StateBasedGame game) {
        ((Game) game).getRenderQueue().add(this);
    }
    
    /**
     * renders the MapLayer
     * 
     * @param gc GameContainer context
     * @param game StateBasedGame context
     * @param g Graphics context
     */
    public void render(GameContainer gc, StateBasedGame game, Graphics g) {
        map.render(0, 0, layer);
    }

    /**
     * Getter for renderPriority
     * 
     * @return renderPriority
     */
    public int getRenderPriority() {
        return renderPriority;
    }

}
