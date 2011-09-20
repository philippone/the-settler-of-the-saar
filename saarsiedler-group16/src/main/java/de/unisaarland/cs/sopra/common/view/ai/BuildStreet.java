package de.unisaarland.cs.sopra.common.view.ai;

import de.unisaarland.cs.sopra.common.controller.ControllerAdapter;
import de.unisaarland.cs.sopra.common.model.Path;

public class BuildStreet extends Stroke {
	
	private final Path destination;

	public BuildStreet(Path destination){
		super(StrokeType.BUILDING_STREET);
		this.destination = destination;
	}
	
	@Override
	public void execute(ControllerAdapter c) {
		c.buildStreet(destination);
	}

	public Path getDestination() {
		return destination;
	}

}
