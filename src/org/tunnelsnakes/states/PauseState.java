package org.tunnelsnakes.states;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;
import org.tunnelsnakes.entities.GameMap;
import org.tunnelsnakes.entities.Player;
import org.tunnelsnakes.geom.SmRectangle;
import org.tunnelsnakes.menus.Menu;
import org.tunnelsnakes.menus.StartMenu;
import org.tunnelsnakes.util.ResourceManager;
import org.tunnelsnakes.Game;

public class PauseState extends BearState {
    private Menu menu;
    private StateBasedGame game;
	@Override
	public void init(GameContainer gc, StateBasedGame game) throws SlickException {
		PauseState = true;
	}

    public void enter(GameContainer gc, StateBasedGame game) {
        menu = new StartMenu(30);
        menu.init(gc, game);
    	this.game = game;
    	Game.getCamera().setScale(3.0f);
    }

    
    @Override
    public void handleKeyPress(int key) {
    	menu.keyPressed(key);
	}
    @Override
    public void handleKeyRelease(int key) {
    	menu.keyReleased(key);
	}
    
	@Override
	public void update(GameContainer gc, StateBasedGame game, int delta) throws SlickException {
		menu.update(gc, game, delta);
	}

	@Override
	public void render(GameContainer gc, StateBasedGame game, Graphics g) throws SlickException {
            g.scale(Game.getCamera().getScale(), Game.getCamera().getScale());
            g.setColor(Color.blue);
            g.fillRect(0, 0, gc.getWidth(), gc.getHeight());
            g.drawImage(ResourceManager.getImage("pauseMenu"), 0, 0);   
            menu.render(gc, game, g);
	}

	@Override
	public int getID() {
		return Game.PAUSE_STATE_ID;
	}
	
	public void leave(GameContainer gc, StateBasedGame game){
	}
	

}
