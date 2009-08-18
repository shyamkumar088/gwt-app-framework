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

import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.RichTextArea;

/**
 * A {@link BeanAdapterProvider} implementation for use with the
 * {@link RichTextArea} class.
 * 
 * @author Sergey Vesselov
 * @author Jason Kong
 * @author georgopoulos.georgios(at)gmail.com
 */
public class RichTextAreaAdapterProvider implements BeanAdapterProvider {

	/**
	 * The property that will be set/get on the RichTextArea
	 */
	private static final String HTML_P = "HTML";

	/**
	 * The Adapter class that will be used to adapt the {@link RichTextArea}
	 * class
	 * 
	 * @author Sergey Vesselov
	 * @author Jason Kong
	 * 
	 */
	public static final class Adapter extends BeanAdapterBase {
		/**
		 * The {@link RichTextArea} reference
		 */
		private RichTextArea area;

		/**
		 * The {@link Handler} for handling data binding firing
		 */
		private Handler handler;

		/**
		 * The {@link RichTextArea}'s cached value, which will be used to fire
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
		 * @param inRichTextArea
		 */
		private Adapter(RichTextArea inRichTextArea) {
			super(HTML_P);
			this.area = inRichTextArea;
		}

		/**
		 * This method adapts {@link RichTextArea}'s getText method
		 * 
		 * @return the value of the RichTextArea
		 */
		public String getHTML() {
			return area.getHTML();
		}

		/**
		 * This method adapts {@link RichTextArea}'s setText method
		 * 
		 * @param value
		 *            the value to set
		 */
		public void setHTML(String value) {
			area.setHTML(value);
		}

		@Override
		protected void listeningStarted() {
			handler = new Handler();
			cachedText = getHTML();
			registration = area.addBlurHandler(handler);
		}

		@Override
		protected void listeningStopped() {
			registration.removeHandler();
			handler = null;
		}

		/**
		 * A {@link BlurHandler} implementation for firing off property changes
		 * 
		 * @author Sergey Vesselov
		 * 
		 */
		private class Handler implements BlurHandler {
			public void onBlur(BlurEvent arg0) {
				String oldText = cachedText;
				cachedText = getHTML();
				firePropertyChange(oldText, cachedText);
			}
		}

	}

	public BeanAdapter createAdapter(Object source, String property) {
		if (!providesAdapter(source.getClass(), property)) {
			throw new IllegalArgumentException();
		}
		return new Adapter((RichTextArea) source);
	}

	public Class<?> getAdapterClass(Class<?> type) {
		return (type == RichTextArea.class) ? RichTextAreaAdapterProvider.Adapter.class
				: null;
	}

	public boolean providesAdapter(Class<?> type, String property) {
		return (type == RichTextArea.class) && property.intern() == HTML_P;
	}
}
