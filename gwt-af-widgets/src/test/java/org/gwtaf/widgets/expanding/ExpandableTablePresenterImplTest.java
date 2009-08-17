/*
 * Copyright 2008. Mount Sinai Hospital, Toronto, Canada.
 * 
 * Licensed under the Apache License, Version 2.0. You
 * can find a copy of the license at:
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * IN NO EVENT SHALL MOUNT SINAI HOSPITAL BE LIABLE TO ANY PARTY FOR DIRECT, 
 * INDIRECT, SPECIAL, INCIDENTAL, OR CONSEQUENTIAL DAMAGES, INCLUDING LOST 
 * PROFITS, ARISING OUT OF THE USE OF THIS SOFTWARE AND ITS DOCUMENTATION, 
 * EVEN IF MOUNT SINAI HOSPITAL HAS BEEN ADVISED OF THE POSSIBILITY OF SUCH 
 * DAMAGE.
 * 
 * MOUNT SINAI HOSPITAL SPECIFICALLY DISCLAIMS ANY IMPLIED WARRANTIES OF 
 * MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE. THE SOFTWARE AND 
 * ACCOMPANYING DOCUMENTATION, IF ANY, PROVIDED HEREUNDER IS PROVIDED "AS IS". 
 * MOUNT SINAI HOSPITAL HAS NO OBLIGATION TO PROVIDE MAINTENANCE, SUPPORT, 
 * UPDATES, ENHANCEMENTS, OR MODIFICATIONS. 
 */
package org.gwtaf.widgets.expanding;

import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.gwtaf.eventbus.EventBus;
import org.gwtaf.widgets.Presenter;
import org.gwtaf.widgets.View;
import org.gwtaf.widgets.expanding.event.PresenterCreatedEvent;
import org.gwtaf.widgets.expanding.event.PresenterRemovedEvent;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.google.gwt.junit.GWTMockUtilities;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Provider;

/**
 * Unit test the {@link ExpandableTablePresenterImpl}.
 * 
 * @author Arthur Kalmenson
 */
public class ExpandableTablePresenterImplTest {

	@Mock
	private EventBus eventBusMock;

	@Mock
	private ExpandableTable<ViewMock> expandableTableMock;

	@Mock
	private Provider<Presenter<ViewMock, String>> presenterProviderMock;

	private ExpandableTablePresenterImpl<Presenter<ViewMock, String>, ViewMock, String> presenter;

	@Mock
	private Provider<PresenterCreatedEvent<Presenter<ViewMock, String>>> createdEventProviderMock;

	@Mock
	private Provider<PresenterRemovedEvent<Presenter<ViewMock, String>>> removedEventProviderMock;

	@Mock
	private Presenter<ViewMock, String> presenterMock;

	@Mock
	private ViewMock viewMock;

	@Mock
	private RemoveButton removeButtonMock;

	/**
	 * A simple class to implement {@link View} so we could use it to type the
	 * {@link Presenter}.
	 * 
	 * @author Arthur Kalmenson
	 */
	private class ViewMock implements View {
		public Widget getContainingWidget() {
			return null;
		}
	}

	@BeforeClass
	public void disarmGwt() {
		GWTMockUtilities.disarm();
	}

	@AfterClass
	public void rearmGwt() {
		GWTMockUtilities.restore();
	}

	@BeforeMethod
	public void initBefore() {
		MockitoAnnotations.initMocks(this);
		presenter = new ExpandableTablePresenterImpl<Presenter<ViewMock, String>, ViewMock, String>(
				eventBusMock, expandableTableMock, presenterProviderMock,
				createdEventProviderMock, removedEventProviderMock);
	}

	/**
	 * Test to ensure the variables we create the
	 * {@link ExpandableTablePresenterImpl} with are stored.
	 */
	@Test
	public void createAndGet() {
		Assert.assertEquals(presenter.getView(), expandableTableMock);
	}

