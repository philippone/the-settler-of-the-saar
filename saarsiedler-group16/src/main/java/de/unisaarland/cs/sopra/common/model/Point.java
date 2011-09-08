package de.unisaarland.cs.sopra.common.model;

public class Point {

	private final int x;
	private final int y;
	
	public Point(int y, int x) {
		this.x = x;
		this.y = y;
	}

	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	@Override
	public boolean equals(Object o) {
		if (o instanceof Point) {
			if ((((Point)o).getX() == this.x) && (((Point)o).getY() == this.y)) return true;
			else return false;
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		return x * 199 + y;
	}
	
	
}
