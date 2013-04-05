package org.tunnelsnakes.states;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.tiled.TiledMap;
import org.tunnelsnakes.Game;
import org.tunnelsnakes.attributes.Renderable;
import org.tunnelsnakes.entities.GameMap;
import org.tunnelsnakes.entities.MapLayer;
import org.tunnelsnakes.entities.Player;
import org.tunnelsnakes.util.RenderableComparator;
import org.tunnelsnakes.util.ResourceManager;
import org.tunnelsnakes.util.KeyManager;

public class BearState extends BasicGameState {
	
    protected int stateMap = 0;
  
    protected Player player;
    
    protected GameMap map;
    
    protected boolean PauseState = false;
    
    protected boolean PreviouslyEntered = false;
    
    protected String musicRef = "";
    
    private float musicPos = 0;
    
    private static KeyManager keys;
    
    //Queue which contains the renderable objects
    protected List<Renderable> renderQueue = new ArrayList<Renderable>();
    
    @Override
    public boolean isAcceptingInput(){
		return false;
    }

	@Override
	public void init(GameContainer gc, StateBasedGame game) throws SlickException {
    	renderQueue.clear();
		try {
			renderQueue.add(player);
			renderQueue.add(map);
			for(MapLayer l : map.getLayers()) {
				renderQueue.add(l);
			}
		} catch(Exception e) {
		}
	}

	@Override
	public void update(GameContainer gc, StateBasedGame game, int delta) throws SlickException {
		Game.getCamera().getPOI().set(player.getShape().getCenterX(), player.getShape().getCenterY());
        Game.getCamera().update(gc, game, delta);
	}

	@Override
	public void render(GameContainer gc, StateBasedGame game, Graphics g)throws SlickException {
        g.scale(Game.getCamera().getScale(), Game.getCamera().getScale());
        g.translate(-Game.getCamera().getX(), -Game.getCamera().getY());
        Collections.sort(renderQueue, new RenderableComparator());
        for(int i = renderQueue.size()-1; i >= 0; i--) {
            renderQueue.get(i).render(gc, game, g);
        }
	}
    
	@Override
	public void enter(GameContainer gc, StateBasedGame game) throws SlickException {
		if(!musicRef.equals("")) {
	        ResourceManager.getMusic(musicRef).setPosition(musicPos);
	        ResourceManager.getMusic(musicRef).loop();
		}
        System.out.println(musicRef);
    	gc.getInput().clearKeyPressedRecord();
    	Game.getCamera().setScale(map.getScale());
		Game.getCamera().setMap(map);
		if(!PreviouslyEntered && !PauseState){
			TiledMap tmap = map.getMap();
			int id;
			for(int i = 0; i < tmap.getLayerCount(); i++) {
				for(int k = 0; k < tmap.getWidth(); k++) {
					for(int j = 0; j < tmap.getHeight(); j++) {
						id = tmap.getTileId(k, j, i);
						if(Boolean.parseBoolean(tmap.getTileProperty(id, "startingPos", "false"))) {
							map.setStartingPos(new Vector2f(k * tmap.getTileWidth(), j * tmap.getTileHeight()));
							player.getShape().setLocation(k * tmap.getTileWidth(), j * tmap.getTileHeight());
						}
					}
				}
			}
			 map.update(gc, game, 1);
		}
        PreviouslyEntered = true;
    }
    
	@Override
    public void leave(GameContainer container, StateBasedGame game) throws SlickException {
        System.out.println("IngameState.leave() is called");
        if(!musicRef.equals("")) {
	        musicPos = ResourceManager.getMusic(musicRef).getPosition();
	        ResourceManager.getMusic(musicRef).stop();
        }
     }

	@Override
	public int getID() {
		return -1;
	}

	public void handleKeyPress(int key) {
		player.keyPressed(key);
	}

	public void handleKeyRelease(int key) {
		player.keyReleased(key);
	}

}
