/*
 * Copyright (C) 2007 Sun Microsystems, Inc. All rights reserved. Use is
 * subject to license terms.
 * 
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

import org.gwt.beansbinding.core.client.ext.BeanAdapter;
import org.gwt.beansbinding.core.client.ext.BeanAdapterProvider;
import org.gwt.beansbinding.ui.client.adapters.BeanAdapterBase;

import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.TextBox;

/**
 * 
 * A {@link BeanAdapterProvider} implementation for use with the {@link TextBox}
 * class.
 * 
 * @author Sergey Vesselov
 * @author Jason Kong
 * @author georgopoulos.georgios(at)gmail.com
 * 
 */
public class TextBoxAdapterProvider implements BeanAdapterProvider {

	/**
	 * The property that will be set/get on the TextBox
	 */
	private static final String TEXT_P = "text";

	/**
	 * The Adapter class that will be used to adapt the {@link TextBox} class
	 * 
	 * @author Jason Kong
	 * 
	 */
	public static final class Adapter extends BeanAdapterBase {
		/**
		 * The {@link TextBox} reference
		 */
		private TextBox textBox;

		/**
		 * The {@link Handler} for handling data binding firing
		 */
		private Handler handler;

		/**
		 * The {@link TextBox}'s cached value, which will be used to fire
		 * property changes
		 */
		private String cachedText;

		/**
		 * The {@link HandlerRegistration} used to keep track of the added
		 * handlers
		 */
		private HandlerRegistration registration;

		/**
		 * Constructs the adapter
		 * 
		 * @param inTextBox
		 */
		private Adapter(TextBox inTextBox) {
			super(TEXT_P);
			this.textBox = inTextBox;
		}

		/**
		 * This method adapts {@link TextBox}'s getText method
		 * 
		 * @return the value of the TextBox
		 */
		public String getText() {
			return textBox.getText();
		}

		/**
		 * This method adapts {@link TextBox}'s setText method
		 * 
		 * @param value
		 *            the value to set
		 */
		public void setText(String value) {
			textBox.setText(value);
		}

		@Override
		protected void listeningStarted() {
			handler = new Handler();
			cachedText = getText();
			registration = textBox.addValueChangeHandler(handler);
		}

		@Override
		protected void listeningStopped() {
			registration.removeHandler();
			handler = null;
		}

		/**
		 * A {@link ValueChangeHandler} implementation for firing off property
		 * changes
		 * 
		 * @author Jason Kong
		 * 
		 */
		private class Handler implements ValueChangeHandler<String> {
			public void onValueChange(ValueChangeEvent<String> event) {
				String oldText = cachedText;
				cachedText = getText();
				firePropertyChange(oldText, cachedText);
			}
		}

	}

	public BeanAdapter createAdapter(Object source, String property) {
		if (!providesAdapter(source.getClass(), property)) {
			throw new IllegalArgumentException();
		}
		return new Adapter((TextBox) source);
	}

	public Class<?> getAdapterClass(Class<?> type) {
		return (type == TextBox.class) ? TextBoxAdapterProvider.Adapter.class
				: null;
	}

	public boolean providesAdapter(Class<?> type, String property) {
		return (type == TextBox.class) && property.intern() == TEXT_P;
	}

}
