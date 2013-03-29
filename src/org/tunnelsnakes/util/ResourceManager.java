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
    
    private static HashMap<String, Resource> mapResources = new HashMap<String, Resource>();
    private static HashMap<String, Resource> imgResources = new HashMap<String, Resource>();
    private static HashMap<String, Resource> musicResources = new HashMap<String, Resource>();
    private static HashMap<String, Resource> fontResources = new HashMap<String, Resource>();
    private static HashMap<String, Resource> animationResources = new HashMap<String, Resource>();
    private static HashMap<String, TiledMap> maps = new HashMap<String, TiledMap>();
    private static HashMap<String, Image> images = new HashMap<String, Image>();
    private static HashMap<String, Music> music = new HashMap<String, Music>();
    private static HashMap<String, UnicodeFont> fonts = new HashMap<String, UnicodeFont>();
    private static HashMap<String, Animation> animations = new HashMap<String, Animation>();
    
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
    
    public static HashMap<String, Resource> getMapResources() {
        return mapResources;
    }
    
    public static HashMap<String, Resource> getImgResources() {
        return imgResources;
    }

    public static HashMap<String, Resource> getMusicResources() {
        return musicResources;
    }
    
    public static HashMap<String, Resource> getFontResources() {
        return fontResources;
    }
    
    public static HashMap<String, Resource> getAnimationResources() {
        return animationResources;
    }
    
    public static HashMap<String, TiledMap> getMaps() {
        return maps;
    }
    
    public static HashMap<String, Image> getImages() {
        return images;
    }

    public static HashMap<String, Music> getMusic() {
        return music;
    }
    
    public static HashMap<String, UnicodeFont> getFonts() {
        return fonts;
    }
    
    public static HashMap<String, Animation> getAnimations() {
        return animations;
    }
    
    public static void load(String key, TiledMap t) {
        maps.put(key, t);
    }
    
    public static void load(String key, Image i) throws SlickException {
        i.setFilter(Image.FILTER_NEAREST);
        images.put(key, i);
    }
    
    public static void load(String key, Music m) {
        music.put(key, m);
    }
    
    @SuppressWarnings("unchecked")
    public static void load(String key, UnicodeFont f) throws SlickException {
        f.addAsciiGlyphs();
        f.addGlyphs(400, 600);
        f.getEffects().add(new ColorEffect(java.awt.Color.WHITE));
        f.loadGlyphs();
        fonts.put(key, f);
    }
    
    public static void load(String key, Animation a) {
        for(int i = 0; i < a.getFrameCount(); i++) {
            a.getImage(i).setFilter(Image.FILTER_NEAREST);
        }
        animations.put(key, a);
    }
    
    public static TiledMap getMap(String key) {
        if(maps.containsKey(key)) {
            return maps.get(key);
        } else {
            throw new RuntimeException("Map not found: " + key);
        }
    }
    
    public static Image getImage(String key) {
        if(images.containsKey(key)) {
            return images.get(key);
        } else {
            throw new RuntimeException("Image not found: " + key);
        }
    }
    
    public static Music getMusic(String key) {
        if(music.containsKey(key)) {
            return music.get(key);
        } else {
            throw new RuntimeException("Music not found: " + key);
        }
    }
    
    public static UnicodeFont getFont(String key) {
        if(fonts.containsKey(key)) {
            return fonts.get(key);
        } else {
            throw new RuntimeException("Font not found: " + key);
        }
    }
    
    public static Animation getAnimation(String key) {
        if(animations.containsKey(key)) {
            return animations.get(key);
        } else {
            throw new RuntimeException("Animation not found: " + key);
        }
    }
    
    public static int getProgress() {
        return (int) (((float) (maps.size() + images.size() + music.size() + fonts.size() + animations.size())) / 
                (mapResources.size() + imgResources.size() + musicResources.size() + fontResources.size() + animationResources.size()) * 100);
    }

}
