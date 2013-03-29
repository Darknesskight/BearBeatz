package org.tunnelsnakes.menus;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;
import org.tunnelsnakes.Game;
import org.tunnelsnakes.util.ResourceManager;

public class StartMenu extends Menu {

    public StartMenu(int fontSize) {
        super(fontSize);
    }
    
    @Override
    public void init(GameContainer gc, StateBasedGame game) {
        options.add(loadOptions((int)(gc.getWidth()*.05), (int)(gc.getHeight()*.1), "Resume", "Exit to Main Menu"));
        super.init(gc, game);
    }
    
    @Override
    public void render(GameContainer gc, StateBasedGame game, Graphics g) {
    	g.drawImage(ResourceManager.getImage("pauseMenu"), 0, 0);
        super.render(gc, game, g);
    }

    @Override
    public void keyPressed(int key, char c) {
        super.keyPressed(key, c);
        if(key == Input.KEY_ENTER) {
            click();
        }
    }
    
    private void click() {
        if(curPage == 0) {
            if(options.get(curPage)[curOption].getTitle().equals("Resume")) {
                game.enterState(Game.IN_GAME_STATE_ID, new FadeOutTransition(), new FadeInTransition());
            } else if(options.get(curPage)[curOption].getTitle().equals("Exit to Main Menu")) {
                game.enterState(Game.MAIN_MENU_STATE_ID, new FadeOutTransition(), new FadeInTransition());
            }
        }
    }
}
