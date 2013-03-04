package org.tunnelsnakes.geom;

import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

@SuppressWarnings("serial")
public class SmRectangle extends Rectangle {

    public SmRectangle(float x, float y, float width, float height) {
        super(x, y, width, height);
    }
    
    @Override
    public boolean intersects(Shape s) {
        return (x + width) > s.getX() && (y + height) > s.getY() && 
                x < (s.getX() + s.getWidth()) && y < (s.getY() + s.getHeight());
    }

}
