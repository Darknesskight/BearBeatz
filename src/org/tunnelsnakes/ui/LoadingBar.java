package org.tunnelsnakes.ui;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

public class LoadingBar {
    private int x;
    private int y;
    private int width;
    private int height;
    private int progress = 0;
    
    public LoadingBar(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }
    
    public void setProgress(int progress) {
        this.progress = progress;
    }

    public void render(GameContainer gc, StateBasedGame game, Graphics g) {
        g.setColor(Color.white);
        g.drawRect(x, y, width, height);
        g.setColor(Color.green);
        g.fillRect(x + 1, y + 1, (int)(width * (progress/100.0)) - 1, height - 1);
    }
}
