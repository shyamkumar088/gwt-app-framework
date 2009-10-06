/*
 * Copyright 2009. Mount Sinai Hospital, Toronto, Canada.
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
package org.gwtaf.widgets.search.model;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 * Test for the {@link SearchResultHeadings} domain object.
 * 
 * @author Jason Kong
 * 
 */
public class SearchResultHeadingsTest {

	/**
	 * The searchResultHeadings to test.
	 */
	private SearchResultHeadings headings;

	/**
	 * The headings for the search result
	 */

	@BeforeMethod
	public void initBefore() {

		headings = new SearchResultHeadings("Test1", "Test2", "Test3");
	}

	/**
	 * A data provider that returns pairs of objects for testing the equals and
	 * hashCode methods.
	 * 
	 * @return an array containing two objects to compare with equals() and
	 *         hashCode(), and a boolean with the expected result of the equals
	 *         comparison.
	 */
	@DataProvider(name = "testProvider")
	public Object[][] dataProviderTestEquals() {
		initBefore();

		SearchResultHeadings otherHeadings = new SearchResultHeadings(
				"Something1", "Something2", "Something3");

		SearchResultHeadings sameHeadings = new SearchResultHeadings("Test1",
				"Test2", "Test3");

		return new Object[][] { { headings, otherHeadings, false },
				{ headings, sameHeadings, true }, { headings, headings, true },
				{ headings, null, false } };
	}

	/**
	 * Tests the equals and hashCode of the {@link SearchResultHeadings} object.
	 * 
	 * @param SearchResultHeadings
	 *            the original SearchResultHeadings object
	 * @param SearchResultHeadingsToCompare
	 *            the other SearchResultHeadings object against which the
	 *            comparison is done.
	 * @param expected
	 *            the expected outcome of the comparison
	 */
	@Test(dataProvider = "testProvider")
	public void testEqualsAndHash(SearchResultHeadings SearchResultHeadings,
			SearchResultHeadings SearchResultHeadingsToCompare, boolean expected) {

		// check the equals
		Assert.assertEquals(SearchResultHeadings
				.equals(SearchResultHeadingsToCompare), expected);

		// check the hash
		if (SearchResultHeadings != null
				&& SearchResultHeadingsToCompare != null) {
			Assert
					.assertEquals(
							SearchResultHeadings.hashCode() == SearchResultHeadingsToCompare
									.hashCode(), expected);
		}
	}

}
