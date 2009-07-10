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
package org.gwtaf.widgets.expanding;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.google.gwt.junit.GWTMockUtilities;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Provider;

/**
 * Test the {@link ExpandableFlexTable} class.
 * 
 * @author Arthur Kalmenson
 */
public class ExpandableFlexTableTest {

	/**
	 * A mock of the main panel of the {@link ExpandableFlexTable}.
	 */
	@Mock
	private FlexTable flexTableMock;

	/**
	 * A provider of widgets that the flextable will use.
	 */
	@Mock
	private Provider<Widget> widgetProviderMock;

	/**
	 * A mock of a widget.
	 */
	@Mock
	private Widget widgetMock;

	/**
	 * Mock of the add button.
	 */
	@Mock
	private Label addLabelMock;

	/**
	 * Mock of remove button provider.
	 */
	@Mock
	private Provider<RemoveButton> removeButtonProviderMock;

	/**
	 * Mock 
	 */
	@Mock
	private RemoveButton removeButtonMock;

	/**
	 * The {@link ExpandableFlexTable} we're testing.
	 */
	private ExpandableFlexTable<Widget> expandableFlexTable;

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

		expandableFlexTable = new ExpandableFlexTable<Widget>(flexTableMock,
				widgetProviderMock, addLabelMock, removeButtonProviderMock);
	}

	/**
	 * Returns constructor arguments that are invalid.
	 * 
	 * @return constructor arguments that are invalid.
	 */
	@DataProvider(name = "constructorArgsProvider")
	public Object[][] constructorArgsProvider() {
		return new Object[][] { { null, null, null, null },
				{ flexTableMock, null, null, null },
				{ null, null, addLabelMock, null } };
	}

	/**
	 * Test to ensure an {@link IllegalArgumentException} is thrown when invalid
	 * arguments are given in the constructor.
	 * 
	 * @param flexTable
	 *            the main panel flex table.
	 * @param widgetProvider
	 *            the widget provider.
	 * @param label
	 *            the label for the add button.
	 * @param removeButtonProvider
	 *            a provider of {@link RemoveButton}s.
	 */
	@Test(expectedExceptions = IllegalArgumentException.class, dataProvider = "constructorArgsProvider")
	public void invalidConstructorArgs(FlexTable flexTable,
			Provider<Widget> widgetProvider, Label label,
			Provider<RemoveButton> removeButtonProvider) {

		// create a new object and expect an IllegalArgumentException.
		new ExpandableFlexTable<Widget>(flexTable, widgetProvider, label,
				removeButtonProvider);
	}

	/**
	 * Test how the widget sets itself up and arranges elements.
	 */
	@Test
	public void widgetSetup() {
		verify(flexTableMock).setWidget(0, 0, addLabelMock);
	}

	/**
	 * Try adding a new entry. We expect the FlexTable to have a new item added,
	 * a remove button added and the add label moved down.
	 */
	@Test
	public void addWidget() {

		// set up the mocks.
		when(widgetProviderMock.get()).thenReturn(widgetMock);
		when(removeButtonProviderMock.get()).thenReturn(removeButtonMock);
		when(flexTableMock.getRowCount()).thenReturn(1);

		// mock the remove button containing widget.
		Widget removeButtonWidget = mock(Widget.class);
		when(removeButtonMock.getContainingWidget()).thenReturn(
				removeButtonWidget);

		// expand the table.
		expandableFlexTable.add();

		// make sure the widgets were added as expected.
		verify(flexTableMock).setWidget(0, 0, widgetMock);
		verify(flexTableMock).setWidget(0, 1, removeButtonWidget);
		verify(flexTableMock).setWidget(1, 0, addLabelMock);
	}

	/**
	 * Try remove a row. We expect the call to be passed to the
	 * {@link FlexTable}.
	 */
	@Test
	public void removeWidget() {

		// remove a row.
		expandableFlexTable.remove(0);
		verify(flexTableMock).removeRow(0);
	}

	/**
	 * Remove with a null {@link RemoveButton}. We expect a
	 * {@link NullPointerException} to be thrown.
	 */
	@Test(expectedExceptions = NullPointerException.class)
	public void removeUsingNullRemoveButton() {
		when(flexTableMock.getRowCount()).thenReturn(2);
		expandableFlexTable.remove(null);
	}

	/**
	 * Remove a row by using the remove button.
	 */
	@Test
	public void removeUsingRemoveButton() {

		// set up the mocks.
		Widget removeButtonWidgetMock = mock(Widget.class);
		when(removeButtonMock.getContainingWidget()).thenReturn(
				removeButtonWidgetMock);
		when(flexTableMock.getRowCount()).thenReturn(1);
		when(flexTableMock.getWidget(0, 1)).thenReturn(removeButtonWidgetMock);

		// remove using the button.
		expandableFlexTable.remove(removeButtonMock);

		// ensure the correct row is removed.
		verify(flexTableMock).removeRow(0);
	}

	/**
	 * Test clearing the table. We're expecting the {@link FlexTable}'s clear
	 * method to be called and the add button to be re-added.
	 */
	@Test
	public void clearTable() {

		// clear and verify behaviour.
		expandableFlexTable.clear();

		verify(flexTableMock).clear();
		// verify(flexTableMock).setWidget(0, 0, addLabelMock);
	}

	/**
	 * Test getting the widgets from an empty {@link FlexTable}. We expect to
	 * get an empty list.
	 */
	@Test
	public void getWidgetsEmpty() {

		// set up the mocks.
		when(flexTableMock.getRowCount()).thenReturn(0);

		// expect an empty list.
		Assert.assertEquals(expandableFlexTable.getWidgets().size(), 0);
	}

	/**
	 * Test getting the widgets from a table that only has the add button. We
	 * expect to get an empty list.
	 */
	@Test
	public void getWidgetsOnlyAddButton() {

		// set up the mocks.
		when(flexTableMock.getRowCount()).thenReturn(1);
		when(flexTableMock.getWidget(0, 0)).thenReturn(addLabelMock);

		// expect an empty list.
		Assert.assertEquals(expandableFlexTable.getWidgets().size(), 0);
	}

	/**
	 * Test getting the widgets when there are a number of widgets in the
	 * expandable table.
	 */
	@Test
	public void getWidgets() {

		// make mocks of widgets.
		Widget widgetMock1 = mock(Widget.class);
		Widget widgetMock2 = mock(Widget.class);

		// set up the mocks.
		when(flexTableMock.getRowCount()).thenReturn(2);
		when(flexTableMock.getWidget(0, 0)).thenReturn(widgetMock1);
		when(flexTableMock.getWidget(1, 0)).thenReturn(widgetMock2);

		// get the resulting list and compare it.
		List<Widget> result = expandableFlexTable.getWidgets();

		Assert.assertEquals(result, Arrays.asList(widgetMock1, widgetMock2));
	}
}
