package de.unisaarland.cs.sopra.common.view.ai;

import java.util.HashSet;
import java.util.Set;

import de.unisaarland.cs.sopra.common.model.BuildingType;
import de.unisaarland.cs.sopra.common.model.Field;
import de.unisaarland.cs.sopra.common.model.Intersection;
import de.unisaarland.cs.sopra.common.model.ModelReader;
import de.unisaarland.cs.sopra.common.model.Resource;

public class KaisInitResourceStrategy extends Strategy {
	

	public KaisInitResourceStrategy(ModelReader mr) {
		super(mr);
	}

	@Override
	public boolean evaluates(Stroke s){
		boolean evaluates = false;
		if (s.getType() == StrokeType.BUILD_VILLAGE){
			Set<Resource> ourResources = getResourcesThatWeOwn();
			evaluates = mr.getHarborTypes(mr.getMe()).size() > 0 && ourResources.size() < 5;
		}
		return evaluates;
	}

	@Override
	public double importance() {
		return 1;
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
		Set<Resource> ourResources = getResourcesThatWeOwn();
		Set<Resource> interResources = getResourcesFromIntersection(stroke.getDestination());
		Set<Resource> missingResources = getMissingVillageResources(ourResources);
		if (interResources.containsAll(missingResources)){
			evaluation = 1;
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
