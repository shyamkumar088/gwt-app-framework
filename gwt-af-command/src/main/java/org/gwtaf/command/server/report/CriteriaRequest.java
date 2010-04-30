package org.gwtaf.command.server.report;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * Encapsulates a single filter requst from the client side.
 * 
 * Consists of field, comparator, and data.
 * 
 * Eg: Date_of_birth , > , 1960-01-01
 * 
 * @author Sergey Vesselov
 */
public class CriteriaRequest implements IsSerializable {

	public enum Comparators {
		EQUAL, NOT_EQUAL, CONTAINS, DOES_NOT_CONTAIN, LESS_THAN, LESS_THAN_OR_EQUAL, MORE_THAN, MORE_THAN_OR_EQUAL, BEFORE, AFTER, ON, IS, IS_NOT, NOT_ON, STARTS_WITH, ENDS_WITH
	}

	private String field;
	private Comparators comparator;
	private String data;

	/**
	 * Empty constructor
	 */
	public CriteriaRequest() {

	}

	/**
	 * Creates a filled CriteriaRequest
	 * 
	 * @param field
	 *            the field to filter by
	 * @param comp
	 *            the comparator to apply to the field
	 * @param data
	 *            the data by which to filter.
	 */
	public CriteriaRequest(String field, Comparators comp, String data) {
		this.field = field;
		this.comparator = comp;
		this.data = data;
	}

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public Comparators getComparator() {
		return comparator;
	}

	public void setComparator(Comparators comparator) {
		this.comparator = comparator;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}
}
