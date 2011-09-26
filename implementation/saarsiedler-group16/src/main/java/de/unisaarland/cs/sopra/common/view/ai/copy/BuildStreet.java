package de.unisaarland.cs.sopra.common.view.ai.copy;

import de.unisaarland.cs.sopra.common.controller.ControllerAdapter;
import de.unisaarland.cs.sopra.common.model.Path;
import de.unisaarland.cs.sopra.common.model.Street;

public class BuildStreet extends Stroke {
	
	private final Path destination;

	public BuildStreet(Path destination){
		super(StrokeType.BUILD_STREET, Street.getPrice());
		this.destination = destination;
	}
	
	@Override
	public void execute(ControllerAdapter c) {
		c.buildStreet(destination);
	}

	public Path getDestination() {
		return destination;
	}
	
	@Override
	public String toString(){
		return String.format("%s: Destination: %s\n", super.toString(), destination.toString());
	}

}
