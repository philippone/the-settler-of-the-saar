package de.unisaarland.cs.sopra.common.model;

public class Location {

	private final int x;
	private final int y;
	private final int orientation;
	
	public Location(int y, int x, int orientation) {
		this.x = x;
		this.y = y;
		this.orientation = orientation;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public int getOrientation() {
		return orientation;
	}

}
