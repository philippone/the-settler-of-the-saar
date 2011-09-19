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

public class BuildVillage implements Strategy {
	private float bestValue = 0;
	private Intersection bestIntersection = null;
	private float intersectionValue = (float)(0.0);
	private float fieldNumberValue = (float)(0.0);
	private float fieldTypeValue = (float)(0.0);
	private float HarborValue = (float)(0.0);
	Strategy s;

	@Override
	public void execute(ModelReader mr, ControllerAdapter ca) throws Exception {

		if (mr.affordableSettlements(BuildingType.Village) > 0 && mr.buildableVillageIntersections(mr.getMe()).size() > 0
				&& mr.buildableVillageIntersections(mr.getMe()).size() <= mr.getMaxBuilding(BuildingType.Village) && mr.getSettlements(mr.getMe(), BuildingType.Village).size() < mr.getMaxBuilding(BuildingType.Village)) {
			Intersection bestIntersection = evaluateIntersection(mr);
			ca.buildSettlement(bestIntersection, BuildingType.Village);
			if (mr.getMe().getVictoryPoints() >= mr.getMaxVictoryPoints())
				ca.claimVictory();
		} else 	if (mr.buildableVillageIntersections(mr.getMe()).size() < 1) {
			Strategy buildStreet = new BuildStreetStrategy();
			buildStreet.execute(mr, ca);
		} 
	}
	public float evaluate(ModelReader mr, ControllerAdapter ca) throws Exception {
		if (!(mr.affordableSettlements(BuildingType.Village) > 0
				&& mr.buildableVillageIntersections(mr.getMe()).size() > 0
				&& mr.buildableVillageIntersections(mr.getMe()).size() <= mr.getMaxBuilding(BuildingType.Village)
				&& mr.getSettlements(mr.getMe(), BuildingType.Village).size() < mr.getMaxBuilding(BuildingType.Village))) return -1;
		// TODO: check the trade
		return 1;
	}
	
	private float evaluateIntersectionValue(ModelReader mr, Intersection i) {
			float intersectionValue=0;
			float fieldNumberValue=0;
			float fieldTypeValue=0;
			Set<Field> neighborFields = mr.getFieldsFromIntersection(i);
			int n;	
			FieldType type;
			for(Field field : neighborFields){
				n=field.getNumber();
				if (n==2 || n==12)	fieldNumberValue= (float) (fieldNumberValue + 0.030);
				else 
					if (n==3 || n==11)
						fieldNumberValue= (float) (fieldNumberValue + 0.060);
				else 
					if (n==4 || n==10) 
						fieldNumberValue= (float) (fieldNumberValue + 0.080);
				else 
					if (n==5 || n==9) 
						fieldNumberValue= (float) (fieldNumberValue + 0.110);
				else if (n==6 || n==8) 
					fieldNumberValue= (float) (fieldNumberValue + 0.140);
				type=field.getFieldType();
				if (type==FieldType.FOREST || type==FieldType.HILLS || type==FieldType.PASTURE || type==FieldType.FIELDS || type==FieldType.MOUNTAINS) fieldTypeValue =(float)(fieldTypeValue+0.10);

			intersectionValue=intersectionValue+fieldTypeValue+fieldNumberValue;
		}
		return intersectionValue;
	}
	
	private Intersection evaluateIntersection(ModelReader mr){

		Set<Intersection> intersectionTest = mr.buildableVillageIntersections(mr.getMe());
		for (Intersection i : intersectionTest){
			float currentValue = evaluateIntersectionValue(mr, i);
			if (currentValue > bestValue) {
				bestValue = currentValue;
				bestIntersection = i;
			}
				
		}
		return bestIntersection;
	}
	
	public AIGameStats getGameStats(ModelReader mr) {
		Player player = mr.getMe();
		//TODO new function buildableVillages
		if (mr.getMaxBuilding(BuildingType.Village)-1 < mr.getSettlements(player, BuildingType.Village).size())
		return new AIGameStats(player, this, new ResourcePackage(0, 0, 0, 0, 0), 0);
		ResourcePackage resourcePackage = player.getResources().copy().add(new ResourcePackage(-1, -1, -1, -1, 0));
		int victoryPoints = player.getVictoryPoints() + 1;
		AIGameStats gameStats = new AIGameStats(player, this, resourcePackage, victoryPoints);
		return gameStats;
	}
	
	public boolean tradePossible(ModelReader mr){
		ResourcePackage resourcePackage = mr.getMe().getResources().copy();
		if (resourcePackage.getResource(Resource.LUMBER) > 0){
			resourcePackage.add(new ResourcePackage(-1, 0, 0, 0, 0));
		}
		if (resourcePackage.getResource(Resource.BRICK) > 0){
			resourcePackage.add(new ResourcePackage(0, -1, 0, 0, 0));
		}
		if (resourcePackage.getResource(Resource.WOOL) > 0){
			resourcePackage.add(new ResourcePackage(0, 0, -1, 0, 0));
		}
		if (resourcePackage.getResource(Resource.GRAIN) > 0){
			resourcePackage.add(new ResourcePackage(0, 0, 0, -1, 0));
		}
		if (resourcePackage.getPositiveResourcesCount() > 0)
			return true;
		else
		return false;
	}
	
}
