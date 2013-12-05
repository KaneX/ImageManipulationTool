package actions;

import imageDataStructure.QuadTreeImage;

class RotateLeft extends Action {

    public String name() { return "Rotate 90 degrees left" ; }

	// flip the image upside
    public void execute(QuadTreeImage quadTreeImage){

    	// NorthWest -> NorthEast -> SouthEast -> SouthWest become
    	// NorthEast -> SouthEast -> SouthWest -> NorthWest (clockwise sequence) 
    	// That is 1->2->3->4 become 2->3->4->1
    	int[] newOrder = new int[4];
    	newOrder[0] = 2;
    	newOrder[1] = 3;
    	newOrder[2] = 4;
    	newOrder[3] = 1;
    	
    	quadTreeImage.rotateImage( newOrder );
    	
    }

    // Undo rotate left is rotate right  
	public void unexecute(QuadTreeImage quadTreeImage) throws InterruptedException{
		
		Action rotateRight = new RotateRight();
		rotateRight.execute(quadTreeImage);
		
	}
    
}