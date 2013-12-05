package actions;

import imageDataStructure.QuadTreeImage;

class VerticalFlip extends Action {

    public String name() { return "Vertical Flip" ; }

	// flip the image upside
    public void execute(QuadTreeImage quadTreeImage){

    	// NorthWest -> NorthEast -> SouthEast -> SouthWest become
    	// NorthEast -> NorthWest -> SouthWest -> SouthEast (clockwise sequence) 
    	// That is 1->2->3->4 become 2->1->4->3
    	int[] newOrder = new int[4];
    	newOrder[0] = 2;
    	newOrder[1] = 1;
    	newOrder[2] = 4;
    	newOrder[3] = 3;
    	
    	quadTreeImage.rotateImage( newOrder );
    }

    // Undo flip is the same as flip  
	public void unexecute(QuadTreeImage quadTreeImage){
		
		execute(quadTreeImage);
		
	}
    
}