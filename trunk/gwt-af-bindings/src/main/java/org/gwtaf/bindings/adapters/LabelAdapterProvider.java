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

import org.gwt.beansbinding.core.client.ext.BeanAdapter;
import org.gwt.beansbinding.core.client.ext.BeanAdapterProvider;
import org.gwt.beansbinding.ui.client.adapters.BeanAdapterBase;

import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.Label;

/**
 * A {@link BeanAdapterProvider} implementation for use with the {@link Label}
 * class.
 * 
 * @author Sergey Vesselov
 * @author Jason Kong
 * @author georgopoulos.georgios(at)gmail.com
 * 
 */
public final class LabelAdapterProvider implements BeanAdapterProvider {

	/**
	 * The property that will be set/get on the Label
	 */
	private static final String TEXT_P = "text";

	/**
	 * The Adapter class that will be used to adapt the {@link Label} class
	 * 
	 * @author Jason Kong
	 * 
	 */
	public static final class Adapter extends BeanAdapterBase {
		/**
		 * The {@link Label} reference
		 */
		private Label label;

		/**
		 * The {@link Handler} for handling data binding firing
		 */
		private Handler handler;

		/**
		 * The {@link Label}'s cached value, which will be used to fire property
		 * changes
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
		 * @param inLabel
		 */
		private Adapter(Label inLabel) {
			super(TEXT_P);
			this.label = inLabel;
		}

		/**
		 * This method adapts {@link Label}'s getText method
		 * 
		 * @return the value of the Label
		 */
		public String getText() {
			return label.getText();
		}

		/**
		 * This method adapts {@link Label}'s setText method
		 * 
		 * @param value
		 *            the value to set
		 */
		public void setText(String value) {
			label.setText(value);
		}

		@Override
		protected void listeningStarted() {
			handler = new Handler();
			cachedText = getText();
			registration = label.addMouseDownHandler(handler);
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
		public class Handler implements MouseDownHandler {

			public void onMouseDown(MouseDownEvent arg0) {
			}
		}

	}

	public BeanAdapter createAdapter(Object source, String property) {
		if (!providesAdapter(source.getClass(), property)) {
			throw new IllegalArgumentException();
		}
		return new Adapter((Label) source);
	}

	public Class<?> getAdapterClass(Class<?> type) {
		return (type == Label.class) ? LabelAdapterProvider.Adapter.class
				: null;
	}

	public boolean providesAdapter(Class<?> type, String property) {
		return (type == Label.class) && property.intern() == TEXT_P;
	}

}
