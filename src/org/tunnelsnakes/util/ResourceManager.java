package org.tunnelsnakes.util;

import java.util.HashMap;

import javax.xml.bind.JAXB;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;
import org.newdawn.slick.tiled.TiledMap;
import org.newdawn.slick.util.ResourceLoader;

public class ResourceManager {
    
    private HashMap<String, Resource> mapResources = new HashMap<String, Resource>();
    private HashMap<String, Resource> imgResources = new HashMap<String, Resource>();
    private HashMap<String, Resource> musicResources = new HashMap<String, Resource>();
    private HashMap<String, Resource> fontResources = new HashMap<String, Resource>();
    private HashMap<String, Resource> animationResources = new HashMap<String, Resource>();
    private HashMap<String, TiledMap> maps = new HashMap<String, TiledMap>();
    private HashMap<String, Image> images = new HashMap<String, Image>();
    private HashMap<String, Music> music = new HashMap<String, Music>();
    private HashMap<String, UnicodeFont> fonts = new HashMap<String, UnicodeFont>();
    private HashMap<String, Animation> animations = new HashMap<String, Animation>();
    
    public ResourceManager(String imgRef, String mapRef, String musicRef, String soundRef, String fontRef, String animationRef) {
        Resources sources = JAXB.unmarshal(ResourceLoader.getResourceAsStream(mapRef), Resources.class);
        for(Resource r : sources.getResource()) {
            mapResources.put(r.getKey(), r);
        }
        
        sources = JAXB.unmarshal(ResourceLoader.getResourceAsStream(imgRef), Resources.class);
        for(Resource r : sources.getResource()) {
            imgResources.put(r.getKey(), r);
        }
        
        sources = JAXB.unmarshal(ResourceLoader.getResourceAsStream(musicRef), Resources.class);
        for(Resource r : sources.getResource()) {
            musicResources.put(r.getKey(), r);
        }
        
        sources = JAXB.unmarshal(ResourceLoader.getResourceAsStream(fontRef), Resources.class);
        for(Resource r : sources.getResource()) {
            fontResources.put(r.getKey(), r);
        }
        
        sources = JAXB.unmarshal(ResourceLoader.getResourceAsStream(animationRef), Resources.class);
        for(Resource r : sources.getResource()) {
            animationResources.put(r.getKey(), r);
        }
    }
    
    public HashMap<String, Resource> getMapResources() {
        return mapResources;
    }
    
    public HashMap<String, Resource> getImgResources() {
        return imgResources;
    }

    public HashMap<String, Resource> getMusicResources() {
        return musicResources;
    }
    
    public HashMap<String, Resource> getFontResources() {
        return fontResources;
    }
    
    public HashMap<String, Resource> getAnimationResources() {
        return animationResources;
    }
    
    public HashMap<String, TiledMap> getMaps() {
        return maps;
    }
    
    public HashMap<String, Image> getImages() {
        return images;
    }

    public HashMap<String, Music> getMusic() {
        return music;
    }
    
    public HashMap<String, UnicodeFont> getFonts() {
        return fonts;
    }
    
    public HashMap<String, Animation> getAnimations() {
        return animations;
    }
    
    public void load(String key, TiledMap t) {
        maps.put(key, t);
    }
    
    public void load(String key, Image i) throws SlickException {
        i.setFilter(Image.FILTER_NEAREST);
        images.put(key, i);
    }
    
    public void load(String key, Music m) {
        music.put(key, m);
    }
    
    @SuppressWarnings("unchecked")
    public void load(String key, UnicodeFont f) throws SlickException {
        f.addAsciiGlyphs();
        f.addGlyphs(400, 600);
        f.getEffects().add(new ColorEffect(java.awt.Color.WHITE));
        f.loadGlyphs();
        fonts.put(key, f);
    }
    
    public void load(String key, Animation a) {
        for(int i = 0; i < a.getFrameCount(); i++) {
            a.getImage(i).setFilter(Image.FILTER_NEAREST);
        }
        animations.put(key, a);
    }
    
    public TiledMap getMap(String key) {
        if(maps.containsKey(key)) {
            return maps.get(key);
        } else {
            throw new RuntimeException("Map not found: " + key);
        }
    }
    
    public Image getImage(String key) {
        if(images.containsKey(key)) {
            return images.get(key);
        } else {
            throw new RuntimeException("Image not found: " + key);
        }
    }
    
    public Music getMusic(String key) {
        if(music.containsKey(key)) {
            return music.get(key);
        } else {
            throw new RuntimeException("Music not found: " + key);
        }
    }
    
    public UnicodeFont getFont(String key) {
        if(fonts.containsKey(key)) {
            return fonts.get(key);
        } else {
            throw new RuntimeException("Font not found: " + key);
        }
    }
    
    public Animation getAnimation(String key) {
        if(animations.containsKey(key)) {
            return animations.get(key);
        } else {
            throw new RuntimeException("Animation not found: " + key);
        }
    }
    
    public int getProgress() {
        return (int) (((float) (maps.size() + images.size() + music.size() + fonts.size() + animations.size())) / 
                (mapResources.size() + imgResources.size() + musicResources.size() + fontResources.size() + animationResources.size()) * 100);
    }

}
