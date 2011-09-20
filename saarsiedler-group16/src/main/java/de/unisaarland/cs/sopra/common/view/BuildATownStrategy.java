package de.unisaarland.cs.sopra.common.view;

import java.util.Set;

import de.unisaarland.cs.sopra.common.controller.ControllerAdapter;
import de.unisaarland.cs.sopra.common.model.BuildingType;
import de.unisaarland.cs.sopra.common.model.Field;
import de.unisaarland.cs.sopra.common.model.FieldType;
import de.unisaarland.cs.sopra.common.model.Intersection;
import de.unisaarland.cs.sopra.common.model.ModelReader;
import de.unisaarland.cs.sopra.common.model.Player;
import de.unisaarland.cs.sopra.common.model.Resource;
import de.unisaarland.cs.sopra.common.model.ResourcePackage;

public class BuildATownStrategy extends Strategy {
	
	public BuildATownStrategy() {
		super(BuildingType.Village.getVictoryPoints(), BuildingType.Town.getPrice());
	}

	@Override
	public void execute(ModelReader mr, ControllerAdapter ca) throws Exception {
		// TODO Auto-generated method stub
		
		if (mr.affordableSettlements(BuildingType.Town)>0 
				&& mr.buildableTownIntersections(mr.getMe()).size() > 0
				&& mr.getSettlements(mr.getMe(),BuildingType.Town).size()<mr.getMaxBuilding(BuildingType.Town))
		{
			Intersection bestIntersection=chooseBestIntersection(mr);
			ca.buildSettlement(bestIntersection, BuildingType.Town);
			if (mr.getMe().getVictoryPoints() >= mr.getMaxVictoryPoints())
				ca.claimVictory();
		} 
	}	
	
	public float evaluate(ModelReader mr, ControllerAdapter ca) throws Exception{
		if (!(mr.affordableSettlements(BuildingType.Town)>0 
				&& mr.buildableTownIntersections(mr.getMe()).size() > 0
				&& mr.buildableTownIntersections(mr.getMe()).size() < mr.getMaxBuilding(BuildingType.Town) 
				&& mr.getSettlements(mr.getMe(),BuildingType.Town).size()<mr.getMaxBuilding(BuildingType.Town))) return -1;
		// TODO: check the trade
		return 1;
	}
	
	private Intersection chooseBestIntersection(ModelReader mr){
		Intersection bestIntersection=null;
		Set<Intersection> intersections=mr.buildableTownIntersections(mr.getCurrentPlayer());
		float bestValue=0;
		float value;
		for (Intersection i: intersections){
			value=evaluateIntersection(mr,i);
			if (value>=bestValue){
				bestValue=value;
				bestIntersection=i;
			}
		}
		return bestIntersection;
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
			if (type==FieldType.DESERT || type==FieldType.WATER)resourceValue=(float)(resourceValue+0.00);
			else resourceValue=(float)(resourceValue+0.11);
		}
		intersectionValue=resourceValue+numberValue;
		return intersectionValue;
	}
	
	public boolean tradePossible(ModelReader mr){
		ResourcePackage resourcePackage = mr.getMe().getResources().copy();
		if (resourcePackage.getResource(Resource.GRAIN) > 0){
			resourcePackage.add(new ResourcePackage(0, 0, 0, -1, 0));
		}
		if (resourcePackage.getResource(Resource.GRAIN) > 0){
			resourcePackage.add(new ResourcePackage(0, 0, 0, -1, 0));
		}
		if (resourcePackage.getResource(Resource.ORE) > 0){
			resourcePackage.add(new ResourcePackage(0, 0, 0, 0, -1));
		}
		if (resourcePackage.getResource(Resource.ORE) > 0){
			resourcePackage.add(new ResourcePackage(0, 0, 0, 0, -1));
		}
		if (resourcePackage.getResource(Resource.ORE) > 0){
			resourcePackage.add(new ResourcePackage(0, 0, 0, 0, -1));
		}
		if (resourcePackage.getPositiveResourcesCount() > 0)
			return true;
		else
		return false;
	}
	
	@Override
	public boolean useable(ModelReader mr) {
		Player p = mr.getMe();
		if (mr.getSettlements(p, BuildingType.Town).size() < mr.getMaxBuilding(BuildingType.Town) &&
				 mr.buildableTownIntersections(p).size() > 0)
					return true;
		return false;
	}
	
}
