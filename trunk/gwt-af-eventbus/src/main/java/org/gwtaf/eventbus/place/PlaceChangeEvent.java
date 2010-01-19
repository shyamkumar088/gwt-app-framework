package org.gwtaf.eventbus.place;

import com.google.gwt.event.shared.GwtEvent;

/**
 * This event is triggered when the request has changed manually (ie, not due to
 * a {@link PlaceRequestEvent}). This allows the {@link PlaceManager} to keep
 * track of the current location. Other classes may, but will typically not need
 * to, implement {@link PlaceChangeHandler} to be informed of manual changes.
 * 
 * @author David Peterson
 * 
 */
public class PlaceChangeEvent extends GwtEvent<PlaceChangeHandler> {

	private static Type<PlaceChangeHandler> TYPE;

	public static Type<PlaceChangeHandler> getType() {
		if (TYPE == null)
			TYPE = new Type<PlaceChangeHandler>();
		return TYPE;
	}

	private final PlaceRequest request;

	public PlaceChangeEvent(PlaceRequest request) {
		this.request = request;
	}

	@Override
	protected void dispatch(PlaceChangeHandler handler) {
		handler.onPlaceChange(this);
	}

	@Override
	public Type<PlaceChangeHandler> getAssociatedType() {
		return getType();
	}

	public PlaceRequest getRequest() {
		return request;
	}
}
