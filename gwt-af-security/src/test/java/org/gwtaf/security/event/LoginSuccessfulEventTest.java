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

import static org.mockito.Mockito.verify;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.google.gwt.event.shared.GwtEvent.Type;

/**
 * Unit test the {@link LoginSuccessfulEvent}.
 * 
 * @author Arthur Kalmenson
 */
public class LoginSuccessfulEventTest {

	@Mock
	private LoginSuccessfulEventHandler handlerMock;

	@Mock
	private Type<LoginSuccessfulEventHandler> typeMock;

	private LoginSuccessfulEvent event;

	@BeforeMethod
	public void initBefore() {
		MockitoAnnotations.initMocks(this);
		event = new LoginSuccessfulEvent(typeMock);
	}

	/**
	 * Ensure that the handler is called.
	 */
	@Test
	public void dispatchToHandler() {
		event.dispatch(handlerMock);
		verify(handlerMock).onLoginSuccessful(event);
	}
}
