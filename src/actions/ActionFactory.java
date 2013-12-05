package actions;

import actions.Action;
import actions.HorizontalFlip;
import actions.RotateLeft;
import actions.RotateRight;

public class ActionFactory {

	Action[] actionList = new Action[]{
			new HorizontalFlip(),
			new VerticalFlip(),
			new RotateLeft(),
			new RotateRight(),
			new Shrink(),
			new Expand()} ;
	
	public int count() {
		return actionList.length ;
	}
	
	public Action get( int i ) {
		return actionList[i] ;
	}
}
