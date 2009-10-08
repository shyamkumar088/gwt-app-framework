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

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.gwtaf.command.shared.Response;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.google.gwt.event.shared.GwtEvent.Type;

/**
 * Test the {@link RpcResponseEvent}.
 * 
 * @author Jason Kong
 */
public class RpcResponseEventTest {

	/**
	 * A mock of the Type.
	 */
	@Mock
	private Type<RpcResponseEventHandler<Response>> type;

	/**
	 * A mock of the {@link Response}
	 */
	@Mock
	private Response mockResponse;

	/**
	 * The {@link RpcResponseEvent} we're testing.
	 */
	private RpcResponseEvent<Response> rpcResponseEvent;

	@BeforeMethod
	public void initBefore() {
		MockitoAnnotations.initMocks(this);
		rpcResponseEvent = new RpcResponseEvent<Response>(type);
	}

	/**
	 * Create and test getting the {@link RpcResponseEvent#getAssociatedType()},
	 * we expect the type to be kept.
	 */
	@Test
	public void createAndGetType() {
		Assert.assertEquals(rpcResponseEvent.getAssociatedType(), type);
	}

	/**
	 * Test setting and getting the {@link Presenter} to ensure it's stored in
	 * the event.
	 */
	@Test
	public void setAndGetResponse() {

		// set it and check they're equal.
		rpcResponseEvent.setResponse(mockResponse);
		Assert.assertEquals(rpcResponseEvent.getResponse(), mockResponse);
	}

	/**
	 * Test dispatching an event and ensure the handler gets called.
	 */
	@Test
	public void dispatchToHandler() {

		// create a handler mock.
		RpcResponseEventHandler<Response> handler = mock(RpcResponseEventHandler.class);

		// fire the event and check it was called.
		rpcResponseEvent.dispatch(handler);
		verify(handler).onRpcResponseEventThrown(rpcResponseEvent);
	}
}
