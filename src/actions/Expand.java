package actions;

import imageDataStructure.QuadTreeImage;

class Expand extends Action {

    public String name() { return "Expand" ; }

	// Expand the image to four times of the original size
    public void execute(QuadTreeImage quadTreeImage){

    	// For every node in the tree, double both the node height and width
    	quadTreeImage.resizeImage(2);
    	
    }

    // Undo expand is shrink  
	public void unexecute(QuadTreeImage quadTreeImage) throws InterruptedException{
		
		Action shrink = new Shrink();
		shrink.execute(quadTreeImage);
		
	}
    
}