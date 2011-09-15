package de.unisaarland.cs.sopra.common.view;

import java.util.Set;

import de.unisaarland.cs.sopra.common.controller.ControllerAdapter;
import de.unisaarland.cs.sopra.common.model.Catapult;
import de.unisaarland.cs.sopra.common.model.ModelReader;
import de.unisaarland.cs.sopra.common.model.Path;
import de.unisaarland.cs.sopra.common.model.ResourcePackage;


public class MoveCatapultStrategy implements Strategy {
 Path sourcePath;
	@Override
	public void execute(ModelReader mr, ControllerAdapter ca) throws Exception {
		if (!(mr.getMe().getResources().copy().add(new ResourcePackage(0,0,0,-1,0))).hasNegativeResources()){
		Path destinationPath = evaluateStreet(mr);
		ca.moveCatapult(sourcePath, destinationPath);
		ca.endTurn();
	} ca.endTurn();
}
	public float evaluateStreetValue(ModelReader mr, Path p) {
		if (p.hasCatapult() && p.getCatapultOwner() != mr.getMe()) {
			return 1;
		} else if (!p.hasCatapult()) {
			return (float) (0.5);
		}
		return 0;
	}
	
	public Path evaluateStreet(ModelReader mr) {
		float bestValue = 0;
		Path bestPath = null;
		Set<Path> catapults = mr.getCatapults(mr.getMe());
		for (Path p : catapults) {
			Set<Path> neighbourPaths = mr.getPathsFromPath(p);
			for (Path path : neighbourPaths) {
				float currentValue = evaluateStreetValue(mr, path);
				if (currentValue > bestValue)
					bestValue = currentValue;
				sourcePath = new Path(p.getLocation());
				bestPath = path;
			}

		}
		return bestPath;
	}

	public int evaluate(ModelReader mr) {
		if (mr.getCatapults(mr.getMe()).size() < 1) {
			return -1;
		}
		return 0;
	}
}
