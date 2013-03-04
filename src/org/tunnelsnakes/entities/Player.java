package org.tunnelsnakes.entities;

import java.util.Stack;

import org.tunnelsnakes.Game;
import org.tunnelsnakes.geom.SmRectangle;
import org.tunnelsnakes.util.AnimationUtils;
import org.tunnelsnakes.util.ResourceManager;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.geom.Path;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

/**
 * Player defines a new Entity controlled by the user.
 * This implementation is for my own game and most functionality
 * are testbeds for broad Entity functionality. 
 * 
 * @author Tucker Lein
 *
 */
public class Player extends ControlledEntity {
    //default jump speed
    private final int DEF_JUMP_SPEED = 6;
    
    
	//boolean determining if player is currently jumping
    private boolean jump = false;
    
    //boolean determining if player is currently sprinting
    private boolean sprint = false;
    
    //speed of the jump
    private double jumpSpeed = DEF_JUMP_SPEED;
    
    //speed to add to the player's movement speed for sprinting
    private int sprintSpeed = 4;
    
    
    //Stack containing the animations being used by the player. Only the top of the stack will render
    private Stack<Animation> animationStack = new Stack<Animation>();
    
    //ResourceManager context
    private ResourceManager rm;
    
    //StateBasedGame context
    private StateBasedGame game;
    
    
    /**
     * Constructs new Player with given shape
     * 
     * @param shape Shape used for collisions and positions
     */
    public Player(Shape shape) {
        super(shape);
        nextStep = new SmRectangle(shape.getX(), shape.getY(), shape.getWidth(), shape.getHeight());
    }

    /**
     * inits Game elements Player requires
     * 
     * @param gc GameContainer context
     * @param game StateBasedGame context
     */
    @Override
    public void init(GameContainer gc, StateBasedGame game) {
        super.init(gc, game);
        ((Game) game).getRenderQueue().add(this);
        rm = ((Game) game).getResourceManager();
        this.game = game;
    }
    
    public void setupAnimations(StateBasedGame game) {
        //Set and load player's static 'animation'
        Image[] player = new Image[] { ((Game) game).getResourceManager().getAnimation("playerWalkRight").getImage(0) };
        ((Game) game).getResourceManager().load("playerRight", new Animation(player, 1));
        ((Game) game).getResourceManager().load("playerLeft", AnimationUtils.returnFlippedAnimation(new Animation(player, 1)));
        animationStack.push(((Game) game).getResourceManager().getAnimation("playerRight"));
    }
    
    @Override
    public void update(GameContainer gc, StateBasedGame game, int delta) {
        super.update(gc, game, delta);
        if(!inMoveStack.isEmpty()) getMovement(inMoveStack.peek(), game);
        if(animationStack.size() == 1) {
            if(dir.equals("left")) animationStack.set(0, rm.getAnimation("playerLeft"));
            if(dir.equals("right")) animationStack.set(0, rm.getAnimation("playerRight"));
        }
        jump(game);
        checkDeath();
    }

    @Override
    public void render(GameContainer gc, StateBasedGame game, Graphics g) {
        super.render(gc, game, g);
        //g.drawImage(((Game) game).getResourceManager().getImage("player"), shape.getX(), shape.getY());
        g.drawAnimation(animationStack.peek(), shape.getX() - ((animationStack.peek().getWidth() - shape.getWidth())/2), shape.getY());
        if(((Game) game).isDebug()) {
            g.setColor(new Color(0, 125, 125, 128));
            g.fillRect(nextStep.getX(), nextStep.getY(), shape.getWidth(), shape.getHeight());
            g.setColor(Color.cyan);
            g.fillRect(shape.getX(), shape.getY(), shape.getWidth(), shape.getHeight());
            g.setColor(Color.orange);
            g.fillRect(collidingBlock.getShape().getX(), collidingBlock.getShape().getY(), collidingBlock.getShape().getWidth(), collidingBlock.getShape().getHeight());
        }
    }
    
    private void getMovement(int inputKey, StateBasedGame game) {
        int extra = 0;
        if(sprint) extra += sprintSpeed;
        if(inputKey == Input.KEY_LEFT) {
            xMovement(-movementSpeed - extra, game);
        } else if(inputKey == Input.KEY_RIGHT) {
            xMovement(movementSpeed + extra, game);
        }
    }

    @Override
    public void keyPressed(int key, char c) {
        super.keyPressed(key, c);
        if(key == Input.KEY_SPACE && onGround) {
            onGround = false;
            jump = true;
            gravCap = 0;
            gravSpeed = 0;
            if(collidingBlock.isOneWay() && inputStack.contains(Input.KEY_DOWN)) {
            	jumpSpeed = 3.0;
            	skipOneWay = true;
            }
        } else if(key == Input.KEY_LSHIFT) {
            sprint = true;
        } else if(key == Input.KEY_K) {
            destroy();
        } else if(key == Input.KEY_UP) {
            ((Game) game).getCurGameMap().getCamera().moveTo(new Vector2f(shape.getCenterX(), shape.getCenterY() - 90), 2);
        }
        changeCurAnimation(key);
    }

