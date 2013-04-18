package org.tunnelsnakes.entities;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.state.StateBasedGame;

/**
 * Block is an Entity that is associated with a collidable wall
 * 
 * @author tucker
 *
 */
public class Block extends Entity {
	//Determines if the block is a one way platform. Meaning an entity will only collide with it from the top
    private boolean oneWay = false;
    private boolean climbable = false;
    private boolean walkthrough = false;

    /**
     * Constructor which creates the base Entity
     * 
     * @param shape Shape of the Entity
     */
    public Block(Shape shape, GameMap map) {
        super(shape, map);
    }
    
    /**
     * Used in debugging, renders the block based on if it's a one way platform or not.
     * Blue = one way platform
     * Red = regular block
     * 
     * @see org.tunnelsnakes.entities.Entity#render(org.newdawn.slick.GameContainer, org.newdawn.slick.state.StateBasedGame, org.newdawn.slick.Graphics)
     */
    @Override
    public void render(GameContainer gc, StateBasedGame game, Graphics g) {
        if(!oneWay) g.setColor(Color.red);
        else g.setColor(Color.blue);
        g.fillRect(shape.getX(), shape.getY(), shape.getWidth(), shape.getHeight());
    }
    
    /**
     * Setter for oneWay
     * 
     * @param oneWay new value for oneWay
     */
    public void setOneWay(boolean oneWay) {
        this.oneWay = oneWay;
    }
    
    /**
     * Getter for oneWay
     * 
     * @return oneWay
     */
    public boolean isOneWay() {
        return oneWay;
    }

	public void setClimbable(boolean b) {
		climbable = b;
		walkthrough = true;
	}
}
