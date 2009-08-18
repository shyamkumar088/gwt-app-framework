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
import com.google.gwt.user.client.ui.RichTextArea;

/**
 * A test of the {@link RichTextAreaAdapterProvider}
 * 
 * @author Sergey Vesselov
 */
public class RichTextAreaAdapterProviderGwtTest extends GWTTestCase {

	/**
	 * Test {@link RichTextArea}
	 */
	private RichTextArea area;

	/**
	 * Test object
	 */
	private Person serge;

	@Override
	protected void gwtSetUp() throws Exception {
		super.gwtSetUp();

		GWTBeansBinding.init();

		// build the objects
		area = new RichTextArea();
		serge = new Person();

		// set up the binding adapters for these objects
		BindingBuilder builder = new BindingBuilder();

		// bind full name to area
		Binding<RichTextArea, String, Person, String> binding = builder
				.createBinding(area, "HTML", String.class, serge, "fullname",
						String.class, new RichTextAreaAdapterProvider());

		// assert the binding is bound.
		Assert.assertTrue(binding.isBound());
	}

	/**
	 * Test that UI changes are reflected in the model.
	 */
	public void testUiToModel() {

		// change the UI values
		area.setHTML("<b>serge</b>");

		// fire off events
		ChangeEvent.fireNativeEvent(Document.get().createBlurEvent(), area);

		// verify that the domain objects changed.
		Assert.assertEquals("<b>serge</b>", serge.getFullname());
	}

	/**
	 * Test that model changes reflect in UI
	 */
	public void testModelToUi() {

		// change the model value
		serge.setFullname("coffee consumer");

		// verify that UI has changed.
		Assert.assertEquals("coffee consumer", area.getHTML());

	}

	@Override
	public String getModuleName() {
		return "org.gwtaf.GwtAfBindings";
	}

}
