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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.gwtaf.widgets.search.model.DynamicSearchResults;
import org.gwtaf.widgets.search.model.SearchResult;

import com.google.gwt.gen2.table.client.FixedWidthFlexTable;
import com.google.gwt.gen2.table.client.FixedWidthGrid;
import com.google.gwt.gen2.table.client.ScrollTable;
import com.google.gwt.gen2.table.client.AbstractScrollTable.SortPolicy;
import com.google.gwt.gen2.table.event.client.ColumnSortEvent;
import com.google.gwt.gen2.table.event.client.ColumnSortHandler;
import com.google.gwt.gen2.table.event.client.RowSelectionHandler;
import com.google.gwt.gen2.table.event.client.TableEvent.Row;
import com.google.gwt.gen2.table.override.client.HTMLTable.RowFormatter;
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

	/**
	 * Main panel to hold the scrolltable.
	 */
	private FlexTable mainPanel;
	/**
	 * The handler listening for row selection events.
	 */
	private RowSelectionHandler rowSelectionHandler;

	/**
	 * The column index where the unique identifier
	 */
	private Integer uniqueIdentifierIndex;

	/**
	 * Whether or not the unique identifier column should be hidden
	 */
	private boolean hideUniqueIdentifier = false;

	/**
	 * The index of the column last sorted
	 */
	private Integer lastSortedColumn;

	/**
	 * The direction of the column last sorted
	 */
	private boolean lastSortDirection;

	/**
	 * Should the user wish that the column with the unique record identifier
	 * not be visible, the unique ID will be stored in this map. Otherwise the
	 * unique ID can be picked up through the proper identification column on
	 * the selected row.
	 */
	private Map<Integer, String> rowToUniqueId = new HashMap<Integer, String>();

	/**
	 * Flag to mark all odd rows with a specific css style.
	 */
	private boolean markOdd;

	/**
	 * Constructs a new {@Code SearchResultsTable}
	 * 
	 * @param scrolltable
	 *            the injected {@link ScrollTable}
	 * @param dataGrid
	 *            the injected {@link FixedWidthGrid}
	 * @param headerTable
	 *            the injected {@link FixedWidthFlexTable}
	 * @param headings
	 *            the injected {@link SearchResultHeadings}
	 */
	@Inject
	public SearchResultScrollTable(FlexTable flexTable,
			ScrollTable scrolltable, FixedWidthGrid dataGrid,
			FixedWidthFlexTable headerTable) {

		assert scrolltable != null && dataGrid != null && headerTable != null;

		this.mainPanel = flexTable;

		// the dataGrid and headerTable are passed in but mainTable will already
		// be constructed using them in the Provider. We need these references
		// because ScrollTable does not offer setter methods
		// if nothing is provided, go with a blank setup.
		if (headerTable.equals(new FixedWidthFlexTable())) {
			FixedWidthGrid grid = new FixedWidthGrid(1, 0);
			grid.setSelectionEnabled(true);
			scrolltable = new ScrollTable(grid, headerTable);
		}

		this.dataGrid = dataGrid;
		this.headerTable = headerTable;
		this.scrollTable = scrolltable;

		initWidget(this.mainPanel);
	}

	public void setDynamicValue(DynamicSearchResults searchResults) {

		int colCount = searchResults.getHeadings().size();

		// replace the header table
		headerTable = new FixedWidthFlexTable();
		int adjust = 0;
		for (int i = 0; i < colCount; i++) {

			// skip adding a header for the unique ID column.
			if (hideUniqueIdentifier && i == uniqueIdentifierIndex) {
				adjust = 1;
				continue;
			}

			headerTable.setHTML(0, i - adjust, searchResults.getHeadings().get(
					i));
		}

		// set the data
		setValue(searchResults.getResults());

		// mark the odd rows
		if (markOdd) {
			markOdd();
		}
	}

	/**
	 * Marks all the odd rows with a specific CSS name.
	 */
	private void markOdd() {
		RowFormatter formatter = dataGrid.getRowFormatter();
		for (int i = 0; i < dataGrid.getRowCount(); i++) {
			if (i % 2 == 1) {
				formatter.addStyleName(i, "gwtaf-oddRow");
			}
			else {
				formatter.removeStyleName(i, "gwtaf-oddRow");
			}
		}
	}

	public void setValue(List<SearchResult> results) {

		// nothing to do if there are no results.
		if (results == null || results.size() == 0) {
			return;
		}

		// figure out how many columns we'll need
		int neededColumns = results.get(0).getDataValues().length;
		neededColumns = hideUniqueIdentifier ? Math.max(0, neededColumns - 1)
				: neededColumns;

		// make a new data grid
		this.dataGrid = createDataGrid(results.size(), neededColumns);
		dataGrid.setCellSpacing(0);

		// pass on the handler
		dataGrid.addRowSelectionHandler(rowSelectionHandler);

		// add handler for sorting
		dataGrid.addColumnSortHandler(new ColumnSortHandler() {

			public void onColumnSorted(ColumnSortEvent event) {
				lastSortedColumn = event.getColumnSortList().getPrimaryColumn();
				lastSortDirection = event.getColumnSortList()
						.isPrimaryAscending();
				markOdd();
			}
		});

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
		for (int i = 0; i < headerTable.getColumnCount(); i++) {
			newHeaderTable.setHTML(0, i, headerTable.getHTML(0, i));
		}

		// replace the scrolltable
		ScrollTable newTable = new ScrollTable(dataGrid, newHeaderTable);
		newTable.setSortPolicy(SortPolicy.SINGLE_CELL);
		newTable.setResizePolicy(ScrollTable.ResizePolicy.FILL_WIDTH);
		this.scrollTable = newTable;

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

		int adjust = 0;
		for (int i = 0; i < result.getDataValues().length; i++) {

			// skip adding a header for the unique ID column.
			if (hideUniqueIdentifier && i == uniqueIdentifierIndex) {
				rowToUniqueId.put(row, result.getDataValues()[i]);
				adjust = 1;
				continue;
			}
			dataGrid.setHTML(row, i - adjust, result.getDataValues()[i]);
		}
	}

	public void clear() {
		dataGrid.clearAll();
		rowToUniqueId.clear();
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
		SearchResult result = new SearchResult(headerTable.getColumnCount());
		for (int i = 0; i < dataGrid.getColumnCount(); i++) {
			result.setData(i, dataGrid.getHTML(row, i));
		}
		return result;
	}

	public void render() {
		mainPanel.setWidget(0, 0, scrollTable);
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

		if (hideUniqueIdentifier) {
			return rowToUniqueId.get(row.getRowIndex());
		}

		// otherwise just do a regular cell lookup
		return dataGrid.getHTML(row.getRowIndex(), uniqueIdentifierIndex);
	}

	public Integer getUniqueIdentifierIndex() {
		return uniqueIdentifierIndex;
	}

	public void setUniqueIdentifierIndex(Integer uniqueIdentifierIndex) {
		this.uniqueIdentifierIndex = uniqueIdentifierIndex;
	}

	public ScrollTable getScrollTable() {
		return scrollTable;
	}

	public void setHideUniqueIdentifier(boolean hideUniqueIdentifier) {
		this.hideUniqueIdentifier = hideUniqueIdentifier;
	}

	public boolean isHidingUniqueIdentifier() {
		return hideUniqueIdentifier;
	}

	public Integer getLastSortedColumn() {
		return lastSortedColumn;
	}

	public boolean getlastSortDirection() {
		return lastSortDirection;
	}

	public void markOdd(boolean markOdd) {
		this.markOdd = markOdd;
	}
}
