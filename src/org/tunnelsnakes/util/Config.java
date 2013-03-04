package org.tunnelsnakes.util;

import java.util.Properties;

public class Config {
    //All property keys
    public static final String WINDOW_WIDTH_CONFIG_KEY = "window.width";
    public static final String WINDOW_HEIGHT_CONFIG_KEY = "window.height";
    public static final String WINDOW_FULLSCREEN_CONFIG_KEY = "window.fullscreen";
    
    //Properties containing all the game's properties
    private Properties props;

    public Config(Properties props) {
        this.props = props;
    }

    public String getString(String key) {
        return props.getProperty(key);
    }

    public Integer getInteger(String key) {
        return Integer.parseInt(props.getProperty(key));
    }

    public Boolean getBoolean(String key) {
        return Boolean.parseBoolean(props.getProperty(key));
    }

}
