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
package org.gwtaf.eventbus.event;

import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.mockito.MockitoAnnotations;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.google.gwt.event.shared.GwtEvent.Type;

/**
 * Test the {@link ApplicationStartedEvent}.
 * 
 * @author Arthur Kalmenson
 */
public class ApplicationStartedEventTest {

	/**
	 * Mock of the type for the event.
	 */
	@Mock
	private Type<ApplicationStartedHandler> typeMock;

	/**
	 * The event we're testing.
	 */
	private ApplicationStartedEvent event;

	@BeforeMethod
	public void initBefore() {
		MockitoAnnotations.initMocks(this);

		event = new ApplicationStartedEvent(typeMock);
	}

	/**
	 * Test creating the event with the given type. We're expecting the type to
	 * be saved and returned.
	 */
	@Test
	public void createAndGetType() {
		Assert.assertEquals(event.getAssociatedType(), typeMock);
	}

	/**
	 * Test dispatching an event and ensure the handler gets called.
	 */
	@Test
	public void dispatchToHandler() {
		ApplicationStartedHandler handlerMock =
				mock(ApplicationStartedHandler.class);
		event.dispatch(handlerMock);
		verify(handlerMock).onApplicationStarted(event);
	}
}
