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
import com.google.gwt.user.client.ui.TextArea;

/**
 * Test of the {@link TextAreaAdapterProvider}
 * 
 * @author Sergey Vesselov
 */
public class TextAreaAdapterProviderGwtTest extends GWTTestCase {

	/**
	 * Verifies that data bindings reflect information from UI to model and
	 * back.
	 */
	public void testTextAreaBindings() {
		GWTBeansBinding.init();

		// build the objects
		TextArea area = new TextArea();
		Person serge = new Person();

		// set up the binding adapters for these objects
		BindingBuilder builder = new BindingBuilder();

		// bind full name to area
		Binding<TextArea, String, Person, String> binding = builder
				.createBinding(area, "text", String.class, serge, "fullname",
						String.class, new TextAreaAdapterProvider());

		// assert the binding is bound.
		Assert.assertTrue(binding.isBound());

		// change the UI values
		area.setText("serge");

		// fire off events
		ValueChangeEvent.fire(area, "serge");

		// verify that the domain objects changed.
		Assert.assertEquals("serge", serge.getFullname());

		// change the model value
		serge.setFullname("coffee consumer");

		// verify that the domain objects changed.
		Assert.assertEquals("coffee consumer", area.getText());
	}

	@Override
	public String getModuleName() {
		return "org.gwtaf.GwtAfBindings";
	}

}
