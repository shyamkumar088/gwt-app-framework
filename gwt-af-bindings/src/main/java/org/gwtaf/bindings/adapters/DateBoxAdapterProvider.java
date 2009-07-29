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

import java.util.Date;

import org.gwt.beansbinding.core.client.ext.BeanAdapter;
import org.gwt.beansbinding.core.client.ext.BeanAdapterProvider;
import org.gwt.beansbinding.ui.client.adapters.BeanAdapterBase;

import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.datepicker.client.DateBox;

/**
 * A {@link BeanAdapterProvider} implementation for use with the {@link DateBox}
 * class
 * 
 * @author Jason Kong
 * @author georgopoulos.georgios(at)gmail.com
 */
public final class DateBoxAdapterProvider implements BeanAdapterProvider {

	/**
	 * The property that will be set/get on the Datebox
	 */
	private static final String VALUE_P = "value";

	/**
	 * The Adapter class that will be used to adapt the {@link DateBox} class
	 * 
	 * @author Jason Kong
	 * 
	 */
	public static final class Adapter extends BeanAdapterBase {
		/**
		 * The {@link Datebox} reference
		 */
		private DateBox dateBox;

		/**
		 * The {@link Handler} for handling data binding firing
		 */
		private Handler handler;

		/**
		 * The {@link DateBox}'s cached value, which will be used to fire
		 * property changes
		 */
		private Date cachedDate;

		/**
		 * The {@link HandlerRegistration} used to keep track of the added
		 * handlers
		 */
		private HandlerRegistration registration;

		/**
		 * Constructs the adapter
		 * 
		 * @param inDateBox
		 */
		private Adapter(DateBox inDateBox) {
			super(VALUE_P);
			this.dateBox = inDateBox;
		}

		/**
		 * This method adapts {@link DateBox}'s getValue method
		 * 
		 * @return the value of the dateBox
		 */
		public Date getValue() {
			return dateBox.getValue();
		}

		/**
		 * This method adapts {@link DateBox}'s setValue method
		 * 
		 * @param value
		 *            the value to set
		 */
		public void setValue(Date date) {
			dateBox.setValue(date, true);
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
			cachedDate = getValue();
			registration = dateBox.addValueChangeHandler(handler);
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
		private class Handler implements ValueChangeHandler<Date> {
			public void onValueChange(ValueChangeEvent<Date> event) {
				Date oldDate = cachedDate;
				cachedDate = getValue();
				firePropertyChange(oldDate, cachedDate);
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
		return new Adapter((DateBox) source);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.gwt.mosaic.db.client.beansbinding.ext.BeanAdapterProvider#getAdapterClass
	 * (java.lang.Class)
	 */
	public Class<?> getAdapterClass(Class<?> type) {
		return (type == DateBox.class) ? DateBoxAdapterProvider.Adapter.class
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
		return (type == DateBox.class) && property.intern() == VALUE_P;
	}

}
