package de.unisaarland.cs.sopra.common.view.ai.copy;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import de.unisaarland.cs.sopra.common.model.BuildingType;
import de.unisaarland.cs.sopra.common.model.Field;
import de.unisaarland.cs.sopra.common.model.HarborType;
import de.unisaarland.cs.sopra.common.model.Intersection;
import de.unisaarland.cs.sopra.common.model.ModelReader;
import de.unisaarland.cs.sopra.common.model.Resource;
import de.unisaarland.cs.sopra.common.model.ResourcePackage;

public class KaisInitResourceStrategy extends Strategy {
	

	public KaisInitResourceStrategy(ModelReader mr) {
		super(mr);
	}

	@Override
	public boolean evaluates(Stroke s){
		switch(s.getType()){
		default:
			throw new IllegalArgumentException("The stroke is no valid stroke!");
		case ATTACK_CATAPULT:
			return false;
		case ATTACK_SETTLEMENT:
			return false;
		case BUILD_VILLAGE:
			return true;
		case BUILD_TOWN:
			return false;
		case BUILD_CATAPULT:
			return false;
		case BUILD_STREET:
			return false;
		case MOVE_CATAPULT:
			return false;
		case MOVE_ROBBER:
			return false;
		case RETURN_RESOURCES:
			return false;
		}
	}

	@Override
	public double importance() {
		return 2;
	}

	@Override
	public double evaluate(AttackCatapult stroke) {
		return 0;
	}

	@Override
	public double evaluate(AttackSettlement stroke) {
		return 0;
	}

	@Override
	public double evaluate(BuildVillage stroke) {
		double evaluation = 0;
		Intersection destination = stroke.getDestination();
		Set<Resource> reslist = new HashSet<Resource>();
		Iterator<Field> fieldIterator = mr.getFieldIterator();
		//resourcePackage showing how many of the resources are there on the map
		ResourcePackage resourcePackage = new ResourcePackage();
		while (fieldIterator.hasNext()){
			Field field = fieldIterator.next();
			if (field.getNumber() != -1)
				resourcePackage.modifyResource(field.getResource(), 1);
			}
		// finding the resource with greatest quantity 
		Resource max = Resource.WOOL;
		for (Resource r : Resource.values()){
			max = resourcePackage.getResource(max) < resourcePackage.getResource(r) ? r : max;
		}	
			// choosing the best Harbor for the map
			HarborType bestHarbor = HarborType.GENERAL_HARBOR;
			if (max == Resource.LUMBER) {
				bestHarbor = HarborType.LUMBER_HARBOR;
			} else if (max == Resource.BRICK)
				bestHarbor = HarborType.BRICK_HARBOR;
			else if (max == Resource.GRAIN)
				bestHarbor = HarborType.GRAIN_HARBOR;
			else if (max == Resource.ORE) {
				bestHarbor = HarborType.ORE_HARBOR;
			} else if (max == Resource.WOOL)
				bestHarbor = HarborType.WOOL_HARBOR;
		// build a village close to a harbour
		if (mr.getSettlements(mr.getMe(), BuildingType.Village).size() == 0){
			if (mr.getHarborIntersections().contains(destination) && mr.getHarborType(destination) == bestHarbor){
				int numberOfLandFields = 0;
				for (Field neighbour : mr.getFieldsFromIntersection(destination)){
					if (neighbour.getResource() != null) numberOfLandFields++;
				}
				if (numberOfLandFields == 2) return 1;
			}
		}
		/*
		// try to get 3 different resources
		if (mr.getSettlements(mr.getMe(), BuildingType.Village).size() == 0){
			getResourcesFromIntersection(destination);
			if (reslist.size() < 3) evaluation = 0;
			else evaluation = 1;
		}
		*/
		// try to get the missing ones to build a village
		else {
			if (getResourcesThatWeOwn().size() < 5){
				int differentResources = 0;
				for (Intersection otherVillages : mr.getSettlements(mr.getMe(), BuildingType.Village)){
					reslist.addAll(getResourcesFromIntersection(otherVillages));
				}
				getMissingVillageResources(reslist);
				for (Field neighbour : mr.getFieldsFromIntersection(destination)){
					if (neighbour.getResource() != null){
						if (!reslist.contains(neighbour.getResource())){
							differentResources++;
						}
					}
				}
				if (reslist.size() + differentResources == 5) return 1;
				else return 0;
			}
			else {
				int numberOfGoodFields = 0;
				for (Field neighbour : mr.getFieldsFromIntersection(destination)){
					if (neighbour.getResource() == max) numberOfGoodFields++;
				}
				if (numberOfGoodFields >= 2) return 1;
			}
		}
		return evaluation;
	}
	
	private Set<Resource> getResourcesThatWeOwn(){
		Set<Resource> resset = new HashSet<Resource>();
		for (Intersection village : mr.getSettlements(mr.getMe(), BuildingType.Village)){
			for (Field neighbours : mr.getFieldsFromIntersection(village)){
				resset.add(neighbours.getResource());
			}
		}
		return resset;
	}
	
	private Set<Resource> getResourcesFromIntersection(Intersection inter){
		Set<Resource> reslist = new HashSet<Resource>();
		for (Field neighbour : mr.getFieldsFromIntersection(inter)){
			if (neighbour.getResource() != null){
				reslist.add(neighbour.getResource());
			}
		}
		return reslist;
	}
	
	private Set<Resource> getMissingVillageResources(Set<Resource> resourcesThatWeOwn){
		Set<Resource> reslist = new HashSet<Resource>();
		for (Resource res : Resource.values()){
			if (BuildingType.Village.getPrice().getResource(res) < 0){
				if (!resourcesThatWeOwn.contains(res)){
					reslist.add(res);
				}
			}
		}
		return reslist;
	}

	@Override
	public double evaluate(BuildTown stroke) {
		return 0;
	}

	@Override
	public double evaluate(BuildCatapult stroke) {
		return 0;
	}

	@Override
	public double evaluate(BuildStreet stroke) {
		return 0;
	}

	@Override
	public double evaluate(MoveCatapult stroke) {
		return 0;
	}

	@Override
	public double evaluate(MoveRobber stroke) {
		return 0;
	}

	@Override
	public double evaluate(ReturnResources stroke) {
		return 0;
	}

}
