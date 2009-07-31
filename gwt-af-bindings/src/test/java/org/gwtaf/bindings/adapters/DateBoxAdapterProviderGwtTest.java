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
package org.gwtaf.bindings.adapters;

import java.text.SimpleDateFormat;
import java.util.Date;

import junit.framework.Assert;

import org.gwt.beansbinding.core.client.Binding;
import org.gwt.beansbinding.core.client.util.GWTBeansBinding;
import org.gwtaf.bindings.BindingBuilder;
import org.gwtaf.bindings.Person;

import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.junit.client.GWTTestCase;
import com.google.gwt.user.datepicker.client.DateBox;

/**
 * GWT Tests for the {@link DateBoxAdapterProvider} class
 * 
 * @author Jason Kong
 * 
 */
public class DateBoxAdapterProviderGwtTest extends GWTTestCase {

	private SimpleDateFormat formatter = new SimpleDateFormat("MM-dd-yyyy");

	/**
	 * Tests the data binding to verify that the DateBox Adaptor works properly
	 */
	public void testBindings() {
		GWTBeansBinding.init();

		// build the objects
		DateBox dateBox = new DateBox();
		Person jason = new Person();

		// set up the binding adapters for these objects

		BindingBuilder builder = new BindingBuilder();

		Binding<DateBox, Date, Person, Date> binding = builder.createBinding(
				dateBox, "value", Date.class, jason, "birthday", Date.class,
				new DateBoxAdapterProvider());

		Assert.assertTrue(binding.isBound());

		// before:
		Assert.assertEquals(dateBox.getValue(), jason.getBirthday());

		// set the date and fire
		Date newDate = new Date(123121561);
		dateBox.setValue(newDate);
		ValueChangeEvent.fire(dateBox, newDate);

		Assert.assertEquals(dateBox.getValue(), jason.getBirthday());

		// check the other direction (model to view)
		jason.setBirthday(new Date(86541231));

		Assert.assertEquals(formatter.format(dateBox.getValue()), formatter
				.format(jason.getBirthday()));
	}

	@Override
	public String getModuleName() {
		return "org.gwtaf.GwtAfBindings";
	}

}
