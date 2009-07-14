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

import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;

import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.google.gwt.junit.GWTMockUtilities;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.inject.Provider;

/**
 * Unit test {@link RadioButtonGroup}.
 * 
 * @author Arthur Kalmenson
 */
public class RadioButtonGroupTest {

	/**
	 * A mock of the panel.
	 */
	@Mock
	private Panel panelMock;

	/**
	 * Mock of the {@link RadioButton} {@link Provider}.
	 */
	@Mock
	private Provider<RadioButton> radioButtonProviderMock;

	/**
	 * The {@link RadioButtonGroup} we're testing.
	 */
	private RadioButtonGroup radioButtonGroup;

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

		radioButtonGroup = new RadioButtonGroup(panelMock,
				radioButtonProviderMock);
	}

	/**
	 * Returns invalid constructor arguments for the {@link RadioButtonGroup}.
	 * 
	 * @return invalid constructor arguments for the {@link RadioButtonGroup}.
	 */
	@DataProvider(name = "invalidConstructorArgProvider")
	public Object[][] invalidConstructorArgProvider() {
		return new Object[][] { { null, null }, { panelMock, null },
				{ null, radioButtonProviderMock } };
	}

	@Test(dataProvider = "invalidConstructorArgProvider", expectedExceptions = IllegalArgumentException.class)
	public void invalidConstructorArgs(Panel panel,
			Provider<RadioButton> radioButtonProvider) {
		radioButtonGroup = new RadioButtonGroup(panel, radioButtonProvider);
	}

	/**
	 * Test create a {@link MockAbstractRadioCollection} with empty choices. We
	 * expect no {@link RadioButton}s to be created and the collection to be
	 * empty.
	 */
	@Test
	public void emptyChoices() {
		Assert.assertEquals(radioButtonGroup.size(), 0);
	}

	/**
	 * Test creating a single {@link RadioButton} in a collection.
	 */
	@Test
	public void singleChoice() {

		// set up the mocks.
		RadioButton radioMock = mock(RadioButton.class);
		when(radioButtonProviderMock.get()).thenReturn(radioMock);

		// create the group.
		radioButtonGroup.setChoices(new String[] { "blah" });

		// check everything was set correctly.
		verify(panelMock).add(radioMock);
		verify(radioMock).setText("blah");

		// make sure we have one in our array.
		Assert.assertEquals(radioButtonGroup.size(), 1);
		Assert.assertEquals(radioButtonGroup.getRadio("blah"), radioMock);
	}

	/**
	 * Test creating multiple {@link RadioButton}s in a collection.
	 */
	@Test
	public void multipleChoices() {

		// set up the mocks.
		RadioButton radioMock1 = mock(RadioButton.class);
		RadioButton radioMock2 = mock(RadioButton.class);
		RadioButton radioMock3 = mock(RadioButton.class);
		when(radioButtonProviderMock.get()).thenReturn(radioMock1, radioMock2,
				radioMock3);

		// create the group
		radioButtonGroup.setChoices(new String[] { "hi", "hello", "test" });

		// check everything was set correctly.
		verify(panelMock).add(radioMock1);
		verify(panelMock).add(radioMock2);
		verify(panelMock).add(radioMock3);
		verify(radioMock1).setText("hi");
		verify(radioMock2).setText("hello");
		verify(radioMock3).setText("test");

		// make sure we have one in our array.
		Assert.assertEquals(radioButtonGroup.size(), 3);
	}

	/**
	 * Check the {@link RadioButtonGroup#isChecked()} and
	 * {@link RadioButtonGroup#getValue()} behaviour when the group is empty. We
	 * expect false for checked and null for the value.
	 */
	@Test
	public void isCheckedValueEmpty() {

		Assert.assertFalse(radioButtonGroup.isChecked());
		Assert.assertNull(radioButtonGroup.getValue());
	}

	/**
	 * Test the behaviour when we check if the {@link RadioButtonGroup} is
	 * checked with one checkbox checked.
	 */
	@Test
	public void isCheckedOneGetValue() {

		// set up the mocks.
		RadioButton radioMock1 = mock(RadioButton.class);
		RadioButton radioMock2 = mock(RadioButton.class);
		when(radioButtonProviderMock.get()).thenReturn(radioMock1, radioMock2);
		when(radioMock1.getValue()).thenReturn(false);
		when(radioMock2.getValue()).thenReturn(true);

		// create the group.
		radioButtonGroup.setChoices(new String[] { "yes", "no" });

		Assert.assertTrue(radioButtonGroup.isChecked());
		Assert.assertEquals(radioButtonGroup.getValue(), "no");
	}

	/**
	 * Test setting a value on an empty {@link RadioButtonGroup}. We expect
	 * nothing to happen and isChecked to stay false.
	 */
	@Test
	public void setValueEmpty() {
		radioButtonGroup.setValue("test");
		Assert.assertFalse(radioButtonGroup.isChecked());
	}

	/**
	 * Test setting a value that doesn't exist. We expect none of the checked
	 * radio buttons to be called.
	 */
	@Test
	public void setNonExistingValue() {

		// set the value.
		List<RadioButton> mocks = populate();
		radioButtonGroup.setValue("something strange");

		// check none of the mocks were called.
		for (RadioButton radio : mocks) {
			verify(radio, never()).setValue(true);
		}
	}

	/**
	 * Test setting the value. We expect the {@link RadioButton} with that value
	 * to be set to true.
	 */
	@Test
	public void setValueOnce() {

		// set the value.
		List<RadioButton> mocks = populate();
		radioButtonGroup.setValue("test");

		// we know it's the third one, check it to ensure it was checked.
		verify(mocks.get(2)).setValue(true);
	}

	/**
	 * Test setting the value multiple times. We expect the previous set
	 * {@link RadioButton} to be set to false and the new value to be set to
	 * true. When we set a value that doesn't exist, we don't expect anything
	 * else to happen.
	 */
	@Test
	public void setValueMultiple() {

		// set the first value.
		List<RadioButton> mocks = populate();
		InOrder inOrder = inOrder(mocks.get(2), mocks.get(0));
		
		radioButtonGroup.setValue("test");
		inOrder.verify(mocks.get(2), times(1)).setValue(true);
		when(mocks.get(2).getValue()).thenReturn(true);

		// set the second time.
		radioButtonGroup.setValue("hi");
		inOrder.verify(mocks.get(2), times(1)).setValue(false);
		inOrder.verify(mocks.get(0), times(1)).setValue(true);
		
		// try to set a value that doesn't exist.
		radioButtonGroup.setValue("something that doesn't exist");
		verify(mocks.get(0), never()).setValue(false);
	}

	/**
	 * Populate the {@link RadioButtonGroup} and returns a list of
	 * {@link RadioButton} mocks used.
	 * 
	 * @return list of {@link RadioButton} mocks.
	 */
	private List<RadioButton> populate() {

		// set up the mocks.
		RadioButton radioMock1 = mock(RadioButton.class);
		RadioButton radioMock2 = mock(RadioButton.class);
		RadioButton radioMock3 = mock(RadioButton.class);

		// add them to the RadioButton provider.
		when(radioButtonProviderMock.get()).thenReturn(radioMock1, radioMock2,
				radioMock3);

		// create the group
		radioButtonGroup.setChoices(new String[] { "hi", "hello", "test" });

		return Arrays.asList(radioMock1, radioMock2, radioMock3);
	}
}
