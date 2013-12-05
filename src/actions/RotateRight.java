package actions;

import imageDataStructure.QuadTreeImage;

class RotateRight extends Action {

    public String name() { return "Rotate 90 degrees right" ; }

	// flip the image upside
    public void execute(QuadTreeImage quadTreeImage){
    	
    	// NorthWest -> NorthEast -> SouthEast -> SouthWest become
    	// SouthWest -> NorthWest -> NorthEast -> SouthEast (clockwise sequence) 
    	// That is 1->2->3->4 become 4->1->2->3
    	int[] newOrder = new int[4];
    	newOrder[0] = 4;
    	newOrder[1] = 1;
    	newOrder[2] = 2;
    	newOrder[3] = 3;
    	
    	quadTreeImage.rotateImage( newOrder );
    	
    }

    // Undo rotate left is rotate left  
	public void unexecute(QuadTreeImage quadTreeImage) throws InterruptedException{
		
		Action rotateLeft = new RotateLeft();
		rotateLeft.execute(quadTreeImage);
		
	}
    
}