package de.unisaarland.cs.sopra.common.model;

public class Field {

	private Point point;
	private boolean containsRobber;
	private int number;
	private FieldType fieldType;
	
	public Field(FieldType fieldType, Point point) {
		if (fieldType == null) throw new IllegalArgumentException();
		if (point == null) throw new IllegalArgumentException();
		this.fieldType = fieldType;
		this.point = point;
		this.number = -1;
		if (fieldType == FieldType.DESERT) containsRobber = true;
	}
	
	public Resource getResource(int gewuerfelteZahl) {
		if (gewuerfelteZahl < 2 || gewuerfelteZahl > 12) throw new IllegalArgumentException();
		if (containsRobber || number == -1) return null;
		else {
			return gewuerfelteZahl == number ? fieldType.getResource() : null;
		}
	}
	
	public Resource getResource() {
		return fieldType.getResource();
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
		return number;
	}
	
	public void setNumber(int number) {
		if (number < 2 || number == 7 || number > 12) throw new IllegalArgumentException();
		if (fieldType == FieldType.DESERT || fieldType == FieldType.WATER) throw new IllegalArgumentException();
		this.number = number;
	}
	
	public Point getLocation() {
		return this.point;
	}
	
	@Override
	public boolean equals(Object o) {
		if(o instanceof Field) {
			return ((Field)o).point.equals(this.point) && ((Field)o).getFieldType() == this.fieldType;
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		return point.hashCode() * 131 + fieldType.ordinal();
	}
	
	@Override
	public String toString(){
		return ("[y, x: " + getLocation().toString() +  "Type: " + fieldType + "]" );
	}
}
