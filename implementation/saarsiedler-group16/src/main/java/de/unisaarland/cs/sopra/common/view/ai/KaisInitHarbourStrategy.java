package de.unisaarland.cs.sopra.common.view.ai;

import java.util.Iterator;

import de.unisaarland.cs.sopra.common.model.Field;
import de.unisaarland.cs.sopra.common.model.HarborType;
import de.unisaarland.cs.sopra.common.model.Intersection;
import de.unisaarland.cs.sopra.common.model.ModelReader;
import de.unisaarland.cs.sopra.common.model.Resource;
import de.unisaarland.cs.sopra.common.model.ResourcePackage;

public class KaisInitHarbourStrategy extends Strategy {

	public KaisInitHarbourStrategy(ModelReader mr) {
		super(mr);
	}

	@Override
	public boolean evaluates(Stroke s) {
		boolean evaluates = false;
		if (s.getType() == StrokeType.BUILD_VILLAGE){
			boolean hasHarbours = mr.getHarborIntersections().size() > 0;
			boolean hasOwnHarbours = mr.getHarborTypes(mr.getMe()).size() > 0;
			evaluates = hasHarbours && !hasOwnHarbours;
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
		if (checkForTwoResourceLandFields(stroke.getDestination()) && isHarbourIntersection(stroke.getDestination())){
			if (numberOfDifferentResources(stroke.getDestination()) == 2){
				if (mr.getHarborType(stroke.getDestination()) == HarborType.GENERAL_HARBOR ||
						mr.getHarborType(stroke.getDestination()) == getBestHarborType()){
					evaluation = 1.5;
				}
				else {
					evaluation = 1.0;
				}
			}
			else {
				evaluation = 0.5;
			}
		}
		return evaluation;
	}
	
	private HarborType getBestHarborType(){
		return resource2HarbourType(getMaxResource());
	}
	
	private Resource getMaxResource(){
		Iterator<Field> fiter = mr.getFieldIterator();
		ResourcePackage resPack = new ResourcePackage();
		while (fiter.hasNext()){
			Field next = fiter.next();
			if (next.getNumber() != -1) resPack.modifyResource(next.getResource(), 1);
		}
		Resource maxResource = Resource.WOOL;
		for (Resource r : Resource.values()){
			maxResource = resPack.getResource(maxResource) < resPack.getResource(r) ? r : maxResource;
		}
		return maxResource;
	}
	
	private HarborType resource2HarbourType(Resource r){
		switch(r){
		default:
			return null;
		case BRICK:
			return HarborType.BRICK_HARBOR;
		case GRAIN:
			return HarborType.GRAIN_HARBOR;
		case LUMBER:
			return HarborType.LUMBER_HARBOR;
		case ORE:
			return HarborType.ORE_HARBOR;
		case  WOOL:
			return HarborType.WOOL_HARBOR;
		}
	}
	
	private int numberOfDifferentResources(Intersection intersection) {
		Resource lastResource = null;
		int numberOfDifferentResources = 0;
		for (Field f : mr.getFieldsFromIntersection(intersection)){
			if (f.getNumber() != -1){
				if (lastResource == null){
					lastResource = f.getResource();
				}
				else if (lastResource == f.getResource()){
					numberOfDifferentResources = 1;
				}
				else {
					numberOfDifferentResources = 2;
				}
			}
		}
		return numberOfDifferentResources;
	}

	private boolean isHarbourIntersection(Intersection intersection){
		return mr.getHarborIntersections().contains(intersection);
	}
	
	private boolean checkForTwoResourceLandFields(Intersection intersection){
		int numberOfLandFields = 0;
		for (Field f : mr.getFieldsFromIntersection(intersection)){
			if (f.getNumber() != -1) numberOfLandFields++;
		}
		return numberOfLandFields == 2;
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
