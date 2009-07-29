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

import junit.framework.Assert;

import org.gwt.beansbinding.core.client.Binding;
import org.gwt.beansbinding.core.client.util.GWTBeansBinding;
import org.gwtaf.bindings.BindingBuilder;
import org.gwtaf.bindings.Person;

import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.junit.client.GWTTestCase;
import com.google.gwt.user.client.ui.CheckBox;

/**
 * GWT Test case for the {@link CheckBoxAdapterProviderGwtTest} class
 * 
 * @author Jason Kong
 * 
 */
public class CheckBoxAdapterProviderGwtTest extends GWTTestCase {

	/**
	 * Tests the data binding to verify that the CheckBox Adaptor works properly
	 */
	public void testBindings() {
		GWTBeansBinding.init();

		// build the objects
		CheckBox checkBox = new CheckBox();
		Person jason = new Person();
		jason.setIsAlive(true);

		// set up the binding
		BindingBuilder builder = new BindingBuilder();

		Binding<CheckBox, Boolean, Person, Boolean> binding = builder
				.createBinding(checkBox, "value", Boolean.class, jason,
						"isAlive", Boolean.class, new CheckBoxAdapterProvider());

		// make assertions
		Assert.assertTrue(binding.isBound());

		// check the value before:
		Assert.assertEquals(checkBox.getValue(), jason.getIsAlive());

		// set the date and fire
		Boolean newBoolean = false;
		checkBox.setValue(newBoolean);
		ValueChangeEvent.fire(checkBox, newBoolean);

		// check the value after
		Assert.assertEquals(checkBox.getValue(), jason.getIsAlive());

	}

	@Override
	public String getModuleName() {
		return "org.gwtaf.GwtAfBindings";
	}

}
