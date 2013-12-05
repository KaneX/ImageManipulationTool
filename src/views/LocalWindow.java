package views;

import imageDataStructure.*;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

public class LocalWindow extends JPanel
                             implements ObserverInterface {
    
    private static final long serialVersionUID = 6016429154671814468L;
	private QuadTreeImage quadTreeImage ;
    
    public LocalWindow( QuadTreeImage quadTreeImage ) {
        this.quadTreeImage = quadTreeImage ;
        quadTreeImage.attachObserver( this ); 
	}
 
    public void updateObserver() {
    	System.out.println("Local window is notified");
    	repaint() ;
    }
        
    @Override 
    public void paintComponent( Graphics g ) {
    	super.paintComponent(g);
    	// display the image
    	recursiveDraw(0, 0, quadTreeImage.getRoot(), g);
    }
    
    private void recursiveDraw(int startX, int startY, QuadTreeNode root, Graphics g) {
    	if (root.isWhite()){
    		g.setColor(Color.WHITE);
    		g.fillRect(startX, startY, root.getWidth(), root.getHeight());
    	}
    	else if (root.isBlack()){
    		g.setColor(Color.BLACK);
    		g.fillRect(startX, startY, root.getWidth(), root.getHeight());
    	}
    	else {
    		// FREEZE!!!
    		recursiveDraw(startX, startY, root.getChild(Position.northWest), g);
    		recursiveDraw(startX+root.getWidth()/2, startY, root.getChild(Position.northEast), g);
    		recursiveDraw(startX, startY+root.getHeight()/2, root.getChild(Position.southWest), g);
    		recursiveDraw(startX+root.getWidth()/2, startY+root.getHeight()/2, root.getChild(Position.southEast), g);
    	}
    }
}