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

import com.google.gwt.junit.client.GWTTestCase;
import com.google.gwt.user.client.ui.Label;

/**
 * Test of the {@link LabelAdapterProvider}
 * 
 * @author Sergey Vesselov
 * 
 */
public class LabelAdapterProviderGwtTest extends GWTTestCase {

	/**
	 * Verifies that data bindings reflect information from model to UI. Ui to
	 * model will not be tested since the UI is a label which is not an input.
	 */
	public void testTextAreaBindings() {
		GWTBeansBinding.init();

		// build the objects
		Label nameLabel = new Label();
		Person serge = new Person();

		// bind fullname to the name label.
		BindingBuilder builder = new BindingBuilder();
		Binding<Label, String, Person, String> binding = builder.createBinding(
				nameLabel, "text", String.class, serge, "fullname",
				String.class, new LabelAdapterProvider());

		// assert the binding is bound.
		Assert.assertTrue(binding.isBound());

		// change the model value
		serge.setFullname("coffee consumer");

		// verify that UI has changed.
		Assert.assertEquals("coffee consumer", nameLabel.getText());
	}

	@Override
	public String getModuleName() {
		return "org.gwtaf.GwtAfBindings";
	}

}
