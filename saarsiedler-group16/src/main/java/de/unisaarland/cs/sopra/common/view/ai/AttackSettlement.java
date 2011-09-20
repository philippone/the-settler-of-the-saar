package de.unisaarland.cs.sopra.common.view.ai;

import de.unisaarland.cs.sopra.common.controller.ControllerAdapter;
import de.unisaarland.cs.sopra.common.model.Intersection;
import de.unisaarland.cs.sopra.common.model.Path;

public class AttackSettlement extends Stroke {
	
	private final Path source;
	private final Intersection destination;
	
	public AttackSettlement(Path source, Intersection destination){
		super(StrokeType.ATTACK_SETTLEMENT);
		this.source = source;
		this.destination = destination;
	}

	@Override
	public void execute(ControllerAdapter c) {
		c.attackSettlement(source, destination);
	}

	public Path getSource() {
		return source;
	}

	public Intersection getDestination() {
		return destination;
	}

}
