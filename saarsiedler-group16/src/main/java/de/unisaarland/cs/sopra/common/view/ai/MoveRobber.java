package de.unisaarland.cs.sopra.common.view.ai;

import de.unisaarland.cs.sopra.common.controller.ControllerAdapter;
import de.unisaarland.cs.sopra.common.model.Field;
import de.unisaarland.cs.sopra.common.model.Player;
import de.unisaarland.cs.sopra.common.model.ResourcePackage;

public class MoveRobber extends Stroke {
	
	public final Field source;
	public final Field destination;
	public final Player victim;
	
	public MoveRobber(Field source, Field destination, Player victim){
		super(StrokeType.MOVE_ROBBER, new ResourcePackage());
		this.source = source;
		this.destination = source;
		this.victim = victim;
	}

	@Override
	public void execute(ControllerAdapter c) {
		c.moveRobber(source, destination, victim);
	}
	
	public Field getSource() {
		return source;
	}

	public Field getDestination() {
		return destination;
	}

	public Player getVictim() {
		return victim;
	}

}
