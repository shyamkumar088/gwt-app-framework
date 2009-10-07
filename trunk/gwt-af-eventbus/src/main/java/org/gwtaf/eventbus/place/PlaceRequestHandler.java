package org.gwtaf.eventbus.place;

import com.google.gwt.event.shared.EventHandler;

/**
 * PlaceRequestHandler
 * 
 * @author David Peterson
 */
public interface PlaceRequestHandler extends EventHandler {
	/**
	 * Called when something has requested a new place. Should be implemented by
	 * instances which can show the place.
	 * 
	 * @param event
	 *            The event.
	 */
	void onPlaceRequest(PlaceRequestEvent event);
}
