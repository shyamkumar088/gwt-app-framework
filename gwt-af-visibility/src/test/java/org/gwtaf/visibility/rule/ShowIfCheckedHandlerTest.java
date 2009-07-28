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
package org.gwtaf.visibility.rule;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import org.gwtaf.widgets.generic.RadioButtonGroup;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.google.gwt.junit.GWTMockUtilities;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Widget;

/**
 * Test the {@link ShowIfChecked} class.
 * 
 * @author Arthur Kalmenson
 */
public class ShowIfCheckedHandlerTest {

	/**
	 * Mock of a {@link CheckBox}.
	 */
	@Mock
	private CheckBox checkBoxMock;

	/**
	 * The rule we're testing.
	 */
	private ShowIfChecked rule;

	@AfterClass
	public void rearmGwt() {
		GWTMockUtilities.restore();
	}

	@BeforeClass
	public void beforeClass() {
		GWTMockUtilities.disarm();
	}

	@BeforeMethod
	public void initBefore() {
		MockitoAnnotations.initMocks(this);
		rule = new ShowIfChecked(checkBoxMock);
	}

	/**
	 * Test to ensure the visibility is not changed on the parent or any child
	 * widgets
	 */
	@Test
	public void visibilityNotChanged() {

		// verify the group's visibility never changed.
		verify(checkBoxMock, never()).setVisible(true);
		verify(checkBoxMock, never()).setVisible(false);

		// add a child widget and make sure it also doesn't have visibility
		// changed.
		Widget mockWidget = mock(Widget.class);
		rule.addChildWidget(mockWidget);
		verify(mockWidget, never()).setVisible(true);
		verify(mockWidget, never()).setVisible(false);
	}

	/**
	 * Returns mocks of {@link CheckBox}es mocked with different values to make
	 * visibility set different.
	 * 
	 * @return mocks of {@link CheckBox}es.
	 */
	@DataProvider(name = "executeProvider")
	public Object[][] executeProvider() {

		// CheckBox not checked.
		CheckBox notChecked = mock(CheckBox.class);
		when(notChecked.getValue()).thenReturn(false);

		// CheckBox checked.
		CheckBox valueMatches = mock(CheckBox.class);
		when(valueMatches.getValue()).thenReturn(true);

		return new Object[][] { { notChecked, false }, { valueMatches, true } };
	}

	/**
	 * Test to ensure the execute function sets the visibility of widgets
	 * correctly based on the parent widget.
	 * 
	 * @param checkBox
	 *            the parent widget who's state is set by Mockito.
	 * @param visible
	 *            what the visibility of the child widgets should be.
	 */
	@Test(dataProvider = "executeProvider")
	public void execute(CheckBox checkBox, boolean visible) {

		// create the rule
		rule = new ShowIfChecked(checkBox);

		// add some widgets.
		Widget widget1 = mock(Widget.class);
		Widget widget2 = mock(Widget.class);
		Widget widget3 = mock(Widget.class);
		rule.addChildWidget(widget1);
		rule.addChildWidget(widget2);
		rule.addChildWidget(widget3);

		// add some triggers.
		rule.addTrigger(true);

		// execute the rule and check the widgets were set to invisible.
		rule.execute();

		// verify
		verify(widget1).setVisible(visible);
		verify(widget2).setVisible(visible);
		verify(widget3).setVisible(visible);
		verifyNoMoreInteractions(widget1);
		verifyNoMoreInteractions(widget2);
		verifyNoMoreInteractions(widget3);
	}
}
