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
import org.gwtaf.widgets.generic.RadioButtonGroup;

import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;

/**
 * A {@link BeanAdapterProvider} implementation for use with the
 * {@link RadioButtonGroup} class
 * 
 * @author Jason Kong
 * @author georgopoulos.georgios(at)gmail.com
 */
public final class RadioButtonGroupAdapterProvider implements
		BeanAdapterProvider {

	/**
	 * The property that will be set/get on the RadioButtonGroup
	 */
	private static final String VALUE_P = "value";

	/**
	 * The actual {@link BeanAdapterBase} adapters returned by this provider.
	 * 
	 * @author jason
	 * 
	 */
	public static final class Adapter extends BeanAdapterBase {
		/**
		 * The {@link RadioButtonGroup} being adapted
		 */
		private RadioButtonGroup radioButtonGroup;

		/**
		 * The {@link Handler} for handling data binding firing
		 */
		private Handler handler;

		/**
		 * The cached value of the {@link RadioButtonGroup}. Used for firing
		 * property changes
		 */
		private String cachedString;

		/**
		 * The {@link HandlerRegistration} used to keep track of the added
		 * handlers
		 */
		private HandlerRegistration registration;

		/**
		 * Constructs the Adapter for the {@link RadioButtonGroup} class
		 * 
		 * @param inRadioButtonGroup
		 *            the {@link RadioButtonGroup} to adapt
		 */
		private Adapter(RadioButtonGroup inRadioButtonGroup) {
			super(VALUE_P);
			this.radioButtonGroup = inRadioButtonGroup;
		}

		public String getValue() {
			return radioButtonGroup.getValue();
		}

		public void setValue(String value) {
			radioButtonGroup.setValue(value, true);
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
			cachedString = getValue();
			registration = radioButtonGroup.addValueChangeHandler(handler);
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
		private class Handler implements ValueChangeHandler<String> {
			public void onValueChange(ValueChangeEvent<String> event) {
				String oldString = cachedString;
				cachedString = getValue();
				firePropertyChange(oldString, cachedString);
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
		return new Adapter((RadioButtonGroup) source);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.gwt.mosaic.db.client.beansbinding.ext.BeanAdapterProvider#getAdapterClass
	 * (java.lang.Class)
	 */
	public Class<?> getAdapterClass(Class<?> type) {
		return (type == RadioButtonGroup.class) ? RadioButtonGroupAdapterProvider.Adapter.class
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
		return (type == RadioButtonGroup.class) && property.intern() == VALUE_P;
	}

}
