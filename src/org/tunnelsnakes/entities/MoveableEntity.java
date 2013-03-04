package org.tunnelsnakes.entities;

import java.util.List;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Path;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;
import org.tunnelsnakes.Game;
import org.tunnelsnakes.geom.SmRectangle;

/**
 * MoveableEntity is an Entity that can move about the world
 * 
 * @author Tucker Lein
 *
 */
public class MoveableEntity extends Entity {
	
	//Default speed gravity is reset to
    protected final int DEF_GRAV_SPEED = 0;
    
    //Default cap for gravity. gravSpeed cannot go over this
    protected final int DEF_GRAV_CAP = 8;
    
    //Default speed for movement
    protected final int DEF_MOVE_SPEED = 2;
    
    
    
    //determines is gravity affects this entity
    protected boolean gravity = true;
    
    //true if entity is colliding with an object
    private boolean colliding = false;
    
    //true if entity is not allowed to move
    private boolean neverMove = false;
    
    //true if entity is on the ground
    protected boolean onGround = false;
    
    //true if player should skip next one way collision
    protected boolean skipOneWay = false;
    
    
    
    //Speed the gravity is currently exerting on the entity
    protected double gravSpeed = 0;
    
    //the gravity speed capacity
    protected int gravCap = DEF_GRAV_CAP;
    
    //speed the entity is moving at
    protected int movementSpeed = 3;
   
    //direction the entity is facing
    protected String dir = "right";
    
    
    
    //Rectangle that shows the next step in the movement to check for collisions
    protected Shape nextStep;
    
    //Block that is currently being collided with
    protected Block collidingBlock = new Block(new SmRectangle(0,0,0,0));

    //Path showing the movement history of the entity
    protected Path movementLine = new Path(shape.getCenterX(), shape.getCenterY());
    
    /**
     * Contructs a new MoveableEntity with a given shape
     * 
     * @param shape shape used by Entity for collisions and position
     */
    public MoveableEntity(Shape shape) {
        super(shape);
    }
    
    /**
     * inits the entity
     * 
     * @param gc GameContainer context
     * @param game StateBasedGame context
     */
    @Override
    public void init(GameContainer gc, StateBasedGame game) {
        super.init(gc, game);
    }
    
    /**
     * updates the MoveableEntity, applies gravity
     * 
     * @param gc GameContainer context
     * @param game StateBasedGame context
     * @param delta time since last update call
     */
    @Override
    public void update(GameContainer gc, StateBasedGame game, int delta) {
        super.update(gc, game, delta);
        if(gravity) {
	        if(gravSpeed < gravCap) gravSpeed += 0.5;
	        yMovement((int)gravSpeed, game);
        }
        movementLine.lineTo(shape.getCenterX(), shape.getCenterY());
    }
    
    /**
     * Applies movement in the y direction
     * 
     * @param amount amount to move in y direction
     * @param game StateBasedGame context
     */
    protected void yMovement(int amount, StateBasedGame game) {
        nextStep.setY(shape.getY() + amount);
        nextStep.setX(shape.getX());

        colliding = checkCollisions(((Game) game).getCurGameMap().getBlocks(), calcLAABB()); //determine if a collision is happening
        if(!colliding) {
        	//jerry rigged way of telling if the entity is on the ground. I don't like this, but it appears to work
            if(gravSpeed > 2.0) {
                if(onGround) { //messy messy messy
                    gravSpeed = DEF_GRAV_SPEED;
                    nextStep.setX(shape.getX());
                    nextStep.setY(shape.getY());
                }
                onGround = false;
            }
            setYPosition();
        } else {
            if(amount > 0) onGround = true;
            fixYPosition();
        }
    }
    
    /**
     * Applies movement in the x direction
     * 
     * @param amount amount to move in the x direction
     * @param game StateBasedGame context
     */
    protected void xMovement(int amount, StateBasedGame game) {
        nextStep.setX(shape.getX() + amount);
        nextStep.setY(shape.getY());
        
        colliding = checkCollisions(((Game) game).getCurGameMap().getBlocks(), calcLAABB());
        if(!colliding) {
            setXPosition();
        } else {
            fixXPosition();
        }
    }
    
