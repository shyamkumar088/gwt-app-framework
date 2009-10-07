package org.gwtaf.eventbus.place;

import org.gwtaf.eventbus.EventBus;

import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.History;
import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * PlaceManager - modified to only handle PlaceChangedEvent for now.
 * 
 * @author David Peterson
 * @author Sergey Vesselov
 */
@Singleton
public class PlaceManager implements ValueChangeHandler<String>,
		PlaceChangedHandler {
	private final EventBus eventBus;

	@Inject
	public PlaceManager(EventBus eventBus) {
		this.eventBus = eventBus;

		// Register ourselves with the History API.
		History.addValueChangeHandler(this);

		// Listen for manual place change events.
		eventBus.addHandler(PlaceChangedEvent.getType(), this);
	}

	/**
	 * Comes from handling ValueChangeHandler of History.
	 */
	public void onValueChange(ValueChangeEvent<String> event) {
		try {
			eventBus.fireEvent(new PlaceChangedEvent(PlaceRequest
					.fromString(event.getValue()), true));
		} catch (PlaceParsingException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Requests Place Manager to change place.
	 */
	public void onPlaceChange(PlaceChangedEvent event) {
		if (!event.isFromHistory()) {
			newPlace(event.getRequest());
		}
	}

	private void newPlace(PlaceRequest request) {
		History.newItem(request.toString(), false);
	}

	public void fireCurrentPlace() {
		if (History.getToken() != null)
			History.fireCurrentHistoryState();
	}

}
