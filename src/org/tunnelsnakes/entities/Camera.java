package org.tunnelsnakes.entities;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;


/**
 * Camera controls the viewport
 * 
 * @author Tucker Lein
 *
 */
public class Camera {
    //x location of the camera
    private int x = 0;
    
    //y location of the camera
    private int y = 0;
    
    //offsets used to move the camera around the POI
    private int xOffset, yOffset = 0;
    
    //scale of the camera
    private float scale = 3.0f;
    
    //Point of Interest, what the camera is focusing on
    private Vector2f poi;
    
    private GameMap map;
    
    //Constructs a new camera with a point of interest 
    public Camera(Vector2f poi, GameMap map) {
        this.poi = poi;
        this.map = map;
    }

    /**
     * Updates the poi and camera's position
     * 
     * @param gc GameContainer context
     * @param game StateBasedGame context
     * @param delta amount of time since last update
     */
    public void update(GameContainer gc, StateBasedGame game, int delta) {
        Input input = gc.getInput();
        poi.set(poi.getX() + xOffset, poi.getY() + yOffset);
        if(!gc.isPaused()) {
            if(input.isKeyDown(Input.KEY_ADD)) scale += 0.01f;
            if(input.isKeyDown(Input.KEY_SUBTRACT)) scale -= 0.01f;
        }
        if(scale < 0.5f) scale = 0.5f;
        rigidCamMovement(gc);
    }
    
    /**
     * Updates the camera's x and y position while stopping at screen edges.
     * 
     * @param gc GameContainer context
     */
    private void rigidCamMovement(GameContainer gc) {
        //x offsets, checking for edges
        if(((poi.getX() - ((gc.getWidth()/scale)/2)) >= 0 &&
                (poi.getX() + (gc.getWidth()/scale)/2 < map.getWidth()))) {
            x = (int) (poi.getX() - (gc.getWidth()/scale)/2);
        }
        else if(poi.getX() < (gc.getWidth()/scale)/2) x = 0;
        else x = (int) (map.getWidth() - (gc.getWidth()/scale));
        
        //y offsets, checking for edges
        if(((poi.getY() - ((gc.getHeight()/scale)/2)) >= 0 &&
                (poi.getY() + (gc.getHeight()/scale)/2 < map.getHeight()))) {
            y = (int) (poi.getY() - (gc.getHeight()/scale)/2);
        }
        else if(poi.getY()  < (gc.getHeight()/scale)/2) y = 0;
        else y = (int) (map.getHeight() - (gc.getHeight()/scale));
    }

    /**
     * Getter for scale
     * 
     * @return scale
     */
    public float getScale() {
        return scale;
    }

    /**
     * Getter for y
     * 
     * @return y
     */
    public int getY() {
        return y;
    }

    /**
     * Getter for x
     * 
     * @return x
     */
    public int getX() {
        return x;
    }
    
    /**
     * Setter for poi
     * 
     * @param poi
     */
    public void setPOI(Vector2f poi) {
        this.poi = poi;
    }

    /**
     * Specifies a new point and speed for the camera to move to
     * 
     * @param point new point to move towards
     * @param speed speed to move at
     */
    public void moveTo(Vector2f point, float speed) {
        
    }

	public Vector2f getPOI() {
		return poi;
	}

	public GameMap getMap() {
		return map;
	}

	public void setMap(GameMap map) {
		this.map = map;
	}

}
