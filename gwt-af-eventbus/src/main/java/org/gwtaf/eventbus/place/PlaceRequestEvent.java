package org.gwtaf.eventbus.place;

import com.google.gwt.event.shared.GwtEvent;

/**
 * PlaceRequestEvent
 * 
 * @author David Peterson
 */
public class PlaceRequestEvent extends GwtEvent<PlaceRequestHandler> {

	private static Type<PlaceRequestHandler> TYPE;

	public static Type<PlaceRequestHandler> getType() {
		if (TYPE == null)
			TYPE = new Type<PlaceRequestHandler>();
		return TYPE;
	}

	private final PlaceRequest request;

	private final boolean fromHistory;

	private boolean changeHistory = true;

	/**
	 * Create a default <code>PlaceRequestEvent</code> with the given
	 * {@link PlaceRequest}. By default, the request is not from History and
	 * will change the History.
	 * 
	 * @param request
	 *            the requested place.
	 */
	public PlaceRequestEvent(PlaceRequest request) {
		this(request, false);
	}

	/**
	 * Create a <code>PlaceRequestEvent</code> with the given request and a
	 * boolean indicating that the request should change the History.
	 * 
	 * @param request
	 *            the requested place.
	 * @param fromHistory
	 *            a boolean indicating if the request came from History.
	 */
	public PlaceRequestEvent(PlaceRequest request, boolean fromHistory) {
		this.request = request;
		this.fromHistory = fromHistory;
	}

	@Override
	protected void dispatch(PlaceRequestHandler handler) {
		handler.onPlaceRequest(this);
	}

	@Override
	public Type<PlaceRequestHandler> getAssociatedType() {
		return getType();
	}

	public PlaceRequest getRequest() {
		return request;
	}

	public boolean isFromHistory() {
		return fromHistory;
	}

	/**
	 * Returns true if the history should be changed and false otherwise.
	 * 
	 * @return true if the history should be changed and false otherwise.
	 */
	public boolean isChangeHistory() {
		return changeHistory;
	}

	/**
	 * Sets whether this request should change the History or not.
	 * 
	 * @param changeHistory
	 *            if true, changes the history, false otherwise.
	 */
	public void setChangeHistory(boolean changeHistory) {
		this.changeHistory = changeHistory;
	}
}
