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

import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Widget;

/**
 * A {@link VisibilityRule} for {@link CheckBox}es.
 * 
 * @author Arthur Kalmenson
 */
public class ShowIfChecked extends
		AbstractVisibilityRule<CheckBox, Widget, Boolean> {

	/**
	 * Create a new <code>ShowIfChecked</code> with the given CheckBox.
	 * 
	 * @param parent
	 *            the parent CheckBox.
	 */
	public ShowIfChecked(CheckBox parent) {
		super(parent);

		parent.addValueChangeHandler(new ShowIfCheckedHandler());
	}

	public void execute() {
		if (getTriggers().contains(getParentWidget().getValue())) {
			setChildrenVisibility(true);
		} else {
			setChildrenVisibility(false);
		}
	}

	/**
	 * {@link ValueChangeHandler} that calls {@link ShowIfChecked#execute()}
	 * whenever the value changes.
	 * 
	 * @author Arthur Kalmenson
	 */
	private class ShowIfCheckedHandler implements ValueChangeHandler<Boolean> {
		public void onValueChange(ValueChangeEvent<Boolean> event) {
			execute();
		}
	}
}
