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
 * Unit test {@link GenericEvent}.
 * 
 * @author Arthur Kalmenson
 */
public class GenericEventTest {

	/**
	 * Test implementation of {@link GenericEvent}.
	 * 
	 * @author Arthur Kalmenson
	 */
	private class TestGenericEventImpl extends
			GenericEvent<TestGenericEventImplHandler> {
		public TestGenericEventImpl(Type<TestGenericEventImplHandler> type) {
			super(type);
		}

		@Override
		protected void dispatch(TestGenericEventImplHandler arg0) {
			// not implemented.
		}
	}

	/**
	 * A test {@link EventHandler} for use by {@link TestGenericEventImpl}.
	 * 
	 * @author Arthur Kalmenson
	 */
	private interface TestGenericEventImplHandler extends EventHandler {
	}

	/**
	 * Test to ensure that the {@link Type} provided in the constructor is saved
	 * in the generic event.
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void typeSaved() {
		Type<TestGenericEventImplHandler> typeMock = mock(Type.class);
		TestGenericEventImpl event = new TestGenericEventImpl(typeMock);
		Assert.assertEquals(event.getAssociatedType(), typeMock);
	}
}
