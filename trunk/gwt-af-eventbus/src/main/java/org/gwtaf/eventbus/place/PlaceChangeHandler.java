package org.gwtaf.eventbus.place;

import com.google.gwt.event.shared.EventHandler;

/**
 * PlaceChangedHandler
 * 
 * @author David Peterson
 */
public interface PlaceChangeHandler extends EventHandler {
	/**
	 * Called after the current place has already changed. Allows handlers to
	 * update any internal tracking, etc.
	 * 
	 * @param event
	 *            The event.
	 */
	void onPlaceChange(PlaceChangeEvent event);
}
