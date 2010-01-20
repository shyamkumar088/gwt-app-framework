/*
 * Copyright 2010. Mount Sinai Hospital, Toronto, Canada.
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
package org.gwtaf.security.event;

import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.mockito.MockitoAnnotations;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.google.gwt.event.shared.GwtEvent.Type;

/**
 * Unit test the {@link GetCurrentUserEvent} class.
 * 
 * @author Arthur Kalmenson
 */
public class GetCurrentUserEventTest {

	@Mock
	private GetCurrentUserEventHandler handlerMock;

	@Mock
	private Type<GetCurrentUserEventHandler> type;
	
	private GetCurrentUserEvent event;

	@BeforeMethod
	public void initBefore() {
		MockitoAnnotations.initMocks(this);
		event = new GetCurrentUserEvent(type);
	}

	/**
	 * Ensure that the handler is called.
	 */
	@Test
	public void dispatchToHandler() {
		event.dispatch(handlerMock);
		verify(handlerMock).onGetCurrentUser(event);
	}
	
	/**
	 * Ensure the type given in the constructor is saved.
	 */
	@Test
	public void typeSaved() {
		Assert.assertEquals(event.getAssociatedType(), type);
	}
}
