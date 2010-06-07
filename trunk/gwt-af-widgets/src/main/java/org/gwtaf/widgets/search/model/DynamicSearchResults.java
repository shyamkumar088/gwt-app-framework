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

import java.util.List;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * This class contains a list of {@link SearchResult}s and an array for column
 * headings.
 * 
 * @author Sergey Vesselov
 */
public class DynamicSearchResults implements IsSerializable {

	private List<String> headings;

	private List<SearchResult> results;

	/**
	 * Empty constructor.
	 */
	public DynamicSearchResults() {

	}

	/**
	 * Create a dymanic search result.
	 * 
	 * @param headings
	 *            the list of column headings
	 * @param results
	 *            the actual data results.
	 */
	public DynamicSearchResults(List<String> headings,
			List<SearchResult> results) {
		this.headings = headings;
		this.results = results;
	}

	public List<String> getHeadings() {
		return headings;
	}

	public void setHeadings(List<String> headings) {
		this.headings = headings;
	}

	public List<SearchResult> getResults() {
		return results;
	}

	public void setResults(List<SearchResult> results) {
		this.results = results;
	}
}
