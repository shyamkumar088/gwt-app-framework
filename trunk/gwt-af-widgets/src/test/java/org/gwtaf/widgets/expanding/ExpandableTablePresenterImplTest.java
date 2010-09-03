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

	@Mock
	private Provider<String> modelProviderMock;

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
				eventBusMock, expandableTableMock, presenterProviderMock, modelProviderMock, 
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
	 * Test setting a null model, we're expecting an {@link AssertionError} to
	 * be thrown because clearing the table should be through setting an empty
	 * model, not a null one.
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
