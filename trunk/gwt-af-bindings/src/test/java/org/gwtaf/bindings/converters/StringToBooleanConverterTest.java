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

import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

/**
 * Tests the {@link StringToBooleanConverter} to ensure that conversion back and
 * forth works
 * 
 * @author Jason Kong
 * 
 */
public class StringToBooleanConverterTest {

	/**
	 * The {@link StringToBooleanConverter} to test with
	 */
	private StringToBooleanConverter converter;

	/**
	 * Some setup before the tests
	 */
	@BeforeTest
	public void setup() {
		converter = new StringToBooleanConverter();
	}

	/**
	 * Tests forward conversion (String to Boolean)
	 */
	@Test
	public void testStringToBoolean() {

		// forwards for yes and no
		Assert.assertEquals(converter.convertForward("Yes"), new Boolean(true));
		Assert.assertEquals(converter.convertForward("No"), new Boolean(false));

	}

	/**
	 * Tests reverse conversion (Boolean to String)
	 */
	@Test
	public void testBooleanToString() {

		// forwards for yes and no
		Assert.assertEquals(converter.convertReverse(false), "No");
		Assert.assertEquals(converter.convertReverse(true), "Yes");

	}

	/**
	 * Tests a bogus conversion (Random string to Boolean)
	 */
	@Test(expectedExceptions = { IllegalStateException.class })
	public void testUnableToConvert() {
		converter.convertForward("bad value");
	}

}