	/**
	 * Test calling the {@link ExpandableTablePresenterImpl#addAndFireEvent()}.
	 * Ensure the expandable table adds a new element and a
	 * {@link PresenterCreatedEvent} is fired. We're also expecting the new
	 * model that was created to be stored and available in
	 * {@link ExpandableTablePresenterImpl#getModel()}.
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void addAndCheckEvent() {

		// set up the mocks.
		PresenterCreatedEvent<Presenter<ViewMock, String>> eventMock = setupCreateMocks();
		when(presenterMock.getModel()).thenReturn("Some String");

		// try to add.
		presenter.addAndFireEvent();

		// verify that the event was fired and the view of the created presenter
		// was added to the ExpandableTable.
		verify(eventMock).setPresenter(presenterMock);
		verify(eventBusMock).fireEvent(eventMock);
		verify(expandableTableMock).add(viewMock);

		// check that the model was added to the Presenters list of models.
		Assert.assertEquals(presenter.getModel().size(), 1);
		Assert.assertEquals(presenter.getModel().get(0), "Some String");
	}

	/**
	 * Try removing with a null {@link RemoveButton}. We're expecting an
	 * {@link AssertionError} to be thrown.
	 */
	@Test(expectedExceptions = AssertionError.class)
	public void removeRowWithNullButton() {
		presenter.removeRow(null);
	}

	/**
	 * Try removing a row with a {@link RemoveButton}. We expect the call to be
	 * passed down to the {@link ExpandableTable} to handle the removal.
	 */
	@Test
	public void removeRow() {
		presenter.removeRow(removeButtonMock);
		verify(expandableTableMock).remove(removeButtonMock);
	}

	/**
	 * Try firing a remove event with a null View. Since this can happen when
	 * the {@link ExpandableTable} tries to remove using a {@link RemoveButton}
	 * that's not there, we don't want to throw any exceptions. Nothing will
	 * happen but we want to verify that the event bus is not called.
	 */
	@Test
	public void fireRemoveEventNullView() {
		presenter.fireRemoveEvent(null);
		verifyZeroInteractions(eventBusMock);
	}

	/**
	 * Test firing a remove event when the View provided doesn't exist in the
	 * {@link ExpandableTablePresenter}.
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void fireRemoveEventNoView() {

		// set up the mocks.
		PresenterRemovedEvent<Presenter<ViewMock, String>> removeEventMock = mock(PresenterRemovedEvent.class);
		when(removedEventProviderMock.get()).thenReturn(removeEventMock);
		setupCreateMocks();
		ViewMock someOtherView = mock(ViewMock.class);

		// add one view but first remove for another.
		presenter.addAndFireEvent();
		presenter.fireRemoveEvent(someOtherView);

		// check to make sure no events were fired.
		verify(eventBusMock, never()).fireEvent(removeEventMock);
	}

	/**
	 * Test removing a row. We want to ensure the correct event is fired once
	 * the row is removed with the correct Presenter.
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void removeAndCheckEvent() {

		// set up the mocks.
		PresenterRemovedEvent<Presenter<ViewMock, String>> removeEventMock = mock(PresenterRemovedEvent.class);
		when(removedEventProviderMock.get()).thenReturn(removeEventMock);
		setupCreateMocks();

		// add and then remove the view added.
		presenter.addAndFireEvent();
		presenter.fireRemoveEvent(viewMock);

		// verify that the event was fired and the model was removed.
		verify(eventBusMock).fireEvent(removeEventMock);
		Assert.assertEquals(presenter.getModel().size(), 0);
	}

	/**
	 * Test setting a null model, we're expecting an
	 * {@link AssertionError} to be thrown because clearing the table
	 * should be through setting an empty model, not a null one.
	 */
	@Test(expectedExceptions = AssertionError.class)
	public void setNullModel() {
		presenter.setModel(null);
	}

	/**
	 * Test setting the list of models with an empty list. We expect the clear
	 * method to be called on the {@link ExpandableTable} and the presenter's
	 * models to be empty.
	 */
	@Test
	public void setModelsEmpty() {

		presenter.setModel(new ArrayList<String>());

		verify(expandableTableMock).clear();
		Assert.assertEquals(presenter.getModel().size(), 0);
	}

