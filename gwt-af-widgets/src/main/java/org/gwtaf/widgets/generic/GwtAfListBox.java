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

import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.ListBox;

/**
 * A GWT-AF Specific implementation of {@link ListBox}. Assumes that the first
 * item in the listbox is a message such as "Select an item"
 * 
 * @author Jason Kong
 * 
 */
public class GwtAfListBox extends ListBox implements HasText, HasValue<String> {

	public GwtAfListBox() {
		super();
	}

	/**
	 * Creates a {@code GwtAfListBox} using the given array of items
	 * 
	 * @param items
	 *            the array of strings to add to the listbox
	 */
	public GwtAfListBox(String... items) {
		super();
		addItems(items);
	}

	/**
	 * Add the specified array of items to the listbox
	 * 
	 * @param items
	 *            the array of items to add
	 */
	public void addItems(String... items) {
		for (String item : items) {
			addItem(item);
		}
	}

	/**
	 * Adds the provided list of items assigning their specified values
	 * 
	 * @param text
	 *            the list of items to add
	 * @param values
	 *            the list of associated values
	 */
	public void addItems(String[] text, String[] values) {

		assert text.length == values.length : this.getClass().getName()
				+ " wrong array parameters. ";

		for (int i = 0; i < text.length; i++) {
			addItem(text[i], values[i]);
		}

	}

	public String getText() {
		int index = getSelectedIndex();

		// index 0 is the "select an option" text in all list boxes. we return
		// null. index -1 will also return a null
		if (index <= 0) {
			return null;
		}
		return getItemText(index);
	}

	/**
	 * For this particular method, we are actually "Selecting" by the given
	 * text. That is, the selected index becomes the index with the text that
	 * matches the specified parameter
	 */
	public void setText(String text) {
		// loop through and select the item with the matching text
		for (int i = 0; i < getItemCount(); i++) {
			if (getItemText(i).equals(text)) {
				setSelectedIndex(i);
				return;
			}
		}
	}

	public String getValue() {
		int index = getSelectedIndex();

		// index 0 is the "select an option" text in all list boxes. we return
		// null. index -1 will also return a null
		if (index <= 0) {
			return null;
		}
		return getValue(index);
	}

	public void setValue(String value) {
		for (int i = 0; i < getItemCount(); i++) {
			if (getValue(i).equals(value)) {
				setSelectedIndex(i);
				return;
			}
		}
	}

	public void setValue(String value, boolean arg1) {
		// not firing for now
		setValue(value);
	}

	public HandlerRegistration addValueChangeHandler(
			ValueChangeHandler<String> arg0) {

		return addHandler(arg0, ValueChangeEvent.getType());
	}

}
