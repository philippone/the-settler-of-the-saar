package de.unisaarland.cs.sopra.common.view.ai;

import de.unisaarland.cs.sopra.common.controller.ControllerAdapter;

public abstract class Stroke {
	
	private final StrokeType type;
	private double evaluation;
	
	public Stroke(StrokeType type){
		this.type = type;
		this.evaluation = Double.NEGATIVE_INFINITY;
	}
	
	public abstract void execute(ControllerAdapter c);
	
	public StrokeType getType(){
		return this.type;
	}

	public void setEvaluation(double evaluation){
		if (this.evaluation != Double.NEGATIVE_INFINITY)
			throw new IllegalStateException("The stroke was already evaluated");
		this.evaluation = evaluation;
	}
	
}
