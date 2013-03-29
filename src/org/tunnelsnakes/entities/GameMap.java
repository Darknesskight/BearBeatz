package org.tunnelsnakes.entities;

import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.Music;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.tiled.TiledMap;
import org.tunnelsnakes.Game;
import org.tunnelsnakes.attributes.Renderable;

/**
 * A GameMap is a map of the game which contains the TiledMap data
 * 
 * @author Tucker Lein
 *
 */
public class GameMap implements Renderable {
    
    //TiledMap of map
    private TiledMap map;
    
    //Level song
    private Music levelMusic;
    
    //List containing all the collision blocks on the map
    private List<Block> blocks = new ArrayList<Block>();

    //List containing all the warps on the map
    private List<Warp> warps = new ArrayList<Warp>();
    
    //All MapLayers on the map
    private List<MapLayer> layers = new ArrayList<MapLayer>();
    
    //width of the map
    private int width;
    
    //height of the map
    private int height;
    
    //render priority of the map
    private int renderPriority = -1000;
    
    //position in the level song, used for restarting the song when paused
    private float musicPosition = 0;
    
    /**
     * Constructs a new GameMap with the given TiledMap
     * 
     * @param map TiledMap context
     */
    public GameMap(TiledMap map) {
        this.map = map;
        for(int i = 0; i < map.getLayerCount(); i++) {
            int priority = i;
            if(Boolean.parseBoolean(map.getLayerProperty(i, "abovePlayer", "false"))) {
                priority += 1001;
            }
            if(!Boolean.parseBoolean(map.getLayerProperty(i, "invisible", "false"))) {
            	layers.add(new MapLayer(map, i, priority));
            }
        }
        width = map.getWidth() * map.getTileWidth();
        height = map.getHeight() * map.getTileHeight();
        locateSpecials();
    }
    
    /**
     * Goes through the tiledMap and finds all the collidable tiles and creates Blocks at those locations
     */
    private void locateSpecials() {
    	
    	/* Find the layer of collision and layer of special */
        int collisionLayer = 0, specialLayer = 0;
        for(int i = 0; i < map.getLayerCount(); i++) { //get the layer which contains the collision blocks
            if(Boolean.parseBoolean(map.getLayerProperty(i, "collisionMask", "false"))) collisionLayer = i; 
            if(Boolean.parseBoolean(map.getLayerProperty(i, "specialMask", "false"))) specialLayer = i;
        }
        int tileId = 0;
        for(int i = 0; i < map.getWidth(); i++) {
            for(int k = 0; k < map.getHeight(); k++) {
                tileId = map.getTileId(i, k, collisionLayer);
                
                /* Find Collidable Block, create new Block instance */
                if(Boolean.parseBoolean(map.getTileProperty(map.getTileId(i, k, collisionLayer), "solid", "false"))) {
                    blocks.add(new Block(new Rectangle(i * map.getTileWidth(), k * map.getTileHeight(), 
                            map.getTileWidth(), map.getTileHeight()), this));
                    if(Boolean.parseBoolean(map.getTileProperty(tileId, "oneWay", "false")))  {
                        blocks.get(blocks.size()-1).setOneWay(true);
                    }
                }

                tileId = map.getTileId(i, k, specialLayer);
                /* Find Special Block, create new instance of type */
                if(Boolean.parseBoolean(map.getTileProperty(tileId, "warp", "false"))) {
                	warps.add(new Warp(new Rectangle(i * map.getTileWidth(), k * map.getTileHeight(),
                			map.getTileWidth(), map.getHeight()),
                			this,
                			map.getTileProperty(tileId, "warpType", "null"),
                			map.getTileProperty(tileId, "warpValue", "null")));
                }
            }
        }
    }
    
    /**
     * inits the GameMap
     * 
     * @param gc GameContainer context
     * @param game StateBasedGame context
     */
    public void init(GameContainer gc, StateBasedGame game) {
        for(MapLayer layer : layers) {
            layer.init(gc, game);
        }
    }
    
    /**
     * updates the GameMap's camera and other attributes
     * 
     * @param gc GameContainer context
     * @param game StateBasedGame context
     * @param delta time since last update call
     */
    public void update(GameContainer gc, StateBasedGame game, int delta) {
        Input input = gc.getInput();
        if(input.isKeyPressed(Input.KEY_M)) {
            if(levelMusic.playing()) stopMusic();
            else playMusic();
        }
    }

    /**
     * Renders the GameMap, which only renders debug mode items.
     * 
     * @param gc GameContainer context
     * @param game StateBasedGame context
     * @param g Graphics context
     */
    public void render(GameContainer gc, StateBasedGame game, Graphics g) {
    	g.setColor(Color.white);
    	g.fillRect(0, 0, map.getWidth()*map.getTileWidth(), map.getHeight() * map.getTileHeight());
        if(((Game) game).isDebug()) {
            for(Block b : blocks) {
                b.render(gc, game, g);
            }
        }
    }
    
    /**
     * Plays the levelMusic at the left off position
     */
    private void playMusic() {
        if(!levelMusic.playing()) {
            levelMusic.setPosition(musicPosition);
            levelMusic.loop();
        }
    }
    
    /**
     * Stops the levelMusic and saves the position
     */
    private void stopMusic() {
        musicPosition = levelMusic.getPosition();
        levelMusic.stop();
    }
    
    /**
     * Getter for blocks
     * 
     * @return blocks
     */
    public List<Block> getBlocks() {
        return blocks;
    }
    
    /**
     * Getter for width
     * 
     * @return width
     */
    public int getWidth() {
        return width;
    }
    
    /**
     * Getter for height
     * 
     * @return height
     */
    public int getHeight() {
        return height;
    }
    
    /**
     * Getter for map
     * 
     * @return map
     */
    public TiledMap getMap() {
        return map;
    }

    /**
     * Getter for renderPriority
     * 
     * @return renderPriority
     */
    public int getRenderPriority() {
        return renderPriority;
    }

	public List<Warp> getWarps() {
		return warps;
	}

	public void setWarps(List<Warp> warps) {
		this.warps = warps;
	}

	public List<MapLayer> getLayers() {
		return layers;
	}
	
	@Override
	public String toString() {
		return map.toString();
	}
}
