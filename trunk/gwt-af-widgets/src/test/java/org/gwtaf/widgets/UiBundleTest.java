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
package org.gwtaf.widgets;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.google.gwt.junit.GWTMockUtilities;
import com.google.gwt.user.client.ui.Widget;

/**
 * Test the {@link UiBundle} class.
 * 
 * @author Arthur Kalmenson
 */
public class UiBundleTest {

	@Mock
	private Widget widgetMock1;

	@Mock
	private Widget widgetMock2;

	/**
	 * The {@link UiBundle} we're testing.
	 */
	private UiBundle<Widget, Widget> uiBundle;

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
	}

	/**
	 * Test creating and getting the values from a new {@link UiBundle}.
	 */
	@Test
	public void createAndGet() {
		uiBundle = new UiBundle<Widget, Widget>(widgetMock1, widgetMock2);

		Assert.assertEquals(uiBundle.getLabel(), widgetMock1);
		Assert.assertEquals(uiBundle.getWidget(), widgetMock2);
	}
	
	/**
	 * Test creating an empty {@link UiBundle} and 
	 */
	@Test
	public void setAndGet() {
		uiBundle = new UiBundle<Widget, Widget>();

		uiBundle.setLabel(widgetMock2);
		uiBundle.setWidget(widgetMock1);

		Assert.assertEquals(uiBundle.getLabel(), widgetMock2);
		Assert.assertEquals(uiBundle.getWidget(), widgetMock1);
	}
}
