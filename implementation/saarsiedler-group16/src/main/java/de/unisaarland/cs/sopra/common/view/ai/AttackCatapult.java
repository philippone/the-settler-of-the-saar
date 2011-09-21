/**
 * 
 */
package de.unisaarland.cs.sopra.common.view.ai;

import de.unisaarland.cs.sopra.common.controller.ControllerAdapter;
import de.unisaarland.cs.sopra.common.model.Catapult;
import de.unisaarland.cs.sopra.common.model.Path;


public class AttackCatapult extends Stroke {

	private final Path source;
	private final Path destination;
	
	
	public Path getSource() {
		return source;
	}

	public Path getDestination() {
		return destination;
	}

	public AttackCatapult(Path source, Path destination){
		super(StrokeType.ATTACK_CATAPULT, Catapult.getAttackcatapultprice());
		this.source = source;
		this.destination = destination;
	}
	
	@Override
	public void execute(ControllerAdapter c) {
		c.moveCatapult(source, destination);
	}

}
