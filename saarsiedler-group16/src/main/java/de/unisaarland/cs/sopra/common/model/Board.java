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
	
	public Board(WorldRepresentation worldRepresentation) {
		this.height = worldRepresentation.getHeight();
		this.width = worldRepresentation.getWidth();
		this.fields = new HashMap<Point, Field>();
		this.paths = new HashMap<Location, Path>();
		this.intersections = new HashMap<Location, Intersection>();
		for (int i = 0; i < width*height; i++) {
			Point p = new Point(i/width,i%width);
			FieldType fieldType = FieldType.convert( worldRepresentation.getFieldType(i/width,i%width) );
			this.fields.put(p, new Field(fieldType, p));
		}
		initPaths();
		initIntersections();
	}
	 
	// Done
	private void initPaths() {
		for (int y = 0; y < height; y++){
			for (int x = 0; x < width; x++){
				for (int o = 0; o < 6; o++){
					Path p = new Path(new Location(y, x, o));
					if (!paths.containsKey(new Location(y, x, o))){
						paths.put(new Location(y, x, o), p);
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
	
	//Done
	private void initIntersections() {
		for (int y = 0; y < height; y++){
			for (int x = 0; x < width; x++){
				for (int o = 0; o < 6; o++){
					Intersection i = new Intersection(new Location(y, x, o));
					if (!intersections.containsKey(new Location(y, x, o))){
						intersections.put(new Location(y, x, o), i);
						if (y % 2 == 1){
							switch(o){
							case 0:
								if(isValid(y-1, x-1)) intersections.put(new Location(y-1, x-1, 2), i);
								if(isValid(y-1, x)) intersections.put(new Location(y-1, x, 4), i);
								break;
							case 1:
								if(isValid(y-1, x)) intersections.put(new Location(y-1, x, 3), i);
								if(isValid(y, x+1)) intersections.put(new Location(y, x+1, 5), i);
								break;
							case 2:
								if(isValid(y, x+1)) intersections.put(new Location(y, x+1, 4), i);
								if(isValid(y+1, x)) intersections.put(new Location(y+1, x, 0), i);
								break;
							case 3:
								if(isValid(y+1, x)) intersections.put(new Location(y+1, x, 5), i);
								if(isValid(y+1, x-1)) intersections.put(new Location(y+1, x-1, 1), i);
								break;
							case 4:
								if(isValid(y+1, x-1)) intersections.put(new Location(y+1, x-1, 0), i);
								if(isValid(y, x-1)) intersections.put(new Location(y, x-1, 2), i);
								break;
							case 5:
								if(isValid(y, x-1)) intersections.put(new Location(y, x-1, 1), i);
								if(isValid(y-1, x-1)) intersections.put(new Location(y-1, x-1, 3), i);
								break;
							}
						} else {
							switch(o){
							case 0:
								if(isValid(y-1, x+1)) intersections.put(new Location(y-1, x+1, 4), i);
								if(isValid(y-1, x)) intersections.put(new Location(y-1, x, 2), i);
								break;
							case 1:
								if(isValid(y-1, x+1)) intersections.put(new Location(y-1, x+1, 3), i);
								if(isValid(y, x+1)) intersections.put(new Location(y, x+1, 5), i);
								break;
							case 2:
								if(isValid(y, x+1)) intersections.put(new Location(y, x+1, 4), i);
								if(isValid(y+1, x+1)) intersections.put(new Location(y+1, x+1, 0), i);
								break;
							case 3:
								if(isValid(y+1, x+1)) intersections.put(new Location(y+1, x+1, 5), i);
								if(isValid(y+1, x)) intersections.put(new Location(y+1, x, 1), i);
								break;
							case 4:
								if(isValid(y+1, x)) intersections.put(new Location(y+1, x, 0), i);
								if(isValid(y, x-1)) intersections.put(new Location(y, x-1, 2), i);
								break;
							case 5:
								if(isValid(y, x-1)) intersections.put(new Location(y, x-1, 1), i);
								if(isValid(y-1, x)) intersections.put(new Location(y-1, x, 3), i);
								break;
							}
						}
					}
				}
			}
		}
	}
	
	//Done
	public Field getField(Point point) {
		//assert(fields.containsKey(point));
		if (point.getX() < 0 || point.getX() > this.width || 
				point.getY() < 0 || point.getY() > this.height) 
			throw new IllegalArgumentException();
		return this.fields.get(point);
	}
	
	//Done
	public Intersection getIntersection(Location location) {
		//assert(intersections.containsKey(location));
		if (location.getX() < 0 || location.getX() > this.width || 
				location.getY() < 0 || location.getY() > this.height ||
				location.getOrientation() < 0 || location.getOrientation() > 5) 
			throw new IllegalArgumentException();
		return this.intersections.get(location);
	}
	
	//Done
	public Path getPath(Location location) {
		//assert(paths.containsKey(location));
		if (location.getX() < 0 || location.getX() > this.width || 
				location.getY() < 0 || location.getY() > this.height ||
				location.getOrientation() < 0 || location.getOrientation() > 5) 
			throw new IllegalArgumentException();
		return this.paths.get(location);
	}
	
	//Done
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
	
	//Done
	public Set<Field> getFieldsFromIntersection(Intersection intersection) {
		Location loc = intersection.getLocation();
		int x = loc.getX();
		int y = loc.getY();
		int o = loc.getOrientation();
		Set<Field> s = new HashSet<Field>();
		s.add(this.getField(new Point(y, x)));
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
	
	//Done
	public Set<Field> getFieldsFromPath(Path path) {
		Location loc = path.getLocation();
		int x = loc.getX();
		int y = loc.getY();
		int o = loc.getOrientation();
		Set<Field> s = new HashSet<Field>();
		s.add(this.getField(new Point(y, x)));
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
	
	//Done
	public Set<Intersection> getIntersectionsFromField(Field field) {
		Point loc = field.getLocation();
		int x = loc.getX();
		int y = loc.getY();
		Set<Intersection> s = new HashSet<Intersection>();
		for (int o = 0; o < 6; o++){ s.add(getIntersection(new Location(y, x, o))); }
		return s;
	}
	
	//Done
	public Set<Intersection> getIntersectionsFromIntersection(Intersection intersection) {
		Location loc = intersection.getLocation();
		int x = loc.getX();
		int y = loc.getY();
		int o = loc.getOrientation();
		Set<Intersection> s = new HashSet<Intersection>();
		s.add(getIntersection(new Location(y, x, (o+5)%6)));
		s.add(getIntersection(new Location(y, x, (o+1)%6)));
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
				if (isValid(y+1, x-1)) s.add(this.getIntersection(new Location(y+1, x-1, 2)));
				if (isValid(y+1, x)) s.add(this.getIntersection(new Location(y+1, x, 4)));
				break;
			case 4:
				if (isValid(y, x-1)) s.add(this.getIntersection(new Location(y, x-1, 3)));
				if (isValid(y+1, x-1)) s.add(this.getIntersection(new Location(y+1, x-1, 5)));
				break;
			case 5:
				if (isValid(y, x-1)) s.add(this.getIntersection(new Location(y, x-1, 0)));
				if (isValid(y-1, x-1)) s.add(this.getIntersection(new Location(y-1, x-1, 4)));
				break;
			}
		} else {
			switch(o){
			case 0:
				if (isValid(y-1, x+1)) s.add(this.getIntersection(new Location(y-1, x+1, 5)));
				if (isValid(y-1, x)) s.add(this.getIntersection(new Location(y-1, x, 1)));
				break;
			case 1:
				if (isValid(y, x+1)) s.add(this.getIntersection(new Location(y, x+1, 0)));
				if (isValid(y-1, x+1)) s.add(this.getIntersection(new Location(y-1, x+1, 2)));
				break;
			case 2:
				if (isValid(y, x+1)) s.add(this.getIntersection(new Location(y, x+1, 3)));
				if (isValid(y+1, x+1)) s.add(this.getIntersection(new Location(y+1, x+1, 1)));
				break;
			case 3:
				if (isValid(y+1, x+1)) s.add(this.getIntersection(new Location(y+1, x+1, 4)));
				if (isValid(y+1, x)) s.add(this.getIntersection(new Location(y+1, x, 2)));
				break;
			case 4:
				if (isValid(y, x-1)) s.add(this.getIntersection(new Location(y, x-1, 3)));
				if (isValid(y+1, x)) s.add(this.getIntersection(new Location(y+1, x, 5)));
				break;
			case 5:
				if (isValid(y, x-1)) s.add(this.getIntersection(new Location(y, x-1, 0)));
				if (isValid(y-1, x)) s.add(this.getIntersection(new Location(y-1, x, 4)));
				break;
			}
		}
		return s;
	}
	
	//Done
	public Set<Intersection> getIntersectionsFromPath(Path path) {
		Location loc = path.getLocation();
		int x = loc.getX();
		int y = loc.getY();
		int o = loc.getOrientation();
		Set<Intersection> s = new HashSet<Intersection>();
		s.add(getIntersection(new Location(y, x, o)));
		s.add(getIntersection(new Location(y, x, (o+1)%6)));
		return s;
	}
	
	//Done
	public Set<Path> getPathsFromField(Field field) {
		Point loc = field.getLocation();
		int x = loc.getX();
		int y = loc.getY();
		Set<Path> s = new HashSet<Path>();
		for (int o = 0; o < 6; o++){ s.add(getPath(new Location(y, x, o))); }
		return s;
	}
	
	//Done
	public Set<Path> getPathsFromIntersection(Intersection intersection) {
		Location loc = intersection.getLocation();
		int x = loc.getX();
		int y = loc.getY();
		int o = loc.getOrientation();
		Set<Path> s = new HashSet<Path>();
		s.add(getPath(new Location(y, x, o)));
		s.add(getPath(new Location(y, x, (o+5)%6)));
		if (y % 2 == 1){
			switch(o){
			case 0:
				if (isValid(y-1, x-1)) s.add(this.getPath(new Location(y-1, x-1, 1)));
				if (isValid(y-1, x)) s.add(this.getPath(new Location(y-1, x, 4)));
				break;
			case 1:
				if (isValid(y-1, x)) s.add(this.getPath(new Location(y-1, x, 2)));
				if (isValid(y, x+1)) s.add(this.getPath(new Location(y, x+1, 5)));
				break;
			case 2:
				if (isValid(y, x+1)) s.add(this.getPath(new Location(y, x+1, 3)));
				if (isValid(y+1, x)) s.add(this.getPath(new Location(y+1, x, 0)));
				break;
			case 3:
				if (isValid(y+1, x)) s.add(this.getPath(new Location(y+1, x, 4)));
				if (isValid(y+1, x-1)) s.add(this.getPath(new Location(y+1, x-1, 1)));
				break;
			case 4:
				if (isValid(y+1, x-1)) s.add(this.getPath(new Location(y+1, x-1, 5)));
				if (isValid(y, x-1)) s.add(this.getPath(new Location(y, x-1, 2)));
				break;
			case 5:
				if (isValid(y, x-1)) s.add(this.getPath(new Location(y, x-1, 0)));
				if (isValid(y-1, x-1)) s.add(this.getPath(new Location(y-1, x-1, 3)));
				break;	
			}
		} else {
			switch(o){
			case 0:
				if (isValid(y-1, x+1)) s.add(this.getPath(new Location(y-1, x+1, 1)));
				if (isValid(y-1, x)) s.add(this.getPath(new Location(y-1, x, 1)));
				break;
			case 1:
				if (isValid(y-1, x+1)) s.add(this.getPath(new Location(y-1, x+1, 2)));
				if (isValid(y, x+1)) s.add(this.getPath(new Location(y, x+1, 5)));
				break;
			case 2:
				if (isValid(y, x+1)) s.add(this.getPath(new Location(y, x+1, 3)));
				if (isValid(y+1, x+1)) s.add(this.getPath(new Location(y+1, x+1, 0)));
				break;
			case 3:
				if (isValid(y+1, x)) s.add(this.getPath(new Location(y+1, x, 1)));
				if (isValid(y+1, x+1)) s.add(this.getPath(new Location(y+1, x+1, 4)));
				break;
			case 4:
				if (isValid(y+1, x)) s.add(this.getPath(new Location(y+1, x, 5)));
				if (isValid(y, x-1)) s.add(this.getPath(new Location(y, x-1, 2)));
				break;
			case 5:
				if (isValid(y, x-1)) s.add(this.getPath(new Location(y, x-1, 0)));
				if (isValid(y-1, x)) s.add(this.getPath(new Location(y-1, x, 3)));
				break;	
			}
		}	
		return s;
	}
	
	//? TODO
	public Set<Path> getPathsFromPath(Path path) {
		int x = path.getLocation().getX();
		int y = path.getLocation().getY();
		int o = path.getLocation().getOrientation();
		Set<Path> s = new HashSet<Path>();
		s.add(getPath(new Location(y, x, (o+1)%6)));
		s.add(getPath(new Location(y, x, (o+5)%6)));
		if (y%2 == 1){
			switch(o){
			case 0:
				if (isValid(x-1, y-1)) s.add(getPath(new Location(x-1, y-1, 1)));
			}
		}
		return s;
	}
	
	//Done
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
	
	//Done
	public Iterator<Path> getPathIterator() {
		return paths.values().iterator();
	}
	
	//Done
	public Iterator<Intersection> getIntersectionIterator() {
		return intersections.values().iterator();
	}
	
	//Done
	private boolean isValid(int y, int x){
		return x >= 0 && x < width && y >= 0 && y < height;
	}

	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
}
