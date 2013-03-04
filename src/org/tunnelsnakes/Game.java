package org.tunnelsnakes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.util.ResourceLoader;
import org.tunnelsnakes.attributes.Renderable;
import org.tunnelsnakes.entities.GameMap;
import org.tunnelsnakes.entities.Player;
import org.tunnelsnakes.geom.SmRectangle;
import org.tunnelsnakes.states.InGameState;
import org.tunnelsnakes.states.LoadState;
import org.tunnelsnakes.states.MainMenuState;
import org.tunnelsnakes.util.Config;
import org.tunnelsnakes.util.ResourceManager;

/**
 * Game contains all the base information related to the game's states and various
 * other attributes. Game is passed among the init, update, and render methods used
 * throughout so many of its attributes are accessible wherever you are
 * 
 * @author Tucker Lein
 *
 */
public class Game extends StateBasedGame {
    //All State IDs
    public static final int SPLASH_STATE_ID = 0;
    public static final int LOAD_STATE_ID = 1;
    public static final int MAIN_MENU_STATE_ID = 2;
    public static final int IN_GAME_STATE_ID = 3;
    
    //List containing all GameMaps in the game
    private ArrayList<GameMap> maps;
    
    //Index of the current GameMap
    private int curGameMap = 0;
    
    //ResourceManager for the project
    private ResourceManager rm;

    //Player object, main controlled entity in the game
    private Player player = new Player(new SmRectangle(32, 32, 42, 29));
    
    //debug boolean, true if debug mode is on
    private boolean debug = false;
    
    //Queue which contains the renderable objects
    private List<Renderable> renderQueue = new ArrayList<Renderable>();

    /**
     * Create new game with specified window name
     * 
     * @param windowName name of the window
     */
    public Game(String windowName) {
        super(windowName);
        maps = new ArrayList<GameMap>();
        rm = new ResourceManager("res/loaders/images.xml", 
                "res/loaders/maps.xml", 
                "res/loaders/music.xml", 
                "res/loaders/sounds.xml",
                "res/loaders/fonts.xml",
                "res/loaders/animations.xml");
    }

    /**
     * @see org.newdawn.slick.state.StateBasedGame#initStatesList(org.newdawn.slick.GameContainer)
     */
    @Override
    public void initStatesList(GameContainer gc) throws SlickException {
        this.addState(new LoadState(this.rm));
        this.addState(new MainMenuState());
        this.addState(new InGameState());
    }
    
    /**
     * Main method, loads in the game's properties and sets up the GameContainer's 
     * settings.
     * 
     * @param args
     */
    public static void main(String[] args) {
        Properties props = new Properties();
        try {
            props.load(ResourceLoader.getResourceAsStream("game.properties"));
            Config config = new Config(props);
            Game game = new Game("PlatformerState");
            AppGameContainer app = new AppGameContainer(game);
            app.setIcons( new String[] {"res/icon/16x16.png", "res/icon/32x32.png"} );  //load icons
            app.setDisplayMode(config.getInteger(Config.WINDOW_WIDTH_CONFIG_KEY), 
                    config.getInteger(Config.WINDOW_HEIGHT_CONFIG_KEY), 
                    config.getBoolean(Config.WINDOW_FULLSCREEN_CONFIG_KEY));
            app.setTargetFrameRate(60);
            app.setShowFPS(false);
            app.start();
        } catch(IOException e) {
            e.printStackTrace();
        } catch(SlickException e) {
            e.printStackTrace();
        }
    }

    /**
     * Adds a GameMap to the list of all maps
     * 
     * @param gameMap GameMap to be added
     */
    public void addGameMap(GameMap gameMap) {
        maps.add(gameMap);
    }

    /**
     * Returns the current GameMap being used by the game
     * 
     * @return the current GameMap
     */
    public GameMap getCurGameMap() {
        return maps.get(curGameMap);
    }
    
    /**
     * Getter for the game's ResourceManager
     * 
     * @return the ResourceManager
     */
    public ResourceManager getResourceManager() {
        return rm;
    }

    /**
     * Getter for player
     * 
     * @return player
     */
    public Player getPlayer() {
        return player;
    }
    
    /**
     * Getter for debug
     * 
     * @return debug
     */
    public boolean isDebug() {
        return debug;
    }
    
    /**
     * Setter for debug
     * 
     * @param debug
     */
    public void setDebug(boolean debug) {
        this.debug = debug;
    }
    
    /**
     * Getter for renderQueue
     * 
     * @return renderQueue
     */
    public List<Renderable> getRenderQueue() {
        return renderQueue;
    }
}
