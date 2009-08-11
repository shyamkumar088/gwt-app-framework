/*
 * Copyright (C) 2007 Sun Microsystems, Inc. All rights reserved. Use is
 * subject to license terms.
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
import org.gwtaf.widgets.generic.RadioButtonGroup;

import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.CheckBox;

/**
 * A {@link BeanAdapterProvider} implementation for use with the
 * {@link RadioButtonGroup} class
 * 
 * @author Jason Kong
 * @author georgopoulos.georgios(at)gmail.com
 */
public final class CheckBoxAdapterProvider implements BeanAdapterProvider {

	/**
	 * The String constant representing 'value'
	 */
	private static final String VALUE_P = "value";

	/**
	 * The Adapter class that will be used to adapt the {@link CheckBox} class
	 * 
	 * @author Jason Kong
	 * 
	 */
	public static final class Adapter extends BeanAdapterBase {
		/**
		 * The {@link CheckBox} reference
		 */
		private CheckBox checkBox;

		/**
		 * The {@link Handler} for handling data binding firing
		 */
		private Handler handler;

		/**
		 * The {@link CheckBox}'s cached value, which will be used to fire
		 * property changes
		 */
		private Boolean cachedBool;

		/**
		 * The {@link HandlerRegistration} used to keep track of the added
		 * handlers
		 */
		private HandlerRegistration registration;

		/**
		 * Constructs an {@link Adapter} for the given {@link CheckBox}
		 * 
		 * @param checkBox
		 */
		private Adapter(CheckBox checkBox) {
			super(VALUE_P);
			this.checkBox = checkBox;
		}

		/**
		 * This method adapts {@link CheckBox}'s getValue method
		 * 
		 * @return the value of the checkbox
		 */
		public Boolean getValue() {
			return checkBox.getValue();
		}

		/**
		 * This method adapts {@link CheckBox}'s setValue method
		 * 
		 * @param value
		 *            the value to set
		 */
		public void setValue(Boolean value) {
			if (value != null) {
				checkBox.setValue(value);
			}
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * org.gwt.beansbinding.ui.client.adapters.BeanAdapterBase#listeningStarted
		 * ()
		 */
		@Override
		protected void listeningStarted() {
			handler = new Handler();
			cachedBool = getValue();
			registration = checkBox.addValueChangeHandler(handler);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * org.gwt.beansbinding.ui.client.adapters.BeanAdapterBase#listeningStopped
		 * ()
		 */
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
		private class Handler implements ValueChangeHandler<Boolean> {

			public void onValueChange(ValueChangeEvent<Boolean> arg0) {
				Boolean oldCheckedValue = cachedBool;
				cachedBool = getValue();
				firePropertyChange(oldCheckedValue, cachedBool);
			}
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.gwt.mosaic.db.client.beansbinding.ext.BeanAdapterProvider#createAdapter
	 * (java.lang.Object, java.lang.String)
	 */
	public BeanAdapter createAdapter(Object source, String property) {
		if (!providesAdapter(source.getClass(), property)) {
			throw new IllegalArgumentException();
		}
		return new Adapter((CheckBox) source);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.gwt.mosaic.db.client.beansbinding.ext.BeanAdapterProvider#getAdapterClass
	 * (java.lang.Class)
	 */
	public Class<?> getAdapterClass(Class<?> type) {
		return (type == CheckBox.class) ? CheckBoxAdapterProvider.Adapter.class
				: null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.gwt.mosaic.db.client.beansbinding.ext.BeanAdapterProvider#providesAdapter
	 * (java.lang.Class, java.lang.String)
	 */
	public boolean providesAdapter(Class<?> type, String property) {
		return (type == CheckBox.class) && property.intern() == VALUE_P;
	}

}
