package de.unisaarland.cs.sopra.common.model;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import de.unisaarland.cs.st.saarsiedler.comm.WorldRepresentation;

public class Board {

	private OurMap<Field> field;
	private OurMap<Path> path;
	private OurMap<Intersection> intersection;
	private int width;
	private int height;
	
	class OurMap<T> {
		Map<Point, T> m;
		
		OurMap(){ m = new HashMap<Point, T>(); }
		
		T get(Point position){
			return m.get(position);
		}
		
		boolean put(Point position, T t){
			boolean validPosition = position.getX() >= 0 && position.getX() < width
					&& position.getY() >= 0 && position.getY() < height;
			if (validPosition){ m.put(position, t); }
			return validPosition;
		}
		
		Iterator<T> iterator(){ return m.values().iterator(); }
	}
	
	public Board(WorldRepresentation worldRepresentation) {
		this.height = worldRepresentation.getHeight();
		this.width = worldRepresentation.getWidth();
		this.field = new OurMap<Field>();
		for (int i = 0; i < width*height; i++) {
			Point p = new Point(i/width,i%width);
			FieldType fieldType = FieldType.convert( worldRepresentation.getFieldType(i/width,i%width) );
			this.field.put(p, new Field(fieldType, p));
		}
		this.path = new OurMap<Path>();
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < height; x++) {
				for (int ori = 0; ori < 6; ori++) {
					Location loc = new Location(y,x,ori);
					Path p = new Path(loc);
					if (path.get(loc) == null) {
						path.put(loc, p);
						switch(loc.getOrientation()) {
							case 0:
								if (loc.getX()%2==0 && loc.getY()-1 > 0 && loc.getX()+1 < width);
							case 1:
							case 2:
							case 3:
							case 4:
							case 5:
						}
						
					}
				}
			}
		}
	}
	
	public Field getField(Point point) {
		return this.field.get(point);
	}
	
	public Intersection getIntersection(Point location) {
		return this.intersection.get(location);
	}
	
	public Path getPath(Point location) {
		return this.path.get(location);
	}
	
	public Set<Field> getFieldsFromField(Field field) {
		Point loc = field.getLocation();
		Set<Field> s = new HashSet<Field>();
		s.add(this.getField(new Point(loc.getY(),loc.getX()-1)));
		s.add(this.getField(new Point(loc.getY(),loc.getX()+1)));
		s.add(this.getField(new Point(loc.getY()-1,loc.getX())));
		s.add(this.getField(new Point(loc.getY()+1,loc.getX())));
		s.add(this.getField(new Point(loc.getY()-1,loc.getX()-1)));
		s.add(this.getField(new Point(loc.getY()-1,loc.getX()+1)));
		s.add(this.getField(new Point(loc.getY()+1,loc.getX()-1)));
		s.add(this.getField(new Point(loc.getY()+1,loc.getX()+1)));
		return s;
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
		int x = path.getLocation().getX();
		int y = path.getLocation().getY();
		int o = path.getLocation().getOrientation();
		Set<Path> ps = new HashSet<Path>();
		ps.add(getPath(new Location(y, x, (o+1)%6)));
		ps.add(getPath(new Location(y, x, (o+5)%6)));
		switch(o){
		case 0:
			if (y%2 == 1){
				
			}
			else {
				
			}
			break;
		case 1:
			ps.add(getPath(new Location(y, x+1, 5)));
			ps.add(getPath(new Location(y, x+1, 3)));
			break;
		case 2:
			ps.add(getPath(new Location(y, x+1, 5)));
			ps.add(getPath(new Location(y, x+1, 3)));
			break;
		case 3:
			ps.add(getPath(new Location(y, x+1, 5)));
			ps.add(getPath(new Location(y, x+1, 3)));
			break;
		case 4:
			ps.add(getPath(new Location(y, x-1, 0)));
			ps.add(getPath(new Location(y, x-1, 2)));
			break;
		case 5:
			ps.add(getPath(new Location(y, x+1, 5)));
			ps.add(getPath(new Location(y, x+1, 3)));
			break;
		}
		return ps;
	}
	
	public Iterator<Field> getFieldIterator() {
		return new Iterator<Field>() {

			private int i = 0;
			
			@Override
			public boolean hasNext() {
				return i < (width)*(height);
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
		return path.iterator();
	}
	
	public Iterator<Intersection> getIntersectionIterator() {
		return intersection.iterator();
	}
	
	private Field getField(int x, int y) { return getField(new Point(x, y)); }
	
	private Intersection getIntersection(int x, int y) { return getIntersection(new Point(x, y)); }
	
	private Path getPath(int x, int y) { return getPath(new Point(x, y)); }
	
}
