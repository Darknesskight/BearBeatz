package org.tunnelsnakes.states;

import java.util.Iterator;
import java.util.Map.Entry;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;
import org.newdawn.slick.tiled.TiledMap;
import org.tunnelsnakes.Game;
import org.tunnelsnakes.entities.GameMap;
import org.tunnelsnakes.ui.LoadingBar;
import org.tunnelsnakes.util.AnimationUtils;
import org.tunnelsnakes.util.Resource;
import org.tunnelsnakes.util.ResourceManager;

public class LoadState extends BasicGameState {
    
    //Iterators for resources
    private Iterator<Entry<String, Resource>> images, maps, music, fonts, animations;
    
    //time the LoadingState begins
    private long time; 
    
    //time in milliseconds the splash screen will remain at a minimum (if loading times don't surpass this)
    private long delay = 1500;
    
    private Image scaledImg;

    public LoadState() {
        images = ResourceManager.getImgResources().entrySet().iterator();
        maps = ResourceManager.getMapResources().entrySet().iterator();
        music = ResourceManager.getMusicResources().entrySet().iterator();
        fonts = ResourceManager.getFontResources().entrySet().iterator();
        animations = ResourceManager.getAnimationResources().entrySet().iterator();
    }

    public void init(GameContainer gc, StateBasedGame game) throws SlickException {
        time = gc.getTime();
    }

    public void update(GameContainer gc, StateBasedGame game, int delta) throws SlickException {
        if(ResourceManager.getProgress() == 100 && gc.getTime() - time > delay) {

        	game.getState(Game.MAIN_MENU_STATE_ID).init(gc, game);
        	game.getState(Game.IN_GAME_STATE_ID).init(gc, game);
            game.enterState(Game.MAIN_MENU_STATE_ID, new FadeOutTransition(), new FadeInTransition());
        }

        if(images.hasNext()) {
            Resource r = images.next().getValue();
            System.out.println(r.getLocation());
            ResourceManager.load(r.getKey(), new Image(r.getLocation()));
        } else if(maps.hasNext()) {
            Resource r = maps.next().getValue();
            ResourceManager.load(r.getKey(), new TiledMap(r.getLocation(), "res/tilesets"));
        } else if(music.hasNext()) {
            Resource r = music.next().getValue();
            ResourceManager.load(r.getKey(), new Music(r.getLocation()));
        } else if(fonts.hasNext()) {
            Resource r = fonts.next().getValue();
            ResourceManager.load(r.getKey(), new UnicodeFont(r.getLocation(), 30, false, false));
        } else if(animations.hasNext()) {
            Resource r = animations.next().getValue();
            Animation ani = new Animation(new SpriteSheet(new Image(r.getLocation()), r.getTileWidth(), r.getTileHeight()), r.getAnimationSpeed());
            if(r.getFlip()) ani = AnimationUtils.returnFlippedAnimation(ani);
            if(!r.getLooping()) ani.setLooping(false);
            ResourceManager.load(r.getKey(), ani);
        }
    }

    public void render(GameContainer gc, StateBasedGame game, Graphics g) throws SlickException {
        g.setColor(Color.lightGray);
        g.fillRect(0, 0, gc.getWidth(), gc.getHeight());
        //try to render the splash screen (first resource to be loaded), if it's not loaded yet do nothing with the exception
        try {
            scaledImg = ResourceManager.getImage("splash").getScaledCopy(gc.getWidth(), (int)(gc.getWidth() / (16.0/9.0)));
            g.drawImage(scaledImg, 0, (gc.getHeight() - scaledImg.getHeight())/2);
        } catch(RuntimeException e) {}
        
        
        LoadingBar lb = new LoadingBar(5, gc.getHeight() - 35, gc.getWidth() - 10, 30);
        lb.setProgress(ResourceManager.getProgress());
        if(ResourceManager.getProgress() != 100) lb.render(gc, game, g);
    }
    
    @Override
    public void leave(GameContainer gc, StateBasedGame game) {
        try {
            ((Game) game).getState(Game.MAIN_MENU_STATE_ID).init(gc, game);
        } catch (SlickException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getID() {
        return Game.LOAD_STATE_ID;
    }

}