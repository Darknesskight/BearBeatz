package org.tunnelsnakes.entities;

import java.util.Stack;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.KeyListener;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.state.StateBasedGame;

/**
 * A ControlledEntity is a MoveableEntity that can be controlled by an input device
 * 
 * @author Tucker Lein
 *
 */
public class ControlledEntity extends MoveableEntity implements KeyListener {
	//Stack that contains ALL input fed in through the input device
    private Stack<Integer> inputStack = new Stack<Integer>();
    
    //Stack that contains only the input used in movement
    private Stack<Integer> inMoveStack = new Stack<Integer>();

    /**
     * Constructs a new ControlledEntity with shape
     * 
     * @param shape Shape of entity
     */
    public ControlledEntity(Shape shape, GameMap map) {
        super(shape, map);
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
        gc.getInput().addKeyListener(this);
    }

    /**
     * updates the ControlledEntity
     * 
     * @param gc GameContainer context
     * @param game StateBasedGame context
     * @param delta time since last update call
     */
    @Override
    public void update(GameContainer gc, StateBasedGame game, int delta) {
        super.update(gc, game, delta);
    }

    /**
     * Renders the ControlledEntity
     * 
     * @param gc GameContainer context
     * @param game StateBasedGame context
     * @param g Graphics context
     */
    @Override
    public void render(GameContainer gc, StateBasedGame game, Graphics g) {
        super.render(gc, game, g);
    }
    
    public void clearInput(){
    	inMoveStack.clear();
    }

    /**
     * Called when a key is pressed
     * 
     * @param key Input key of the pressed key
     * @param char of the pressed key
     */
    public void keyPressed(int key, char c) {
        if(key == Input.KEY_LEFT || 
                key == Input.KEY_RIGHT) {
            inMoveStack.add(key);
            if(key == Input.KEY_LEFT) dir = "left";
            else dir = "right";
        }
        inputStack.add(key);
    }

    /**
     * Called when a key is released
     * 
     * @param key Input key of the released key
     * @param char of the released key
     */
    public void keyReleased(int key, char c) {
        if(key == Input.KEY_LEFT || 
                key == Input.KEY_RIGHT) {
            inMoveStack.remove((Integer)key);
        }
        inputStack.remove((Integer)key);
    }

    /* UNUSED METHODS IMPLEMENTED FROM KEYLISTENER */
    public void inputEnded() {}
    public void inputStarted() {}
    public void setInput(Input input) {}
    public boolean isAcceptingInput() { return true; }

	public Stack<Integer> getInputStack() {
		return inputStack;
	}

	public void setInputStack(Stack<Integer> inputStack) {
		this.inputStack = inputStack;
	}

	public Stack<Integer> getInMoveStack() {
		return inMoveStack;
	}

	public void setInMoveStack(Stack<Integer> inMoveStack) {
		this.inMoveStack = inMoveStack;
	}

}
