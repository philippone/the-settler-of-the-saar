package de.unisaarland.cs.sopra.common.view;

import java.util.Set;

import de.unisaarland.cs.sopra.common.controller.ControllerAdapter;
import de.unisaarland.cs.sopra.common.model.BuildingType;
import de.unisaarland.cs.sopra.common.model.Field;
import de.unisaarland.cs.sopra.common.model.FieldType;
import de.unisaarland.cs.sopra.common.model.Intersection;
import de.unisaarland.cs.sopra.common.model.ModelReader;

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
			ca.endTurn();
		} else 
			 ca.endTurn();

	}
	public float evaluate(ModelReader mr, ControllerAdapter ca) throws Exception {
		if (!(mr.affordableSettlements(BuildingType.Village) > 0
				&& mr.buildableVillageIntersections(mr.getMe()).size() > 0
				&& mr.buildableVillageIntersections(mr.getMe()).size() <= mr.getMaxBuilding(BuildingType.Village)
				&& mr.getSettlements(mr.getMe(), BuildingType.Village).size() < mr.getMaxBuilding(BuildingType.Village))) return -1;
		float value=0;	
		
		return value;
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
	
	
}
