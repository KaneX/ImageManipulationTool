package imageDataStructure;

public class QuadTreeNode{

    /**
     * Fields
     */
	// The area covered by this QuadTree
	private int height;
	private int width;
	
	// Record the white pixel number
	private int whitePixelNum;
	// Record the black pixel number
	private int blackPixelNum;
	
	// Indicates if the area is all white
	private boolean isWhite;
	// Indicates if the area is all black
	private boolean isBlack;
	// Indicates if the area is grey
	private boolean isGrey;
	
	// If the area is grey, 
	// then this QuadTree has four children
	// Points to north west
	private QuadTreeNode nw;
	// Points to north east
	private QuadTreeNode ne;
	// Points to south west
	private QuadTreeNode sw;
	// Points to south east
	private QuadTreeNode se;
	
    /**
     * Constructor
     */
	public QuadTreeNode( int startX, int startY, int height, int width, int data[][] ){

		// set coordination
		this.height = height;
		this.width = width;
		
		// Initialize white pixel number and black pixel number
		whitePixelNum = 0;
		blackPixelNum = 0;
		
		// Count white pixels and black pixels
		for(int i=startX; i<startX+height; i++){
			for(int j=startY; j<startY+width; j++){
				if(data[i][j] >= 1) { whitePixelNum++; }
				else { blackPixelNum++; } 
			}
		}
		
		// If pixels are all white pixels
		if(blackPixelNum == 0 && whitePixelNum == height*width){
			isWhite = true;
			isBlack = false;
			isGrey = false;}
		// If pixels are all black pixels
		else if(whitePixelNum == 0 && blackPixelNum == height*width){
			isBlack = true;
			isWhite = false;
			isGrey = false;}
		// Otherwise there are both black pixels and white pixels
		else{
			isGrey = true;
			isWhite = false;
			isBlack = false;}
		
		// If this node is grey, create four children
		// each child represents one forth
		if( isGrey ){
			nw = new QuadTreeNode(startX, startY, height/2, width/2, data);
			ne = new QuadTreeNode(startX, startY+width/2, height/2, width/2, data);
			sw = new QuadTreeNode(startX+height/2, startY, height/2, width/2, data);
			se = new QuadTreeNode(startX+height/2, startY+width/2, height/2, width/2, data);
		}
		else{
			nw = null;
			ne = null;
			sw = null;
			se = null;
		}
	}
	
	public int[][] toMatrix(){
		int[][] rtnMatrix = new int[height][width];
		rtnMatrix = toSubMatrix(0,0,height, width, this);
		
		return rtnMatrix;
	}
	
	public int[][] toSubMatrix(int startX, int startY, int height, int width, QuadTreeNode parent){
		int[][] data = new int[height][width];
		
		if (parent.isWhite){
			for(int i=0; i<height; i++){
				for(int j=0; j<width; j++){
					data[i][j] = 1;
				}
			}
		}
		else if (parent.isBlack){
			for(int i=0; i<height; i++){
				for(int j=0; j<width; j++){
					data[i][j] = 0;
				}
			}
		}
		else {
			int[][] childData = new int[height/2][width/2];
			
			// NorthWest child matrix
			childData = toSubMatrix(startX, startY, height/2, width/2, parent.getChild(Position.northWest));
			for(int i=0; i<height/2; i++){
				for(int j=0; j<width/2; j++){
					data[i][j] = childData[i][j];
				}
			}
			// NorthEast child matrix
			childData = toSubMatrix(startX, startY+width/2, height/2, width/2, parent.getChild(Position.northEast));
			for(int i=0; i<height/2; i++){
				for(int j=0; j<width/2; j++){
					data[i][width/2+j] = childData[i][j];
				}
			}
			// SouthWest child matrix
			childData = toSubMatrix(startX+height/2, startY, height/2, width/2, parent.getChild(Position.southWest));
			for(int i=0; i<height/2; i++){
				for(int j=0; j<width/2; j++){
					data[height/2+i][j] = childData[i][j];
				}
			}
			// SouthEast child matrix
			childData = toSubMatrix(startX+height/2, startY+width/2, height/2, width/2, parent.getChild(Position.southEast));
			for(int i=0; i<height/2; i++){
				for(int j=0; j<width/2; j++){
					data[height/2+i][width/2+j] = childData[i][j];
				}
			}
		}
		
		return data;
	}
	
    /**
     * Getters
     */
	public int getHeight(){ return height;}
	public int getWidth(){ return width;}
	
	public boolean isWhite(){ return isWhite;}
	public boolean isBlack(){ return isBlack;}
	public boolean isGrey(){ return isGrey;}
	
	public QuadTreeNode getChild(Position pos){
		switch (pos){
			case northWest: return nw;
			case northEast: return ne;
			case southWest: return sw;
			case southEast: return se;
			default: return null;
		}
	}

    /**
     * Setters
     */
	public void setHeight(int newHeight){ this.height = newHeight; }
	public void setWidth(int newWidth){ this.width = newWidth; }
	
	public void setWhite(boolean newIsWhite){ this.isWhite = newIsWhite; }
	public void setBlack(boolean newIsBlack){ this.isBlack = newIsBlack; }
	public void setGrey(boolean newIsGrey){ this.isGrey = newIsGrey; }
	
	public void setChild(Position pos, QuadTreeNode newNode){
		switch (pos){
			case northWest: this.nw = newNode; break;
			case northEast: this.ne = newNode; break;
			case southWest: this.sw = newNode; break;
			case southEast: this.se = newNode; break;
		}
	}
	
}