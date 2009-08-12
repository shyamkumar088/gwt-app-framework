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
import org.testng.annotations.Test;

/**
 * Test of the {@link IntegerToStringConverter}
 * 
 * @author Sergey Vesselov
 */
public class IntegerToStringConverterTest {

	/**
	 * The converter to test
	 */
	IntegerToStringConverter converter = new IntegerToStringConverter();

	/**
	 * Test Integer to String conversion.
	 */
	@Test
	public void fromIntegerToString() {
		Assert.assertEquals("32", converter.convertForward(32));
		Assert.assertEquals("56", converter.convertForward(new Integer(56)));
	}

	/**
	 * Test from String to Integer
	 */
	@Test
	public void fromStringToInteger() {
		Assert.assertEquals(new Integer(77), converter.convertReverse("77"));
	}

	/**
	 * Test a strange test case.
	 */
	@Test(expectedExceptions = NumberFormatException.class)
	public void weirdTest() {
		converter.convertReverse("cow");
	}
}
