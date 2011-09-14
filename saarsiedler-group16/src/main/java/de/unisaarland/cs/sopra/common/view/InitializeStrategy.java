package de.unisaarland.cs.sopra.common.view;

import java.io.IOException;

import java.util.HashSet;
import java.util.Set;

import de.unisaarland.cs.sopra.common.controller.ControllerAdapter;
import de.unisaarland.cs.sopra.common.model.BuildingType;
import de.unisaarland.cs.sopra.common.model.Field;
import de.unisaarland.cs.sopra.common.model.FieldType;
import de.unisaarland.cs.sopra.common.model.Intersection;
import de.unisaarland.cs.sopra.common.model.ModelReader;
import de.unisaarland.cs.sopra.common.model.Path;

public class InitializeStrategy implements Strategy {

	@Override
	public void execute(ModelReader mr, ControllerAdapter ca) throws Exception {
		if (mr.getMe() == mr.getCurrentPlayer()){
		Set<Intersection> intersections =mr.buildableVillageIntersections(mr.getMe());
		/*	Intersection i = intersections.iterator().next();
			ca.buildSettlement(i, BuildingType.Village);
			Set<Path> paths = mr.buildableStreetPaths(mr.getMe());
			Path p = paths.iterator().next();
			ca.buildStreet(p);*/
	
		
		Intersection bestIntersection=null;
		float bestValue=0;
		float value;
		for (Intersection i : intersections){
			value=evaluateIntersection(mr,i);
			if (value>=bestValue){
				bestIntersection=i;
				bestValue=value;
			}

		}
		
		ca.buildSettlement(bestIntersection, BuildingType.Village);
		Set<Path> neighbourPaths = mr.buildableStreetPaths(mr.getMe());
		
		Path path = neighbourPaths.iterator().next();	
		ca.buildStreet(path);
		
		}
	}
	
	private float evaluateIntersection(ModelReader mr,Intersection i){
		float intersectionValue=0;
		float resourceValue=0;
		float numberValue=0;
		Set<Field> neighborFields=mr.getFieldsFromIntersection(i);
		int n;
		FieldType type;
		for(Field field : neighborFields){
			n=field.getNumber();
			if (n==2 || n==12)numberValue=(float)(numberValue+0.030);
			else if (n==3 || n==11)numberValue=(float)(numberValue+0.060);
			else if (n==4 || n==10) numberValue=(float)(numberValue+0.080);
			else if (n==5 || n==9) numberValue=(float)(numberValue+0.110);
			else if (n==6 || n==8) numberValue=(float)(numberValue+0.140);
			type=field.getFieldType();
			if (type==FieldType.FOREST || type==FieldType.HILLS || type==FieldType.PASTURE ||
					type==FieldType.FIELDS || type==FieldType.MOUNTAINS)
							resourceValue =(float)(resourceValue+0.10);
		}
		intersectionValue=resourceValue+numberValue;
		return intersectionValue;
	}
//	
//	private float evaluatePath(ModelReader mr,Path p){
//	
//		Set<Path> neighbourPaths = mr.getPathsFromPath(p);
//		Set<Path> buildablePaths = new HashSet<Path>();
//		for (Path p1 : neighbourPaths){
//			if (mr.buildableStreetPaths(mr.getMe()).contains(p1)) {
//				buildablePaths.add(p1);
//			}
//			Path path = buildablePaths.iterator().next();	
//			}
//		}
//		return pathValue;
//	}
	
	
	public int evaluate(){
		// TODO implement this method
		return 0;
	}
}
