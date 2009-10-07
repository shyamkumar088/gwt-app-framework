package org.gwtaf.eventbus.place;

import com.google.gwt.event.shared.EventHandler;

/**
 * PlaceChangedHandler
 * 
 * @author David Peterson
 */
public interface PlaceChangedHandler extends EventHandler {
	/**
	 * Called after the current place has already changed. Allows handlers to
	 * update any internal tracking, etc.
	 * 
	 * @param event
	 *            The event.
	 */
	void onPlaceChange(PlaceChangedEvent event);
}
