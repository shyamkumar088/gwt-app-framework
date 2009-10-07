package org.gwtaf.eventbus.place;

import junit.framework.Assert;

import org.gwtaf.eventbus.EventBus;
import org.gwtaf.eventbus.EventBusGinModule;
import org.gwtaf.eventbus.place.PlaceManager;

import com.google.gwt.core.client.GWT;
import com.google.gwt.inject.client.GinModules;
import com.google.gwt.inject.client.Ginjector;
import com.google.gwt.junit.client.GWTTestCase;
import com.google.gwt.user.client.History;

/**
 * Test of the Place classes.
 * 
 * @author Sergey Vesselov
 */
public class GwtTestPlace extends GWTTestCase {

	/**
	 * The injected place manager
	 */
	private PlaceManager manager;

	/**
	 * The injected event bus
	 */
	private EventBus bus;

	@GinModules(EventBusGinModule.class)
	public static interface PlaceGinjector extends Ginjector {
		PlaceManager getPlaceManager();

		EventBus getEventBus();
	}

	@Override
	protected void gwtSetUp() throws Exception {
		super.gwtSetUp();

		// inject the manager.
		PlaceGinjector injector = GWT.create(PlaceGinjector.class);
		manager = injector.getPlaceManager();

		// inject the event bus
		bus = injector.getEventBus();
	}

	/**
	 * Test PlaceChangedEvent, History changes and the catching of
	 * PlaceRequestEvent.
	 */
	public void testChangeEventRequestEvent() {

		// create a listening class
		ReactionClass listeningClass = new ReactionClass(bus);

		// create a request
		Place place = new Place("New Place");
		PlaceRequest request = new PlaceRequest(place);
		PlaceChangedEvent event = new PlaceChangedEvent(request);

		// fire event
		bus.fireEvent(event);

		// make sure history is updated
		Assert.assertTrue(History.getToken().equals("New Place"));

		// make sure listening classes get the event
		Assert.assertTrue(listeningClass.getValueCaught().equals("New Place"));
	}

	/**
	 * Check that history changes throw the right events.
	 */
	public void testHistoryThrowing() {

		// create a listening class
		ReactionClass listeningClass = new ReactionClass(bus);

		// make a new history class
		History.newItem("Home Page");

		// make sure listening classes get the event
		Assert.assertTrue(listeningClass.getValueCaught().equals("Home Page"));
	}

	/**
	 * Sample class to catch {@link PlaceRequestEvent}s
	 * 
	 * @author Sergey Vesselov
	 * 
	 */
	private class ReactionClass implements PlaceChangedHandler {

		/**
		 * Value of the place caught.
		 */
		private String valueCaught;

		public ReactionClass(EventBus bus) {
			bus.addHandler(PlaceChangedEvent.getType(), this);
		}

		public void onPlaceChange(PlaceChangedEvent event) {
			valueCaught = event.getRequest().getPlace().getId();
		}

		public String getValueCaught() {
			return valueCaught;
		}
	}

	@Override
	public String getModuleName() {
		return "org.gwtaf.GwtAfEventbusTest";
	}
}
