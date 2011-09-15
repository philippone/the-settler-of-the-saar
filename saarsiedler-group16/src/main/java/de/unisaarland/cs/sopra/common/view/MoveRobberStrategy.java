package de.unisaarland.cs.sopra.common.view;

import java.io.IOException;
import java.util.Set;

import de.unisaarland.cs.sopra.common.controller.ControllerAdapter;
import de.unisaarland.cs.sopra.common.model.Field;
import de.unisaarland.cs.sopra.common.model.Intersection;
import de.unisaarland.cs.sopra.common.model.ModelReader;
import de.unisaarland.cs.sopra.common.model.Player;
import de.unisaarland.cs.sopra.common.model.Resource;
import de.unisaarland.cs.sopra.common.model.ResourcePackage;

public class MoveRobberStrategy implements Strategy{

	@Override
	public void execute(ModelReader mr, ControllerAdapter ca) throws Exception {
		ResourcePackage myrp = mr.getMe().getResources().copy();
		if (myrp.size() > 7){
			int give = myrp.size()/2;
			Resource max = Resource.LUMBER;
			ResourcePackage tmp = new ResourcePackage();
			while (give > 0){
				// find the resource that you have most
				for (Resource r : Resource.values())
					max = myrp.getResource(r)>myrp.getResource(max)?r:max;
				myrp.modifyResource(max, -1);
				tmp.modifyResource(max, 1);
				give--;
			}
			// return the resources
			try {
				ca.returnResources(tmp);
			} catch (IllegalStateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		}
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