    /**
     * Calculates a Large Axis Aligned Bounding Box around the shape and the nextStep.
     * This is then used to check for collisions, preventing an object from moving "through" another at high speeds
     * 
     * @return a SmRectangle surrounding the bounds of shape and nextStep
     */
    private Shape calcLAABB() {
        return new SmRectangle(Math.min(shape.getX(), nextStep.getX()), Math.min(shape.getY(), nextStep.getY()),
                               Math.abs(shape.getX() - nextStep.getX()) + shape.getWidth(), Math.abs(shape.getY() - nextStep.getY()) + shape.getHeight());
    }

    /**
     * Checks through a list of Blocks and checks if the shapeToCheck collides with any of them
     * 
     * @param blocks List of Blocks to check against a collision
     * @param shapeToCheck Shape used to check for collision against the blocks
     * @return true if colliding
     */
    private boolean checkCollisions(List<Block> blocks, Shape shapeToCheck) {
        boolean colliding = false;
        float distance = Float.MAX_VALUE;
        Vector2f shapeCoord = new Vector2f(shape.getCenterX(), shape.getCenterY());
        Vector2f colCoord = new Vector2f(0, 0);
        for(Block b : blocks) {
            if(shapeToCheck.intersects(b.getShape()) && validOneWayCollision(b)) { //if it collides with the shape and if it is a validOneWayCollision
            	if(!skipOneWay) {
	                colCoord.set(b.getShape().getCenterX(), b.getShape().getCenterY());
	                if(shapeCoord.distance(colCoord) < distance) { //set collidingBlock if the distance between it and the shapeToCheck is the shortest found
	                    distance = shapeCoord.distance(colCoord);
	                    collidingBlock = b;
	                }
	                colliding = true;
            	} else {
            		if(b.getShape().getY() > collidingBlock.getShape().getY()) {
            			skipOneWay = false;
            		}
            	}
            } 
        }
        return colliding;
    }
    
    /**
     * Checks to see if a collision with a one way block is coming from the top of the block
     * 
     * @param b Block to check
     * @return true if block is not a one way block or if the collision is coming from the top
     */
    private boolean validOneWayCollision(Block b) {
		return !b.isOneWay() || (b.isOneWay() && shape.getY() + shape.getHeight() <= b.getShape().getY());
    }
    
    /**
     * Sets the y position of the shape
     */
    private void setYPosition() {
        if(!neverMove) shape.setLocation(nextStep.getX(), nextStep.getY());
    }
    
    /**
     * Sets the x position of the shape
     */
    private void setXPosition() {
        if(!neverMove) shape.setLocation(nextStep.getX(), nextStep.getY());
    }

    /**
     * This determines how much to correctly move the entity in the y direction in the event of a collision
     */
    private void fixYPosition() {
        int yStep = 0;
        
        if(shape.getY() < collidingBlock.getShape().getY()) { //COLLIDING WITH SOMETHING BELOW ENTITY
            yStep = (int) Math.abs(((shape.getY() + shape.getHeight() + (collidingBlock.getShape().getHeight()/2)) - (collidingBlock.getShape().getY() + (collidingBlock.getShape().getHeight()/2))));
            shape.setY(shape.getY() + yStep);
        } else { //COLLIDING WITH SOMETHING ABOVE ENTITY
            yStep = (int) Math.abs(((shape.getY() - (collidingBlock.getShape().getHeight()/2)) - (collidingBlock.getShape().getY() + (collidingBlock.getShape().getHeight()/2))));
            shape.setY(shape.getY() - yStep);
        }
    }

    /**
     * This determines how much to correctly move the entity in the x direction in the event of a collision
     */
    private void fixXPosition() {
        int xStep = 0;
        
        if(shape.getX() < collidingBlock.getShape().getX()) {
            xStep = (int) Math.abs(((shape.getX() + shape.getWidth() + (collidingBlock.getShape().getWidth()/2)) - (collidingBlock.getShape().getX() + (collidingBlock.getShape().getWidth()/2))));
            shape.setX(shape.getX() + xStep);
        } else {
            xStep = (int) Math.abs(((shape.getX() - (collidingBlock.getShape().getWidth()/2)) - (collidingBlock.getShape().getX() + (collidingBlock.getShape().getWidth()/2))));
            shape.setX(shape.getX() - xStep);
        }
    }
    

    /**
     * Renders the MoveableEntity
     * 
     * @param gc GameContainer context
     * @param game StateBasedGame context
     * @param g Graphics context
     */
    @Override
    public void render(GameContainer gc, StateBasedGame game, Graphics g) {
        super.render(gc, game, g);
    }

}
