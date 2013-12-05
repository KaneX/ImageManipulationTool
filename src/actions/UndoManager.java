package actions;

import imageDataStructure.QuadTreeImage;

import java.util.LinkedList;

public class UndoManager{
	
	// Create a list to store the actions done
	LinkedList<Action> done = new LinkedList<Action>();
	// Create a list to store the actions undone
	LinkedList<Action> undone = new LinkedList<Action>();

    /**
     * Setters
     */
	// Every time an action is performed, add it to done list
	public void doAction(Action action){
		done.add(action);
	}
	
	public void undo(QuadTreeImage quadTreeImage) throws InterruptedException{
		// If no action is done, do nothing
		if( done.isEmpty() ){
			return;
		}
		else{
			// Get the latest done action
			int last = done.size()-1;
			// Undo this action
			done.get(last).unexecute(quadTreeImage);
			// Add this action to undone list
			undone.add(done.get(last));
			// Remove this action from done list
			done.remove(last);
		}
	}
	
	public void redo(QuadTreeImage quadTreeImage) throws InterruptedException{
		// If no action is undone, do nothing
		if( undone.isEmpty() ){
			return;
		}
		else{
			// Get the latest undone action
			int last = undone.size()-1;
			// Re-do this action
			undone.get(last).execute(quadTreeImage);
			// Add this action to done list
			done.add(undone.get(last));
			// Remove this action from undone list
			undone.remove(last);
		}
	}
	
    /**
     * Getters
     */
	public boolean isDoneEmpty() { return done.isEmpty(); }
	public boolean isUndoneEmpty() { return undone.isEmpty(); }
}