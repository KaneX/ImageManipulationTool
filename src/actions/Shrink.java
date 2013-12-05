package actions;

import imageDataStructure.QuadTreeImage;

class Shrink extends Action {

    public String name() { return "Shrink" ; }

	// Shrink the image to one fourth of the original size
    public void execute(QuadTreeImage quadTreeImage){
    	
    	if(quadTreeImage.getHeight() < 4){
        	// Can not accept image that is smaller than 4*4
    		return;
    	}
    	else{
        	// Get the leaves at single pixel level, delete them and set the parent node
        	quadTreeImage.deleteSinglePixelLeaf();
        	// For every node in the tree, reduce both the node height and width by half
        	quadTreeImage.resizeImage(0.5);
    	}
    	
    }

    // Undo shrink is expand
    // Note that shrink may reduce the definition of the image
	public void unexecute(QuadTreeImage quadTreeImage) throws InterruptedException{
		
		Action expand = new Expand();
		expand.execute(quadTreeImage);
		
	}
    
}