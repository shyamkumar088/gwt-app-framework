package org.gwtaf.eventbus.place;

import com.google.gwt.event.shared.GwtEvent;

/**
 * This event is triggered when the request has changed manually (ie, not due to
 * a {@link PlaceRequestEvent}). This allows the {@link PlaceManager} to keep
 * track of the current location. Other classes may, but will typically not need
 * to, implement {@link PlaceChangedHandler} to be informed of manual changes.
 * 
 * @author David Peterson
 * 
 */
public class PlaceChangedEvent extends GwtEvent<PlaceChangedHandler> {

	private static Type<PlaceChangedHandler> TYPE;

	private final boolean fromHistory;

	public static Type<PlaceChangedHandler> getType() {
		if (TYPE == null)
			TYPE = new Type<PlaceChangedHandler>();
		return TYPE;
	}

	private final PlaceRequest request;

	public PlaceChangedEvent(PlaceRequest request) {
		this.request = request;
		this.fromHistory = false;
	}

	public PlaceChangedEvent(PlaceRequest request, boolean fromHistory) {
		this.request = request;
		this.fromHistory = fromHistory;
	}

	@Override
	protected void dispatch(PlaceChangedHandler handler) {
		handler.onPlaceChange(this);
	}

	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<PlaceChangedHandler> getAssociatedType() {
		return getType();
	}

	boolean isFromHistory() {
		return fromHistory;
	}

	public PlaceRequest getRequest() {
		return request;
	}
}
