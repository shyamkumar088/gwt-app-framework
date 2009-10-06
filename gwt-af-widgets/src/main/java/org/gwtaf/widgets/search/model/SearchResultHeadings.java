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

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * Represents the headings of a search result
 * 
 * @author Jason Kong
 * 
 */
public class SearchResultHeadings implements IsSerializable {

	/**
	 * A list of strings that make up the headings of a search result
	 */
	private String[] headings;

	/**
	 * The index of the heading that is used as the unique identifier of the
	 * search result
	 */
	private Integer uniqueIdentifierIndex;

	/**
	 * Creates a {@code SearchResultHeadings} object using the list of strings
	 * as headings. AUTOMATICALLY ASSUMES THE FIRST ELEMENT OF THE LIST IS THE
	 * UNIQUE IDENTIFIER
	 * 
	 * @param headings
	 */
	public SearchResultHeadings(String... headings) {
		this(headings, 0);
	}

	/**
	 * Creates a {@code SearchResultHeadings} object using the list of strings
	 * as headings, using the heading at the specified index as the unique
	 * identifier
	 * 
	 * @param headings
	 * @param identifierIndex
	 */
	public SearchResultHeadings(String[] headings, Integer identifierIndex) {
		this.headings = headings;
		uniqueIdentifierIndex = identifierIndex;
	}

	/**
	 * Returns the heading that is used as the unique identifier
	 * 
	 * @return
	 */
	public String getUniqueIdentifierHeading() {
		return headings[uniqueIdentifierIndex];
	}

	/**
	 * Returns the index in the headings of the unique identifier
	 * 
	 * @return the index of the unique identifier
	 */
	public Integer getUniqueIdentifierIndex() {
		return uniqueIdentifierIndex;
	}

	/**
	 * Returns the headings
	 * 
	 * @return
	 */
	public String[] getHeadings() {
		return headings;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(headings);
		result = prime
				* result
				+ ((uniqueIdentifierIndex == null) ? 0 : uniqueIdentifierIndex
						.hashCode());
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
		SearchResultHeadings other = (SearchResultHeadings) obj;
		if (!Arrays.equals(headings, other.headings))
			return false;
		if (uniqueIdentifierIndex == null) {
			if (other.uniqueIdentifierIndex != null)
				return false;
		} else if (!uniqueIdentifierIndex.equals(other.uniqueIdentifierIndex))
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("<SearchResultHeadings: headings=");
		builder.append(Arrays.toString(headings));
		builder.append(" / uniqueIdentifierIndex=");
		builder.append(uniqueIdentifierIndex);
		builder.append(">");
		return builder.toString();
	}

}