	/**
	 * Test setting a list of a few models. We're expecting everything to be
	 * cleared and then three views added to the {@link ExpandableTable} and
	 * three events to be thrown.
	 */
	@Test
	public void setModels() {

		PresenterCreatedEvent<Presenter<ViewMock, String>> eventMock = setupCreateMocks();
		when(presenterMock.getModel()).thenReturn("Some String");

		// create the list of models and set it.
		List<String> models = Arrays.asList("hello", "hi", "hey");
		presenter.setModel(models);

		// verify that the event was fired and the view of the created presenter
		// was added to the ExpandableTable.
		verify(eventMock, times(3)).setPresenter(presenterMock);
		verify(eventBusMock, times(3)).fireEvent(eventMock);
		verify(expandableTableMock, times(3)).add(viewMock);

		// check that the model was added to the Presenters list of models.
		Assert.assertEquals(presenter.getModel().size(), 3);
		Assert.assertEquals(presenter.getModel().get(0), "Some String");
	}

	/**
	 * Pretend the AddButton is clicked. We're expecting the
	 * {@link ExpandableTablePresenterImpl#addAndFireEvent()} to be called.
	 */
	@Test
	public void clickedAddButton() {

		// set up mocks.
		PresenterCreatedEvent<Presenter<ViewMock, String>> eventMock = setupCreateMocks();

		// add button mock setup.
		AddButton addButtonMock = mock(AddButton.class);
		Widget widgetMock = mock(Widget.class);
		when(addButtonMock.getContainingWidget()).thenReturn(widgetMock);
		Element elementMock = mock(Element.class);
		when(addButtonMock.getContainingWidget().getElement()).thenReturn(
				elementMock);
		when(expandableTableMock.getAddButton()).thenReturn(addButtonMock);

		// add button clicked.
		presenter.viewClicked(elementMock);

		// verify that the new event was fired.
		verify(eventBusMock).fireEvent(eventMock);
	}

	/**
	 * Pretend a RemoveButton is clicked. We're expecting the
	 * {@link ExpandableTablePresenterImpl#removeRow(RemoveButton)} and
	 * {@link ExpandableTablePresenterImpl#fireRemoveEvent(View)} to be called.
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void clickedRemoveButton() {

		// set up the mocks.
		PresenterRemovedEvent<Presenter<ViewMock, String>> removeEventMock = mock(PresenterRemovedEvent.class);
		when(removedEventProviderMock.get()).thenReturn(removeEventMock);
		PresenterCreatedEvent<Presenter<ViewMock, String>> createdEventMock = setupCreateMocks();
		when(expandableTableMock.remove(removeButtonMock)).thenReturn(viewMock);

		// remove button mock setup.
		Widget removeWidgetMock = mock(Widget.class);
		when(removeButtonMock.getContainingWidget()).thenReturn(removeWidgetMock);
		Element removeElementMock = mock(Element.class);
		when(removeButtonMock.getContainingWidget().getElement()).thenReturn(
				removeElementMock);
		when(expandableTableMock.getRemoveButtons()).thenReturn(
				Arrays.asList(removeButtonMock));
		
		// mock out the add button too.
		AddButton addButtonMock = mock(AddButton.class);
		Widget widgetMock = mock(Widget.class);
		when(addButtonMock.getContainingWidget()).thenReturn(widgetMock);
		Element elementMock = mock(Element.class);
		when(addButtonMock.getContainingWidget().getElement()).thenReturn(
				elementMock);
		when(expandableTableMock.getAddButton()).thenReturn(addButtonMock);

		// add and then remove pretend the remove button is pressed.
		presenter.addAndFireEvent();
		presenter.viewClicked(removeElementMock);

		// verify that the add then remove events are fired.
		verify(eventBusMock).fireEvent(createdEventMock);
		verify(eventBusMock).fireEvent(removeEventMock);
	}

	/**
	 * Set up the new presenter created mocks and return the event.
	 * 
	 * @return the mocked event.
	 */
	@SuppressWarnings("unchecked")
	private PresenterCreatedEvent<Presenter<ViewMock, String>> setupCreateMocks() {
		PresenterCreatedEvent<Presenter<ViewMock, String>> eventMock = mock(PresenterCreatedEvent.class);
		when(createdEventProviderMock.get()).thenReturn(eventMock);
		when(presenterProviderMock.get()).thenReturn(presenterMock);
		when(presenterMock.getView()).thenReturn(viewMock);
		return eventMock;
	}
}
