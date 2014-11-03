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

import org.gwtaf.widgets.search.model.DynamicSearchResults;
import org.gwtaf.widgets.search.model.SearchResult;

import com.google.gwt.gen2.table.client.FixedWidthFlexTable;
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
 * @author Sergey Vesselov
 * 
 */
public class SearchResultScrollTable extends Composite implements
		SearchResultTable<SearchResult> {

	/**
	 * The main {@link ScrollTable} for this widget
	 */
	private ScrollTable scrollTable;

	/**
	 * The {@link SearchDataGrid} that holds the data
	 */
	private SearchDataGrid dataGrid;

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
	 * The index of the column last sorted
	 */
	private Integer lastSortedColumn;

	/**
	 * The direction of the column last sorted
	 */
	private boolean lastSortDirection;

	private List<Integer> hiddenColumns = new ArrayList<Integer>();

	/**
	 * Flag to mark all odd rows with a specific css style.
	 */
	private boolean markOdd;

	private CellClickedHandler clickHandler;

	private DynamicSearchResults fullResults;

	/**
	 * Constructs a new {@Code SearchResultsTable}
	 * 
	 * @param scrolltable
	 *            the injected {@link ScrollTable}
	 * @param dataGrid
	 *            the injected {@link SearchDataGrid}
	 * @param headerTable
	 *            the injected {@link FixedWidthFlexTable}
	 * @param headings
	 *            the injected {@link SearchResultHeadings}
	 */
	@Inject
	public SearchResultScrollTable(FlexTable flexTable,
			ScrollTable scrolltable, SearchDataGrid dataGrid,
			FixedWidthFlexTable headerTable) {

		assert scrolltable != null && dataGrid != null && headerTable != null;

		this.mainPanel = flexTable;

		// the dataGrid and headerTable are passed in but mainTable will already
		// be constructed using them in the Provider. We need these references
		// because ScrollTable does not offer setter methods
		// if nothing is provided, go with a blank setup.
		if (headerTable.equals(new FixedWidthFlexTable())) {
			SearchDataGrid grid = new SearchDataGrid(1, 0);
			grid.setSelectionEnabled(true);
			scrolltable = new ScrollTable(grid, headerTable);
		}

		this.dataGrid = dataGrid;
		this.headerTable = headerTable;
		this.scrollTable = scrolltable;

		initWidget(this.mainPanel);
	}

	public void setDynamicValue(DynamicSearchResults searchResults) {

		this.fullResults = searchResults;

		/********************************
		 * Filter the results
		 *******************************/
		int neededCols = searchResults.getHeadings().size()
				- hiddenColumns.size();
		DynamicSearchResults filtered = new DynamicSearchResults();

		// parse the old columns
		List<String> headings = new ArrayList<String>();
		for (int i = 0; i < searchResults.getHeadings().size(); i++) {
			if (hiddenColumns.contains(i)) {
				continue;
			}
			headings.add(searchResults.getHeadings().get(i));
		}
		filtered.setHeadings(headings);

		// parse the old data
		List<SearchResult> results = new ArrayList<SearchResult>();
		for (int i = 0; i < searchResults.getResults().size(); i++) {

			SearchResult orig = searchResults.getResults().get(i);
			SearchResult newSearch = new SearchResult(neededCols);
			int validColIndex = 0;
			for (int col = 0; col < orig.getNumberOfHeadings(); col++) {
				if (hiddenColumns.contains(col)) {
					continue;
				}
				newSearch.setData(validColIndex, orig.getDataValues()[col]);
				validColIndex++;
			}
			results.add(newSearch);
		}
		filtered.setResults(results);

		/***********************************
		 * Proceed with filtered results
		 ***********************************/
		int colCount = filtered.getHeadings().size();

		// replace the header table
		headerTable = new FixedWidthFlexTable();
		for (int i = 0; i < colCount; i++) {
			String heading = filtered.getHeadings().get(i);
			heading = "<div class='gwtAf-Heading'>" + heading + "</div>";
			headerTable.setHTML(0, i, heading);
		}

		// set the data
		setValue(filtered.getResults(), false);

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
				formatter.removeStyleName(i, "gwtaf-evenRow");
			} else {
				formatter.removeStyleName(i, "gwtaf-oddRow");
				formatter.addStyleName(i, "gwtaf-evenRow");
			}
		}
	}

	public void setValue(List<SearchResult> results) {
		setValue(results, true);
	}

	private void setValue(List<SearchResult> results, boolean saveFullResult) {

		// store the full results if necessary.
		if (saveFullResult) {
			fullResults = new DynamicSearchResults();
			fullResults.setResults(results);
		}

		// nothing to do if there are no results.
		if (results == null) {
			return;
		}

		// figure out how many columns we'll need
		int neededColumns = results.size() == 0 ? 0 : results.get(0)
				.getDataValues().length;

		// make a new data grid
		this.dataGrid = createDataGrid(results.size(), neededColumns);
		dataGrid.setCellSpacing(0);

		// pass on the cell selection handler
		dataGrid.addCellClickHandler(clickHandler);

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
		for (int i = 0; i < result.getDataValues().length; i++) {

			// skip adding a header for the unique ID column.
			if (i == uniqueIdentifierIndex) {
				String uniqueId = fullResults.getResults().get(row)
						.getDataValues()[i];
				dataGrid.getRowFormatter().getElement(row).setAttribute(
						"uniqId", uniqueId);
			}
			dataGrid.setHTML(row, i, result.getDataValues()[i]);
		}
	}

	public void clear() {
		dataGrid.clearAll();
		hiddenColumns.clear();
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
	private SearchDataGrid createDataGrid(int rowCount, int colCount) {

		// Create a new table
		SearchDataGrid dataTable = new SearchDataGrid(rowCount, colCount);

		// Return the data table
		return dataTable;
	}

	public List<SearchResult> getResults() {
		List<SearchResult> results = new ArrayList<SearchResult>();
		RowFormatter formatter = dataGrid.getRowFormatter();
		for (int i = 0; i < dataGrid.getRowCount(); i++) {
			if (formatter.isVisible(i)) {
				results.add(createSingleResultFromRow(i));
			}
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

	public List<String> getHeadings() {
		List<String> head = new ArrayList<String>();
		for (int i = 0; i < headerTable.getColumnCount(); i++) {
			head.add(headerTable.getText(0, i));
		}
		return head;
	}

	public void render() {
		mainPanel.setWidget(0, 0, scrollTable);
		scrollTable.fillWidth();
	}

	public SearchDataGrid getDataTable() {
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
		return valueAtIdentifierOfRow(row.getRowIndex());
	}

	public String valueAtIdentifierOfRow(Integer row) {
		return dataGrid.getRowFormatter().getElement(row)
				.getAttribute("uniqId");
	}

	public void addCellClickedHandler(CellClickedHandler clickHandler) {
		this.clickHandler = clickHandler;
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

	public Integer getLastSortedColumn() {
		return lastSortedColumn;
	}

	public boolean getlastSortDirection() {
		return lastSortDirection;
	}

	public void markOdd(boolean markOdd) {
		this.markOdd = markOdd;
	}

	public void addHiddenColumn(int col) {
		hiddenColumns.add(col);
	}

	/**
	 * Returns the value at the specified row and column of an unfiltered
	 * (excluding all hidden columns) result set. Useful for grabbing IDs of
	 * elements you don't want displayed.
	 */
	public String valueAtFullResultTable(int row, int col) {
		return fullResults.getResults().get(row).getDataValues()[col];
	}

}
