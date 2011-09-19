package de.unisaarland.cs.sopra.common.view;

import de.unisaarland.cs.sopra.common.controller.ControllerAdapter;
import de.unisaarland.cs.sopra.common.model.ModelReader;
import de.unisaarland.cs.sopra.common.model.ResourcePackage;

public class InitializeStrategy extends Strategy {
	
	
	public InitializeStrategy() {
		super(0, new ResourcePackage());
	}

	@Override
	public void execute(ModelReader mr, ControllerAdapter ca) throws Exception {
		new BuildVillageStrategy().execute(mr, ca);
		new BuildStreetStrategy().execute(mr, ca);
	}

	
	//private Set<Resource> playersResources = new HashSet<Resource>();

	/*
	@Override
	public void execute(ModelReader mr, ControllerAdapter ca) throws Exception {
		if (mr.getMe() == mr.getCurrentPlayer()){
		Set<Intersection> intersections =mr.buildableVillageIntersections(mr.getMe());
		//	Intersection i = intersections.iterator().next();
		//	ca.buildSettlement(i, BuildingType.Village);
		//	Set<Path> paths = mr.buildableStreetPaths(mr.getMe());
		//	Path p = paths.iterator().next();
		//	ca.buildStreet(p);
		
	
		
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
		setPlayersResources(mr, bestIntersection);
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
			if (type==FieldType.FOREST){
				if (playersResources.contains(Resource.LUMBER))	{
					resourceValue = (float) (resourceValue + 0.145);
				} 
				resourceValue = (float) (resourceValue + 0.5);
			}
			else 			if (type==FieldType.HILLS){
				if (playersResources.contains(Resource.BRICK))	{
					resourceValue = (float) (resourceValue + 0.145);
				} 
				resourceValue = (float) (resourceValue + 0.5);
			}
			else 			if (type==FieldType.PASTURE){
				if (playersResources.contains(Resource.WOOL))	{
					resourceValue = (float) (resourceValue + 0.145);
				} 
				resourceValue = (float) (resourceValue + 0.5);
			}
			else 			if (type==FieldType.FIELDS){
				if (playersResources.contains(Resource.GRAIN))	{
					resourceValue = (float) (resourceValue + 0.145);
				} 
				resourceValue = (float) (resourceValue + 0.5);
			} 
			else 			if (type==FieldType.MOUNTAINS){
				if (playersResources.contains(Resource.ORE))	{
					resourceValue = (float) (resourceValue + 0.145);
				} 
				resourceValue = (float) (resourceValue + 0.25);
			}
			
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
	
	
	public void setPlayersResources(ModelReader mr,Intersection i){
		Set<Field> fieldSet = mr.getFieldsFromIntersection(i);
		for (Field f : fieldSet){
			playersResources.add(f.getResource());
		}
			
	}
	*/
	
	@Override
	public boolean useable(ModelReader mr) {
		//TODO implement this operation
		return true;
	}

}
