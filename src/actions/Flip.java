package actions;

import imageDataStructure.QuadTreeImage;

class Flip extends Action {

    public String name() { return "Flip" ; }

	// flip the image upside
    public void execute(QuadTreeImage quadTreeImage){

    	// NorthWest -> NorthEast -> SouthEast -> SouthWest become
    	// SouthWest -> SouthEast -> NorthEast -> NorthWest (clockwise sequence) 
    	// That is 1->2->3->4 become 4->3->2->1
    	int[] newOrder = new int[4];
    	newOrder[0] = 4;
    	newOrder[1] = 3;
    	newOrder[2] = 2;
    	newOrder[3] = 1;
    	
    	quadTreeImage.rotateImage( newOrder );
    	System.out.println("Image filped ");
    }

    // Undo flip is the same as flip  
	public void unexecute(QuadTreeImage quadTreeImage){
		
		execute(quadTreeImage);
		
	}
    
}