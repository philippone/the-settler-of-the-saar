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

	@Override
	public boolean equals(Object o) {
		if (o instanceof Location) {
			if ((((Location)o).getX() == this.x) && (((Location)o).getY() == this.y) && (((Location)o).getOrientation() == this.orientation)) return true;
			else return false;
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		return x * 271 + y * 199 + orientation;
	}
	
}
