package de.unisaarland.cs.sopra.common.model;

public class Location extends Point {

	protected final int orientation;
	
	public Location(int y, int x, int orientation) {
		super(y, x);
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
		return y * 271 + x * 199 + orientation;
	}
	@Override
	public String toString() {
		return "("+y+","+x+","+orientation+")";
	}
}
