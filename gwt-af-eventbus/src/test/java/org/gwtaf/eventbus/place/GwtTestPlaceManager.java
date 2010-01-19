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
 * @author Arthur Kalmenson
 */
public class GwtTestPlaceManager extends GWTTestCase {

	/**
	 * The injected place manager
	 */
	private PlaceManager manager;

	/**
	 * The injected event bus
	 */
	private EventBus bus;
	
	private ReactionClass listeningClass;

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
		
		// create the listening class.
		listeningClass = new ReactionClass(bus);
	}

	/**
	 * Test firing a {@link PlaceRequestEvent} on the bus and ensure that the
	 * {@link History} is changed and the event is translated into a
	 * {@link PlaceChangeEvent}.
	 */
	public void testChangeEventRequestEvent() {

		// create a request
		Place place = new Place("New Place");
		PlaceRequest request = new PlaceRequest(place);
		PlaceRequestEvent event = new PlaceRequestEvent(request);

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

		// make a new history class
		History.newItem("Home Page");

		// make sure listening classes get the change event.
		assertTrue(listeningClass.getValueCaught().equals("Home Page"));
	}
	
	/**
	 * Test firing the current place.
	 */
	public void testFireCurrentPlace() {
		
		// add a new place.
		History.newItem("current-place", false);
		
		// now fire it and check that the listening class go the translated class.
		manager.fireCurrentPlace();
		assertTrue(listeningClass.getValueCaught().equals("current-place"));
	}

	/**
	 * Sample class to catch {@link PlaceChangeEvent}s
	 * 
	 * @author Sergey Vesselov
	 */
	private class ReactionClass implements PlaceChangeHandler {

		/**
		 * Value of the place caught.
		 */
		private String valueCaught;

		public ReactionClass(EventBus bus) {
			bus.addHandler(PlaceChangeEvent.getType(), this);
		}

		public void onPlaceChange(PlaceChangeEvent event) {
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
