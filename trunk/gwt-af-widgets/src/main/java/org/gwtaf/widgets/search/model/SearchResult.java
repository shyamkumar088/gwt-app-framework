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
	private Integer numberOfHeadings;

	/**
	 * A String array of the data stored in this search result. Each data
	 * element is mapped to a particular heading, for easier access
	 */
	private String[] dataValues;

	/**
	 * Creates a new {@code SearchResult} with the specified headings
	 * 
	 * @param numHeadings
	 *            the Headings of this search result
	 */
	public SearchResult(Integer numHeadings) {
		this.numberOfHeadings = numHeadings;
		dataValues = new String[numHeadings];
	}

	/**
	 * Creates a new {@code SearchResult} with the given headings and values
	 * 
	 * @param headings
	 *            the {@link SearchResultHeadings} to use
	 * @param dataValues
	 *            the array of Strings containing the data values
	 */
	public SearchResult(Integer headings, String[] dataValues) {
		// assert that they are of the same length
		assert headings == dataValues.length : "Number"
				+ " of Data Values does not match the number of "
				+ "search result headings";

		// set the values
		this.numberOfHeadings = headings;
		this.dataValues = dataValues;
	}

	public String[] getDataValues() {
		return dataValues;
	}

	public Integer getNumberOfHeadings() {
		return numberOfHeadings;
	}

	public void setData(Integer index, String dataValue) {
		dataValues[index] = dataValue;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("<SearchResult: dataValues=");
		builder.append(Arrays.toString(dataValues));
		builder.append(" / headings=");
		builder.append(numberOfHeadings);
		builder.append(">");
		return builder.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(dataValues);
		result = prime
				* result
				+ ((numberOfHeadings == null) ? 0 : numberOfHeadings.hashCode());
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
		if (numberOfHeadings == null) {
			if (other.numberOfHeadings != null)
				return false;
		} else if (!numberOfHeadings.equals(other.numberOfHeadings))
			return false;
		return true;
	}
}
