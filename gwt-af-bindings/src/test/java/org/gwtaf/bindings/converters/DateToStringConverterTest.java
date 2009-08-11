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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Tests for the {@link DateToStringConverter}
 * 
 * @author Jason Kong
 * 
 */
public class DateToStringConverterTest {

	@Test
	public void testConversion() {

		// set up a date formatter
		SimpleDateFormat formatter = new SimpleDateFormat("MM-dd-yyyy");

		DateToStringConverter converter = new DateToStringConverter(
				"MM-dd-yyyy");

		Date date = null;
		try {
			date = formatter.parse("12-31-1984");
		} catch (ParseException e) {
			Assert.fail("Failed to parse date", e);
		}

		// compare the default toString values because those should be
		// sufficient to compare the Date objects
		Assert.assertEquals(converter.convertReverse("12-31-1984").toString(),
				date.toString());

		// the other way
		Assert.assertEquals(converter.convertForward(date), "12-31-1984");

	}

}
