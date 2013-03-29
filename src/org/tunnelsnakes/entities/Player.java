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
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

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
    
    
	//boolean deteResourceManagerining if player is currently jumping
    private boolean jump = false;
    
    //boolean deteResourceManagerining if player is currently sprinting
    private boolean sprint = false;
    
    //speed of the jump
    private double jumpSpeed = DEF_JUMP_SPEED;
    
    //speed to add to the player's movement speed for sprinting
    private int sprintSpeed = 4;
    
    
    //Stack containing the animations being used by the player. Only the top of the stack will render
    private Stack<Animation> animationStack = new Stack<Animation>();
    
    /**
     * Constructs new Player with given shape
     * 
     * @param shape Shape used for collisions and positions
     */
    public Player(Shape shape, GameMap map) {
        super(shape, map);
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
    }
    
    public void setupAnimations(StateBasedGame game) {
        //Set and load player's static 'animation'
        Image[] player = new Image[] { ResourceManager.getAnimation("playerWalkRight").getImage(0) };
        ResourceManager.load("playerRight", new Animation(player, 1));
        ResourceManager.load("playerLeft", AnimationUtils.returnFlippedAnimation(new Animation(player, 1)));
        animationStack.push(ResourceManager.getAnimation("playerRight"));
    }
    
    @Override
    public void update(GameContainer gc, StateBasedGame game, int delta) {
        super.update(gc, game, delta);
        if(!getInMoveStack().isEmpty()) getMovement(getInMoveStack().peek(), game);
        if(animationStack.size() == 1) {
            if(dir.equals("left")) animationStack.set(0, ResourceManager.getAnimation("playerLeft"));
            if(dir.equals("right")) animationStack.set(0, ResourceManager.getAnimation("playerRight"));
        }
        jump(game);
        checkDeath();
        checkWarps(gc, game);
    }

	private void checkWarps(GameContainer gc, StateBasedGame game) {
		for(Warp w : map.getWarps()) {
			if(collidingBlock.getShape().intersects(w.getShape()) && onGround && shape.getY() < w.getShape().getY()) {
				if(w.getType().equals("state")) {
					if(w.getValue().equals("inGame") && gc.getInput().isKeyDown(Input.KEY_ENTER)) {
			            game.enterState(Game.IN_GAME_STATE_ID, new FadeOutTransition(), new FadeInTransition());
					}
				} else if(w.getType().equals("container") && gc.getInput().isKeyDown(Input.KEY_ENTER)) {
					if(w.getValue().equals("exit")) {
						System.exit(0);
					}
				}
			}
		}
	}

	@Override
    public void render(GameContainer gc, StateBasedGame game, Graphics g) {
        super.render(gc, game, g);
        if(animationStack.isEmpty()) setupAnimations(game);
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
            if(collidingBlock.isOneWay() && getInputStack().contains(Input.KEY_DOWN)) {
            	jumpSpeed = 3.0;
            	skipOneWay = true;
            }
        } else if(key == Input.KEY_LSHIFT) {
            sprint = true;
        } else if(key == Input.KEY_K) {
            destroy();
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
        shape.setLocation(32, 32);
        movementLine = new Path(shape.getCenterX(), shape.getCenterY());
    }

    private void checkDeath() {
        if(shape.getY() > 1000) {
            destroy();
        }
    }
    
    private void changeCurAnimation(int inputKey) {
        if(inputKey == Input.KEY_LEFT) {
            animationStack.push(ResourceManager.getAnimation("playerWalkLeft"));
            removeNonMovementAnimations();
        } else if(inputKey == Input.KEY_RIGHT) {
            animationStack.push(ResourceManager.getAnimation("playerWalkRight"));
            removeNonMovementAnimations();
        } else if(inputKey == Input.KEY_SPACE) {
            //if(dir.equals("left")) animationStack.push(ResourceManager.getAnimation("playerJumpLeft"));
            //else animationStack.push(ResourceManager.getAnimation("playerJumpRight"));
            removeNonMovementAnimations();
        } else if(inputKey == Input.KEY_UP && getInMoveStack().isEmpty()) {
            //if(dir.equals("left")) animationStack.push(ResourceManager.getAnimation("playerLookUpLeft"));
            //else animationStack.push(ResourceManager.getAnimation("playerLookUpRight"));
        } else if(inputKey == Input.KEY_DOWN && getInMoveStack().isEmpty()) {
            //if(dir.equals("left")) animationStack.push(ResourceManager.getAnimation("playerLookDownLeft"));
            //else animationStack.push(ResourceManager.getAnimation("playerLookDownRight"));
        }
    }
    
    private void releaseAnimation(int inputKey, boolean pastRelease) {
        if(inputKey == Input.KEY_UP) {
            if(dir.equals("left") || pastRelease) {
                //ResourceManager.getAnimation("playerLookUpLeft").restart();
                //animationStack.remove(ResourceManager.getAnimation("playerLookUpLeft"));
            } 
            if(dir.equals("right") || pastRelease) {
                //ResourceManager.getAnimation("playerLookUpRight").restart();
                //animationStack.remove(ResourceManager.getAnimation("playerLookUpRight"));
            }
        } else if(inputKey == Input.KEY_DOWN) {
            if(dir.equals("left") || pastRelease) {
                //ResourceManager.getAnimation("playerLookDownLeft").restart();
                //animationStack.remove(ResourceManager.getAnimation("playerLookDownLeft"));
            } 
            if(dir.equals("right") || pastRelease) {
                //ResourceManager.getAnimation("playerLookDownRight").restart();
                //animationStack.remove(ResourceManager.getAnimation("playerLookDownRight"));
            }
        } else if(inputKey == Input.KEY_SPACE) {
            if(dir.equals("left") || pastRelease) {
                //ResourceManager.getAnimation("playerJumpLeft").restart();
                //animationStack.remove(ResourceManager.getAnimation("playerJumpLeft"));
            } 
            if(dir.equals("right") || pastRelease) {
                //ResourceManager.getAnimation("playerJumpRight").restart();
                //animationStack.remove(ResourceManager.getAnimation("playerJumpRight"));
            }
        } else if(inputKey == Input.KEY_LEFT) {
            animationStack.remove(ResourceManager.getAnimation("playerWalkLeft"));
        } else if(inputKey == Input.KEY_RIGHT) {
            animationStack.remove(ResourceManager.getAnimation("playerWalkRight"));
        }
    }
    
    private void removeNonMovementAnimations() {
        releaseAnimation(Input.KEY_DOWN, true);
        releaseAnimation(Input.KEY_UP, true);
    }
}
