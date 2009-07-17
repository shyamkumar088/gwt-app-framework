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
package org.gwtaf.widgets.generic;

import org.mockito.InOrder;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.google.gwt.junit.GWTMockUtilities;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;

/**
 * Test the {@link UiBundleWidget} class.
 * 
 * @author Arthur Kalmenson
 */
public class UiBundleWidgetTest {

	/**
	 * A mock of a {@link Panel}.
	 */
	@Mock
	private Panel panelMock;

	/**
	 * A mock of the label.
	 */
	@Mock
	private Label labelMock;

	/**
	 * A mock of a textbox.
	 */
	@Mock
	private Widget widgetMock;

	/**
	 * The {@link UiBundleWidget} we're testing.
	 */
	private UiBundleWidget<Label, Widget> uiBundleWidget;

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
	 * Return a list of bad constructor arguments.
	 * 
	 * @return a list of bad constructor arguments.
	 */
	@DataProvider(name = "badArgs")
	public Object[][] badConstructorArgProvider() {
		return new Object[][] { { panelMock, null, null },
				{ null, labelMock, null }, { panelMock, null, widgetMock } };
	}

	/**
	 * Test creating a {@link UiBundleWidget} with bad constructor arguments. We
	 * expect a {@link NullPointerException} for nulls.
	 * 
	 * @param panel
	 *            the {@link Panel}.
	 * @param label
	 *            the {@link Label}.
	 * @param widget
	 *            the {@link Widget}.
	 */
	@Test(expectedExceptions = NullPointerException.class, dataProvider = "badArgs")
	public void badConstructorArgs(Panel panel, Label label, Widget widget) {
		uiBundleWidget = new UiBundleWidget<Label, Widget>(panel, label, widget);
	}

	/**
	 * Test to ensure the label and widget are added to the Panel that's given.
	 * We also want to ensure the label is added before the widget.
	 */
	@Test
	public void layoutCheck() {

		// create it.
		uiBundleWidget = new UiBundleWidget<Label, Widget>(panelMock,
				labelMock, widgetMock);

		// verify they were added.
		InOrder inOrder = inOrder(panelMock);
		inOrder.verify(panelMock).add(labelMock);
		inOrder.verify(panelMock).add(widgetMock);

		// verify there was no other action.
		verifyZeroInteractions(labelMock);
		verifyZeroInteractions(widgetMock);
	}
}
