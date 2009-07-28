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
import com.google.gwt.user.client.ui.Widget;

/**
 * Test the {@link ShowIfGroupChecked} class.
 * 
 * @author Arthur Kalmenson
 */
public class ShowIfGroupCheckedTest {

	/**
	 * A mock of the {@link RadioButtonGroup}.
	 */
	@Mock
	private RadioButtonGroup groupMock;

	/**
	 * The rule we're testing.
	 */
	private ShowIfGroupChecked rule;

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
		rule = new ShowIfGroupChecked(groupMock);
	}

	/**
	 * Test to ensure the visibility is not changed on the parent or any child
	 * widgets
	 */
	@Test
	public void visibilityNotChanged() {

		// verify the group's visibility never changed.
		verify(groupMock, never()).setVisible(true);
		verify(groupMock, never()).setVisible(false);

		// add a child widget and make sure it also doesn't have visibility
		// changed.
		Widget mockWidget = mock(Widget.class);
		rule.addChildWidget(mockWidget);
		verify(mockWidget, never()).setVisible(true);
		verify(mockWidget, never()).setVisible(false);
	}

	/**
	 * Returns mocks of {@link RadioButtonGroup}s mocked with different values
	 * to make visibility set different.
	 * 
	 * @return mocks of {@link RadioButtonGroup}s.
	 */
	@DataProvider(name = "executeProvider")
	public Object[][] executeProvider() {

		// radio not checked.
		RadioButtonGroup notChecked = mock(RadioButtonGroup.class);
		when(notChecked.isChecked()).thenReturn(false);

		// radio checked but the value doesn't match.
		RadioButtonGroup valueNotMatch = mock(RadioButtonGroup.class);
		when(valueNotMatch.isChecked()).thenReturn(true);
		when(valueNotMatch.getValue()).thenReturn("some other value");

		// radio checked and value matches.
		RadioButtonGroup valueMatches = mock(RadioButtonGroup.class);
		when(valueMatches.isChecked()).thenReturn(true);
		when(valueMatches.getValue()).thenReturn("hello");

		return new Object[][] { { notChecked, false },
				{ valueNotMatch, false }, { valueMatches, true } };
	}

	/**
	 * Test to ensure the execute function sets the visibility of widgets
	 * correctly based on the parent widget.
	 * 
	 * @param group
	 *            the parent widget who's state is set by Mockito.
	 * @param visible
	 *            what the visibility of the child widgets should be.
	 */
	@Test(dataProvider = "executeProvider")
	public void execute(RadioButtonGroup group, boolean visible) {

		// create the rule
		rule = new ShowIfGroupChecked(group);

		// add some widgets.
		Widget widget1 = mock(Widget.class);
		Widget widget2 = mock(Widget.class);
		rule.addChildWidget(widget1);
		rule.addChildWidget(widget2);

		// add some triggers.
		rule.addTrigger("test");
		rule.addTrigger("hello");

		// execute the rule and check the widgets were set to invisible.
		rule.execute();

		// verify
		verify(widget1).setVisible(visible);
		verify(widget2).setVisible(visible);
		verifyNoMoreInteractions(widget1);
		verifyNoMoreInteractions(widget2);
	}
}
