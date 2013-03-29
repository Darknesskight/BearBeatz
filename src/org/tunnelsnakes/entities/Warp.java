package org.tunnelsnakes.entities;

import org.newdawn.slick.geom.Shape;

public class Warp extends Entity {
	/* Type of the warp */
	private String type; 
	
	/* Value of the Warp */
	private String value;

	public Warp(Shape shape, GameMap map) {
		super(shape, map);
	}
	
	public Warp(Shape shape, GameMap map, String type, String value) {
		this(shape, map);
		this.type = type;
		this.value = value;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}


}
