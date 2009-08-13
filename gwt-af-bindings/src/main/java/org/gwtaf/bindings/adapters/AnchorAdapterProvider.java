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

import com.google.gwt.user.client.ui.Anchor;

/**
 * A {@link BeanAdapterProvider} implementation for use with the {@link Anchor}
 * class.
 * 
 * @author Sergey Vesselov
 * @author Jason Kong
 * @author georgopoulos.georgios(at)gmail.com
 * 
 */
public class AnchorAdapterProvider implements BeanAdapterProvider {

	/**
	 * The property that will be set/get on the Anchor
	 */
	private static final String TEXT_P = "text";

	/**
	 * The second property that will be set/get on the Anchor
	 */
	private static final String URL_P = "href";

	/**
	 * The Adapter class that will be used to adapt the {@link Anchor} class
	 * 
	 * @author Sergey Vesselov
	 * @author Jason Kong
	 * 
	 */
	public static final class Adapter extends BeanAdapterBase {
		/**
		 * The {@link Anchor} reference
		 */
		private Anchor anchor;

		/**
		 * Constructs the adapter
		 * 
		 * @param inAnchor
		 */
		private Adapter(Anchor inAnchor, String property) {
			super(property);
			this.anchor = inAnchor;
		}

		/**
		 * This method adapts {@link Anchor}'s getText method
		 * 
		 * @return the value of the Anchor
		 */
		public String getText() {
			return anchor.getText();
		}

		/**
		 * This method adapts {@link Anchor}'s setText method
		 * 
		 * @param value
		 *            the value to set
		 */
		public void setText(String value) {
			anchor.setText(value);
		}

		/**
		 * This method adapts {@link Anchor}'s setHref method
		 * 
		 * @param value
		 *            the URL to set
		 */
		public void setHref(String value) {
			anchor.setHref(value);
		}

		/**
		 * This method adapts {@link Anchor}'s getHref method
		 * 
		 * @return the value of the Anchor
		 */
		public String getHref() {
			return anchor.getHref();
		}
	}

	public BeanAdapter createAdapter(Object source, String property) {
		if (!providesAdapter(source.getClass(), property)) {
			throw new IllegalArgumentException();
		}
		return new Adapter((Anchor) source, property);
	}

	public Class<?> getAdapterClass(Class<?> type) {
		return (type == Anchor.class) ? AnchorAdapterProvider.Adapter.class
				: null;
	}

	public boolean providesAdapter(Class<?> type, String property) {
		return (type == Anchor.class)
				&& (property.intern() == TEXT_P || property.intern() == URL_P);
	}

}
