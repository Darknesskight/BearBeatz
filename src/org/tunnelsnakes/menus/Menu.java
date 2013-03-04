package org.tunnelsnakes.menus;

import java.util.ArrayList;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.KeyListener;
import org.newdawn.slick.state.StateBasedGame;
import org.tunnelsnakes.Game;

public class Menu implements KeyListener {
    protected ArrayList<Option[]> options = new ArrayList<Option[]>();
    
    protected int curOption = 0;
    
    private int fontSize;
    
    protected StateBasedGame game;
    
    protected int numPages = 0;
    
    protected int curPage = 0;
    
    public Menu(int fontSize) {
        this.fontSize = fontSize;
    }
    
    protected Option[] loadOptions(int x, int y, String... optionTitles) {
        Option[] optionPage = new Option[optionTitles.length];
        for(int i = 0; i < optionTitles.length; i++) {
            if(i == 0) {
                optionPage[i] = new Option(optionTitles[i], x, y, true);
            } else {
                optionPage[i] = new Option(optionTitles[i], x, optionPage[i-1].getY() + (int)(this.fontSize * 1.5), false);
            }
        }
        numPages++;
        return optionPage;
    }

    public void init(GameContainer gc, StateBasedGame game) {
        this.game = game;
        Input input = gc.getInput();
        input.enableKeyRepeat();
        for(int i = 0; i < options.get(curPage).length; i++) {
            options.get(curPage)[i].init(gc, game);
        }
        input.addKeyListener(this);
    }

    public void update(GameContainer gc, StateBasedGame game, int delta) {
        for(int i = 0; i < options.get(curPage).length; i++) {
            options.get(curPage)[i].update(gc, game, delta);
        }
    }

    public void render(GameContainer gc, StateBasedGame game, Graphics g) {
        g.scale(1/((Game) game).getCurGameMap().getCamera().getScale(), 1/((Game) game).getCurGameMap().getCamera().getScale());
        for(int i = 0; i < options.get(curPage).length; i++) {
            options.get(curPage)[i].render(gc, game, g);
        }
        g.scale(((Game) game).getCurGameMap().getCamera().getScale(), ((Game) game).getCurGameMap().getCamera().getScale());
    }
    

    public void keyPressed(int key, char c) {
        if(key == Input.KEY_UP) {
            this.setCurOption(curOption - 1);
            if(curOption < 0) this.setCurOption(options.get(curPage).length - 1);
        } else if(key == Input.KEY_DOWN) {
            this.setCurOption(curOption + 1);
            if(curOption == options.get(curPage).length) this.setCurOption(0);
        }
    }

    public void keyReleased(int key, char c) {
    }

    public void inputEnded() {
    }

    public void inputStarted() {
    }

    public boolean isAcceptingInput() {
        return true;
    }

    public void setInput(Input arg0) {
    }
    
    public void setCurOption(int curOption) {
        this.curOption = curOption;
        for(int i = 0; i < options.get(curPage).length; i++) {
            if(i != curOption) options.get(curPage)[i].setSelected(false);
            else options.get(curPage)[i].setSelected(true);
        }
    }
}
