package de.unisaarland.cs.sopra.common.model;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import de.unisaarland.cs.st.saarsiedler.comm.WorldRepresentation;

public class Board {

	private Map<Point, Field> fields;
	private Map<Location, Path> paths;
	private Map<Location, Intersection> intersections;
	private int width;
	private int height;
	
	//TODO
	public Board(WorldRepresentation worldRepresentation) {
		this.height = worldRepresentation.getHeight();
		this.width = worldRepresentation.getWidth();
		this.fields = new HashMap<Point, Field>();
		this.paths = new HashMap<Location, Path>();
		this.intersections = new HashMap<Location, Intersection>();
		// ok
		for (int i = 0; i < width*height; i++) {
			Point p = new Point(i/width,i%width);
			FieldType fieldType = FieldType.convert( worldRepresentation.getFieldType(i/width,i%width) );
			this.fields.put(p, new Field(fieldType, p));
		}
		initPaths();
		initIntersections();
	}
	
	private void initPaths() {
		for (int x = 0; x < width; x++){
			for (int y = 0; y < height; y++){
				for (int o = 0; o < 6; o++){
					Path p = new Path(new Location(x, y, o));
					if (!paths.containsKey(new Location(x, y, o))){
						paths.put(new Location(x, y, o), p);
						if (y % 2 == 1){
							switch(o){
							case 0:
								if(isValid(y-1, x)) paths.put(new Location(y-1, x, 3), p);
								break;
							case 1:
								if(isValid(y, x+1)) paths.put(new Location(y, x+1, 4), p);
								break;
							case 2:
								if(isValid(y+1, x)) paths.put(new Location(y+1, x, 5), p);
								break;
							case 3:
								if(isValid(y+1, x-1)) paths.put(new Location(y+1, x-1, 0), p);
								break;
							case 4:
								if(isValid(y, x-1)) paths.put(new Location(y, x-1, 1), p);
								break;
							case 5:
								if(isValid(y-1, x-1)) paths.put(new Location(y-1, x-1, 2), p);
								break;
							}
						} else {
							switch(o){
							case 0:
								if(isValid(y-1, x+1)) paths.put(new Location(y-1, x+1, 3), p);
								break;
							case 1:
								if(isValid(y, x+1)) paths.put(new Location(y, x+1, 4), p);
								break;
							case 2:
								if(isValid(y+1, x+1)) paths.put(new Location(y+1, x+1, 5), p);
								break;
							case 3:
								if(isValid(y+1, x)) paths.put(new Location(y+1, x, 0), p);
								break;
							case 4:
								if(isValid(y, x-1)) paths.put(new Location(y, x-1, 1), p);
								break;
							case 5:
								if(isValid(y-1, x)) paths.put(new Location(y-1, x, 2), p);
								break;
							}
						}
					}
				}
			}
		}
	}
	
	private void initIntersections() {
		throw new UnsupportedOperationException();
	}
	
	public Field getField(Point point) {
		return this.fields.get(point);
	}
	
	public Intersection getIntersection(Location location) {
		return this.intersections.get(location);
	}
	
