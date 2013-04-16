package org.tunnelsnakes.states;


import java.util.Collections;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;
import org.tunnelsnakes.Game;
import org.tunnelsnakes.entities.GameMap;
import org.tunnelsnakes.entities.Player;
import org.tunnelsnakes.geom.SmRectangle;
import org.tunnelsnakes.util.ResourceManager;

public class MainMenuState extends BearState {
    
    public void init(GameContainer gc, StateBasedGame game) throws SlickException { 
    	
    	try {
    		map = new GameMap(ResourceManager.getMap("title"), 3.0f);
    		Game.getCamera().setMap(map);
    		player = new Player(new SmRectangle(0, 0, 42, 30), map);
            player.init(gc, game);
            super.init(gc, game);
    	} catch(Exception e) {}
    	
    }
    
    public void update(GameContainer gc, StateBasedGame game, int delta) throws SlickException {
    	super.update(gc, game, delta);
        player.update(gc, game, delta);
        map.update(gc, game, delta);
        if(gc.getInput().isKeyPressed(Input.KEY_D)) {
            if(((Game) game).isDebug()) ((Game) game).setDebug(false);
            else ((Game) game).setDebug(true);
        }
    }

    public void render(GameContainer gc, StateBasedGame game, Graphics g) throws SlickException {
		super.render(gc, game, g);
    }
	
	@Override
	public void enter(GameContainer gc, StateBasedGame game) throws SlickException {
		super.enter(gc, game);
		System.out.println("entering MainMenuState");
	}

    @Override
    public int getID() {
        return Game.MAIN_MENU_STATE_ID;
    }



}
