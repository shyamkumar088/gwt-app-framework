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

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.i18n.client.Constants;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.inject.Provider;

/**
 * A concrete implementation of the {@link ExpandableTable} interface using a
 * {@link FlexTable}. <code>AbstractDynamicFlexTable</code> implements all the
 * common methods that {@link ExpandableTable}s would need. All a subclass needs
 * to do is implement the {@link ExpandableTable#add()} method which is easy
 * with the {@link ExpandableFlexTable#add(Widget...)} method.
 * 
 * @author Arthur Kalmenson
 * 
 * @param <T>
 *            the type of widget this <code>ExpandableFlexTable</code> will be
 *            replicating.
 */
public class ExpandableFlexTable<T> extends Composite implements
		ExpandableTable<T> {

	/**
	 * The externised String constants for this table.
	 * 
	 * @author Arthur Kalmenson
	 */
	public interface ExpandableFlexTableConstants extends Constants {

		@DefaultStringValue("add")
		String addLabel();

		@DefaultStringValue("remove")
		String removeLabel();
	}

	/**
	 * The main panel that will display the dynamic table.
	 */
	private FlexTable mainPanel;

	/**
	 * A {@link Provider} of {@link RemoveButton}s.
	 */
	private Provider<RemoveButton> removeButtonProvider;

	/**
	 * The add button to add new items to the expandable table.
	 */
	private AddButton addButton;

	/**
	 * The list of remove buttons.
	 */
	private List<RemoveButton> removeButtons = new ArrayList<RemoveButton>();

	/**
	 * Creates a new <code>ExpandableFlexTable</code> with the given text to
	 * display for its label.
	 * 
	 * @param label
	 *            the label that describes this table.
	 */
	@Inject
	public ExpandableFlexTable(FlexTable flexTable, AddButton addButton,
			Provider<RemoveButton> removeButtonProvider) {

		// check the parameters.
		assert flexTable != null && addButton != null
				&& removeButtonProvider != null : getClass().getName()
				+ ": null constructor arguments aren't accepted.";

		// save the given variables.
		mainPanel = flexTable;
		this.addButton = addButton;
		this.removeButtonProvider = removeButtonProvider;

		// init the widget.
		initWidget(mainPanel);
	}

	public void render() {

		// set up the add button.
		mainPanel.setWidget(0, 0, addButton.getContainingWidget());
	}

	public void add(T widget) {

		// add to the row right before the add button and the first column.
		int rowNum = mainPanel.getRowCount() - 1;
		int colNum = 0;

		// set the given widget.
		mainPanel.setWidget(rowNum, colNum, (Widget) widget);
		colNum++;

		// add a remove button to the new row.
		RemoveButton removeButton = removeButtonProvider.get();
		mainPanel.setWidget(rowNum, colNum, removeButton.getContainingWidget());

		// put the add another button at the bottom.
		rowNum++;
		mainPanel.setWidget(rowNum, 0, addButton.getContainingWidget());

		// add the remove button for return later.
		removeButtons.add(removeButton);
	}

	@SuppressWarnings("unchecked")
	public T remove(RemoveButton removeButton) {

		// the removed element.
		T removed = null;

		// find the location of the remove button given.
		for (int row = 0; row < mainPanel.getRowCount(); row++) {
			if (mainPanel.getWidget(row, 1) == removeButton
					.getContainingWidget()) {

				// remove the button but save the item that was removed.
				removed = (T) mainPanel.getWidget(row, 0);
				mainPanel.removeRow(row);

				// remove the button from our list.
				removeButtons.remove(removeButton);
				break;
			}
		}

		return removed;
	}

	public void clear() {
		mainPanel.clear();
		mainPanel.setWidget(0, 0, addButton.getContainingWidget());
	}

	@SuppressWarnings("unchecked")
	public List<T> getWidgets() {
		List<T> result = new ArrayList<T>();

		for (int row = 0; row < mainPanel.getRowCount(); row++) {

			// don't add the add button.
			Widget widget = mainPanel.getWidget(row, 0);
			if (widget != addButton.getContainingWidget()) {

				result.add((T) mainPanel.getWidget(row, 0));
			}
		}

		return result;
	}

	public AddButton getAddButton() {
		return addButton;
	}

	public List<RemoveButton> getRemoveButtons() {
		return removeButtons;
	}

	public Widget getContainingWidget() {
		return this;
	}

	public HandlerRegistration addClickHandler(ClickHandler handler) {
		return addDomHandler(handler, ClickEvent.getType());
	}
}