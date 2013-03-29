package org.tunnelsnakes.states;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;
import org.tunnelsnakes.Game;
import org.tunnelsnakes.entities.GameMap;
import org.tunnelsnakes.entities.Player;
import org.tunnelsnakes.geom.SmRectangle;
import org.tunnelsnakes.util.ResourceManager;

public class InGameState extends BearState {
    
	@Override
    public void init(GameContainer gc, StateBasedGame game) throws SlickException {
    	try {
    		map = new GameMap(ResourceManager.getMap("level1"));
    		System.out.println(map + " SDFSDFSDSD");
    		Game.getCamera().setMap(map);
    		player = new Player(new SmRectangle(0, 0, 32, 32), map);
            player.init(gc, game);
    	} catch(Exception e) {}
    	super.init(gc, game);
    }

	@Override
    public void update(GameContainer gc, StateBasedGame game, int delta) throws SlickException {
		super.update(gc, game, delta);
        player.update(gc, game, delta);
        map.update(gc, game, delta);
        if(gc.getInput().isKeyPressed(Input.KEY_D)) {
            if(((Game) game).isDebug()) ((Game) game).setDebug(false);
            else ((Game) game).setDebug(true);
        } else if(gc.getInput().isKeyPressed(Input.KEY_ESCAPE)) {
            game.enterState(Game.PAUSE_STATE_ID, new FadeOutTransition(), new FadeInTransition());
        }
    }

	@Override
    public void render(GameContainer gc, StateBasedGame game, Graphics g) throws SlickException {
		super.render(gc, game, g);
    }

    @Override
    public int getID() {
        return Game.IN_GAME_STATE_ID;
    }

}
