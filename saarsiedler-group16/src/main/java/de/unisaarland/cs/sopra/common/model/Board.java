package de.unisaarland.cs.sopra.common.model;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import de.unisaarland.cs.st.saarsiedler.comm.WorldRepresentation;

public class Board {

	private Map<Point,Field> field;
	private Map<Location,Path> path;
	private Map<Location,Intersection> intersection;
	private int width;
	private int height;
	
	public Board(WorldRepresentation worldRepresentation) {
		throw new UnsupportedOperationException();
	}
	
	public Field getField(Point point) {
		throw new UnsupportedOperationException();
	}
	
	public Intersection getIntersection(Location location) {
		throw new UnsupportedOperationException();
	}
	
	public Path getPath(Location location) {
		throw new UnsupportedOperationException();
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
		throw new UnsupportedOperationException();
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
