package de.unisaarland.cs.sopra.common.model;

public class Field {

	private Point point;
	private boolean containsRobber;
	private long number;
	private FieldType fieldType;
	
	public Field(FieldType fieldType, Point point) {
		if (fieldType == null) throw new IllegalArgumentException();
		if (point == null) throw new IllegalArgumentException();
		this.fieldType = fieldType;
		this.point = point;
	}
	
	public Resource getResource(int gewuerfelteZahl) {
		if (gewuerfelteZahl < 2 || gewuerfelteZahl > 12) throw new IllegalArgumentException();
		if (containsRobber || number == 0) return null;
		else {
			return gewuerfelteZahl == number ? fieldType.getResource() : null;
		}
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
	
	public long getNumber() {
		return number;
	}
	
	public void setNumber(long number) {
		if (number == 7) throw new IllegalArgumentException();
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
	
}
