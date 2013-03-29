package org.tunnelsnakes.menus;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;
import org.tunnelsnakes.util.ResourceManager;

public class Option {
    private String title;
    
    private int x, y;
    
    private boolean selected;
    
    public Option(String title, int x, int y, boolean selected) {
        this.title = title;
        this.x = x;
        this.y = y;
        this.selected = selected;
    }

    public void init(GameContainer gc, StateBasedGame game) {
    }

    public void update(GameContainer gc, StateBasedGame game, int delta) {
        
    }

    public void render(GameContainer gc, StateBasedGame game, Graphics g) {
        if(selected) g.setColor(Color.white);
        else g.setColor(Color.darkGray);
        g.setFont(ResourceManager.getFont("m04b"));
        g.drawString(title, x, y);
        g.setColor(Color.black);
        g.setFont(ResourceManager.getFont("m04"));
        g.drawString(title, x, y);
    }
    
    public int getY() {
        return y;
    }
    
    public int getX() {
        return x;
    }
    
    public boolean getSelected() {
        return selected;
    }
    
    public String getTitle() {
        return title;
    }
    
    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
