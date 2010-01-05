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

import com.google.gwt.junit.client.GWTTestCase;

/**
 * GWT Test cases for the {@link GwtAfListBox}
 * 
 * @author Jason Kong
 * 
 */
public class GwtTestGwtAfListBox extends GWTTestCase {

	/**
	 * The {@link GwtAfListBox} to test with
	 */
	private GwtAfListBox listBox;

	@Override
	protected void gwtSetUp() throws Exception {
		super.gwtSetUp();
		listBox = new GwtAfListBox();
	}

	/**
	 * Test to see that the items are added into the list box correctly
	 */
	public void testAddItems() {
		String[] items = new String[] { "Select a Number", "One", "Two",
				"Three", "Four", "Five" };
		listBox.addItems(items);

		// assert
		for (int i = 0; i < listBox.getItemCount(); i++) {
			assertEquals(listBox.getItemText(i), items[i]);
		}
	}

	/**
	 * Tests the {@code setValue} and {@code getValue} methods
	 */
	public void testSetAndGetValue() {
		String[] items = new String[] { "Select a Person", "Jason", "Arthur",
				"Serge" };
		listBox.addItems(items);

		// set the value to "Jason"
		listBox.setValue(items[1]);

		assertEquals(listBox.getSelectedIndex(), 1);

		assertEquals(listBox.getValue(), items[1]);

		// try setting again (Arthur)
		listBox.setValue(items[2]);

		assertEquals(listBox.getSelectedIndex(), 2);

		assertEquals(listBox.getValue(), items[2]);

	}

	/**
	 * Tests to see that the first index of the {@link GwtAfListBox} returns a
	 * null
	 */
	public void testFirstIndexIsNull() {
		String[] items = new String[] { "Select a colour", "Red", "Green",
				"Blue" };
		listBox.addItems(items);

		listBox.setSelectedIndex(0);

		assertNull(listBox.getValue());
	}

	/**
	 * Tests that if the listbox is left unselected, that a null is returned on
	 * getValue
	 */
	public void testUnselectedReturnsNull() {
		String[] items = new String[] { "Select a size", "Small", "Medium",
				"Large" };
		listBox.addItems(items);

		assertNull(listBox.getValue());
	}

	/**
	 * Tests the {@code setText} and {@code getText} methods
	 */
	public void testSetAndGetText() {
		String[] textValues = new String[] { "Select a Month", "January",
				"February", "March", "April" };

		String[] values = new String[] { null, "01", "02", "03", "04" };

		assertEquals(values.length, textValues.length);

		// set the text values
		for (int i = 0; i < textValues.length; i++) {
			listBox.addItem(textValues[i]);
			listBox.setValue(i, values[i]);
		}

		// set text

		listBox.setText(textValues[3]);

		assertEquals(listBox.getText(), textValues[3]);
		assertEquals(listBox.getValue(), values[3]);

		// one more time

		listBox.setText(textValues[2]);
		assertEquals(listBox.getText(), textValues[2]);
		assertEquals(listBox.getValue(), values[2]);

	}

	@Override
	public String getModuleName() {
		return "org.gwtaf.GwtAfWidgetsTesting";
	}
}
