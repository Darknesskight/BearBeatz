package org.tunnelsnakes.entities;


import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.state.StateBasedGame;
import org.tunnelsnakes.attributes.Renderable;

/**
 * An Entity is an object in the game with a position and size
 * 
 * @author Tucker Lein
 *
 */
public class Entity implements Renderable {
    
    //Slick shape used by the Entity for position and collision
    protected Shape shape;
    
    /**
     * Contructs a new Entity with the given shape
     * 
     * @param shape Slick shape used by the Entity for position and collision
     */
    public Entity(Shape shape) {
        this.shape = shape;
    }

    /**
     * Initializes the entity
     * 
     * @param gc GameContainer context
     * @param game StateBasedGame context
     */
    public void init(GameContainer gc, StateBasedGame game) {
    }
    
    /**
     * Updates the entity
     * 
     * @param gc GameContainer context
     * @param game StateBasedGame context
     * @param delta amount of time since the last update was called
     */
    public void update(GameContainer gc, StateBasedGame game, int delta) {
        
    }
    
    /**
     * Renders the entity
     * 
     * @param gc GameContainer context
     * @param game StateBasedGame context
     * @param g Graphics context
     */
    public void render(GameContainer gc, StateBasedGame game, Graphics g) {
    }
    
    /**
     * Getter for shape
     * 
     * @return shape
     */
    public Shape getShape() {
        return shape;
    }

    /**
     * Each entity defaults with a renderPriority of 0
     * 
     * @see org.tunnelsnakes.attributes.Renderable#getRenderPriority()
     */
    public int getRenderPriority() {
        return 0;
    }
    
    /**
     * Specifies what to do when destroying an entity. Does nothing by default.
     */
    protected void destroy() {
        
    }
    
}
