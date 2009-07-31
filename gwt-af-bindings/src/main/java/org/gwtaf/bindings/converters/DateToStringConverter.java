/*
 * Copyright 2009. Mount Sinai Hospital, Toronto, Canada.
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
package org.gwtaf.bindings.converters;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.gwt.beansbinding.core.client.Converter;

/**
 * A {@link Converter} used to convert to and from {@link Date}s and
 * {@link String}s
 * 
 * @author Jason Kong
 * 
 */
public class DateToStringConverter extends Converter<Date, String> {

	/**
	 * A {@link SimpleDateFormat} that we will use to convert dates to strings
	 * etc.
	 */
	private SimpleDateFormat formatter;

	/**
	 * Creates a new {@code DateToStringConverter} using the specified date
	 * format
	 * 
	 * @param dateFormat
	 *            the pattern to use with the {@link SimpleDateFormat}
	 */
	public DateToStringConverter(String dateFormat) {
		formatter = new SimpleDateFormat(dateFormat);
	}

	@Override
	public String convertForward(Date value) {
		return formatter.format((Date) value);
	}

	@Override
	public Date convertReverse(String value) {
		// target to source
		try {
			return formatter.parse((String) value);
		} catch (Exception e) {
			throw new IllegalArgumentException("Unable to Convert");
		}
	}
}
