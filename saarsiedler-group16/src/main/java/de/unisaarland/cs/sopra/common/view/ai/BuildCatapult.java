package de.unisaarland.cs.sopra.common.view.ai;

import de.unisaarland.cs.sopra.common.controller.ControllerAdapter;
import de.unisaarland.cs.sopra.common.model.Path;

public class BuildCatapult extends Stroke {
	
	private final Path destination;
	
	public BuildCatapult (Path destination) {
		super(StrokeType.BUILD_CATAPULT);
		this.destination = destination;
	}

	@Override
	public void execute(ControllerAdapter c) {
		c.buildCatapult(destination);
	}

	public Path getDestination() {
		return destination;
	}

}