package de.unisaarland.cs.sopra.common.view.ai;

import de.unisaarland.cs.sopra.common.controller.ControllerAdapter;
import de.unisaarland.cs.sopra.common.model.ModelReader;
import de.unisaarland.cs.sopra.common.model.ResourcePackage;

public class ReturnResources extends Stroke {

	private final ResourcePackage resources;
	
	public ReturnResources(ResourcePackage resources){
		super(StrokeType.RETURN_RESOURCES, resources);
		this.resources = resources;
	}
	
	public ResourcePackage getResources(){
		return this.resources;
	}
	
	@Override
	public void execute(ControllerAdapter c) {
		c.returnResources(resources);
	}

}
