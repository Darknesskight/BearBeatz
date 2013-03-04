package org.tunnelsnakes.states;

import java.util.Collections;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.tunnelsnakes.Game;
import org.tunnelsnakes.util.RenderableComparator;

public class InGameState extends BasicGameState {
    
    public void init(GameContainer gc, StateBasedGame game) throws SlickException {
        ((Game) game).getPlayer().init(gc, game);
    }

    public void update(GameContainer gc, StateBasedGame game, int delta) throws SlickException {
        ((Game) game).getPlayer().update(gc, game, delta);
        ((Game) game).getCurGameMap().update(gc, game, delta);
        if(gc.getInput().isKeyPressed(Input.KEY_D)) {
            if(((Game) game).isDebug()) ((Game) game).setDebug(false);
            else ((Game) game).setDebug(true);
        }
    }

    public void render(GameContainer gc, StateBasedGame game, Graphics g) throws SlickException {
        g.scale(((Game) game).getCurGameMap().getCamera().getScale(), ((Game) game).getCurGameMap().getCamera().getScale());
        g.translate(-((Game) game).getCurGameMap().getCamera().getX(), -((Game) game).getCurGameMap().getCamera().getY());
        Collections.sort(((Game) game).getRenderQueue(), new RenderableComparator());
        for(int i = ((Game) game).getRenderQueue().size()-1; i >= 0; i--) {
            ((Game) game).getRenderQueue().get(i).render(gc, game, g);
        }
    }

    @Override
    public int getID() {
        return Game.IN_GAME_STATE_ID;
    }

}
