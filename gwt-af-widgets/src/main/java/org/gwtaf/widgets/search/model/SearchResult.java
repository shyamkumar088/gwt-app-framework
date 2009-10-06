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
package org.gwtaf.widgets.search.model;

import java.util.Arrays;
import java.util.HashMap;

/**
 * The object representation of a single search result
 * 
 * @author Jason Kong
 * 
 */
public class SearchResult {

	/**
	 * Each {@code SearchResult} must have a set of headings
	 */
	private SearchResultHeadings headings;

	/**
	 * A {@link HashMap} of the data stored in this search result. Each data
	 * element is mapped to a particular heading, for easier access
	 */
	private String[] dataValues;

	/**
	 * Creates a new {@code SearchResult} with the specified headings
	 * 
	 * @param headings
	 *            the Headings of this search result
	 */
	public SearchResult(SearchResultHeadings headings) {
		this.headings = headings;
		dataValues = new String[headings.getHeadings().length];
	}

	/**
	 * Creates a new {@code SearchResult} with the given headings and values
	 * 
	 * @param headings
	 *            the {@link SearchResultHeadings} to use
	 * @param dataValues
	 *            the array of Strings containing the data values
	 */
	public SearchResult(SearchResultHeadings headings, String[] dataValues) {
		// assert that they are of the same length
		assert headings.getHeadings().length == dataValues.length;

		// set the values
		this.headings = headings;
		this.dataValues = dataValues;
	}

	/**
	 * Retrieves the value of the unique identifier of this search result
	 * 
	 * @return the value of the unique identifier
	 */
	public String getUniqueIdentifierValue() {
		return dataValues[headings.getUniqueIdentifierIndex()];
	}

	public String[] getDataValues() {
		return dataValues;
	}

	public SearchResultHeadings getHeadings() {
		return headings;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("<SearchResult: dataValues=");
		builder.append(Arrays.toString(dataValues));
		builder.append(" / headings=");
		builder.append(headings);
		builder.append(">");
		return builder.toString();
	}

	public void setData(String heading, String dataValue) {
		// assert that the heading exists
		int index = Arrays.binarySearch(headings.getHeadings(), heading);
		if (index != -1) {
			dataValues[index] = dataValue;
		} else {
			throw new IllegalArgumentException("Heading <" + heading
					+ "> does not exist in this Search Result");
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(dataValues);
		result = prime * result
				+ ((headings == null) ? 0 : headings.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SearchResult other = (SearchResult) obj;
		if (!Arrays.equals(dataValues, other.dataValues))
			return false;
		if (headings == null) {
			if (other.headings != null)
				return false;
		} else if (!headings.equals(other.headings))
			return false;
		return true;
	}
}
