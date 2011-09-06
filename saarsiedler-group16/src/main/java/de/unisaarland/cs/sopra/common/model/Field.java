package de.unisaarland.cs.sopra.common.model;

public class Field {

	private Point point;
	private boolean containsRobber;
	private long number;
	private FieldType fieldType;
	
	public Field(FieldType fieldType, Point point) {
		throw new UnsupportedOperationException();
	}
	
	public Resource getResource(int gewuerfelteZahl) {
		throw new UnsupportedOperationException();
	}
	
	public boolean hasRobber() {
		return this.containsRobber;
	}
	
	public void setRobber(boolean containsRobber) {
		this.containsRobber = containsRobber;
	}
	
	public FieldType getFieldType() {
		return this.fieldType;
	}
	
	public int getNumber() {
		return this.number;
	}
	
	public void setNumber(long number) {
		this.number = number;
	}
	
	public Point getLocation() {
		return this.point;
	}
	
}
