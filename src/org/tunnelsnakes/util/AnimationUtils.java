package org.tunnelsnakes.util;

import org.newdawn.slick.Animation;

import org.newdawn.slick.Image;

public final class AnimationUtils {
    public static Animation returnFlippedAnimation(Animation animation) {
        Image[] imgs = new Image[animation.getFrameCount()];
        for(int i = 0; i < imgs.length; i++) {
            imgs[i] = animation.getImage(i).getFlippedCopy(true, false);
        }
        return new Animation(imgs, animation.getDurations());
    }

}
