package de.unisaarland.cs.sopra.common.view;

import de.unisaarland.cs.sopra.common.controller.ControllerAdapter;
import de.unisaarland.cs.sopra.common.model.Field;
import de.unisaarland.cs.sopra.common.model.ModelReader;
import de.unisaarland.cs.sopra.common.model.Resource;
import de.unisaarland.cs.sopra.common.model.ResourcePackage;

public class RobberStrategy extends Strategy {

	public RobberStrategy() {
		super(0, new ResourcePackage());
	}
	
	@Override
	public void execute(ModelReader mr, ControllerAdapter ca)  throws Exception{
		ResourcePackage myrp = mr.getMe().getResources().copy();
		if (myrp.size() > 7){
			int give = myrp.size()/2;
			Resource max = Resource.LUMBER;
			ResourcePackage tmp = new ResourcePackage();
			while (give >= 0){
				// find the resource that you have most
				for (Resource r : Resource.values())
					max = myrp.getResource(r)>myrp.getResource(max)?r:max;
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
			// and replace the robber if it is my turn
		if (mr.getMe() == mr.getCurrentPlayer()){
				Field destinationField = mr.canPlaceRobber().iterator().next();
				Field sourceField = mr.getRobberFields().iterator().next();
				try {
					ca.moveRobber(sourceField, destinationField, null);
				} catch (IllegalStateException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();

				}

			}
	}


}
