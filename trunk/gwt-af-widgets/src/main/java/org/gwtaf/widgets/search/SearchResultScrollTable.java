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
package org.gwtaf.widgets.search;

import java.util.ArrayList;
import java.util.List;

import org.gwtaf.widgets.search.model.SearchResult;
import org.gwtaf.widgets.search.model.SearchResultHeadings;

import com.google.gwt.gen2.table.client.FixedWidthFlexTable;
import com.google.gwt.gen2.table.client.FixedWidthGrid;
import com.google.gwt.gen2.table.client.ScrollTable;
import com.google.gwt.gen2.table.client.AbstractScrollTable.SortPolicy;
import com.google.gwt.gen2.table.client.SelectionGrid.SelectionPolicy;
import com.google.gwt.gen2.table.event.client.RowSelectionHandler;
import com.google.gwt.gen2.table.event.client.TableEvent.Row;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

/**
 * A concrete implementation of {@link SearchResultTable}
 * 
 * @author Jason Kong
 * 
 */
public class SearchResultScrollTable extends Composite implements
		SearchResultTable<SearchResult> {

	/**
	 * The main {@link ScrollTable} for this widget
	 */
	private ScrollTable scrollTable;

	/**
	 * The {@link FixedWidthGrid} that holds the data
	 */
	private FixedWidthGrid dataGrid;

	/**
	 * The {@link FixedWidthFlexTable} that holds the headers
	 */
	private FixedWidthFlexTable headerTable;

	private SearchResultHeadings headings;

	/**
	 * Main panel to hold the scrolltable.
	 */
	private FlexTable mainPanel;

	/**
	 * The handler listening for row selection events.
	 */
	private RowSelectionHandler rowSelectionHandler;

	/**
	 * Constructs a new {@Code SearchResultsTable}
	 * 
	 * @param scrollTable
	 *            the injected {@link ScrollTable}
	 * @param dataGrid
	 *            the injected {@link FixedWidthGrid}
	 * @param headerTable
	 *            the injected {@link FixedWidthFlexTable}
	 * @param headings
	 *            the injected {@link SearchResultHeadings}
	 */
	@Inject
	public SearchResultScrollTable(FlexTable mainPanel,
			ScrollTable scrollTable, FixedWidthGrid dataGrid,
			FixedWidthFlexTable headerTable, SearchResultHeadings headings) {

		assert scrollTable != null && dataGrid != null && headerTable != null
				&& headings != null;

		this.mainPanel = mainPanel;
		this.scrollTable = scrollTable;

		// the dataGrid and headerTable are passed in but mainTable will already
		// be constructed using them in the Provider. We need these references
		// because ScrollTable does not offer setter methods

		this.dataGrid = dataGrid;
		this.headerTable = headerTable;
		this.headings = headings;

		initWidget(this.mainPanel);
	}

	public void setValue(List<SearchResult> results) {

		// make a new data grid
		this.dataGrid = createDataGrid(results.size(), headerTable
				.getColumnCount());
		dataGrid.setCellSpacing(0);

		// pass on the handler
		dataGrid.addRowSelectionHandler(rowSelectionHandler);

		int dataGridIndex = 0;

		// set the values into the list
		for (int i = 0; i < results.size(); i++) {

			// if not null
			if (results.get(i) != null) {
				setSingleResult(results.get(i), dataGridIndex);
				dataGridIndex++;
			}
		}

		/**
		 * Creating a new header table. Cannot re-use the old one due to
		 * scrolltable not supporting remove. (one parent widget constraint)
		 */
		FixedWidthFlexTable newHeaderTable = new FixedWidthFlexTable();
		for (int i = 0; i < headings.getHeadings().length; i++) {
			newHeaderTable.setHTML(0, i, headings.getHeadings()[i]);
		}

		// replace the scrolltable
		ScrollTable newTable = new ScrollTable(dataGrid, newHeaderTable);
		newTable.setSortPolicy(SortPolicy.SINGLE_CELL);
		newTable.setResizePolicy(ScrollTable.ResizePolicy.FILL_WIDTH);

		mainPanel.clear();
		mainPanel.setWidget(0, 0, newTable);
	}

	/**
	 * Sets a single result in the data grid at the given row
	 * 
	 * @param result
	 *            the {@link SearchResult} to set
	 * @param row
	 *            the row to set it to
	 */
	private void setSingleResult(SearchResult result, int row) {

		assert result.getHeadings() == headings;

		for (int i = 0; i < headerTable.getColumnCount(); i++) {
			dataGrid.setHTML(row, i, result.getDataValues()[i]);
		}
	}

	public void clear() {
		dataGrid.clearAll();
	}

	public Widget getContainingWidget() {
		return this;
	}

	/**
	 * Return the data table to display lists of search results. Its size will
	 * depend on how many results we're showing.
	 * 
	 * @param rowCount
	 *            the number of results to show.
	 * @param colCount
	 *            the number of columns we have.
	 * @return the data table to display lists of search results.
	 */
	private FixedWidthGrid createDataGrid(int rowCount, int colCount) {

		// Create a new table
		FixedWidthGrid dataTable = new FixedWidthGrid(rowCount, colCount);

		// Return the data table
		return dataTable;
	}

	public List<SearchResult> getResults() {
		List<SearchResult> results = new ArrayList<SearchResult>();
		for (int i = 0; i < dataGrid.getRowCount(); i++) {
			results.add(createSingleResultFromRow(i));
		}
		return results;
	}

	/**
	 * Creates a single {@link SearchResult} from a given row in the data grid
	 * 
	 * @param row
	 *            the row to create the {@link SearchResult} from
	 * @return the {@link SearchResult} created from the given row
	 */
	private SearchResult createSingleResultFromRow(int row) {
		SearchResult result = new SearchResult(headings);
		String[] headingStrings = headings.getHeadings();
		for (int i = 0; i < headingStrings.length; i++) {
			result.setData(headingStrings[i], dataGrid.getHTML(row, i));
		}
		return result;
	}

	public void render() {
		mainPanel.setWidget(0, 0, scrollTable);
	}

	public SearchResultHeadings getHeadings() {
		return headings;
	}

	public FixedWidthGrid getDataTable() {
		return dataGrid;
	}

	/**
	 * Sets the row selection handler to be passed to the grid.
	 * 
	 * @param handler
	 *            row selection handler to use.
	 */
	public void setRowSelectionHandler(RowSelectionHandler handler) {
		this.rowSelectionHandler = handler;
	}

	/**
	 * Returns the value of the identifier cell of the given row.
	 * 
	 * @param row
	 *            the row who's identifier cell to check
	 * @return the value of the identifier cell of the given row.
	 */
	public String valueAtIdentifierOfRow(Row row) {
		return dataGrid.getHTML(row.getRowIndex(), headings
				.getUniqueIdentifierIndex());
	}
}
