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

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Widget;

/**
 * The <code>ShowIfSelected</code> {@link VisibilityRule} is a rule that allows
 * a child widget to be shown when a particular {@link ListBox} widget element
 * is selected.
 * 
 * @author Arthur Kalmenson
 */
public class ShowIfSelected implements VisibilityRule<ListBox, Widget, String> {

	/**
	 * The parent {@link ListBox} widget.
	 */
	private ListBox parent;

	/**
	 * The child widgets.
	 */
	private List<Widget> children = new ArrayList<Widget>();

	/**
	 * The set of triggers that would make the child widget appear.
	 */
	private Set<String> triggers = new HashSet<String>();

	/**
	 * Creates a new <code>ShowIfSelected</code> with the given parent
	 * {@link ListBox}.
	 * 
	 * @param parent
	 *            the parent {@link ListBox}.
	 */
	public ShowIfSelected(ListBox parent) {
		if (parent == null) {
			throw new IllegalArgumentException(getClass().getName()
					+ ": ListBox must be instantiated.");
		}
		this.parent = parent;

		// add the change listener to the parent.
		parent.addChangeHandler(new ListBoxSelectListener());
	}

	public void execute() {
		int selectedIndex = parent.getSelectedIndex();
		if (selectedIndex != -1
				&& triggers.contains(parent.getValue(selectedIndex))) {
			setChildrenVisibility(true);
		} else {
			setChildrenVisibility(false);
		}
	}

	public ListBox getParentWidget() {
		return parent;
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

	public void addChildWidget(Widget child) {
		if (child == null) {
			throw new IllegalArgumentException(getClass().getName()
					+ ": child widget cannot be null.");
		}
		children.add(child);
	}

	public List<Widget> getChildWidgets() {
		return children;
	}

	public void removeChildWidget(Widget child) {
		children.remove(child);
	}

	private void setChildrenVisibility(boolean visible) {
		for (Widget child : children) {
			child.setVisible(visible);
		}
	}

	/**
	 * The {@link ChangeHandler} that listens for items being selected in the
	 * list box and executes the rule when they are.
	 * 
	 * @author Arthur Kalmenson
	 */
	private class ListBoxSelectListener implements ChangeHandler {

		public void onChange(ChangeEvent arg0) {
			execute();
		}
	}
}
