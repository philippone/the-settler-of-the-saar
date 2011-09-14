package de.unisaarland.cs.sopra.common.view;

import java.awt.Point;
import java.util.Set;

import de.unisaarland.cs.sopra.common.controller.ControllerAdapter;
import de.unisaarland.cs.sopra.common.model.Field;
import de.unisaarland.cs.sopra.common.model.ModelReader;
import de.unisaarland.cs.sopra.common.model.Player;
import de.unisaarland.cs.sopra.common.model.Intersection;

public class MoveRobberStrategy implements Strategy{

	@Override
	public void execute(ModelReader mr, ControllerAdapter ca) throws Exception {
		// TODO Auto-generated method stub
		Player player=mr.getCurrentPlayer();
		Field sourceField =chooseBestSource(player, mr);
		Field destinationField=chooseBestDestination(player, mr);
		ca.moveRobber(sourceField, destinationField,chooseVictim(player, destinationField, mr));
	}
	
	private Field chooseBestDestination(Player player, ModelReader mr){
		Set<Field> fields=mr.canPlaceRobber();
		Field destinationField = null;
		float bestValue=0;
		float value;
		for (Field field : fields){
			value=evaluateDestination(player, mr, field);
			if (value>=bestValue){
				bestValue=value;
				destinationField=field;
			}
		}
		return destinationField;
	}
	
	private float evaluateDestination(Player player, ModelReader mr, Field field){
		float value=0;
		Set<Intersection> intersections= mr.getIntersectionsFromField(field);
		for (Intersection i : intersections){
			if (i.hasOwner() && i.getOwner()!=player) value=(float)(value+0.3);
			else if (!i.hasOwner()) value=(float)(value+0.15);
		}
		return value;
	}
	
	private Field chooseBestSource(Player player, ModelReader mr){
		Set<Field> fields=mr.getRobberFields();
		Field sourceField = null;
		float bestValue=0;
		float value;
		for (Field field : fields){
			value=evaluateSource(player, mr, field);
			if (value>=bestValue){
				bestValue=value;
				sourceField=field;
			}
		}
		return sourceField;
	}
	
	private float evaluateSource(Player player, ModelReader mr, Field field){
		float value=0;
		Set<Intersection> intersections= mr.getIntersectionsFromField(field);
		for (Intersection i : intersections){
			if (i.hasOwner() && i.getOwner()==player) value=(float)(value+0.3);
			else if (!i.hasOwner()) value=(float)(value+0.15);
		}
		return value;
	}
	
	private Player chooseVictim(Player player, Field field, ModelReader mr){
		Player victim=null;
		Set<Intersection> intersections= mr.getIntersectionsFromField(field);
		for (Intersection i : intersections){
			if (i.hasOwner() && i.getOwner()!=player) {victim=i.getOwner(); break;}
		}
		return victim;
	}
}
