package de.unisaarland.cs.sopra.common.view;

import java.util.Set;

import de.unisaarland.cs.sopra.common.controller.ControllerAdapter;
import de.unisaarland.cs.sopra.common.model.BuildingType;
import de.unisaarland.cs.sopra.common.model.Field;
import de.unisaarland.cs.sopra.common.model.FieldType;
import de.unisaarland.cs.sopra.common.model.Intersection;
import de.unisaarland.cs.sopra.common.model.ModelReader;

public class BuildATownStrategy implements Strategy {
Strategy s;
	@Override
	public void execute(ModelReader mr, ControllerAdapter ca) throws Exception {
		// TODO Auto-generated method stub
		
		if (mr.affordableSettlements(BuildingType.Town)>0 && mr.buildableTownIntersections(mr.getMe()).size() > 0
				&& mr.buildableTownIntersections(mr.getMe()).size() <= mr.getMaxBuilding(BuildingType.Town))
		{
			Intersection bestIntersection=chooseBestIntersection(mr);
			ca.buildSettlement(bestIntersection, BuildingType.Town);
			 ca.endTurn();
		} else 
			 ca.endTurn();
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
	
}
