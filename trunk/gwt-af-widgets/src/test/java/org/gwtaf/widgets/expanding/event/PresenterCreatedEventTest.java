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
package org.gwtaf.widgets.expanding.event;

import org.gwtaf.widgets.Presenter;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.mockito.MockitoAnnotations;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.google.gwt.event.shared.GwtEvent.Type;

/**
 * Test the {@link PresenterCreatedEvent}.
 * 
 * @author Arthur Kalmenson
 */
public class PresenterCreatedEventTest {

	/**
	 * A mock of the Type.
	 */
	@Mock
	private Type<PresenterCreatedHandler<Presenter<?, ?>>> type;

	/**
	 * The {@link PresenterCreatedEvent} we're testing.
	 */
	private PresenterCreatedEvent<Presenter<?, ?>> presenterEvent;

	@BeforeMethod
	public void initBefore() {
		MockitoAnnotations.initMocks(this);
		presenterEvent = new PresenterCreatedEvent<Presenter<?, ?>>(type);
	}

	/**
	 * Create and test getting the
	 * {@link PresenterCreatedEvent#getAssociatedType()}, we expect the type to
	 * be kept.
	 */
	@Test
	public void createAndGetType() {
		Assert.assertEquals(presenterEvent.getAssociatedType(), type);
	}

	/**
	 * Test setting and getting the {@link Presenter} to ensure it's stored in
	 * the event.
	 */
	@Test
	public void setAndGetPresenter() {

		// mock the Presenter.
		Presenter<?, ?> presenterMock = mock(Presenter.class);

		// set it and check they're equal.
		presenterEvent.setPresenter(presenterMock);
		Assert.assertEquals(presenterEvent.getPresenter(), presenterMock);
	}

	/**
	 * Test dispatching an event and ensure the handler gets called.
	 */
	@Test
	@SuppressWarnings("unchecked")
	public void dispatchToHandler() {

		// create a handler mock.
		PresenterCreatedHandler<Presenter<?, ?>> handler = mock(PresenterCreatedHandler.class);

		// fire the event and check it was called.
		presenterEvent.dispatch(handler);
		verify(handler).onPresenterCreated(presenterEvent);
	}
}
