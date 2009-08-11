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
package org.gwtaf.bindings;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import junit.framework.Assert;

import org.gwt.beansbinding.core.client.Binding;
import org.gwt.beansbinding.core.client.util.HasPropertyChangeSupport;
import org.gwtaf.bindings.adapters.TextBoxAdapterProvider;
import org.gwtaf.bindings.converters.StringToBooleanConverter;
import org.gwtaf.widgets.generic.RadioButtonGroup;

import com.google.gwt.junit.client.GWTTestCase;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.TextBox;
import com.google.inject.Provider;

/**
 * Gwt Tests for the {@link BindingBuilder} class
 * 
 * @author Jason Kong
 * 
 */
public class BindingBuilderGwtTest extends GWTTestCase {

	/**
	 * Tests the normal course of building a binding using
	 * {@link BindingBuilder}
	 */
	public void testCreateBinding() {

		Person personA = new Person();
		TextBox textBox = new TextBox();

		personA.setFullname("Person A");

		BindingBuilder builder = new BindingBuilder();

		Binding<Person, String, TextBox, String> binding = builder
				.createBinding(personA, "fullname", String.class, textBox,
						"text", String.class, new TextBoxAdapterProvider());
		// if all goes well we should pass
		Assert.assertTrue(binding.isBound());
	}

	/**
	 * Tests creation of a binding using a converter
	 */
	public void testCreateBindingWithConverter() {
		// we're going to bind a String value to a Boolean value using the the
		// StringToBooleanConverter

		Person personB = new Person();

		// some setup
		RadioButtonGroup buttonGroup = new RadioButtonGroup(
				new HorizontalPanel(), new Provider<RadioButton>() {

					public RadioButton get() {
						return new RadioButton("testCreateBindingWithConverter");
					}

				});

		buttonGroup.setChoices(new String[] { "Yes", "No" });

		// set person to be live to ensure a conversion is called
		personB.setIsAlive(true);

		BindingBuilder builder = new BindingBuilder();

		Binding<RadioButtonGroup, String, Person, Boolean> binding = builder
				.createBindingWithConverter(buttonGroup, "value", String.class,
						personB, "isAlive", Boolean.class,
						new StringToBooleanConverter(),
						new TextBoxAdapterProvider());

		// if all goes well we should pass
		Assert.assertTrue(binding.isBound());
	}

	/**
	 * Tests to see what happens if the source/target property value objects are
	 * of a different type than defined by the binding. (This is because the
	 * BeanProperty.create() method uses reflection)
	 */
	public void testBadGenericDefinitions() {

		BadGeneric badGeneric = new BadGeneric("Hello", "World");

		BindingBuilder builder = new BindingBuilder();

		// we define it with "integer" so that it breaks the method

		try {
			Binding<BadGeneric, String, BadGeneric, Integer> binding = builder
					.createBinding(badGeneric, "value", String.class,
							badGeneric, "title", Integer.class);
			Assert.assertNotNull(binding);
			// there isn't an exception: the FAIL

			Assert.assertFalse(true);

		} catch (IllegalArgumentException e) {
			// it passes
			Assert.assertTrue(true);
		}

	}

	/**
	 * A helper class for testing the creation of bindings with mismatched
	 * generic types
	 * 
	 * @author Jason Kong
	 * 
	 */
	public class BadGeneric implements HasPropertyChangeSupport {

		/**
		 * A simple String field
		 */
		private String value;

		/**
		 * Another simple String field
		 */
		private String title;

		/**
		 * {@link PropertyChangeSupport} for the data binding
		 */
		protected PropertyChangeSupport props = new PropertyChangeSupport(this);

		/**
		 * Creates a new {@code BadGeneric} with the specified parameter
		 * 
		 * @param inValue
		 *            the value of this {@code BadGeneric}
		 */
		public BadGeneric(String inValue, String title) {
			this.value = inValue;
			this.title = title;
		}

		public String getValue() {
			return value;
		}

		public void setValue(String value) {
			String oldValue = this.value;
			this.value = value;
			props.firePropertyChange("value", oldValue, value);
		}

		public String getTitle() {
			return title;
		}

		public void setTitle(String title) {
			String oldTitle = this.title;
			this.title = title;
			props.firePropertyChange("title", oldTitle, title);
		}

		public void addPropertyChangeListener(PropertyChangeListener listener) {
			props.addPropertyChangeListener(listener);
		}

		public void removePropertyChangeListener(PropertyChangeListener listener) {
			props.removePropertyChangeListener(listener);
		}
	}

	@Override
	public String getModuleName() {
		return "org.gwtaf.GwtAfBindings";
	}
}
