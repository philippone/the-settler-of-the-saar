package de.unisaarland.cs.sopra.common.view.ai;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import de.unisaarland.cs.sopra.common.model.BuildingType;
import de.unisaarland.cs.sopra.common.model.Field;
import de.unisaarland.cs.sopra.common.model.Intersection;
import de.unisaarland.cs.sopra.common.model.ModelReader;
import de.unisaarland.cs.sopra.common.model.Path;
import de.unisaarland.cs.sopra.common.model.Resource;

public class KaisGetTheMissingResourcesStrategy extends Strategy {

	public KaisGetTheMissingResourcesStrategy(ModelReader mr) {
		super(mr);
	}

	@Override
	public boolean evaluates(Stroke s) {
		boolean evaluates = false;
		if (s.getType() == StrokeType.BUILD_STREET)
			evaluates = getMissingResources().size() > 0 &&
			mr.buildableVillageIntersections(mr.getMe()).size() == 0;
		else if (s.getType() == StrokeType.BUILD_VILLAGE)
			evaluates = getMissingResources().size() > 0;
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
		if (hasTheMissingResources(stroke.getDestination()))
				evaluation = 1;
		return evaluation;
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
		double evaluation = 0;
		if (hasTheMissingResources(getTheRightIntersectionNeighbour(stroke.getDestination())))
				evaluation = 1;
		return evaluation;
	}
	
	private boolean hasTheMissingResources(Intersection destination){
		int numberOfResourcesWeCouldGet = 0;
		Set<Resource> missingResources = getMissingResources();
		Set<Resource> resourcesWeCouldGet = new HashSet<Resource>();
		Intersection theRightOne = destination;
		for (Field neighbourField : mr.getFieldsFromIntersection(theRightOne)){
			if (neighbourField.getNumber() != -1)
				resourcesWeCouldGet.add(neighbourField.getResource());
		}
		numberOfResourcesWeCouldGet = getNumberOfEqualElements(missingResources, resourcesWeCouldGet);
		return (numberOfResourcesWeCouldGet == missingResources.size());
	}
	
	private Intersection getTheRightIntersectionNeighbour(Path destination){
		Intersection theRightOne = null;
		for (Intersection neighbour : mr.getIntersectionsFromPath(destination)){
			int numberOfMyPaths = 0;
			for (Path path : mr.getPathsFromIntersection(neighbour)){
				if (path.hasStreet() && path.getStreetOwner() == mr.getMe()) numberOfMyPaths++;
			}
			if (numberOfMyPaths <= 1)
				theRightOne = neighbour;
		}
		return theRightOne;
	}
	
	private int getNumberOfEqualElements(Set<Resource> set1, Set<Resource> set2){ 
		int numberOfEqualElements = 0;
		for (Resource r1 : set1){
			if (set2.contains(r1)) numberOfEqualElements++;
		}
		return numberOfEqualElements;
	}
	
	private Set<Resource> getResourcesThatWeOwn(){
		Set<Resource> resset = new HashSet<Resource>();
		for (Intersection village : mr.getSettlements(mr.getMe(), BuildingType.Village)){
			for (Field neighbours : mr.getFieldsFromIntersection(village)){
				resset.add(neighbours.getResource());
			}
		}
		for (Intersection village : mr.getSettlements(mr.getMe(), BuildingType.Town)){
			for (Field neighbours : mr.getFieldsFromIntersection(village)){
				resset.add(neighbours.getResource());
			}
		}
		return resset;
	}
	
	private Set<Resource> getMissingResources(){
		Set<Resource> resourcesThatWeOwn = getResourcesThatWeOwn();
		Set<Resource> reslist = new HashSet<Resource>();
		reslist.addAll(Arrays.asList(Resource.values()));
		for (Resource res : resourcesThatWeOwn){
			reslist.remove(res);
		}
		return reslist;
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
