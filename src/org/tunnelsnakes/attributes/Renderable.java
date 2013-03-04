package org.tunnelsnakes.attributes;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

/**
 * Interface for renderable objects
 * 
 * @author Tucker Lein
 */
public interface Renderable {
    

    /**
     * Makes sure every renderable object has init method
     * 
     * @param gc GameContainer context
     * @param game StateBasedGame context
     */
    public void init(GameContainer gc, StateBasedGame game);
    
    /**
     * Makes sure ever renderable object has render method
     * 
     * @param gc GameContainer context
     * @param game StateBasedGame context
     * @param g Graphics context
     */
    public void render(GameContainer gc, StateBasedGame game, Graphics g);
    
    /**
     * Getter for renderPriority. 
     * renderPriority determines when the object will be rendered. 
     * First object rendered is the object with the lowest render priority.
     * 
     * @return renderPriority
     */
    public int getRenderPriority();
}
