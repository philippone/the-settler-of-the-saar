package de.unisaarland.cs.sopra.common.view;

import java.util.Set;

import de.unisaarland.cs.sopra.common.controller.ControllerAdapter;
import de.unisaarland.cs.sopra.common.model.Catapult;
import de.unisaarland.cs.sopra.common.model.Intersection;
import de.unisaarland.cs.sopra.common.model.ModelReader;
import de.unisaarland.cs.sopra.common.model.Path;
import de.unisaarland.cs.sopra.common.model.ResourcePackage;


public class MoveCatapultStrategy extends Strategy {
	
	public MoveCatapultStrategy() {
		//TODO hier nochmal draufschauen, ob attackprice = moveprice
		super(0, Catapult.getAttackcatapultprice());
	}

	Path sourcePath;
 
	@Override
	public void execute(ModelReader mr, ControllerAdapter ca) throws Exception {
		if (!(mr.getMe().getResources().copy().add(new ResourcePackage(0,0,0,-1,0))).hasNegativeResources()){
		Path destinationPath = evaluateStreet(mr);
		ca.moveCatapult(sourcePath, destinationPath);
	} 
}
	public float evaluateStreetValue(ModelReader mr, Path p) {
		if (p.hasCatapult() && p.getCatapultOwner() != mr.getMe()) {
			return 1;
		} 
		float value=0;
		Set<Intersection> intersections=mr.getIntersectionsFromPath(p);
		for (Intersection i: intersections){
			if (i.hasOwner() && i.getOwner()!=mr.getMe()) value=(float) (value+0.3);
		}
		Set<Path> paths=mr.getPathsFromPath(p);
		for (Path p1: paths){
			if (p1.hasCatapult() && p1.getCatapultOwner() != mr.getMe()) value=(float) (value+0.3);
			
		}
		return value;
	}
	
	public Path evaluateStreet(ModelReader mr) {
		float bestValue = 0;
		Path bestPath = null;
		Set<Path> catapults = mr.getCatapults(mr.getMe());
		for (Path p : catapults) {
			Set<Path> neighbourPaths = mr.getPathsFromPath(p);
			for (Path path : neighbourPaths) {
				float currentValue = evaluateStreetValue(mr, path);
				if (currentValue > bestValue){
					bestValue = currentValue;
					sourcePath = new Path(p.getLocation());
					bestPath = path;
				}
			}

		}
		return bestPath;
	}
	
	@Override
	public boolean useable() {
		//TODO implement this operation
		throw new UnsupportedOperationException();
	}
	
}
