package de.unisaarland.cs.sopra.common.view.ai;


import java.util.Set;

import de.unisaarland.cs.sopra.common.model.BuildingType;

import de.unisaarland.cs.sopra.common.model.Intersection;
import de.unisaarland.cs.sopra.common.model.ModelReader;
import de.unisaarland.cs.sopra.common.model.Path;

public class AttackStrategy extends Strategy {

	public AttackStrategy(ModelReader mr) {
		super(mr);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean evaluates(Stroke s) {
		switch(s.getType()){
		default:
			throw new IllegalArgumentException("The stroke is no valid stroke!");
		case ATTACK_CATAPULT:
			return true;
		case ATTACK_SETTLEMENT:
			return true;
		case BUILD_VILLAGE:
			return false;
		case BUILD_TOWN:
			return true;
		case BUILD_CATAPULT:
			return true;
		case BUILD_STREET:
			return false;
		case MOVE_CATAPULT:
			return true;
		case MOVE_ROBBER:
			return false;
		case RETURN_RESOURCES:
			return false;
		}
	}

	@Override
	public double evaluate(AttackCatapult stroke) {
		double value=0;
		Path destination=stroke.getDestination();
		if (destination.hasCatapult() && destination.getCatapultOwner()!=mr.getMe())
			value=value+0.7;
		// searching if there's a next target
		double targetingCatapultValue=0;
		double targetingTownValue=0;
		double targetingVillageValue=0;
		Set<Path> paths1=mr.getPathsFromPath(destination);
		 for (Path p1 : paths1) {
			 if (p1.hasCatapult() && p1.getCatapultOwner()!=mr.getMe())
				 targetingCatapultValue=targetingCatapultValue+0.2;
			 // for all neighbor intersections of neighbor Paths
			 Set<Intersection> si=mr.getIntersectionsFromPath(p1);
			 for (Intersection i: si){
				 if (i.hasOwner() && i.getOwner()!=mr.getMe()){
					 if (i.getBuildingType()==BuildingType.Town)
						 targetingTownValue=targetingTownValue+0.2;
					 else if(i.getBuildingType()==BuildingType.Village)
						 targetingVillageValue=targetingVillageValue+0.2;
				 }
			 }
			 // for all neighbor paths of neighbor paths
			 Set<Path> paths2=mr.getPathsFromPath(p1);
			 for (Path p2: paths2){
				 if (p2!=destination){
				 if (p2.hasCatapult() && p2.getCatapultOwner()!=mr.getMe())
					 targetingCatapultValue=targetingCatapultValue+0.1;
				 // for all neighbor intersections of neighbor Paths of neighbor paths
				 si=mr.getIntersectionsFromPath(p2);
				 for (Intersection i: si){
					 if (i.hasOwner() && i.getOwner()!=mr.getMe()){
						 if (i.getBuildingType()==BuildingType.Town)
							 targetingTownValue=targetingTownValue+0.1;
						 else if(i.getBuildingType()==BuildingType.Village)
							 targetingVillageValue=targetingVillageValue+0.1;
					 }
				 }
				 }
			 } 
		 }
		 value=value+targetingCatapultValue+targetingTownValue+targetingVillageValue;
		return Math.min(value, 1);
	}

	@Override
	public double evaluate(AttackSettlement stroke) {
		Path source=stroke.getSource();
		Set<Path> paths=mr.getPathsFromPath(source);
		for (Path p: paths){
			if (mr.attackableCatapults(source).contains(p)) return 0;
			// there's a catapult to destroy before the settlement
		}
		double value=0;
		Intersection destination=stroke.getDestination();
		if (destination.getBuildingType()==BuildingType.Town) value=value+0.9;
		else if (destination.getBuildingType()==BuildingType.Village) value=value+0.8;
		return value;
	}

	@Override
	public double evaluate(BuildVillage stroke) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double evaluate(BuildTown stroke) {
		 double value=0;
		 Intersection location=stroke.getDestination();
		 Set<Path> sp0=mr.getPathsFromIntersection(location);
		 for (Path p0: sp0){
			 Set<Path> sp1=mr.getPathsFromPath(p0);
			 for (Path p1: sp1){
			 	Set<Intersection> si=mr.getIntersectionsFromPath(p1);
				for (Intersection i: si){
					if (i.hasOwner() && i.getOwner()==mr.getMe() && i.getBuildingType()==BuildingType.Town)
						return 0;
					// Ok no need to build a town, we got one not so far that can create catapults
					if (i.hasOwner() && i.getOwner()!=mr.getMe())
						value=value+0.5;
					// interesting there's targets
				}
				if (p1.hasCatapult() && p1.getCatapultOwner()!=mr.getMe())
					value=value+0.5;
				// Warning there's an ennemy catapult coming
			 }
		 }
		 if (mr.getSettlements(mr.getMe(), BuildingType.Town).size()<1) value=value+0.5;
		 return Math.min(value,1);
	}

	@Override
	public double evaluate(BuildCatapult stroke) {
		 double pathValue= 0.0;
		 double targetingCatapultValue=0;
		 double targetingTownValue=0;
		 double targetingVillageValue=0;
		 boolean doesIHazAlreadyACatapultNotSoFar=false;
		 Path p0 = stroke.getDestination();
		 if (p0.hasCatapult() && p0.getCatapultOwner()==mr.getMe()) 
			 return 0;
		 	// no need to build a catapult here, there's already one
		 // now: SEARCHIIIIIIIIIIIIIING, SEEK AND DESTROY!!
		 // for all neighbor paths
		 Set<Path> paths1=mr.getPathsFromPath(p0);
		 for (Path p1 : paths1) {
			 doesIHazAlreadyACatapultNotSoFar=(p1.hasCatapult() && p1.getCatapultOwner()==mr.getMe());
			 if (p1.hasCatapult() && p1.getCatapultOwner()!=mr.getMe())
				 targetingCatapultValue=targetingCatapultValue+0.2;
			 // for all neighbor intersections of neighbor Paths
			 Set<Intersection> si=mr.getIntersectionsFromPath(p1);
			 for (Intersection i: si){
				 if (i.hasOwner() && i.getOwner()!=mr.getMe()){
					 if (i.getBuildingType()==BuildingType.Town)
						 targetingTownValue=targetingTownValue+0.2;
					 else if(i.getBuildingType()==BuildingType.Village)
						 targetingVillageValue=targetingVillageValue+0.2;
				 }
			 }
			 // for all neighbor paths of neighbor paths
			 Set<Path> paths2=mr.getPathsFromPath(p1);
			 for (Path p2: paths2){
				 if (p2!=p0){
				 if (p2.hasCatapult() && p2.getCatapultOwner()!=mr.getMe())
					 targetingCatapultValue=targetingCatapultValue+0.1;
				 // for all neighbor intersections of neighbor Paths of neighbor paths
				 si=mr.getIntersectionsFromPath(p2);
				 for (Intersection i: si){
					 if (i.hasOwner() && i.getOwner()!=mr.getMe()){
						 if (i.getBuildingType()==BuildingType.Town)
							 targetingTownValue=targetingTownValue+0.1;
						 else if(i.getBuildingType()==BuildingType.Village)
							 targetingVillageValue=targetingVillageValue+0.1;
					 }
				 }
				 }
			 } 
		 }
		 if (p0.hasCatapult() && p0.getCatapultOwner()!=mr.getMe() && !doesIHazAlreadyACatapultNotSoFar ) 
			 targetingCatapultValue=targetingCatapultValue+0.3;
		 pathValue=3*targetingCatapultValue+2*targetingTownValue+targetingVillageValue;
		 if (mr.getCatapults(mr.getMe()).size()<1) pathValue=pathValue+0.6;
		 // little bonus if we don't have any catapult yet
		 return Math.min(pathValue,1);
	}

	@Override
	public double evaluate(BuildStreet stroke) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double evaluate(MoveCatapult stroke) {
		Path source=stroke.getSource();
		Set<Intersection> si=mr.getIntersectionsFromPath(source);
		for (Intersection i:si){
			if (i.hasOwner() && i.getOwner()!=mr.getMe())
				return 0;
			// no need to move we got a target!
		}
		if (mr.attackableCatapults(source).size()>0) return 0;
		// no need to move we got a target!
		// now let seek the next target
		Path destination=stroke.getDestination();
		double value=0;
		double targetingCatapultValue=0;
		double targetingTownValue=0;
		double targetingVillageValue=0;
		Set<Path> paths1=mr.getPathsFromPath(destination);
		 for (Path p1 : paths1) {
			 si=mr.getIntersectionsFromPath(p1);
			 for (Intersection i: si){
				 if (i.hasOwner() && i.getOwner()!=mr.getMe()){
					 if (i.getBuildingType()==BuildingType.Town)
						 targetingTownValue=targetingTownValue+0.2;
					 else if(i.getBuildingType()==BuildingType.Village)
						 targetingVillageValue=targetingVillageValue+0.2;
				 }
			 }
			 Set<Path> paths2=mr.getPathsFromPath(p1);
			 for (Path p2: paths2){
				 if (p2!=destination){
				 if (p2.hasCatapult() && p2.getCatapultOwner()!=mr.getMe())
					 targetingCatapultValue=targetingCatapultValue+0.1;
				 si=mr.getIntersectionsFromPath(p2);
				 for (Intersection i: si){
					 if (i.hasOwner() && i.getOwner()!=mr.getMe()){
						 if (i.getBuildingType()==BuildingType.Town)
							 targetingTownValue=targetingTownValue+0.1;
						 else if(i.getBuildingType()==BuildingType.Village)
							 targetingVillageValue=targetingVillageValue+0.1;
					 }
				 }
				 }
			 } 
		 }
		 value=3*targetingCatapultValue+2*targetingTownValue+targetingVillageValue;
		return Math.max(value, 1);
	}

	@Override
	public double evaluate(MoveRobber stroke) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double evaluate(ReturnResources stroke) {
		// TODO Auto-generated method stub
		return 0;
	}

}
