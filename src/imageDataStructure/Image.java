package imageDataStructure;

import java.util.* ;

abstract public class Image {
    
    private Set<ObserverInterface> observers = new HashSet<ObserverInterface>() ;
    
    /**
     * Attach an observer
     */
    public void attachObserver( ObserverInterface newObserver ) {
        observers.add( newObserver ) ;
        newObserver.updateObserver() ; }
    
    /**
     * Remove an observer
     */
    public void dettachObserver( ObserverInterface observer ) {
        observers.remove( observer ) ; }

    /**
     * Notify all observers 
     */
    public void notifyObservers() {
    	for( final ObserverInterface observer : observers ) {
    		observer.updateObserver() ;
    	}
    }
}