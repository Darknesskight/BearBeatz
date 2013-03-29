package org.tunnelsnakes.states;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;
import org.tunnelsnakes.menus.Menu;
import org.tunnelsnakes.menus.StartMenu;
import org.tunnelsnakes.Game;

public class PauseState extends BearState {
    private Menu menu;

	@Override
	public void init(GameContainer gc, StateBasedGame game) throws SlickException {
		
	}

    @Override 
    public void enter(GameContainer gc, StateBasedGame game) {
    	super.enter(gc, game);
        menu = new StartMenu(30);
        menu.init(gc, game);
    }
    
	@Override
	public void update(GameContainer gc, StateBasedGame game, int delta) throws SlickException {
		menu.update(gc, game, delta);
	}

	@Override
	public void render(GameContainer gc, StateBasedGame game, Graphics g) throws SlickException {
        if(loaded) {
            g.scale(Game.getCamera().getScale(), Game.getCamera().getScale());
            g.setColor(Color.blue);
            g.fillRect(0, 0, gc.getWidth(), gc.getHeight());
            menu.render(gc, game, g);
        }
	}

	@Override
	public int getID() {
		return Game.PAUSE_STATE_ID;
	}

}
