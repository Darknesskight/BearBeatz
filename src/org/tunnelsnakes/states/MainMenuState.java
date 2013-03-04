package org.tunnelsnakes.states;


import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.tunnelsnakes.Game;
import org.tunnelsnakes.menus.MainMenu;
import org.tunnelsnakes.menus.Menu;

public class MainMenuState extends BasicGameState {
    private Menu menu;
    
    private boolean loaded = false;
    
    public void init(GameContainer gc, StateBasedGame game) throws SlickException { 
    }
    
    @Override 
    public void enter(GameContainer gc, StateBasedGame game) {
        menu = new MainMenu(30);
        menu.init(gc, game);
        loaded = true;
    }
    
    @Override
    public void leave(GameContainer gc, StateBasedGame game) {
        gc.getInput().disableKeyRepeat();
    }
    
    public void update(GameContainer gc, StateBasedGame game, int delta) throws SlickException {
        menu.update(gc, game, delta);
    }

    public void render(GameContainer gc, StateBasedGame game, Graphics g) throws SlickException {
        if(loaded) {
            g.scale(((Game) game).getCurGameMap().getCamera().getScale(), ((Game) game).getCurGameMap().getCamera().getScale());
            g.setColor(Color.blue);
            g.fillRect(0, 0, gc.getWidth(), gc.getHeight());
            menu.render(gc, game, g);
        }
    }

    @Override
    public int getID() {
        return Game.MAIN_MENU_STATE_ID;
    }

}
