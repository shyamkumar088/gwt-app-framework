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
import java.util.Arrays;
import java.util.List;

import junit.framework.Assert;

import org.gwtaf.widgets.search.model.SearchResult;

import com.google.gwt.core.client.GWT;
import com.google.gwt.gen2.table.client.FixedWidthFlexTable;
import com.google.gwt.gen2.table.client.FixedWidthGrid;
import com.google.gwt.gen2.table.client.ScrollTable;
import com.google.gwt.inject.client.AbstractGinModule;
import com.google.gwt.inject.client.GinModules;
import com.google.gwt.inject.client.Ginjector;
import com.google.gwt.junit.client.GWTTestCase;
import com.google.inject.Provider;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.google.inject.TypeLiteral;
import com.google.inject.name.Named;

/**
 * A set of GWT test cases for the {@link SearchResultScrollTable}
 * 
 * @author Jason Kong
 * 
 */
public class GwtTestSearchResultScrollTable extends GWTTestCase {

	/**
	 * The {@link SearchResultScrollTableTestGinjector} we'll use for testing
	 */
	private SearchResultScrollTableTestGinjector injector;

	/**
	 * The {@link SearchResultTable} we'll be testing with. (Concrete
	 * implementation will be of {@link SearchResultScrollTable})
	 */
	private SearchResultTable<SearchResult> resultsTable;

	/**
	 * An {@Ginjector} used for testing
	 * 
	 * @author Jason
	 * 
	 */
	@GinModules(SearchResultScrollTableTestGinModule.class)
	public static interface SearchResultScrollTableTestGinjector extends
			Ginjector {

		/**
		 * Returns a {@link SearchResultTable} for testing
		 * 
		 * @return a {@link SearchResultTable} for testing
		 */
		public SearchResultTable<SearchResult> getSearchResultsTable();

		/**
		 * Returns a {@link ScrollTable} to ensure wiring
		 * 
		 * @return a {@link ScrollTable} to ensure wiring
		 */
		public ScrollTable getScrollTable();

		/**
		 * Returns a {@link FixedWidthGrid} to test wiring
		 * 
		 * @return a {@link FixedWidthGrid} to test wiring
		 */
		public FixedWidthGrid getDataGrid();

		/**
		 * Returns a {@link FixedWidthFlexTable} to test wiring
		 * 
		 * @return a {@link FixedWidthFlexTable} to test wiring
		 */
		public FixedWidthFlexTable getHeaderTable();

	}

	/**
	 * An {@link AbstractGinModule} implementation for testing the
	 * {@link SearchResultScrollTable}
	 * 
	 * @author Jason Kong
	 * 
	 */
	public static class SearchResultScrollTableTestGinModule extends
			AbstractGinModule {

		@Override
		protected void configure() {
			bind(new TypeLiteral<SearchResultTable<SearchResult>>() {
			}).to(SearchResultScrollTable.class);

		}

		/**
		 * Provides a {@link ScrollTable} for constructing the
		 * {@link SearchResultScrollTable} with
		 * 
		 * @param dataGrid
		 *            the injected data {@link FixedWidthGrid}
		 * @param headerTable
		 *            the injected header {@link FixedWidthFlexTable}
		 * @return the wired up {@link ScrollTable}
		 */
		@Provides
		public ScrollTable scrollTableProvider(FixedWidthGrid dataGrid,
				FixedWidthFlexTable headerTable) {
			return new ScrollTable(dataGrid, headerTable);
		}

		/**
		 * Provides a {@link FixedWidthFlexTable} for constructing the
		 * {@link SearchResultScrollTable}
		 * 
		 * @param headings
		 *            the injected {@link SearchResultHeadings}
		 * @return the wired up {@link FixedWidthFlexTable}
		 */
		@Provides
		public FixedWidthFlexTable headerTableProvider(
				@Named("Headings") Provider<List<String>> headings) {
			FixedWidthFlexTable table = new FixedWidthFlexTable();
			for (int i = 0; i < headings.get().size(); i++) {
				table.setHTML(0, i, headings.get().get(i));
			}
			return table;
		}

		/**
		 * Provides a {@link FixedWidthGrid} for constructing the
		 * {@link SearchResultScrollTable}
		 * 
		 * @param headings
		 *            the injected {@link SearchResultHeadings}
		 * @return the wired up {@link FixedWidthGrid}
		 */
		@Provides
		public FixedWidthGrid dataGridProvider(
				@Named("Headings") Provider<List<String>> headings) {
			// return 1 row by default. When SetValue is called on the
			// ScrollTable, it will resize the data grid as needed

			return new FixedWidthGrid(1, headings.get().size());
		}

		/**
		 * Provides the headings for the search result table
		 * 
		 * @return the headings for the search result table
		 */
		@Provides
		@Singleton
		@Named("Headings")
		public List<String> headingsProvider() {
			return new ArrayList<String>(Arrays.asList("Heading 1",
					"Heading 2", "Heading 3"));
		}

	}

	@Override
	protected void gwtSetUp() throws Exception {
		super.gwtSetUp();

		injector = GWT.create(SearchResultScrollTableTestGinjector.class);

		resultsTable = injector.getSearchResultsTable();
	}

	/**
	 * Tests to see if the Gin bindings are working
	 */
	public void testComponents() {
		assertNotNull(injector.getDataGrid());
		assertNotNull(injector.getHeaderTable());
		assertNotNull(injector.getScrollTable());
		assertNotNull(injector.getSearchResultsTable());
	}

	/**
	 * Tests the setting and getting of results in the
	 * {@link SearchResultScrollTable}
	 */
	public void testSetValueAndGetResults() {

		// set the headings of the table

		SearchResult result1 = new SearchResult(3,
				new String[] { "A", "B", "C" });
		SearchResult result2 = new SearchResult(3,
				new String[] { "D", "E", "F" });
		SearchResult result3 = new SearchResult(3,
				new String[] { "G", "H", "I" });

		List<SearchResult> results = new ArrayList<SearchResult>();
		results.add(result1);
		results.add(result2);
		results.add(result3);

		// test the set value
		resultsTable.setValue(results);

		// verify what you put in is equal to what you get out
		Assert.assertEquals(resultsTable.getResults(), results);
	}

	/**
	 * Tests setting a null list of results into the
	 * {@link SearchResultScrollTable}
	 */
	public void testSetNullValue() {
		try {
			resultsTable.setValue(null);
		} catch (NullPointerException e) {
			Assert.assertTrue(true);
		}
	}

	/**
	 * Tests setting a list containing a null value into the scroll table. The
	 * table itself should have skipped the null value
	 */
	public void testSetListWithNullValue() {
		// create the list
		List<SearchResult> resultWithNull = new ArrayList<SearchResult>();
		SearchResult result1 = new SearchResult(3,
				new String[] { "A", "B", "C" });

		SearchResult result2 = new SearchResult(3,
				new String[] { "D", "D", "D" });

		// add the items, including a null
		resultWithNull.add(result1);
		resultWithNull.add(null);
		resultWithNull.add(result2);

		resultsTable.setValue(resultWithNull);

		// get the results out
		List<SearchResult> returnedResults = resultsTable.getResults();

		// assert that the null was skipped
		Assert.assertEquals(returnedResults.get(0), result1);
		Assert.assertEquals(returnedResults.get(1), result2);
	}

	@Override
	public String getModuleName() {
		return "org.gwtaf.GwtAfWidgetsTesting";
	}
}
