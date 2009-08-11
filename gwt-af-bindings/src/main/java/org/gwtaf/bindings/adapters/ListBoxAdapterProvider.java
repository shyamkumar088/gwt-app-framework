/*
 * Copyright (C) 2007 Sun Microsystems, Inc. All rights reserved. Use is subject
 * to license terms.
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

import java.util.ArrayList;
import java.util.List;

import org.gwt.beansbinding.core.client.ext.BeanAdapter;
import org.gwt.beansbinding.core.client.ext.BeanAdapterProvider;
import org.gwt.beansbinding.ui.client.adapters.BeanAdapterBase;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.datepicker.client.DateBox;

/**
 * A {@link BeanAdapterProvider} implementation for use with the {@link ListBox}
 * class
 * 
 * @author Jason Kong
 * @author georgopoulos.georgios(at)gmail.com
 */
public class ListBoxAdapterProvider implements BeanAdapterProvider {

	/**
	 * The property string for a single select {@link ListBox}
	 */
	private static final String SELECTED_ELEMENT_P = "selectedElement";

	/**
	 * The property string for a multi select {@link ListBox}
	 */
	private static final String SELECTED_ELEMENTS_P = "selectedElements";

	/**
	 * The Adapter class that will be used to adapt the {@link ListBox} class
	 * 
	 * @author Jason Kong
	 * 
	 */
	public static final class Adapter extends BeanAdapterBase {
		/**
		 * The {@link ListBox} being adapted
		 */
		private ListBox list;

		/**
		 * The {@link Handler} for dealing with events
		 */
		private Handler handler;

		/**
		 * The cached selected element or elements of the {@link ListBox}
		 */
		private Object cachedElementOrElements;

		/**
		 * The {@link HandlerRegistration} for managing the {@link Handler}
		 */
		private HandlerRegistration registration;

		/**
		 * Constructs the adapter using the specified {@link ListBox} for the
		 * given {@code property}
		 * 
		 * @param list
		 * @param property
		 */
		private Adapter(ListBox list, String property) {
			super(property);
			this.list = list;
		}

		/**
		 * Returns whether or not the list is a single or multi select
		 * 
		 * @return whether or not the list is a single or multi select
		 */
		private boolean isPlural() {
			return property == SELECTED_ELEMENTS_P;
		}

		/**
		 * Adapter "get" method for the selectedElement property.
		 * 
		 * @return the selected element of the listbox
		 */
		public Object getSelectedElement() {
			return ListBoxAdapterProvider.getSelectedElement(list);
		}

		/**
		 * Adapter "get" method for the selectedElements property.
		 * 
		 * @return the selected elements of the listbox
		 */
		public List<Object> getSelectedElements() {
			return ListBoxAdapterProvider.getSelectedElements(list);
		}

		/**
		 * Adapter "set" method for the selectedElement property.
		 * 
		 * @param value
		 *            the value to set as the selected element of the
		 *            {@link ListBox}
		 */
		public void setSelectedElement(Object value) {
			ListBoxAdapterProvider.setSelectedElement(list, value);
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
			cachedElementOrElements = isPlural() ? getSelectedElements()
					: getSelectedElement();
			list.addChangeHandler(handler);
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
			cachedElementOrElements = null;
			handler = null;
		}

		private class Handler implements ChangeHandler {

			public void onChange(ChangeEvent arg0) {
				Object oldElementOrElements = cachedElementOrElements;
				cachedElementOrElements = isPlural() ? getSelectedElements()
						: getSelectedElement();
				firePropertyChange(oldElementOrElements,
						cachedElementOrElements);
			}
		}
	}

	/**
	 * Returns the selected elements of the given {@link ListBox}
	 * 
	 * @param list
	 *            the {@link ListBox} to look in
	 * @return the selected elements
	 */
	private static List<Object> getSelectedElements(ListBox list) {
		assert list != null;

		List<Object> elements = new ArrayList<Object>();

		if (list.getSelectedIndex() == -1) {
			return elements;
		}

		for (int i = 0, n = list.getItemCount(); i < n; ++i) {
			if (list.isItemSelected(i)) {
				elements.add(list.getItemText(i));
			}
		}

		return elements;
	}

	/**
	 * Returns the selected element of the given {@link ListBox}
	 * 
	 * @param list
	 *            the {@link ListBox} to look in
	 * @return the selected element
	 */
	private static Object getSelectedElement(ListBox list) {
		assert list != null;

		int index = list.getSelectedIndex();

		if (index == -1) {
			return null;
		}

		return list.getItemText(index);
	}

	private static void setSelectedElement(ListBox list, Object value) {
		// find the value
		int index = -1;
		for (int i = 0; i < list.getItemCount(); i++) {
			if (value.equals(list.getValue(i))) {
				index = i;
			}
		}
		if (index != -1) {
			list.setSelectedIndex(index);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.gwt.beansbinding.core.client.ext.BeanAdapterProvider#createAdapter
	 * (java.lang.Object, java.lang.String)
	 */
	public BeanAdapter createAdapter(Object source, String property) {
		if (!providesAdapter(source.getClass(), property)) {
			throw new IllegalArgumentException();
		}
		return new Adapter((ListBox) source, property);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.gwt.beansbinding.core.client.ext.BeanAdapterProvider#getAdapterClass
	 * (java.lang.Class)
	 */
	public Class<?> getAdapterClass(Class<?> type) {
		return (type == ListBox.class) ? ListBoxAdapterProvider.Adapter.class
				: null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.gwt.beansbinding.core.client.ext.BeanAdapterProvider#providesAdapter
	 * (java.lang.Class, java.lang.String)
	 */
	public boolean providesAdapter(Class<?> type, String property) {
		if (type != ListBox.class) {
			return false;
		}

		property = property.intern();

		return property == SELECTED_ELEMENT_P
				|| property == SELECTED_ELEMENTS_P;
	}

}
