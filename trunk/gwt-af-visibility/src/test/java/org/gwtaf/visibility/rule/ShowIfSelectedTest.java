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
package org.gwtaf.visibility.rule;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.google.gwt.junit.GWTMockUtilities;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Widget;

/**
 * Test the {@link ShowIfSelected} class.
 * 
 * @author Arthur Kalmenson
 */
public class ShowIfSelectedTest {

	/**
	 * Mock of the list box.
	 */
	@Mock
	private ListBox listBoxMock;

	/**
	 * The {@link ShowIfSelected} rule we're testing.
	 */
	private ShowIfSelected rule;

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
		rule = new ShowIfSelected(listBoxMock);
	}

	/**
	 * Test to ensure the visibility is not changed on the parent or any child
	 * widgets
	 */
	@Test
	public void visibilityNotChanged() {

		// verify the ListBox's visibility never changed.
		verify(listBoxMock, never()).setVisible(true);
		verify(listBoxMock, never()).setVisible(false);

		// add a child widget and make sure it also doesn't have visibility
		// changed.
		Widget mockWidget = mock(Widget.class);
		rule.addChildWidget(mockWidget);
		verify(mockWidget, never()).setVisible(true);
		verify(mockWidget, never()).setVisible(false);
	}

	/**
	 * Returns mocks of {@link ListBox}es mocked with different behaviour to get
	 * visibility of the child widgets to change.
	 * 
	 * @return mocks of {@link ListBox}es.
	 */
	@DataProvider(name = "executeProvider")
	public Object[][] executeProvider() {

		// ListBox not selected
		ListBox notChecked = mock(ListBox.class);
		when(notChecked.getSelectedIndex()).thenReturn(-1);

		// ListBox selected but not the right value.
		ListBox valueNotMatch = mock(ListBox.class);
		when(valueNotMatch.getSelectedIndex()).thenReturn(4);
		when(valueNotMatch.getValue(4)).thenReturn("not equal value");

		// ListBox selected and value matches.
		ListBox valueMatches = mock(ListBox.class);
		when(valueMatches.getSelectedIndex()).thenReturn(2);
		when(valueMatches.getValue(2)).thenReturn("hello");

		return new Object[][] { { notChecked, false },
				{ valueNotMatch, false }, { valueMatches, true } };
	}

	/**
	 * Test to ensure the execute function sets the visibility of widgets
	 * correctly based on the parent widget.
	 * 
	 * @param listBox
	 *            the parent widget who's state is set by Mockito.
	 * @param visible
	 *            what the visibility of the child widgets should be.
	 */
	@Test(dataProvider = "executeProvider")
	public void execute(ListBox listBox, boolean visible) {

		// create the rule
		rule = new ShowIfSelected(listBox);

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
