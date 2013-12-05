package imageDataStructure;

import imageDataStructure.Image;
import imageDataStructure.Position;

import java.io.IOException;

import readAndWriteImage.*;

public class QuadTreeImage extends Image {

    /**
     * Fields
     */
	// path to original image file
	private String filePath = null;
	// The height of image
	private int height = 0;
	// The width of image
	private int width = 0;
	// The 2D data matrix of image
	// Containing 0s and 1s
	private int[][] dataMatrix;
	// Points to the root of QuadTree
	private QuadTreeNode root = null;
	
	// Define the only and only instance of this class 
	private static QuadTreeImage firstInstance = null;

    /**
     * Constructor
     */
	private QuadTreeImage(String filePath) {
    	// Load image from filePath
		// Choose image loading method according to image format
		ImageFactory image = new ImageFactory();
		image.setImageFormat(new PGM());
		try {
			image.readImage(filePath);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		this.filePath = filePath;
		// Get the image size and pixel matrix
		this.height = image.getHeight();
		this.width = image.getWidth();
		dataMatrix = new int[height][width];
		dataMatrix = image.getData();
		// Construct the QuadTree
		root = new QuadTreeNode(0, 0, height, width, dataMatrix);
		
    	notifyObservers();
    }
	// Get the one and only instance of this class
	// There is no instance, create one
	public static QuadTreeImage getQuadTreeImage(String newFilePath){
		
		if(firstInstance == null){
			firstInstance = new QuadTreeImage(newFilePath);
		}
		
		return firstInstance;
		
	}

    /**
     * Setters
     */

    /*
     * Load new image from a given filePath
     */
    public void loadNewImage(String filePath) {
    	// Load image from filePath
		// Choose image loading method according to image format
		ImageFactory image = new ImageFactory();
		image.setImageFormat(new PGM());
		try {
			image.readImage(filePath);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		this.filePath = filePath;
		// Get the image size and pixel matrix
		this.height = image.getHeight();
		this.width = image.getWidth();
		dataMatrix = new int[height][width];
		dataMatrix = image.getData();
		// Construct the QuadTree
		root = new QuadTreeNode(0, 0, height, width, dataMatrix);
		
    	notifyObservers();
	}

    /*
     * Save image to the original location (i.e. overwrite the original image)
     */
    public boolean saveImage(){
    	// Save image to filePath
		// Choose image loading method according to image format
		ImageFactory image = new ImageFactory();
		image.setImageFormat(new PGM());
    	
		dataMatrix = root.toMatrix();
		image.setHeight(height);
		image.setWidth(width);
		image.setData(dataMatrix);
		try {
			image.writeImage(filePath);
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		
		return true;
    }

    /*
     * Resize the image (i.e. expand or shrink) according to size factor
     */
    public void resizeImage(double sizeFactor){
    	
    	// Recursive resize the image
    	resizeImageBlock(root, sizeFactor);
    	// Update image height and width
    	height = root.getHeight();
    	width = root.getWidth();
    	
    	notifyObservers();
    	
    }
    private void resizeImageBlock(QuadTreeNode node, double sizeFactor){
    	
    	node.setHeight((int) (node.getHeight() * sizeFactor));
    	node.setWidth((int) (node.getWidth() * sizeFactor));
    	if(node.isGrey()){
    		resizeImageBlock(node.getChild(Position.northEast), sizeFactor);
    		resizeImageBlock(node.getChild(Position.northWest), sizeFactor);
    		resizeImageBlock(node.getChild(Position.southEast), sizeFactor);
    		resizeImageBlock(node.getChild(Position.southWest), sizeFactor);
    	}
    	
    }

    /*
     * Get the leaves at single pixel level, delete them and set the parent node
     */
    public void deleteSinglePixelLeaf(){
    	
    	recursiveDeleteSinglePixelLeaf(root);
    	notifyObservers();
    	
    }
    private void recursiveDeleteSinglePixelLeaf(QuadTreeNode node){
    	
    	if(node.isGrey()){
    		// Single pixel level children found
    		if( node.getChild(Position.northEast).getHeight() == 1 ){
    			
    			// Count the black pixel number of the four children
        		int countBlack = 0;
    			if( node.getChild(Position.northEast).isBlack() ){ countBlack++; }
    			if( node.getChild(Position.northWest).isBlack() ){ countBlack++; }
    			if( node.getChild(Position.southEast).isBlack() ){ countBlack++; }
    			if( node.getChild(Position.southWest).isBlack() ){ countBlack++; }
    			
    			if( countBlack < 2 ){ node.setWhite(true); node.setBlack(false); node.setGrey(false); }
    			else{ node.setWhite(false); node.setBlack(true); node.setGrey(false); }
    			
    		}
    		// No single pixel level children found
    		else{
    			recursiveDeleteSinglePixelLeaf(node.getChild(Position.northEast));
    			recursiveDeleteSinglePixelLeaf(node.getChild(Position.northWest));
    			recursiveDeleteSinglePixelLeaf(node.getChild(Position.southEast));
    			recursiveDeleteSinglePixelLeaf(node.getChild(Position.southWest));
    		}
    		
    	}
    	
    }

    /*
     * Rotate the image (i.e. flip and rotate)
     */
    // by changing the order of NorthWest, NorthEast, SouthWest, SouthEast children
    public void rotateImage(int[] newOrder){
    	
    	rotateImageBlock(root, newOrder);
    	notifyObservers();
    	
    }
    private void rotateImageBlock(QuadTreeNode node, int[] newOrder){
    	// If this node has children, change the order of them
    	// otherwise, do nothing
    	if(node.isGrey()){
    		
    		QuadTreeNode [] childrenNode = new QuadTreeNode [4];
    		childrenNode[0] = node.getChild(Position.northWest);
    		childrenNode[1] = node.getChild(Position.northEast);
    		childrenNode[2] = node.getChild(Position.southEast);
    		childrenNode[3] = node.getChild(Position.southWest);
    		
    		node.setChild(Position.northWest, childrenNode[newOrder[0]-1]);
    		node.setChild(Position.northEast, childrenNode[newOrder[1]-1]);
    		node.setChild(Position.southEast, childrenNode[newOrder[2]-1]);
    		node.setChild(Position.southWest, childrenNode[newOrder[3]-1]);
        	
        	rotateImageBlock(node.getChild(Position.northWest), newOrder);
        	rotateImageBlock(node.getChild(Position.northEast), newOrder);
        	rotateImageBlock(node.getChild(Position.southWest), newOrder);
        	rotateImageBlock(node.getChild(Position.southEast), newOrder);
    	}
    }

    /**
     * Getters
     */  
    public int getHeight() { return height ; }
    public int getWidth() { return width ; }
    public QuadTreeNode getRoot() { return root; }
    public String getFilePath() { return filePath; }
}