package de.unisaarland.cs.sopra.common.view.ai;

import de.unisaarland.cs.sopra.common.controller.ControllerAdapter;
import de.unisaarland.cs.sopra.common.model.ResourcePackage;

public abstract class Stroke implements Comparable<Stroke>{
	
	private final StrokeType type;
	private double evaluation;
	private final ResourcePackage price;
	
	public Stroke(StrokeType type, ResourcePackage price){
		this.type = type;
		this.price = price;
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
	
	public double getEvaluation(){
		return this.evaluation;
	}
	
	public ResourcePackage getPrice(){
		return this.price;
	}

	@Override
	public int compareTo(Stroke o) {
		if (this.evaluation < o.getEvaluation()) return -1;
		else if (this.evaluation > o.getEvaluation()) return 1;
		else return 0;
	}
	
	@Override
	public String toString(){
		return String.format(">> %s",type.toString());
	}
	
}
