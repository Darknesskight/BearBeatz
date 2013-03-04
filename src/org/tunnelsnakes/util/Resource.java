package org.tunnelsnakes.util;

import javax.xml.bind.annotation.XmlAttribute;

public class Resource {
    private String key;
    
    private String location;
    
    private int animationSpeed, tileWidth, tileHeight; //for animations && sprite sheets
    
    private boolean looping = true;
    
    private boolean flip = false;
    
    @XmlAttribute
    public String getKey() {
        return key;
    }
    
    public void setKey(String key) {
        this.key = key;
    }
    
    @XmlAttribute
    public String getLocation() {
        return location;
    }
    
    public void setLocation(String location) {
        this.location = location;
    }
    
    @XmlAttribute
    public int getAnimationSpeed() {
        return animationSpeed;
    }
    
    public void setAnimationSpeed(int animationSpeed) {
        this.animationSpeed = animationSpeed;
    }
    
    @XmlAttribute
    public int getTileWidth() {
        return tileWidth;
    }
    
    public void setTileWidth(int tileWidth) {
        this.tileWidth = tileWidth;
    }
    
    @XmlAttribute
    public int getTileHeight() {
        return tileHeight;
    }
    
    public void setTileHeight(int tileHeight) {
        this.tileHeight = tileHeight;
    }
    
    @XmlAttribute
    public boolean getLooping() {
        return looping;
    }
    
    public void setLooping(boolean looping) {
        this.looping = looping;
    }
    
    @XmlAttribute
    public boolean getFlip() {
        return flip;
    }
    
    public void setFlip(boolean flip) {
        this.flip = flip;
    }
}