    @Override
    public void keyReleased(int key, char c) {
        releaseAnimation(key, false);
        super.keyReleased(key, c);
        if(key == Input.KEY_SPACE) {
            jump = false;
            gravCap = DEF_GRAV_CAP;
            jumpSpeed = DEF_JUMP_SPEED;
        } else if(key == Input.KEY_LSHIFT) {
            sprint = false;
        } else if(key == Input.KEY_UP) {
            ((Game) game).getCurGameMap().getCamera().moveTo(new Vector2f(shape.getCenterX(), shape.getCenterY()), 2);
        }
    }
    
    private void jump(StateBasedGame game) {
        if(jump) {
            if(jumpSpeed > -1) {
                yMovement((int)-Math.ceil(jumpSpeed), game);
                jumpSpeed -= .5;
            } else {
                jump = false;
                jumpSpeed = DEF_JUMP_SPEED;
                gravCap = DEF_GRAV_CAP;
                gravSpeed = DEF_GRAV_SPEED;
            }
        }
    }
    
    @Override
    public int getRenderPriority() {
        return 1000;
    }
    
    @Override
    protected void destroy() {
        shape.setLocation(186, 500);
        movementLine = new Path(shape.getCenterX(), shape.getCenterY());
    }

    private void checkDeath() {
        if(shape.getY() > 1000) {
            destroy();
        }
    }
    
    private void changeCurAnimation(int inputKey) {
        if(inputKey == Input.KEY_LEFT) {
            animationStack.push(rm.getAnimation("playerWalkLeft"));
            removeNonMovementAnimations();
        } else if(inputKey == Input.KEY_RIGHT) {
            animationStack.push(rm.getAnimation("playerWalkRight"));
            removeNonMovementAnimations();
        } else if(inputKey == Input.KEY_SPACE) {
            //if(dir.equals("left")) animationStack.push(rm.getAnimation("playerJumpLeft"));
            //else animationStack.push(rm.getAnimation("playerJumpRight"));
            removeNonMovementAnimations();
        } else if(inputKey == Input.KEY_UP && inMoveStack.isEmpty()) {
            //if(dir.equals("left")) animationStack.push(rm.getAnimation("playerLookUpLeft"));
            //else animationStack.push(rm.getAnimation("playerLookUpRight"));
        } else if(inputKey == Input.KEY_DOWN && inMoveStack.isEmpty()) {
            //if(dir.equals("left")) animationStack.push(rm.getAnimation("playerLookDownLeft"));
            //else animationStack.push(rm.getAnimation("playerLookDownRight"));
        }
    }
    
    private void releaseAnimation(int inputKey, boolean pastRelease) {
        if(inputKey == Input.KEY_UP) {
            if(dir.equals("left") || pastRelease) {
                //rm.getAnimation("playerLookUpLeft").restart();
                //animationStack.remove(rm.getAnimation("playerLookUpLeft"));
            } 
            if(dir.equals("right") || pastRelease) {
                //rm.getAnimation("playerLookUpRight").restart();
                //animationStack.remove(rm.getAnimation("playerLookUpRight"));
            }
        } else if(inputKey == Input.KEY_DOWN) {
            if(dir.equals("left") || pastRelease) {
                //rm.getAnimation("playerLookDownLeft").restart();
                //animationStack.remove(rm.getAnimation("playerLookDownLeft"));
            } 
            if(dir.equals("right") || pastRelease) {
                //rm.getAnimation("playerLookDownRight").restart();
                //animationStack.remove(rm.getAnimation("playerLookDownRight"));
            }
        } else if(inputKey == Input.KEY_SPACE) {
            if(dir.equals("left") || pastRelease) {
                //rm.getAnimation("playerJumpLeft").restart();
                //animationStack.remove(rm.getAnimation("playerJumpLeft"));
            } 
            if(dir.equals("right") || pastRelease) {
                //rm.getAnimation("playerJumpRight").restart();
                //animationStack.remove(rm.getAnimation("playerJumpRight"));
            }
        } else if(inputKey == Input.KEY_LEFT) {
            animationStack.remove(rm.getAnimation("playerWalkLeft"));
        } else if(inputKey == Input.KEY_RIGHT) {
            animationStack.remove(rm.getAnimation("playerWalkRight"));
        }
    }
    
    private void removeNonMovementAnimations() {
        releaseAnimation(Input.KEY_DOWN, true);
        releaseAnimation(Input.KEY_UP, true);
    }
}
