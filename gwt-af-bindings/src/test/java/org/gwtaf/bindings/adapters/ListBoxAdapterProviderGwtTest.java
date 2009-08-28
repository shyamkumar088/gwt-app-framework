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

import com.google.gwt.dom.client.Document;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.junit.client.GWTTestCase;
import com.google.gwt.user.client.ui.ListBox;

/**
 * GWT Tests for the {@link ListBoxAdapterProvider} class
 * 
 * @author Jason Kong
 * 
 */
public class ListBoxAdapterProviderGwtTest extends GWTTestCase {

	/**
	 * A {@link ListBox} we've created.
	 */
	private ListBox listBox;

	/**
	 * A {@link Person} we're syncing to.
	 */
	private Person person;

	/**
	 * The binding.
	 */
	private Binding<Person, String, ListBox, String> binding;

	public void gwtSetUp() {

		// initialize the binding.
		GWTBeansBinding.init();
		
		// build the objects
		listBox = new ListBox();
		person = new Person();

		// set up some things
		listBox.addItem("Jason Kong");
		listBox.addItem("Serge Vesselov");
		listBox.addItem("Arthur Kalmenson");

		BindingBuilder builder = new BindingBuilder();

		binding =
				builder.createBinding(person, "fullname", String.class,
						listBox, "selectedElement", String.class,
						new ListBoxAdapterProvider());
	}

	/**
	 * Test changing the UI and checking whether the model has been modified.
	 */
	public void testUiToModel() {

		// set up the binding adapters for these objects
		Assert.assertTrue(binding.isBound());

		// select another element
		listBox.setSelectedIndex(1); // should select serge
		ChangeEvent
				.fireNativeEvent(Document.get().createChangeEvent(), listBox);
		Assert.assertEquals("Serge Vesselov", person.getFullname());
	}

	/**
	 * Test changing the model and checking whether the UI has been modified.
	 */
	public void testModelToUi() {

		// check the other direction (model to view)
		person.setFullname("Arthur Kalmenson");

		Assert.assertEquals("Arthur Kalmenson", listBox.getValue(listBox
				.getSelectedIndex()));
	}

	/**
	 * Test setting the model to a value that's not in the list box.
	 */
	public void testModelSetNotInListBox() {

		// set to something not there.
		person.setFullname("blah blah");
		
		// assert that it stays the first option.
		Assert.assertEquals("Jason Kong", listBox.getValue(listBox
				.getSelectedIndex()));
	}

	@Override
	public String getModuleName() {
		return "org.gwtaf.GwtAfBindings";
	}

}
