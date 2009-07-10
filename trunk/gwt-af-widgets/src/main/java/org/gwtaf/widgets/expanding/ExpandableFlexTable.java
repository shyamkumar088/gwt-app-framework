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
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.i18n.client.Constants;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Widget;
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
 *            the type of widget this <code>AbstractDynamicFlexTable</code> will
 *            be replicating.
 */
public class ExpandableFlexTable<T extends Widget> extends Composite implements
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
	private HasClickHandlers addButton;

	/**
	 * A {@link Provider} of the type we're making copies of.
	 */
	private Provider<T> typeProvider;

	/**
	 * Creates a new <code>ExpandableFlexTable</code> with the given text to
	 * display for its label.
	 * 
	 * @param label
	 *            the label that describes this table.
	 */
	public ExpandableFlexTable(FlexTable flexTable,
			Provider<T> typeProvider,
			HasClickHandlers addButton,
			Provider<RemoveButton> removeButtonProvider) {

		// check the parameters.
		if (flexTable == null || typeProvider == null || addButton == null
				|| removeButtonProvider == null) {
			throw new IllegalArgumentException(getClass().getName()
					+ ": null constructor arguments aren't accepted.");
		}

		// save the given variables.
		mainPanel = flexTable;
		this.typeProvider = typeProvider;
		this.addButton = addButton;
		this.removeButtonProvider = removeButtonProvider;

		// set up the add button.
		addButton.addClickHandler(new AddButtonHandler());
		mainPanel.setWidget(0, 0, (Widget) addButton);
		this.addButton = addButton;

		// init the widget.
		initWidget(mainPanel);
	}

	public void add() {

		// add to the row right before the add button and the first column.
		int rowNum = mainPanel.getRowCount() - 1;
		int colNum = 0;

		// get the widget to add from its provider and add it.
		T widgetToAdd = typeProvider.get();
		mainPanel.setWidget(rowNum, colNum, widgetToAdd);
		colNum++;

		// add a remove button to the new row.
		RemoveButton removeButton = removeButtonProvider.get();
		removeButton.addClickHandler(new RemoveButtonListener());
		mainPanel.setWidget(rowNum, colNum, removeButton.getContainingWidget());

		// put the add another button at the bottom.
		rowNum++;
		mainPanel.setWidget(rowNum, 0, (Widget) addButton);
	}

	public void remove(int i) {
		mainPanel.removeRow(i);
	}

	/**
	 * Removes the row that contains the given {@link RemoveButton}.
	 * 
	 * @param removeButton
	 *            the row to remove with the given button.
	 */
	protected void remove(RemoveButton removeButton) {

		for (int row = 0; row < mainPanel.getRowCount(); row++) {
			if (mainPanel.getWidget(row, 1) == removeButton
					.getContainingWidget()) {
				remove(row);
			}
		}
	}

	public void clear() {
		mainPanel.clear();
		mainPanel.setWidget(0, 0, (Widget) addButton);
	}

	@SuppressWarnings("unchecked")
	public List<T> getWidgets() {
		List<T> result = new ArrayList<T>();

		for (int row = 0; row < mainPanel.getRowCount(); row++) {

			// don't add the add button.
			Widget widget = mainPanel.getWidget(row, 0);
			if (widget != addButton) {

				result.add((T) mainPanel.getWidget(row, 0));
			}
		}

		return result;
	}

	public Widget getContainingWidget() {
		return this;
	}

	/**
	 * The {@link ClickHandler} added to the add button for this
	 * {@link ExpandableTable}.
	 * 
	 * @author Arthur Kalmenson
	 */
	protected class AddButtonHandler implements ClickHandler {
		public void onClick(ClickEvent arg0) {
			add();
		}
	}

	/**
	 * The {@link ClickHandler} added to the remove buttons in this
	 * {@link ExpandableTable}.
	 * 
	 * @author Arthur Kalmenson
	 */
	protected class RemoveButtonListener implements ClickHandler {
		public void onClick(ClickEvent arg0) {
			Object source = arg0.getSource();
			if (source instanceof RemoveButton) {
				remove((RemoveButton) source);
			}
		}
	}
}
