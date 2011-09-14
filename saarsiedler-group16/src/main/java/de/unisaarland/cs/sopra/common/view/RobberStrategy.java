package de.unisaarland.cs.sopra.common.view;

import java.io.IOException;

import de.unisaarland.cs.sopra.common.controller.ControllerAdapter;
import de.unisaarland.cs.sopra.common.model.Field;
import de.unisaarland.cs.sopra.common.model.ModelReader;
import de.unisaarland.cs.sopra.common.model.Resource;
import de.unisaarland.cs.sopra.common.model.ResourcePackage;

public class RobberStrategy implements Strategy {

	@Override
	public void execute(ModelReader mr, ControllerAdapter ca) {
		ResourcePackage myrp = mr.getMe().getResources();
		int give = myrp.size()/2;
		ResourcePackage tmp = new ResourcePackage();
		while (give > 0){
			// find the resource that you have most
			Resource max = null;
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
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// and replace the robber if it is my turn and end the turn
		if (mr.getMe() == mr.getCurrentPlayer()){
			Field destinationField = mr.canPlaceRobber().iterator().next();
			Field sourceField = mr.getRobberFields().iterator().next();
			try {
				ca.moveRobber(sourceField, destinationField, null);
				ca.endTurn();
			} catch (IllegalStateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	public int evaluate(){
		// TODO implement this method
		return 0;
	}
}