	public Path getPath(Location location) {
		return this.paths.get(location);
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
		Location loc = path.getLocation();
		int x = loc.getX();
		int y = loc.getY();
		int o = loc.getOrientation();
		Set<Field> s = new HashSet<Field>();
		s.add(this.getField(new Point(x, y)));
		if (y % 2 == 1){
			switch(o){
			case 0:
				if (isValid(y-1, x)) s.add(this.getField(new Point(y-1, x)));
				break;
			case 1:
				if (isValid(y, x+1)) s.add(this.getField(new Point(y, x+1)));
				break;
			case 2:
				if (isValid(y+1, x)) s.add(this.getField(new Point(y+1, x)));
				break;
			case 3:
				if (isValid(y+1, x-1)) s.add(this.getField(new Point(y+1, x-1)));
				break;
			case 4:
				if (isValid(y, x-1)) s.add(this.getField(new Point(y, x-1)));
				break;
			case 5:
				if (isValid(y-1, x-1)) s.add(this.getField(new Point(y-1, x-1)));
				break;
			}
		} else {
			switch(o){
			case 0:
				if (isValid(y-1, x+1)) s.add(this.getField(new Point(y-1, x+1)));
				break;
			case 1:
				if (isValid(y, x+1)) s.add(this.getField(new Point(y, x+1)));
				break;
			case 2:
				if (isValid(y+1, x+1)) s.add(this.getField(new Point(y+1, x+1)));
				break;
			case 3:
				if (isValid(y+1, x)) s.add(this.getField(new Point(y+1, x)));
				break;
			case 4:
				if (isValid(y, x-1)) s.add(this.getField(new Point(y, x-1)));
				break;
			case 5:
				if (isValid(y-1, x)) s.add(this.getField(new Point(y-1, x)));
				break;
			}
		}
		return s;
	}
	
	public Set<Intersection> getIntersectionsFromField(Field field) {
		Point loc = field.getLocation();
		int x = loc.getX();
		int y = loc.getY();
		Set<Intersection> s = new HashSet<Intersection>();
		for (int o = 0; o < 6; o++){ s.add(getIntersection(new Location(x, y, o))); }
		return s;
	}
	
	public Set<Intersection> getIntersectionsFromIntersection(Intersection intersection) {
		Location loc = intersection.getLocation();
		int x = loc.getX();
		int y = loc.getY();
		int o = loc.getOrientation();
		Set<Intersection> s = new HashSet<Intersection>();
		s.add(getIntersection(new Location(x, y, (o+5)%6)));
		s.add(getIntersection(new Location(x, y, (o+1)%6)));
		if (y % 2 == 1){
			switch(o){
			case 0:
				if (isValid(y-1, x-1)) s.add(this.getIntersection(new Location(y-1, x-1, 1)));
				if (isValid(y-1, x)) s.add(this.getIntersection(new Location(y-1, x, 5)));
				break;
			case 1:
				if (isValid(y, x+1)) s.add(this.getIntersection(new Location(y, x+1, 0)));
				if (isValid(y-1, x)) s.add(this.getIntersection(new Location(y-1, x, 2)));
				break;
			case 2:
				if (isValid(y, x+1)) s.add(this.getIntersection(new Location(y, x+1, 3)));
				if (isValid(y+1, x)) s.add(this.getIntersection(new Location(y+1, x, 1)));
				break;
			case 3:
				if (isValid(y+1, x-1)) s.add(this.getIntersection(new Location(y+1, x-1, 1)));
				if (isValid(y+1, x)) s.add(this.getIntersection(new Location(y+1, x, 5)));
				break;
			case 4:
				if (isValid(y, x-1)) s.add(this.getIntersection(new Location(y, x-1, 2)));
				if (isValid(y+1, x-1)) s.add(this.getIntersection(new Location(y+1, x-1, 0)));
				break;
			case 5:
				if (isValid(y, x-1)) s.add(this.getIntersection(new Location(y, x-1, 1)));
				if (isValid(y-1, x-1)) s.add(this.getIntersection(new Location(y-1, x-1, 3)));
				break;
			}
		}
		return s;
	}
	
	public Set<Intersection> getIntersectionsFromPath(Path path) {
		throw new UnsupportedOperationException();
	}
	
	public Set<Path> getPathsFromField(Field field) {
		Point loc = field.getLocation();
		int x = loc.getX();
		int y = loc.getY();
		Set<Path> s = new HashSet<Path>();
		for (int o = 0; o < 6; o++){ s.add(getPath(new Location(x, y, o))); }
		return s;
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
				i++;
				return fields.get(p);
			}

			@Override
			public void remove() {
				throw new UnsupportedOperationException();
			}
			
		};
	}
	
	public Iterator<Path> getPathIterator() {
		return paths.values().iterator();
	}
	
	public Iterator<Intersection> getIntersectionIterator() {
		return intersections.values().iterator();
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
