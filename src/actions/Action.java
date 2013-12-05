package actions;

import imageDataStructure.QuadTreeImage;

public abstract class Action {
	
	public abstract void execute(QuadTreeImage quadTreeImage) throws InterruptedException ;
	public abstract void unexecute(QuadTreeImage quadTreeImage) throws InterruptedException ;
	
	public abstract String name() ;
	
}
