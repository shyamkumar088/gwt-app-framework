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

import org.gwtaf.visibility.VisibilityRule;
import org.gwtaf.widgets.generic.RadioButtonGroup;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Widget;

/**
 * A concrete implementation of {@link VisibilityRule} for
 * {@link RadioButtonGroup}s.
 * 
 * @author Arthur Kalmenson
 */
public class ShowIfGroupChecked extends
		AbstractVisibilityRule<RadioButtonGroup, Widget, String> {

	/**
	 * Creates a new <code>ShowIfGroupChecked</code> with the given parent.
	 * 
	 * @param radioButtonGroup
	 *            the parent {@link RadioButtonGroup}.
	 */
	public ShowIfGroupChecked(RadioButtonGroup radioButtonGroup) {

		super(radioButtonGroup);
		radioButtonGroup.addClickHandler(new RadioClickedListener());
	}

	public void execute() {

		// if we found a valid checked RadioButton, we show the child widget,
		// otherwise we hide it.
		if (getParentWidget().isChecked()
				&& getTriggers().contains(getParentWidget().getValue())) {
			setChildrenVisibility(true);
		} else {
			setChildrenVisibility(false);
		}
	}

	public class RadioClickedListener implements ClickHandler {
		public void onClick(ClickEvent radioGroup) {
			execute();
		}
	}
}
