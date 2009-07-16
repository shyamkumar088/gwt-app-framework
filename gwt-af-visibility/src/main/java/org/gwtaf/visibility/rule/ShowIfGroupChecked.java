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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
public class ShowIfGroupChecked implements
		VisibilityRule<RadioButtonGroup, Widget, String> {

	/**
	 * The parent concrete implementation of {@link RadioButtonGroup}.
	 */
	private RadioButtonGroup parent;

	/**
	 * The child widget.
	 */
	private List<Widget> children = new ArrayList<Widget>();

	/**
	 * The list of triggers that would show the child widget.
	 */
	private Set<String> triggers = new HashSet<String>();

	/**
	 * Creates a new <code>ShowIfGroupChecked</code> with the given parent.
	 * 
	 * @param radioButtonGroup
	 *            the parent {@link RadioButtonGroup}.
	 */
	public ShowIfGroupChecked(RadioButtonGroup radioButtonGroup) {

		if (radioButtonGroup == null) {
			throw new IllegalArgumentException("All constructor arguments to "
					+ getClass().getName() + " must be instantiated.");
		}

		parent = radioButtonGroup;
		parent.addClickHandler(new RadioClickedListener());
	}

	public void execute() {

		// if we found a valid checked RadioButton, we show the child widget,
		// otherwise we hide it.
		if (parent.isChecked() && triggers.contains(parent.getValue())) {
			setChildrenVisibility(true);
		} else {
			setChildrenVisibility(false);
		}
	}

	public RadioButtonGroup getParentWidget() {
		return parent;
	}

	public void addChildWidget(Widget widget) {
		if (widget == null) {
			throw new IllegalArgumentException(getClass().getName()
					+ ": Child widget cannot be null.");
		}
		children.add(widget);
	}

	public void removeChildWidget(Widget child) {
		children.remove(child);
	}

	public List<Widget> getChildWidgets() {
		return children;
	}

	public void addTrigger(String value) {
		if (value == null) {
			throw new IllegalArgumentException(getClass().getName()
					+ ": Trigger cannot be null.");
		}
		triggers.add(value);
	}

	public Set<String> getTriggers() {
		return triggers;
	}

	public class RadioClickedListener implements ClickHandler {
		public void onClick(ClickEvent radioGroup) {
			execute();
		}
	}

	private void setChildrenVisibility(boolean visible) {
		for (Widget child : children) {
			child.setVisible(visible);
		}
	}
}
