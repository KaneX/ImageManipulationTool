package main;

import imageDataStructure.QuadTreeImage;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import actions.*;
import views.LocalWindow;

public class Main {
	
    /**
     * Get the default image
     */
	QuadTreeImage quadTreeImage = QuadTreeImage.getQuadTreeImage("test16.pgm");
    UndoManager undoManager = new UndoManager();
   
    /**
     * Define frames and buttons
     */
	// Frame 0 is the main frame for user to perform actions
    JFrame frame0 = new JFrame("Image Manipulation");
    // Frame 1 is the local window to display image
    // and receives update
    JFrame frame1 = new JFrame("Image Display");
    
    // Define image manipulations buttons
    ActionFactory actionFactory = new ActionFactory();
    JButton [] actionButtons = new JButton[ actionFactory.count() ];
    
    // Define load new image button
    JButton newImageButton = new JButton("Load New Image");
    
    // Define save image button
    JButton saveButton = new JButton("Save Image");
    
    // Define load undo button
    JButton undoButton = new JButton("Undo");
    // Define load re-do button
    JButton redoButton = new JButton("Redo");
    
    public Main( ) {

        /**
         * Add action listeners for buttons
         */
    	// Get available actions
    	// and add listeners for these action buttons
        for( int i = 0 ; i < actionButtons.length ; ++i ) {
        	final Action action = actionFactory.get(i) ;
        	JButton button = new JButton( action.name() ) ;
            button.addActionListener(new ActionListener() {
    			public void actionPerformed(ActionEvent e) {
    				try {
    					
    					// Execute the action
						action.execute(quadTreeImage);
						// Add this action to done list
						undoManager.doAction(action);

						updateUndoAndRedoButtons();
						
					} catch (InterruptedException e1) {
						e1.printStackTrace();
					}} } );
            actionButtons[i] = button;
        }
        
        // Add listener for load new image button
        newImageButton.addActionListener(
        	new ActionListener() {
        		class pgmFilter extends javax.swing.filechooser.FileFilter {
        			   public boolean accept( File f ) {
        			      if( f.isDirectory() || f.getName().endsWith( ".pgm" ) ) {
        			         return true;
        			      } else {
        			         return false;
        			      }
        			   }
        			   public String getDescription() {
        			      return "PGM image files (*.pgm)";
        			   }
        			}
        		
        		public void actionPerformed(ActionEvent e) {
        			// load new image method here
        			JFileChooser file = new JFileChooser();
        			file.removeChoosableFileFilter(file.getFileFilter());
        		    pgmFilter filter = new pgmFilter();
        		    file.setFileFilter(filter);
                    int ret = file.showOpenDialog(null);
                    if(ret == JFileChooser.APPROVE_OPTION){
                    	quadTreeImage.loadNewImage(file.getSelectedFile().getPath()) ;
                    }
            }
        });
        
        // Add listener for save image button
        saveButton.addActionListener(
        	new ActionListener() {
        		public void actionPerformed(ActionEvent e) {
        			if (quadTreeImage.saveImage()){
        				JOptionPane.showMessageDialog(null,"Image is saved to\n"+quadTreeImage.getFilePath());
        			}
        			else {
        				JOptionPane.showMessageDialog(null,"An error occured while trying to save the image.");
        			}
            }
        });
        
        // Add listener for undo image button
        undoButton.addActionListener(
        	new ActionListener() {
        		public void actionPerformed(ActionEvent e) {
        			try {
        				
						undoManager.undo(quadTreeImage);
						updateUndoAndRedoButtons();
						
					} catch (InterruptedException e1) {
						e1.printStackTrace();
					}
            }
        });
        
        // Add listener for re-do image button
        redoButton.addActionListener(
        	new ActionListener() {
        		public void actionPerformed(ActionEvent e) {
        			try {
        				
						undoManager.redo(quadTreeImage);
						updateUndoAndRedoButtons();
						
					} catch (InterruptedException e1) {
						e1.printStackTrace();
					}
            }
        });
        
        /**
         * Set frame0
         */
        frame0.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE) ;
        frame0.setLayout( new GridLayout(4+actionButtons.length, 1, 0, 5) ) ;
        frame0.add( newImageButton ) ;
        for( JButton button : actionButtons ) 
        	frame0.add( button ) ;
        frame0.add( undoButton ) ;
        undoButton.setEnabled(false);
        frame0.add( redoButton ) ;
        redoButton.setEnabled(false);
        frame0.add( saveButton );
        frame0.setLocation(0,0);
        frame0.setSize(200, 400) ;
        frame0.setVisible( true ) ;

        /**
         * Set frame1
         */
        frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE) ;
        frame1.setLayout( new BorderLayout() ) ;
        JPanel imageDisplay = new LocalWindow( quadTreeImage ) ;
        frame1.add( imageDisplay ) ;
        frame1.setLocation(250,0);
        frame1.setSize(512, 512) ;
        frame1.setVisible( true ) ;
    }
    
    private void updateUndoAndRedoButtons(){
		// Update the undo button
		if(undoManager.isDoneEmpty()) { undoButton.setEnabled(false); }
		else { undoButton.setEnabled(true); }
		// Update the re-do button
		if(undoManager.isUndoneEmpty()) { redoButton.setEnabled(false); }
		else { redoButton.setEnabled(true); }
    }
    
    public static void main( String args[] ) {
    	SwingUtilities.invokeLater( new Runnable () {
    		@Override public void run() {
				new Main() ; }} ) ;
    }
}