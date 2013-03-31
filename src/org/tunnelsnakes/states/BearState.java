package org.tunnelsnakes.states;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.tiled.TiledMap;
import org.tunnelsnakes.Game;
import org.tunnelsnakes.attributes.Renderable;
import org.tunnelsnakes.entities.GameMap;
import org.tunnelsnakes.entities.MapLayer;
import org.tunnelsnakes.entities.Player;
import org.tunnelsnakes.util.RenderableComparator;

public class BearState extends BasicGameState {

    
    protected int stateMap = 0;
  
    protected Player player;
    
    protected GameMap map;
    
    protected boolean PauseState = false;
    
    protected boolean PreviouslyEntered = false;
    
    //Queue which contains the renderable objects
    protected List<Renderable> renderQueue = new ArrayList<Renderable>();
    

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
    
    public void enter(GameContainer gc, StateBasedGame game) {
        
    	gc.getInput().clearKeyPressedRecord();
		if(!PreviouslyEntered && !PauseState){
			TiledMap tmap = map.getMap();
			int id;
			for(int i = 0; i < tmap.getLayerCount(); i++) {
				for(int k = 0; k < tmap.getWidth(); k++) {
					for(int j = 0; j < tmap.getHeight(); j++) {
						id = tmap.getTileId(k, j, i);
						if(Boolean.parseBoolean(tmap.getTileProperty(id, "startingPos", "false"))) {
							player.getShape().setLocation(k * tmap.getTileWidth(), j * tmap.getTileHeight());
						}
					}
				}
			}
			 map.update(gc, game, 1);
		}
		Game.getCamera().setMap(map);
	        PreviouslyEntered = true;
        
    }
    
    public void leave(GameContainer container, StateBasedGame game) throws SlickException {
        System.out.println("IngameState.leave() is called");
//        music.stop();
     }

	@Override
	public int getID() {
		return -1;
	}

}
