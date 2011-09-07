package de.unisaarland.cs.sopra.common.model;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import de.unisaarland.cs.st.saarsiedler.comm.WorldRepresentation;

public class Board {

	private Map<Point,Field> field;
	private Map<Location,Path> path;
	private Map<Location,Intersection> intersection;
	private int width;
	private int height;
	
	public Board(WorldRepresentation worldRepresentation) {
		this.height = worldRepresentation.getHeight();
		this.width = worldRepresentation.getWidth();
		this.field = new HashMap<Point,Field>();
		for (int i = 0; i < width*height; i++) {
			Point p = new Point(i/width,i%width);
			FieldType fieldType = FieldType.convert( worldRepresentation.getFieldType(i/width,i%width) );
			this.field.put(p, new Field(fieldType, p));
		}
	}
	
	public Field getField(Point point) {
		return this.field.get(point);
	}
	
	public Intersection getIntersection(Location location) {
		return this.intersection.get(location);
	}
	
	public Path getPath(Location location) {
		return this.path.get(location);
	}
	
	public Set<Field> getFieldsFromField(Field field) {
		throw new UnsupportedOperationException();
	}
	
	public Set<Field> getFieldsFromIntersection(Intersection intersection) {
		throw new UnsupportedOperationException();
	}
	
	public Set<Field> getFieldsFromPath(Path path) {
		throw new UnsupportedOperationException();
	}
	
	public Set<Intersection> getIntersectionsFromField(Field field) {
		throw new UnsupportedOperationException();
	}
	
	public Set<Intersection> getIntersectionsFromIntersection(Intersection intersection) {
		throw new UnsupportedOperationException();
	}
	
	public Set<Intersection> getIntersectionsFromPath(Path path) {
		throw new UnsupportedOperationException();
	}
	
	public Set<Path> getPathsFromField(Field field) {
		throw new UnsupportedOperationException();
	}
	
	public Set<Path> getPathsFromIntersection(Intersection intersection) {
		throw new UnsupportedOperationException();
	}
	
	public Set<Path> getPathsFromPath(Path path) {
		throw new UnsupportedOperationException();
	}
	
	public Iterator<Field> getFieldIterator() {
		return new Iterator<Field>() {

			private int i = 0;
			
			@Override
			public boolean hasNext() {
				return i < (width)*(height)-1;
			}

			@Override
			public Field next() {
				Point p = new Point(i/width,i%width);
				return field.get(p);
			}

			@Override
			public void remove() {
				throw new UnsupportedOperationException();
			}
			
		};
	}
	
	public Iterator<Path> getPathIterator() {
		throw new UnsupportedOperationException();
	}
	
	public Iterator<Intersection> getIntersectionIterator() {
		throw new UnsupportedOperationException();
	}
	
	public void setHarbor(Location location, HarborType harborType){
		throw new UnsupportedOperationException();
	}
	
}
