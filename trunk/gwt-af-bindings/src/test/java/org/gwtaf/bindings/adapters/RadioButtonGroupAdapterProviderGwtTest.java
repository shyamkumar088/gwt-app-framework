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
import org.gwtaf.bindings.adapters.RadioButtonGroupAdapterProvider;
import org.gwtaf.widgets.generic.RadioButtonGroup;

import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.junit.client.GWTTestCase;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.inject.Provider;

/**
 * A {@link GWTTestCase} for testing the {@link RadioButtonGroupAdapterProvider}
 * 
 * @author Jason
 * 
 */
public class RadioButtonGroupAdapterProviderGwtTest extends GWTTestCase {

	/**
	 * Tests the data binding to verify that the DateBox Adaptor works properly
	 */
	public void testBindings() {
		GWTBeansBinding.init();

		// build the objects
		RadioButtonGroup buttonGroup = new RadioButtonGroup(
				new HorizontalPanel(), new Provider<RadioButton>() {

					public RadioButton get() {
						return new RadioButton(
								"RadioButtonGroupAdapterProviderGwtTest");
					}

				});
		Person programmer = new Person();

		// set the choices
		buttonGroup.setChoices(new String[] { "Sergey Vesselov",
				"Arthur Kalmenson", "Jason Kong" });

		// set up the binding adapters for these objects
		BindingBuilder builder = new BindingBuilder();

		Binding<RadioButtonGroup, String, Person, String> binding = builder
				.createBinding(buttonGroup, "value", String.class, programmer,
						"fullname", String.class,
						new RadioButtonGroupAdapterProvider());

		// make sure it's bound
		Assert.assertTrue(binding.isBound());

		// before:
		Assert.assertEquals(buttonGroup.getValue(), programmer.getBirthday());

		// set and fire
		String newName = "Jason Kong";

		buttonGroup.setValue(newName);
		ValueChangeEvent.fire(buttonGroup, newName);

		Assert.assertEquals(buttonGroup.getValue(), programmer.getFullname());
	}

	@Override
	public String getModuleName() {
		return "org.gwtaf.GwtAfBindings";
	}
}
