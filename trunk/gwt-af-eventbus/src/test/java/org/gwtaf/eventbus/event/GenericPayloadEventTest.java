/*
 * Copyright 2009. Mount Sinai Hospital, Toronto, Canada.
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

import org.testng.Assert;
import org.testng.annotations.Test;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent.Type;

/**
 * Unit test {@link GenericPayloadEvent}. Some of the testing was already done
 * for the <code>GenericEvent</code>, so we'll just be testing the payload.
 * 
 * @author Arthur Kalmenson
 */
public class GenericPayloadEventTest {

	/**
	 * Test implementation of {@link GenericPayloadEvent}.
	 * 
	 * @author Arthur Kalmenson
	 */
	private class TestGenericPayloadEventImpl extends
			GenericPayloadEvent<String, TestGenericPayloadEventImplHandler> {
		public TestGenericPayloadEventImpl(
				Type<TestGenericPayloadEventImplHandler> type) {
			super(type);
		}

		@Override
		protected void dispatch(TestGenericPayloadEventImplHandler arg0) {
			// not implemented.
		}
	}

	/**
	 * A test {@link EventHandler} for use by
	 * {@link TestGenericPayloadEventImpl}.
	 * 
	 * @author Arthur Kalmenson
	 */
	private interface TestGenericPayloadEventImplHandler extends EventHandler {
	}

	/**
	 * Test to ensure that when the payload is set, it is saved in the event.
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void getSetPayload() {
		Type<TestGenericPayloadEventImplHandler> typeMock = mock(Type.class);
		TestGenericPayloadEventImpl event =
				new TestGenericPayloadEventImpl(typeMock);
		String payload = "test";
		event.setPayload(payload);
		Assert.assertTrue(event.getPayload() == payload);
	}
}
