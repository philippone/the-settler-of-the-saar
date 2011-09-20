package de.unisaarland.cs.sopra.common.view;

import java.util.Set;

import de.unisaarland.cs.sopra.common.controller.ControllerAdapter;
import de.unisaarland.cs.sopra.common.model.BuildingType;
import de.unisaarland.cs.sopra.common.model.Catapult;
import de.unisaarland.cs.sopra.common.model.Intersection;
import de.unisaarland.cs.sopra.common.model.ModelReader;
import de.unisaarland.cs.sopra.common.model.Path;
import de.unisaarland.cs.sopra.common.model.Resource;
import de.unisaarland.cs.sopra.common.model.ResourcePackage;

public class AttackSettlementStrategy extends Strategy {

	public AttackSettlementStrategy() {
		super(0, Catapult.getAttackbuildingprice());
	}

	Path sourcePath;
	@Override
	public void execute(ModelReader mr, ControllerAdapter ca) throws Exception {
		// TODO Auto-generated method stub
		if ((mr.getCatapults(mr.getMe()).size()>0
				&& (mr.attackableSettlements(mr.getMe(), BuildingType.Town).size()>0)
				&& (mr.attackableSettlements(mr.getMe(), BuildingType.Village).size()>0))){
			Intersection target=chooseTarget(mr);
			ca.attackSettlement(sourcePath, target);
		}	
	}
	
	private Intersection chooseTarget(ModelReader mr){
		float bestValue = 0;
		Intersection bestIntersection = null;
		Set<Path> catapults = mr.getCatapults(mr.getMe());
		for (Path p : catapults) {
			Set<Intersection> neighbourIntersections = mr.getIntersectionsFromPath(p);
			for (Intersection intersection : neighbourIntersections) {
				float currentValue = evaluateIntersection(mr, intersection);
				if (currentValue > bestValue){
					bestValue = currentValue;
					sourcePath = p;
					bestIntersection = intersection;
				}
			}

		}
		return bestIntersection;
	}
	
	private float evaluateIntersection(ModelReader mr, Intersection i){
		if (i.hasOwner() && i.getOwner()!=mr.getMe() && i.getBuildingType()==BuildingType.Town) return 1;
		if (i.hasOwner() && i.getOwner()!=mr.getMe() && i.getBuildingType()==BuildingType.Village) return (float) 0.8;
		return 0;
	}
	
	public float evaluate(ModelReader mr, ControllerAdapter ca) throws Exception {
		// TODO Auto-generated method stub
		if (!(mr.getCatapults(mr.getMe()).size()>0
				&& (mr.attackableSettlements(mr.getMe(), BuildingType.Town).size()>0)
				&& (mr.attackableSettlements(mr.getMe(), BuildingType.Village).size()>0))) return -1;
		// TODO: check the trade
		return 1;
	}
	
	public boolean tradePossible(ModelReader mr){
		ResourcePackage resourcePackage = mr.getMe().getResources().copy();
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
		if (mr.getCatapults(mr.getMe()).size() < 0)
			return false;
		else if (mr.attackableSettlements(mr.getMe(), BuildingType.Village).size() > 0
				|| mr.attackableSettlements(mr.getMe(), BuildingType.Town).size() > 0)
			return true;
		return false;
	}
}
