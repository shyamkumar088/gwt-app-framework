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
package org.gwtaf.bindings.converters;

import org.gwt.beansbinding.core.client.Converter;

/**
 * A {@link Converter} implementation for converting between Strings and
 * Booleans
 * 
 * @author Jason Kong
 * 
 */
public class StringToBooleanConverter extends Converter<String, Boolean> {

	@Override
	public Boolean convertForward(String value) {
		if (value.equalsIgnoreCase("No")) {
			return false;
		} else if (value.equalsIgnoreCase("Yes")) {
			return true;
		} else {
			throw new IllegalStateException("Unable to convert String: "
					+ value + " into a boolean");
		}
	}

	@Override
	public String convertReverse(Boolean value) {
		if (value == true) {
			return "Yes";
		} else {
			return "No";
		}
	}

}
