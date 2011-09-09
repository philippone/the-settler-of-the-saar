package de.unisaarland.cs.sopra.common.model;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import de.unisaarland.cs.st.saarsiedler.comm.WorldRepresentation;

public class Board {

	private Map<Point, Field> field;
	private Map<Location, Path> path;
	private Map<Location, Intersection> intersection;
	private int width;
	private int height;
	
	public Board(WorldRepresentation worldRepresentation) {
		this.height = worldRepresentation.getHeight();
		this.width = worldRepresentation.getWidth();
		this.field = new HashMap<Point, Field>();
		for (int i = 0; i < width*height; i++) {
			Point p = new Point(i/width,i%width);
			FieldType fieldType = FieldType.convert( worldRepresentation.getFieldType(i/width,i%width) );
			this.field.put(p, new Field(fieldType, p));
		}
		this.path = new HashMap<Location, Path>();
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
		int x = loc.getX();
		int y = loc.getY();
		Set<Field> s = new HashSet<Field>();
		if(isValid(y-1, x)) s.add(this.getField(new Point(y-1,x)));
		if(isValid(y, x-1)) s.add(this.getField(new Point(y,x-1)));
		if(isValid(y, x+1)) s.add(this.getField(new Point(y,x+1)));
		if(isValid(y+1, x)) s.add(this.getField(new Point(y+1,x)));
		if (y%2 == 1 && isValid(y-1, x-1)) s.add(this.getField(new Point(y-1, x-1)));
		if (y%2 == 0 && isValid(y-1, x+1)) s.add(this.getField(new Point(y-1, x+1)));
		if (y%2 == 1 && isValid(y+1, x-1)) s.add(this.getField(new Point(y+1, x-1)));
		if (y%2 == 0 && isValid(y+1, x+1)) s.add(this.getField(new Point(y+1, x+1)));
		return s;
	}
	
	public Set<Field> getFieldsFromIntersection(Intersection intersection) {
		Location loc = intersection.getLocation();
		int x = loc.getX();
		int y = loc.getY();
		int o = loc.getOrientation();
		Set<Field> s = new HashSet<Field>();
		s.add(this.getField(new Point(x, y)));
		if (y % 2 == 1){
			switch(o){
			case 0:
				if (isValid(y-1, x-1)) s.add(this.getField(new Point(y-1, x-1)));
				if (isValid(y-1, x)) s.add(this.getField(new Point(y-1, x)));
				break;
			case 1:
				if (isValid(y, x+1)) s.add(this.getField(new Point(y, x+1)));
				if (isValid(y-1, x)) s.add(this.getField(new Point(y-1, x)));
				break;
			case 2:
				if (isValid(y, x+1)) s.add(this.getField(new Point(y, x+1)));
				if (isValid(y+1, x)) s.add(this.getField(new Point(y+1, x)));
				break;
			case 3:
				if (isValid(y+1, x)) s.add(this.getField(new Point(y+1, x)));
				if (isValid(y+1, x-1)) s.add(this.getField(new Point(y+1, x-1)));
				break;
			case 4:
				if (isValid(y+1, x-1)) s.add(this.getField(new Point(y+1, x-1)));
				if (isValid(y, x-1)) s.add(this.getField(new Point(y, x-1)));
				break;
			case 5:
				if (isValid(y, x-1)) s.add(this.getField(new Point(y, x-1)));
				if (isValid(y-1, x-1)) s.add(this.getField(new Point(y-1, x-1)));
				break;
			}
		} else {
			switch(o){
			case 0:
				if (isValid(y-1, x+1)) s.add(this.getField(new Point(y-1, x+1)));
				if (isValid(y-1, x)) s.add(this.getField(new Point(y-1, x)));
				break;
			case 1:
				if (isValid(y, x+1)) s.add(this.getField(new Point(y, x+1)));
				if (isValid(y-1, x+1)) s.add(this.getField(new Point(y-1, x+1)));
				break;
			case 2:
				if (isValid(y, x+1)) s.add(this.getField(new Point(y, x+1)));
				if (isValid(y+1, x+1)) s.add(this.getField(new Point(y+1, x+1)));
				break;
			case 3:
				if (isValid(y+1, x)) s.add(this.getField(new Point(y+1, x)));
				if (isValid(y+1, x+1)) s.add(this.getField(new Point(y+1, x+1)));
				break;
			case 4:
				if (isValid(y+1, x)) s.add(this.getField(new Point(y+1, x)));
				if (isValid(y, x-1)) s.add(this.getField(new Point(y, x-1)));
				break;
			case 5:
				if (isValid(y, x-1)) s.add(this.getField(new Point(y, x-1)));
				if (isValid(y-1, x)) s.add(this.getField(new Point(y-1, x)));
				break;
			}
		}
		return s;
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
				return i < (width)*(height) -1;
			}

			@Override
			public Field next() {
				Point p = new Point(i/width,i%width);
				i++;
				return field.get(p);
			}

			@Override
			public void remove() {
				throw new UnsupportedOperationException();
			}
			
		};
	}
	
	public Iterator<Path> getPathIterator() {
		return path.values().iterator();
	}
	
	public Iterator<Intersection> getIntersectionIterator() {
		return intersection.values().iterator();
	}
	
	private boolean isValid(int x, int y){
		return x >= 0 && x < width && y >= 0 && y < height;
	}

	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
}
